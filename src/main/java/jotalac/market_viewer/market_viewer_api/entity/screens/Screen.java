package jotalac.market_viewer.market_viewer_api.entity.screens;

import jakarta.persistence.*;
import jotalac.market_viewer.market_viewer_api.entity.AbstractEntity;
import jotalac.market_viewer.market_viewer_api.entity.Device;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "SCREEN_TYPE")
@NoArgsConstructor
public abstract class Screen extends AbstractEntity {

    public abstract ScreenType getScreenType();

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Device device;

    @Setter
    @Getter
    @Column(nullable = false)
    protected Integer position;

}
