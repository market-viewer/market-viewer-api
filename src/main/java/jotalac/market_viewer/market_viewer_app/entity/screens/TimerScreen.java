package jotalac.market_viewer.market_viewer_app.entity.screens;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("TIMER")
@Getter
@Setter
@NoArgsConstructor
public class TimerScreen extends Screen{

    @Column(nullable = false)
    @NotBlank
    private String name = "Timer";

    @Override
    public ScreenType getScreenType() {
        return ScreenType.TIMER;
    }

}
