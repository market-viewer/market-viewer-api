package jotalac.market_viewer.market_viewer_app.entity;

import jakarta.persistence.*;
import jotalac.market_viewer.market_viewer_app.util.ApiKeyConvertor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "endpoint"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiKey extends AbstractEntity{

    @Convert(converter = ApiKeyConvertor.class)
    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApiKeyProvider endpoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
