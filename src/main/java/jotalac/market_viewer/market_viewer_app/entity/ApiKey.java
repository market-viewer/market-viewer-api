package jotalac.market_viewer.market_viewer_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "endpoint"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiKey extends AbstractEntity{

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApiKeyEndpoint endpoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;
}
