package jotalac.market_viewer.market_viewer_app.entity.screens;

import jakarta.persistence.*;
import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenDto;
import jotalac.market_viewer.market_viewer_app.dto.screen.update.ScreenUpdateRequest;
import jotalac.market_viewer.market_viewer_app.entity.AbstractEntity;
import jotalac.market_viewer.market_viewer_app.entity.Device;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "SCREEN_TYPE")
@NoArgsConstructor
public abstract class Screen extends AbstractEntity {

    public abstract ScreenType getScreenType();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    protected Device device;

    @Setter
    @Getter
    @Column(nullable = false)
    protected Integer position;

}
