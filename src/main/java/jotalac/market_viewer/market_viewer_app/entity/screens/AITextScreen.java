package jotalac.market_viewer.market_viewer_app.entity.screens;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jotalac.market_viewer.market_viewer_app.config.Constants.DEFAULT_BG_COLOR;


@Entity
@DiscriminatorValue("AI_TEXT")
@NoArgsConstructor
@Getter
@Setter
public class AITextScreen extends Screen implements UpdatableScreen{

    @Column(nullable = false)
    private String prompt = "Tell me today's news about bitcoin";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String displayText = "";

    @Column(nullable = false)
    private String bgColor = DEFAULT_BG_COLOR;

    // fetch interval in hours
    @Column(nullable = false)
    @Positive
    private Integer fetchIntervalHours = 5;

    @Column
    private LocalDateTime lastFetchTime;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.AI_TEXT;
    }

    @Override
    public LocalDateTime getLastUpdateTime() {
        return lastFetchTime;
    }

    @Override
    public boolean needsUpdate() {
        if (displayText.isEmpty() || lastFetchTime == null) return true;

        return lastFetchTime.plusHours(fetchIntervalHours).isBefore(LocalDateTime.now());
    }
}
