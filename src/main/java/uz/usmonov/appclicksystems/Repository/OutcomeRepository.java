package uz.usmonov.appclicksystems.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.usmonov.appclicksystems.entity.Outcome;

import java.util.List;

public interface OutcomeRepository extends JpaRepository<Outcome,Integer> {

    Page<Outcome> findAllByFromCard_Username(Pageable pageable,String username);

    List<Outcome> findAllByFromCard_Username(String username);

    boolean existsOutcomeByFromCard_IdAndFromCard_Username(Integer id,String username);
}
