package jotalac.market_viewer.market_viewer_app.entity.screens;

import java.time.LocalDateTime;

public interface UpdatableScreen {
    LocalDateTime getLastUpdateTime();
    boolean needsUpdate();

}
