package spring.min.work.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "estimate")
public class Estimate {
    private static final String SEQ_TYPE = "seq_est";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_TYPE)
    @SequenceGenerator(name = SEQ_TYPE, sequenceName = SEQ_TYPE, allocationSize = 1)
    private Integer id;
    private List<String> rooms;
    private List<String> categories;
    private List<String> descriptions;
    private String manufacturer;
    private String product;
    private Integer quantity;
    private Double price;
    private Double sum;
    @OneToOne
    @JoinColumn(name = "usr_id")
    private User user;

    public Estimate(String manufacturer, String product, Integer quantity, Double price, User user) {
        this.rooms = new ArrayList<>(List.of("kitchen", "kitchen_living_room", "main_toilet",
                "bedroom", "master_bedroom", "room", "children_room", "guest_room","corridor",
                "hallway", "toilet", "main_toilet", "guest_toilet", "balcony", "loggia", "other"));
        this.categories = new ArrayList<>(List.of("decoration", "electrics", "plumbing_fixtures",
                "ventilation", "lighting", "other"));
        this.descriptions = new ArrayList<>(List.of("floor", "wall", "ceiling", "window", "doors",
                "sockets", "switches", "bath", "sanitaryware", "faucets", "installation_system",
                "shower_cabins", "shower_systems", "forced_ventilation", "air_conditioner",
                "hood", "lamps", "light_source", "light_lines", "backlight", "other"));
        this.manufacturer = manufacturer;
        this.product = product;
        if(quantity != null && quantity > 0) this.quantity = quantity;
        if(price != null && price > 0) this.price = price;
        this.sum = quantity * price;
        this.user = user;
    }
}
