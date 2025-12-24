package jotalac.market_viewer.market_viewer_app.repository;

import jotalac.market_viewer.market_viewer_app.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    boolean existsByUserIdAndName(Integer userId, String name);


}
