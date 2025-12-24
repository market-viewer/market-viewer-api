package jotalac.market_viewer.market_viewer_app.entity.screens;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jotalac.market_viewer.market_viewer_app.entity.Device;
import lombok.NoArgsConstructor;

import static jotalac.market_viewer.market_viewer_app.config.ValidationConstants.DEFAULT_BG_COLOR;


@Entity
@DiscriminatorValue("AI_TEXT")
@NoArgsConstructor
public class AITextScreen extends Screen{

    @Column(nullable = false)
    private String prompt = "";

    @Column(nullable = false)
    private String displayTex = "";

    @Column(nullable = false)
    private String bgColor = DEFAULT_BG_COLOR;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.AI_TEXT;
    }
}
