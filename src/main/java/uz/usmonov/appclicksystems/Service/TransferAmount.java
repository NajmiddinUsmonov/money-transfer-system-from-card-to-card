package uz.usmonov.appclicksystems.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.usmonov.appclicksystems.Repository.CardRepository;
import uz.usmonov.appclicksystems.Result.Result;
import uz.usmonov.appclicksystems.entity.Card;
import uz.usmonov.appclicksystems.payload.OutcomeDto;

@Service
public class TransferAmount {
    
    @Autowired
    CardRepository cardRepository;
    
    public Result transfer(OutcomeDto outcomeDto){


        Card fromCard = cardRepository.getById ( outcomeDto.getFromCardId () );
        Card toCard = cardRepository.getById ( outcomeDto.getToCardId () );

        double com_amount = outcomeDto.getAmount () /100;

        double withdraw=outcomeDto.getAmount ()+com_amount;

       boolean sum=fromCard.getBalance ()>=withdraw;

       if ( sum ){
        double left=fromCard.getBalance ()-withdraw;
        fromCard.setBalance ( left );
        cardRepository.save ( fromCard );
       double incomeCardBalance=toCard.getBalance ()+outcomeDto.getAmount ();
       toCard.setBalance ( incomeCardBalance );
       cardRepository.save ( toCard );
       return new Result (true,com_amount);
       }
       return new Result(false,com_amount);

    }
}
