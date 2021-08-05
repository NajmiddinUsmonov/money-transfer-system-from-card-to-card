package uz.usmonov.appclicksystems.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.usmonov.appclicksystems.Service.CardService;
import uz.usmonov.appclicksystems.entity.Card;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping
    public HttpEntity<?> add(@RequestBody Card card){
        HttpEntity<?> add = cardService.add ( card );
        return add;
    }

    @GetMapping("/{id}")
    public HttpEntity<?> get(@PathVariable Integer id){
        HttpEntity<?> card = cardService.get ( id );
        return card;
    }
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@RequestBody Card card){
        HttpEntity<?> editcard = cardService.edit ( card, id );
        return editcard;
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        HttpEntity<?> delete = cardService.delete ( id );
        return delete;
    }


}
