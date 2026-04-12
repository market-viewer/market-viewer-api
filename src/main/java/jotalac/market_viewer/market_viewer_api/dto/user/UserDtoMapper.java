package jotalac.market_viewer.market_viewer_api.dto.user;

import jotalac.market_viewer.market_viewer_api.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDto toDto(User user);
//
//    @Mapping(target = "password", ignore = true)
//    User toEntity(UserDto userDto);
}
