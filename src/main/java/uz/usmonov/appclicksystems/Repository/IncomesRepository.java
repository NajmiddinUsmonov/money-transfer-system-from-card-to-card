package uz.usmonov.appclicksystems.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.usmonov.appclicksystems.entity.Income;
import uz.usmonov.appclicksystems.entity.Outcome;

import java.util.List;
import java.util.Optional;


public interface IncomesRepository extends JpaRepository<Income,Integer> {

    Page<Income> findAllByToCard_Username(Pageable pageable,String username);

    boolean existsIncomeByToCard_IdAndToCard_Username(Integer id,String username);

    Optional<Income> findIncomeByToCard_idAndToCard_Username(Integer id, String Username);

    List<Income> findAllByFromCard_Username(String username);
}
