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
import org.springframework.web.bind.annotation.RequestParam;
import uz.usmonov.appclicksystems.Repository.CardRepository;
import uz.usmonov.appclicksystems.Repository.IncomesRepository;
import uz.usmonov.appclicksystems.Result.Result;
import uz.usmonov.appclicksystems.entity.Income;

import java.util.List;
import java.util.Optional;


@Service
public class IncomeService {

    @Autowired
    IncomesRepository incomesRepository;

    @Autowired
    CardRepository cardRepository;


    public HttpEntity<?> get(Integer id){
        User principal = (User) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
        String username = principal.getUsername ();
        Optional<Income> income2 = incomesRepository.findIncomeByToCard_idAndToCard_Username ( id, username );
        if ( !income2.isPresent () )
            return ResponseEntity.status (404).body ( new Result ( false,"There is no such an income with id="+id ) );
        return ResponseEntity.status ( income2.isPresent ()?200:403).body (income2.get ());
    }

    public HttpEntity<?> getAllwithPage(Integer page){
        User principal = (User) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
        String username = principal.getUsername ();
        Pageable pageable= PageRequest.of ( page,5 );
        Page<Income> all = incomesRepository.findAllByToCard_Username ( pageable,username );
        return ResponseEntity.ok (all);
    }

    public HttpEntity<?> delete(Integer id){
        User principal = (User) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
        String username = principal.getUsername ();
        boolean income = incomesRepository.existsIncomeByToCard_IdAndToCard_Username ( id, username );

        if (! income )
            return ResponseEntity.status (404).body ( new Result ( false,"There is no such an income with id="+id ) );

        incomesRepository.deleteById ( id );
        return ResponseEntity.ok ().body ( new Result ( true, "Deleted Successfully"  ) );
    }


    public HttpEntity<?> getAll(){
        User principal = (User) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
        String username = principal.getUsername ();
        List<Income> all = incomesRepository.findAllByFromCard_Username (username);
        return ResponseEntity.ok (all);
    }


}
