package jotalac.market_viewer.market_viewer_app.entity.screens;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jotalac.market_viewer.market_viewer_app.entity.AbstractEntity;

@Entity
public class StockPriceData extends AbstractEntity {

    @Column
    private Double price;

    @Column
    private Double priceChange;

    @Column
    private Double openPrice;

    @Column
    private Double closePrice;
}
