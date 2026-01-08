package jotalac.market_viewer.market_viewer_app.dto.screen_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "screenType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AITextScreenDataDto.class, name = "AI_TEXT"),
        @JsonSubTypes.Type(value = CryptoScreenDataDto.class, name = "CRYPTO"),
        @JsonSubTypes.Type(value = StockScreenDataDto.class, name = "STOCK")
})
@Getter
@Setter
public abstract class ScreenDataDto {

    public abstract ScreenType getScreenType();
}
