package jotalac.market_viewer.market_viewer_api.service;

import jotalac.market_viewer.market_viewer_api.dto.screen.ScreenDto;
import jotalac.market_viewer.market_viewer_api.dto.screen.ScreenDtoMapper;
import jotalac.market_viewer.market_viewer_api.dto.screen_data.ScreenDataDto;
import jotalac.market_viewer.market_viewer_api.dto.screen_data.ScreenDataDtoMapper;
import jotalac.market_viewer.market_viewer_api.entity.Device;
import jotalac.market_viewer.market_viewer_api.entity.screens.*;
import jotalac.market_viewer.market_viewer_api.entity.screens.crypto_screen.CryptoScreen;
import jotalac.market_viewer.market_viewer_api.entity.screens.stock_screen.StockScreen;
import jotalac.market_viewer.market_viewer_api.exception.NotFoundException;
import jotalac.market_viewer.market_viewer_api.repository.DeviceRepository;
import jotalac.market_viewer.market_viewer_api.repository.ScreenRepository;
import jotalac.market_viewer.market_viewer_api.service.provider.AIGenerationProvider;
import jotalac.market_viewer.market_viewer_api.service.screen_refresh_service.AIScreenRefreshService;
import jotalac.market_viewer.market_viewer_api.service.screen_refresh_service.CryptoScreenRefreshService;
import jotalac.market_viewer.market_viewer_api.service.screen_refresh_service.StockScreenRefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HardwareService {

    private final DeviceRepository deviceRepository;
    private final ScreenRepository screenRepository;
    private final ScreenDtoMapper screenDtoMapper;
    private final ScreenDataDtoMapper screenDataDtoMapper;
    private final CryptoScreenRefreshService cryptoScreenRefreshService;
    private final StockScreenRefreshService stockScreenRefreshService;
    private final AIGenerationProvider aIGenerationProvider;
    private final AIScreenRefreshService aIScreenRefreshService;

    private Device getDeviceFromHash(UUID deviceHash) {
        return deviceRepository.findByDeviceHash(deviceHash)
                .orElseThrow(() -> new NotFoundException("Device with hash " + deviceHash + " not found"));
    }

    public List<ScreenDto> getScreensForDevice(UUID deviceHash) {
        Device device = getDeviceFromHash(deviceHash);

        List<Screen> screens = screenRepository.getScreensByDevice(device);
        return screenDtoMapper.toDtos(screens);
    }

    public ScreenDto getSingleScreenForDevice(UUID deviceHash, Integer position) {
        Device device = getDeviceFromHash(deviceHash);

        Screen screen = screenRepository.findByDeviceAndPosition(device, position)
                .orElseThrow(() -> new NotFoundException("Screen at position " + position + " doesnt exist"));

        return screenDtoMapper.toDto(screen);
    }

    public ScreenDataDto getScreenData(UUID deviceHash, Integer screenPosition) {
        Device device = getDeviceFromHash(deviceHash);

        Screen screen = screenRepository.findByDeviceAndPosition(device, screenPosition).orElseThrow(() -> new NotFoundException("Screen at position " + screenPosition + " not found"));

        if (screen instanceof AITextScreen aiTextScreen) {
            if (aiTextScreen.needsUpdate()) {
                aIScreenRefreshService.refreshAiScreen(aiTextScreen);
                screenRepository.save(aiTextScreen);
            }

            return screenDataDtoMapper.toAITextDto(aiTextScreen);
        }
        if (screen instanceof CryptoScreen cryptoScreen) {
            if (cryptoScreen.needsUpdate()) {
                cryptoScreenRefreshService.refreshCryptoScreen(cryptoScreen);
                screenRepository.save(cryptoScreen);
            }
            return screenDataDtoMapper.toCryptoDto(cryptoScreen.getPriceData());
        }
        if (screen instanceof StockScreen stockScreen) {
            if (stockScreen.needsUpdate()) {
                stockScreenRefreshService.refreshStockScreen(stockScreen);
                screenRepository.save(stockScreen);
            }

            return screenDataDtoMapper.toStockDto(stockScreen.getPriceData());
        }

        throw new IllegalArgumentException("Screen type not supported for data fetch");

    }


}
