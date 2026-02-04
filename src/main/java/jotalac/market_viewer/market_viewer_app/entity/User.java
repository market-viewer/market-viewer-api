package jotalac.market_viewer.market_viewer_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
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

    @Column(unique = true)
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

}
