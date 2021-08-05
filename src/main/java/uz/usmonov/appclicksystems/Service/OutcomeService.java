package uz.usmonov.appclicksystems.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import uz.usmonov.appclicksystems.Repository.CardRepository;
import uz.usmonov.appclicksystems.Repository.IncomesRepository;
import uz.usmonov.appclicksystems.Repository.OutcomeRepository;
import uz.usmonov.appclicksystems.Result.Result;
import uz.usmonov.appclicksystems.entity.Income;
import uz.usmonov.appclicksystems.entity.Outcome;
import uz.usmonov.appclicksystems.payload.OutcomeDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class OutcomeService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OutcomeRepository outcomeRepository;

    @Autowired
    IncomesRepository incomesRepository;

    @Autowired
    TransferAmount transferAmount;

    public HttpEntity<?> add(OutcomeDto outcomeDto){

        boolean fromCard = cardRepository.existsById ( outcomeDto.getFromCardId () );
        boolean toCard = cardRepository.existsById ( outcomeDto.getToCardId () );

        if ( !fromCard )
            return ResponseEntity.status ( 409 ).body ( new Result ( false,"FromCardId not found"  ) );

        if ( !toCard )
            return ResponseEntity.status ( 409 ).body ( new Result ( false,"ToCardId not found"  ) );

        Result transfer = transferAmount.transfer ( outcomeDto );

        if ( !transfer.isSuccess () )
            return ResponseEntity.status ( 409 ).body ( new Result ( false,"Not enough money in your Card"  ) );


        Outcome outcome=new Outcome ();
        outcome.setFromCard ( cardRepository.getById ( outcomeDto.getFromCardId () ) );
        outcome.setToCard ( cardRepository.getById ( outcomeDto.getToCardId () ) );
        outcome.setAmount ( outcomeDto.getAmount () );
        LocalDateTime transfertime = LocalDateTime.now ();
        outcome.setData ( transfertime);
        outcome.setCommission_amount ( (Double) transfer.getObject () );
        outcomeRepository.save ( outcome );

        Income income=new Income ();
        income.setFromCard ( cardRepository.getById ( outcomeDto.getFromCardId () ) );
        income.setToCard ( cardRepository.getById ( outcomeDto.getToCardId () ) );
        income.setAmount ( outcomeDto.getAmount () );
        income.setData ( transfertime );

        incomesRepository.save ( income );
        return ResponseEntity.ok (new Result ( true,"Successfully transferred" ));
    }

    public HttpEntity<?> get(Integer id){
        boolean exists = outcomeRepository.existsById ( id );

        if ( !exists )
            return ResponseEntity.notFound ().build ();
        Optional<Outcome> outcome = outcomeRepository.findById ( id );
        return ResponseEntity.ok (outcome.get ());
    }

    public HttpEntity<?> getAllOutcome(){
        User principal = (User) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
        String username = principal.getUsername ();
        List<Outcome> all = outcomeRepository.findAllByFromCard_Username ( username );
        return ResponseEntity.ok (all);
    }

    public HttpEntity<?> delete(Integer id){
        User principal = (User) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
        String username = principal.getUsername ();
        boolean outcome = outcomeRepository.existsOutcomeByFromCard_IdAndFromCard_Username ( id,username );

        if (! outcome )
            return ResponseEntity.status (404).body ( new Result ( false,"There is no such an outcome with id="+id ) );

        outcomeRepository.deleteById ( id );
        return ResponseEntity.ok ().build ();
    }

    public HttpEntity<?> getPage(int page){
        User principal = (User) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
        String username = principal.getUsername ();
        Pageable pageable=PageRequest.of ( page,5 );
        Page<Outcome> all = outcomeRepository.findAllByFromCard_Username ( pageable, username );
        return ResponseEntity.ok (all);
    }

}
