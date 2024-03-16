package spring.min.work.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "estimate")
public class Estimate {
    private static final String SEQ_TYPE = "seq_est";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_TYPE)
    @SequenceGenerator(name = SEQ_TYPE, sequenceName = SEQ_TYPE, allocationSize = 1)
    private Integer id;
    private String room;
    private String category;
    private String description;
    private String manufacturer;
    private String product;
    private String quantity;
    private String price;
    private String sum;
    @OneToOne
    @JoinColumn(name = "usr_id")
    private User user;

    public Estimate(String room, String category, String description, String manufacturer, String product, String quantity, String price) {
        this.room = room;
        this.category = category;
        this.description = description;
        this.manufacturer = manufacturer;
        this.product = product;
        if(quantity != null && Integer.valueOf(quantity) > 0) {
            this.quantity = quantity;
        } else {
            this.quantity = "1";
        }
        if(price != null && Double.valueOf(price) > 0) {
            this.price = price;
        } else {
            this.price = "1";
        }
        this.sum = String.valueOf(Integer.parseInt(quantity) * Double.parseDouble(price));
//        this.user = user;
    }
}
