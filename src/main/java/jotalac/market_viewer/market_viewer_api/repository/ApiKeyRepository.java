package jotalac.market_viewer.market_viewer_api.repository;

import jotalac.market_viewer.market_viewer_api.entity.ApiKey;
import jotalac.market_viewer.market_viewer_api.entity.ApiKeyProvider;
import jotalac.market_viewer.market_viewer_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    Optional<ApiKey> findByEndpointAndUser(ApiKeyProvider endpoint, User user);
    Boolean existsByEndpointAndUser(ApiKeyProvider endpoint, User user);

    List<ApiKey> findByUser(User user);
}
