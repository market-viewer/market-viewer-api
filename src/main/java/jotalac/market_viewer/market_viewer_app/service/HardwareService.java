package jotalac.market_viewer.market_viewer_app.service;

import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenDto;
import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenDtoMapper;
import jotalac.market_viewer.market_viewer_app.dto.screen_data.ScreenDataDto;
import jotalac.market_viewer.market_viewer_app.dto.screen_data.ScreenDataDtoMapper;
import jotalac.market_viewer.market_viewer_app.entity.Device;
import jotalac.market_viewer.market_viewer_app.entity.screens.*;
import jotalac.market_viewer.market_viewer_app.exception.NotFoundException;
import jotalac.market_viewer.market_viewer_app.repository.DeviceRepository;
import jotalac.market_viewer.market_viewer_app.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jotalac.market_viewer.market_viewer_app.config.Constants.PRICE_DATA_LIFETIME_MINUTES;

@Service
@RequiredArgsConstructor
public class HardwareService {

    private final DeviceRepository deviceRepository;
    private final ScreenRepository screenRepository;
    private final ScreenDtoMapper screenDtoMapper;
    private final ScreenDataDtoMapper screenDataDtoMapper;
    private final ScreenUpdateService screenUpdateService;

    private Device getDeviceFromHash(UUID deviceHash) {
        return deviceRepository.findByDeviceHash(deviceHash)
                .orElseThrow(() -> new NotFoundException("Device with hash " + deviceHash + " not found"));
    }

    private Boolean shouldFetchNewData(LocalDateTime lastFetchTime) {
        if (lastFetchTime == null) {
            return true;
        }
        return lastFetchTime.plusMinutes(PRICE_DATA_LIFETIME_MINUTES).isBefore(LocalDateTime.now());
    }


    public List<ScreenDto> getScreensForDevice(UUID deviceHash) {
        Device device = getDeviceFromHash(deviceHash);

        List<Screen> screens = screenRepository.getScreensByDevice(device);
        return screenDtoMapper.toDtos(screens);
    }

    public ScreenDataDto getScreenData(UUID deviceHash, Integer screenPosition) {
        Device device = getDeviceFromHash(deviceHash);

        Screen screen = screenRepository.findByDeviceAndPosition(device, screenPosition).orElseThrow(() -> new NotFoundException("Screen at position " + screenPosition + " not found"));

        if (screen instanceof AITextScreen aiTextScreen) {
            if (aiTextScreen.getDisplayText().isEmpty()) {
                screenUpdateService.updateAiTextScreen();
            }
            return screenDataDtoMapper.toAITextDto(aiTextScreen);
        }
        if (screen instanceof CryptoScreen cryptoScreen) {
            if (shouldFetchNewData(cryptoScreen.getPriceData().getFetchTime())) {
                screenUpdateService.updateCryptoScreen();
            }
            return screenDataDtoMapper.toCryptoDto(cryptoScreen.getPriceData());
        }
        if (screen instanceof StockScreen stockScreen) {
            if (shouldFetchNewData(stockScreen.getPriceData().getFetchTime())) {
                screenUpdateService.updateStockScreen();
            }
            return screenDataDtoMapper.toStockDto(stockScreen.getPriceData());
        }

        throw new IllegalArgumentException("Screen type not supported for data fetch");

    }


}
