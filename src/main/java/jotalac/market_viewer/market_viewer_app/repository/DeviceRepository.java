package jotalac.market_viewer.market_viewer_app.repository;

import jotalac.market_viewer.market_viewer_app.entity.Device;
import jotalac.market_viewer.market_viewer_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    boolean existsByUserIdAndName(Integer userId, String name);
    Optional<Device> findByDeviceHash(UUID deviceHash);
    Optional<Device> findByIdAndUser(Integer id, User user);

    boolean existsByIdAndUser(Integer id, User user);

}
