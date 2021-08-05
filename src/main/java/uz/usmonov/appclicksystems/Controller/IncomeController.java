package uz.usmonov.appclicksystems.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.usmonov.appclicksystems.Service.IncomeService;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @GetMapping("/{id}")
    public HttpEntity<?> get(@PathVariable Integer id){
        HttpEntity<?> income = incomeService.get ( id );
        return ResponseEntity.ok (income);
    }

    @GetMapping
    private HttpEntity<?> getAllwithPage(@RequestParam Integer page){
        HttpEntity<?> all = incomeService.getAllwithPage (page);
        return ResponseEntity.ok (all);
    }

    @GetMapping("/all")
    public HttpEntity<?> getAll(){
        HttpEntity<?> all = incomeService.getAll ();
        return ResponseEntity.ok (all);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        HttpEntity<?> delete = incomeService.delete ( id );
        return ResponseEntity.ok (delete);
    }

}
