package jotalac.market_viewer.market_viewer_api.dto.api_key;

import jotalac.market_viewer.market_viewer_api.entity.ApiKey;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ApiKeyDtoMapper {
    ApiKeyDto toDto(ApiKey apiKey);

    List<ApiKeyDto> toDtoList(List<ApiKey> apiKeyList);
}
