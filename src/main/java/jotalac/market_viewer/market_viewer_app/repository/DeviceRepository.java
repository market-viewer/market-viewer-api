package jotalac.market_viewer.market_viewer_app.repository;

import jotalac.market_viewer.market_viewer_app.dto.device.DeviceDto;
import jotalac.market_viewer.market_viewer_app.entity.Device;
import jotalac.market_viewer.market_viewer_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    boolean existsByUserIdAndName(Integer userId, String name);
    Optional<Device> findByDeviceHash(UUID deviceHash);
    Optional<Device> findByIdAndUser(Integer id, User user);
    List<Device> findByUser(User user);

    boolean existsByIdAndUser(Integer id, User user);

    @Query(" SELECT new jotalac.market_viewer.market_viewer_app.dto.device.DeviceDto(d.id, d.name, COUNT(s)) " +
            "FROM Device d " +
            "LEFT JOIN Screen s ON d.id = s.device.id " +
            "WHERE d.user = :user " +
            "GROUP BY d.id, d.name")
    List<DeviceDto> findDeviceDtosByUser(@Param("user") User user);

}
