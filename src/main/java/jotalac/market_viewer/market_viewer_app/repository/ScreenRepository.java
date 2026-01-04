package jotalac.market_viewer.market_viewer_app.repository;

import jotalac.market_viewer.market_viewer_app.entity.Device;
import jotalac.market_viewer.market_viewer_app.entity.screens.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {
    Integer countScreensByDevice(Device device);

    List<Screen> getScreensByDevice(Device device);

    @Modifying
    @Query("UPDATE Screen s SET s.position = s.position - 1 WHERE s.device.id = :deviceId AND s.position > :deletedPosition")
    void changeIndicesAfterDelete(@Param("deviceId") Integer deviceId, @Param("deletedPosition") Integer deletedPosition);
}
