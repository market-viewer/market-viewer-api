package jotalac.market_viewer.market_viewer_app.repository;

import jotalac.market_viewer.market_viewer_app.entity.ApiKey;
import jotalac.market_viewer.market_viewer_app.entity.ApiKeyEndpoint;
import jotalac.market_viewer.market_viewer_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    Optional<ApiKey> findByEndpointAndUser(ApiKeyEndpoint endpoint, User user);
    Boolean existsByEndpointAndUser(ApiKeyEndpoint endpoint, User user);

    List<ApiKey> findByUser(User user);
}
