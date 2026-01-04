package jotalac.market_viewer.market_viewer_app.dto.screen.update.mapper;

import jotalac.market_viewer.market_viewer_app.dto.screen.update.AITextScreenUpdateRequest;
import jotalac.market_viewer.market_viewer_app.entity.screens.AITextScreen;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AITextScreenUpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(AITextScreenUpdateRequest dto, @MappingTarget AITextScreen entity);
}
