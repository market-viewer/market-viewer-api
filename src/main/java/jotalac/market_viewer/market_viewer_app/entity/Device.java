package jotalac.market_viewer.market_viewer_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "name"}), // device name is unique withing user
        indexes = @Index(name = "idx_device_hash", columnList = "deviceHash")
)
public class Device extends AbstractEntity {

    public Device(String name, User user) {
        deviceHash = UUID.randomUUID();
        this.name = name;
        this.user = user;
    }

    @Column(unique = true, nullable = false)
    private UUID deviceHash;

    @Setter
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
