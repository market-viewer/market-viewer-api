package jotalac.market_viewer.market_viewer_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_user",
    uniqueConstraints = @UniqueConstraint(columnNames = {"oauth_provider", "oauth_provider_id"})
)
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

    public User(String username, String hashedPassword) {
        this.username = username;
        this.password = hashedPassword;
    }

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String username;

    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider")
    private OAuthProvider oauthProvider;

    @Column(name = "oauth_provider_id")
    private String oauthProviderId;

}
