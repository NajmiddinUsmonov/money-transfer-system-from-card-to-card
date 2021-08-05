package uz.usmonov.appclicksystems.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.usmonov.appclicksystems.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {

    boolean existsCardByNumber(String number);

}
