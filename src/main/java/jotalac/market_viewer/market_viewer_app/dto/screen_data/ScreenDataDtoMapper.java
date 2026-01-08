package jotalac.market_viewer.market_viewer_app.dto.screen_data;

import jotalac.market_viewer.market_viewer_app.entity.screens.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ScreenDataDtoMapper {

//    default ScreenDataDto toDto(Object object) {
//        if (object instanceof AITextScreen s) return toAITextDto(s);
//        if (object instanceof CryptoPriceData priceData) return toCryptoDto(priceData);
//        if (object instanceof StockPriceData priceData) return toStockDto(priceData);
//        return null;
//    }

    AITextScreenDataDto toAITextDto(AITextScreen screen);
    CryptoScreenDataDto toCryptoDto(CryptoPriceData priceData);
    StockScreenDataDto toStockDto(StockPriceData priceData);

}
