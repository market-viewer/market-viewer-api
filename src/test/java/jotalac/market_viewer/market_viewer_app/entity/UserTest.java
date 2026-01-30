package jotalac.market_viewer.market_viewer_app.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("User should be created with valid fields")
    void shouldCreateUserWithValidFields() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("securepassword");

        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("securepassword", user.getPassword());
    }

    @Test
    @DisplayName("User constructor should initialize fields correctly")
    void shouldInitializeFieldsViaConstructor() {
        User user = new User("testuser", "test@example.com", "securepassword");

        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("securepassword", user.getPassword());
    }

    // Note: JPA annotations like @Column(nullable = false) are not enforced by standard Bean Validation (JSR 380)
    // without Hibernate Validator's specific integration or database constraints.
    // However, if we had jakarta.validation.constraints annotations (e.g. @NotNull), we could test them here.
    // Since User.java uses @Basic(optional = false) and @Column(nullable = false), these are JPA checks.
    // We strictly test the POJO behavior here.

}
