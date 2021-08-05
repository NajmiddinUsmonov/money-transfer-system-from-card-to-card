package uz.usmonov.appclicksystems.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.usmonov.appclicksystems.Service.OutcomeService;
import uz.usmonov.appclicksystems.payload.OutcomeDto;

@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {

    @Autowired
    OutcomeService outcomeService;

    @PostMapping()
    public HttpEntity<?> add(@RequestBody OutcomeDto outcomeDto){
        HttpEntity<?> add = outcomeService.add ( outcomeDto );
        return ResponseEntity.ok (add);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> get(@PathVariable Integer id){
        HttpEntity<?> outcome = outcomeService.get ( id );
        return ResponseEntity.ok ( outcome );
    }

    @GetMapping("/all")
    public HttpEntity<?> getAlloutcome(){
        HttpEntity<?> all = outcomeService.getAllOutcome ();
        return ResponseEntity.ok (all);
    }

    @GetMapping
    public HttpEntity<?> getPage(@RequestParam int page){
        HttpEntity<?> page1 = outcomeService.getPage ( page );
        return ResponseEntity.ok (page1);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        HttpEntity<?> delete = outcomeService.delete ( id );
        return ResponseEntity.ok (delete);
    }
}
