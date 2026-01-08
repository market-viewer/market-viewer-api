package jotalac.market_viewer.market_viewer_app.entity.screens;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jotalac.market_viewer.market_viewer_app.entity.Device;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("STOCK")
@Getter
@Setter
@NoArgsConstructor
public class StockScreen extends Screen{

    @Column(nullable = false)
    @NotBlank
    private String assetName = "APPL";

    @Column(nullable = false)
    @NotBlank
    private String timeFrame = "7D";

    @Column(nullable = false)
    @NotBlank
    private String currency = "USD";

    @Column(nullable = false)
    private Boolean displayGraph = true;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GraphType  graphType = GraphType.LINE;

    @Column(nullable = false)
    private Boolean simpleDisplay = false;

    @Column(nullable = false)
    @Positive
    private Integer fetchInterval = 10; // minutes

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "price_data_id")
    private StockPriceData priceData = new StockPriceData();

    @Override
    public ScreenType getScreenType() {
        return ScreenType.STOCK;
    }

}
