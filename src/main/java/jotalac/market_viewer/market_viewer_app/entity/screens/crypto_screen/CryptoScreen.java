package jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jotalac.market_viewer.market_viewer_app.entity.screens.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

import static jotalac.market_viewer.market_viewer_app.config.Constants.PRICE_DATA_LIFETIME_MINUTES;

@Entity
@DiscriminatorValue("CRYPTO")
@Getter
@Setter
@NoArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn
public class CryptoScreen extends Screen implements UpdatableScreen {

    // list of all asset names - /coins/list
    @Column(nullable = false)
    @NotBlank
    private String assetName = "bitcoin";

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetTimeFrame timeFrame = AssetTimeFrame.DAY;

    // list all vs_currencies - /simple/supported_vs_currencies
    @Column(nullable = false)
    @NotBlank
    private String currency = "USD";

    @Column(nullable = false)
    private Boolean displayGraph = true;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GraphType graphType = GraphType.LINE;

    @Column(nullable = false)
    private Boolean simpleDisplay = false;

    // fetch interval in minutes
    @Column(nullable = false)
    @Positive
    private Integer fetchInterval = 10;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    @JoinColumn(name = "price_data_id", unique = true)
    private CryptoPriceData priceData = new CryptoPriceData();

    @Override
    public ScreenType getScreenType() {
        return ScreenType.CRYPTO;
    }

    @Override
    public LocalDateTime getLastUpdateTime() {
        return priceData.getFetchTimePrice();
    }

    @Override
    public boolean needsUpdate() {
        if (priceData.getFetchTimePrice() == null) {
            return true;
        }

        return priceData.getFetchTimePrice().plusMinutes(PRICE_DATA_LIFETIME_MINUTES).isBefore(LocalDateTime.now());
    }
}
