package jotalac.market_viewer.market_viewer_api.repository;

import jotalac.market_viewer.market_viewer_api.entity.RecoveryCode;
import jotalac.market_viewer.market_viewer_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecoveryCodeRepository extends JpaRepository<RecoveryCode, String> {
    List<RecoveryCode> findByOwner(User user);
}
