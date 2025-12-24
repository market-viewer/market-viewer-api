package jotalac.market_viewer.market_viewer_app.repository;

import jotalac.market_viewer.market_viewer_app.entity.Device;
import jotalac.market_viewer.market_viewer_app.entity.screens.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {
    Integer countScreensByDevice(Device device);
}
