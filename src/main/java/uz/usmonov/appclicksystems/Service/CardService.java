package uz.usmonov.appclicksystems.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import uz.usmonov.appclicksystems.Repository.CardRepository;
import uz.usmonov.appclicksystems.Result.Result;
import uz.usmonov.appclicksystems.entity.Card;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;


    public HttpEntity<?> add(Card card){

        boolean number = cardRepository.existsCardByNumber ( card.getNumber () );
        if ( number )
            return ResponseEntity.status ( 409 ).body ( new Result ( false,"Card already exists" ) );
        if(card.getNumber ().length ()!=20)
            return ResponseEntity.status ( HttpStatus.CONFLICT ).body (new Result(false,"Card number should consists of 20 digits") );

        User principal = (User) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
        Card card1=new Card ();
        card1.setNumber (card.getNumber ());
        card1.setUsername ( principal.getUsername () );
        card1.setBalance ( card.getBalance () );
        card1.setExpireData ( card.getExpireData () );
        Card save = cardRepository.save ( card1 );
        return ResponseEntity.ok (save);
    }

    public HttpEntity<?> get(Integer id){
        boolean exists = cardRepository.existsById ( id );
        if ( !exists )
            return ResponseEntity.notFound ().build ();

        Card card = cardRepository.getById ( id );
        return ResponseEntity.ok (card);
    }

    public HttpEntity<?> edit(Card card,Integer id){
        User principal = (User) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();

        boolean card1 = cardRepository.existsById ( id );
        if (! card1 )
            return ResponseEntity.notFound ().build ();
        Card editingCard = cardRepository.getById ( id );
        editingCard.setNumber ( card.getNumber () );
        editingCard.setBalance ( card.getBalance () );
        editingCard.setExpireData ( card.getExpireData () );
        editingCard.setUsername ( principal.getUsername () );

        cardRepository.save ( editingCard );
        return ResponseEntity.accepted ().build ();
    }

    public HttpEntity<?> delete(Integer id){
        boolean exists = cardRepository.existsById ( id );

        if (! exists )
            return ResponseEntity.notFound ().build ();

        cardRepository.deleteById ( id );
        return ResponseEntity.ok ().build ();
    }

}
