package jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jotalac.market_viewer.market_viewer_app.entity.screens.GraphType;
import jotalac.market_viewer.market_viewer_app.entity.screens.Screen;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CRYPTO")
@Getter
@Setter
@NoArgsConstructor
public class CryptoScreen extends Screen {

    // list of all asset names - /coins/list
    @Column(nullable = false)
    @NotBlank
    private String assetName = "bitcoin";

    @Column(nullable = false)
    @NotBlank
    private CryptoTimeFrame timeFrame = CryptoTimeFrame.DAY;

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

}
