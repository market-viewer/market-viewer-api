package jotalac.market_viewer.market_viewer_app.entity.screens.clock_screen;


import jakarta.persistence.*;
import jotalac.market_viewer.market_viewer_app.entity.screens.Screen;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jotalac.market_viewer.market_viewer_app.config.Constants.DEFAULT_BG_COLOR;

@Entity
@DiscriminatorValue("CLOCK")
@Getter
@Setter
@NoArgsConstructor
public class ClockScreen extends Screen {

    @Column(nullable = false)
    private String timezone = "Europe/London";

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClockType clockType = ClockType.ANALOG;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeFormat timeFormat = TimeFormat.TWENTY_FOUR_HOUR;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.CLOCK;
    }
}
