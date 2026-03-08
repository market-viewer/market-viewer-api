package jotalac.market_viewer.market_viewer_app.entity.screens.stock_screen;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jotalac.market_viewer.market_viewer_app.entity.screens.GraphType;
import jotalac.market_viewer.market_viewer_app.entity.screens.Screen;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import jotalac.market_viewer.market_viewer_app.entity.screens.UpdatableScreen;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

import static jotalac.market_viewer.market_viewer_app.config.Constants.PRICE_DATA_LIFETIME_MINUTES;

@Entity
@DiscriminatorValue("STOCK")
@Getter
@Setter
@NoArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn
public class StockScreen extends Screen implements UpdatableScreen {

    @Column(nullable = false)
    @NotBlank
    private String symbol = "AAPL";

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StockTimeFrame timeFrame = StockTimeFrame.DAY;

    @Column(nullable = false)
    private Boolean displayGraph = true;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GraphType graphType = GraphType.LINE;

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

    @Override
    public LocalDateTime getLastUpdateTime() {
        return priceData.getLastFetchTime();
    }

    @Override
    public boolean needsUpdate() {
        if (priceData.getLastFetchTime() == null) return true;

        return priceData.getLastFetchTime().plusMinutes(PRICE_DATA_LIFETIME_MINUTES).isBefore(LocalDateTime.now());
    }
}
