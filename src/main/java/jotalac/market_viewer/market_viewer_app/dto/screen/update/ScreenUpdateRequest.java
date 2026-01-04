package jotalac.market_viewer.market_viewer_app.dto.screen.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jotalac.market_viewer.market_viewer_app.entity.screens.AITextScreen;
import jotalac.market_viewer.market_viewer_app.entity.screens.ClockScreen;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AITextScreen.class, name = "AI_TEXT"),
        @JsonSubTypes.Type(value = ClockScreen.class, name = "CLOCK"),
        @JsonSubTypes.Type(value = ClockScreen.class, name = "CRYPTO"),
        @JsonSubTypes.Type(value = ClockScreen.class, name = "STOCK")
})
@Setter
@Getter
public abstract class ScreenUpdateRequest {
}
