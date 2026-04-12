package jotalac.market_viewer.market_viewer_api.entity.screens;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@DiscriminatorValue("TIMER")
@Getter
@Setter
@NoArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn
public class TimerScreen extends Screen{

    @Column(nullable = false)
    @NotBlank
    private String name = "Timer";

    @Override
    public ScreenType getScreenType() {
        return ScreenType.TIMER;
    }

}
