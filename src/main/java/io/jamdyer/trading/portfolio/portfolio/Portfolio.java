package io.jamdyer.trading.portfolio.portfolio;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jamdyer.trading.portfolio.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @JsonIgnore
    private User user;

    private LocalDateTime createdOn;

    public Portfolio(String name) {
        this.name = name;
        this.createdOn = LocalDateTime.now();
    }

}
