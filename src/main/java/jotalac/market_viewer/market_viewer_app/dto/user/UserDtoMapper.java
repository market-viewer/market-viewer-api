package jotalac.market_viewer.market_viewer_app.dto.user;

import jotalac.market_viewer.market_viewer_app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDto toDto(User user);
//
//    @Mapping(target = "password", ignore = true)
//    User toEntity(UserDto userDto);
}
