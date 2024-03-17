package spring.min.work.domain;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    @BooleanFlag
    private boolean active;
    private String phone;
    private String address;
    @OneToMany
    @JoinColumn(name = "estimate_id")
    private List<Estimate> estimates;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(String username, String email, String password, boolean active, String phone, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.phone = phone;
        this.address = address;
        this.estimates = new ArrayList<>();
    }
}
