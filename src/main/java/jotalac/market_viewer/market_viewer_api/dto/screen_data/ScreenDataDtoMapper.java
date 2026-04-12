package jotalac.market_viewer.market_viewer_api.dto.screen_data;

import jotalac.market_viewer.market_viewer_api.entity.screens.*;
import jotalac.market_viewer.market_viewer_api.entity.screens.crypto_screen.CryptoPriceData;
import jotalac.market_viewer.market_viewer_api.entity.screens.stock_screen.StockPriceData;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ScreenDataDtoMapper {

    AITextScreenDataDto toAITextDto(AITextScreen screen);
    CryptoScreenDataDto toCryptoDto(CryptoPriceData priceData);
    StockScreenDataDto toStockDto(StockPriceData priceData);

}
