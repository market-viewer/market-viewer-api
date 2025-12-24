package jotalac.market_viewer.market_viewer_app.entity.screens;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jotalac.market_viewer.market_viewer_app.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CryptoPriceData extends AbstractEntity {
    @Column
    private Double price;

    @Column
    private Double priceChange;

    @Column
    private Double allTimeHigh;

    @Column
    private Double allTimeHighChange;

    @Column
    private List<Double> graphData;
}
