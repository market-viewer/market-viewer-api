package jotalac.market_viewer.market_viewer_app.dto.user;

import jotalac.market_viewer.market_viewer_app.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
