package spring.min.work.domain;


import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    // EAGER - всегда будет подгружена роль, LAZY - только при обращении
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    // Говорит что данное поле будет храниться в отдельной таблице, для которой мы не описывали mapping.
    // Это позволяет создать табличку user-role, которая будет соединяться с текущей табличкой через user_id
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
//    @OneToOne(cascade = CascadeType.REMOVE)
//    private Bucket bucket;
    private String phone;
    private String address;

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
    }
}
