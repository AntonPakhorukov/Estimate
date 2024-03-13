package spring.min.work.domain;


import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String password;
//    @BooleanFlag
//    private boolean archive;
//    @Enumerated(EnumType.STRING)
//    private Role role;
//    @OneToOne(cascade = CascadeType.REMOVE)
//    private Bucket bucket;
    private String phone;
    private String address;

    public User(String name, String email, String password, String phone, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
//        this.archive = archive;
        this.phone = phone;
        this.address = address;
    }
}
