package jotalac.market_viewer.market_viewer_api.dto.screen;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jotalac.market_viewer.market_viewer_api.entity.screens.*;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "screenType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AITextScreenDto.class, name = "AI_TEXT"),
        @JsonSubTypes.Type(value = ClockScreenDto.class, name = "CLOCK"),
        @JsonSubTypes.Type(value = CryptoScreenDto.class, name = "CRYPTO"),
        @JsonSubTypes.Type(value = StockScreenDto.class, name = "STOCK"),
        @JsonSubTypes.Type(value = TimerScreenDto.class, name = "TIMER")
})
@Getter
@Setter
public abstract class ScreenDto {

    //server sends this to the client, but doesnt need it when it gets it from the client
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Integer id;

    protected Integer position;

    public abstract ScreenType getScreenType();
}
