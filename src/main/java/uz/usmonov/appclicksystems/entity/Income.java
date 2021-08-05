package uz.usmonov.appclicksystems.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Income {

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
}
