package jotalac.market_viewer.market_viewer_app.dto.screen.update.mapper;

import jotalac.market_viewer.market_viewer_app.dto.screen.update.CryptoScreenUpdateRequest;
import jotalac.market_viewer.market_viewer_app.entity.screens.CryptoScreen;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CryptoScreenUpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastFetchTime", ignore = true)
    @Mapping(target = "priceData", ignore = true)
    void updateEntityFromDto(CryptoScreenUpdateRequest dto, @MappingTarget CryptoScreen entity);
}
