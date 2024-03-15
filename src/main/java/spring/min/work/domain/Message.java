package spring.min.work.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Message {
    private static final String SEQ_TYPE = "seq_mes";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_TYPE)
    @SequenceGenerator(name = SEQ_TYPE, sequenceName = SEQ_TYPE, allocationSize = 1)
    private Integer id;
    private String text;
    private String tag;

//    @PrePersist
//    public void assignId(){
//        EntityManagerFactory factory = Persistence
//                .createEntityManagerFactory("");
//        EntityManager manager = factory.createEntityManager();
//        Integer maxValue = manager.createQuery("SELECT MAX(m.id) FROM messages m", Integer.class)
//                .getSingleResult();
//        if (maxValue == null) {
//            id = 1;
//        } else {
//            id = maxValue + 1;
//        }
//    }
    public Message(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }
}
