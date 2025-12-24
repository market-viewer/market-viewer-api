package jotalac.market_viewer.market_viewer_app.repository;

import jotalac.market_viewer.market_viewer_app.entity.Device;
import jotalac.market_viewer.market_viewer_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
