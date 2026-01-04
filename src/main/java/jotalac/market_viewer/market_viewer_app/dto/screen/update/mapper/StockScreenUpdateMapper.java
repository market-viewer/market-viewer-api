package jotalac.market_viewer.market_viewer_app.dto.screen.update.mapper;

import jotalac.market_viewer.market_viewer_app.dto.screen.update.AITextScreenUpdateRequest;
import jotalac.market_viewer.market_viewer_app.dto.screen.update.StockScreenUpdateRequest;
import jotalac.market_viewer.market_viewer_app.entity.screens.AITextScreen;
import jotalac.market_viewer.market_viewer_app.entity.screens.StockScreen;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StockScreenUpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(StockScreenUpdateRequest dto, @MappingTarget StockScreen entity);
}
