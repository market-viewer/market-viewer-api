package jotalac.market_viewer.market_viewer_app.entity.screens;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CRYPTO")
@Getter
@Setter
@NoArgsConstructor
public class CryptoScreen extends Screen{

    @Column(nullable = false)
    @NotBlank
    private String assetName = "BTC";

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
