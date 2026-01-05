package jotalac.market_viewer.market_viewer_app.entity.screens;


import jakarta.persistence.*;
import jotalac.market_viewer.market_viewer_app.entity.Device;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jotalac.market_viewer.market_viewer_app.config.ValidationConstants.DEFAULT_BG_COLOR;

@Entity
@DiscriminatorValue("CLOCK")
@Getter
@Setter
@NoArgsConstructor
public class ClockScreen extends Screen {

    @Column(nullable = false)
    private String timeZone = "Europe/London";

    @Column(nullable = false)
    private String bgColor = DEFAULT_BG_COLOR;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeFormat timeFormat = TimeFormat.TWENTY_FOUR_HOUR;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.CLOCK;
    }
}
