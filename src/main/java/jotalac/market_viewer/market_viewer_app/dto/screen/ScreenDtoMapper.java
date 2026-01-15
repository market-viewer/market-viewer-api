package jotalac.market_viewer.market_viewer_app.dto.screen;

import jotalac.market_viewer.market_viewer_app.entity.screens.*;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoPriceData;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoScreen;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ScreenDtoMapper {

    default ScreenDto toDto(Screen screen) {
        if (screen instanceof AITextScreen s) return toAITextDto(s);
        if (screen instanceof ClockScreen s) return toClockDto(s);
        if (screen instanceof CryptoScreen s) return toCryptoDto(s);
        if (screen instanceof StockScreen s) return toStockDto(s);
        return null;
    }

    List<ScreenDto> toDtos(List<Screen> screens);

    AITextScreenDto toAITextDto(AITextScreen screen);
    ClockScreenDto toClockDto(ClockScreen screen);
    CryptoScreenDto toCryptoDto(CryptoScreen screen);
    StockScreenDto toStockDto(StockScreen screen);

    default void updateEntityFromDto(ScreenDto dto, @MappingTarget Screen entity) {
        if (dto instanceof AITextScreenDto d && entity instanceof AITextScreen e) {
            updateAIText(d, e);
        } else if (dto instanceof ClockScreenDto d && entity instanceof ClockScreen e) {
            updateClock(d, e);
        } else if (dto instanceof CryptoScreenDto d && entity instanceof CryptoScreen e) {
            updateCrypto(d, e);
            //on update reset the data
            e.setPriceData(new CryptoPriceData());
        } else if (dto instanceof StockScreenDto d && entity instanceof StockScreen e) {
            updateStock(d, e);
            //on update reset the data
            e.setPriceData(new StockPriceData());

        } else {
            throw new IllegalArgumentException("Screen update data do not match screen type");
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping (target = "displayText", ignore = true)
    void updateAIText(AITextScreenDto dto, @MappingTarget AITextScreen entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "position", ignore = true)
    void updateClock(ClockScreenDto dto, @MappingTarget ClockScreen entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "priceData", ignore = true)     // Read-only field
    void updateCrypto(CryptoScreenDto dto, @MappingTarget CryptoScreen entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "priceData", ignore = true)     // Read-only field
    void updateStock(StockScreenDto dto, @MappingTarget StockScreen entity);

    default Screen toEntity(ScreenDto dto) {
        if (dto instanceof AITextScreenDto d) return toAITextEntity(d);
        if (dto instanceof ClockScreenDto d) return toClockEntity(d);
        if (dto instanceof CryptoScreenDto d) return toCryptoEntity(d);
        if (dto instanceof StockScreenDto d) return toStockEntity(d);
        throw new IllegalArgumentException("Unknown Screen DTO type: " + dto.getClass().getName());
    }

    default AITextScreen toAITextEntity(AITextScreenDto dto) {
        AITextScreen entity = new AITextScreen();
        updateAIText(dto, entity);
        return entity;
    }

    default ClockScreen toClockEntity(ClockScreenDto dto) {
        ClockScreen entity = new ClockScreen();
        updateClock(dto, entity);
        return entity;
    }

    default CryptoScreen toCryptoEntity(CryptoScreenDto dto) {
        CryptoScreen entity = new CryptoScreen();
        updateCrypto(dto, entity);
        return entity;
    }

    default StockScreen toStockEntity(StockScreenDto dto) {
        StockScreen entity = new StockScreen();
        updateStock(dto, entity);
        return entity;
    }
}
