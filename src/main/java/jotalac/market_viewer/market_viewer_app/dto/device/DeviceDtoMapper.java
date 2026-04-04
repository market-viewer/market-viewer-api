package jotalac.market_viewer.market_viewer_app.dto.device;

import jotalac.market_viewer.market_viewer_app.entity.Device;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface DeviceDtoMapper {
    DeviceDto toDto(Device device);

    List<DeviceDto> toDtoList(List<Device> devices);
}
