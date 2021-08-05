package uz.usmonov.appclicksystems.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Outcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Card fromCard;

    @ManyToOne
    private Card toCard;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private double commission_amount;


}
