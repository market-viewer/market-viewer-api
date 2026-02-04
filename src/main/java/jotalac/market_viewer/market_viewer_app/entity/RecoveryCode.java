package jotalac.market_viewer.market_viewer_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryCode extends AbstractEntity {

    @ManyToOne(optional = false)
    private User owner;

    @Column(nullable = false)
    private String hashedCode;

    @Column(nullable = false)
    private boolean isUsed = false;
}
