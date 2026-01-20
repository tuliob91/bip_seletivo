package com.example.backend.beneficio;

import com.example.model.Beneficio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class BeneficioController {

    private final BeneficioService beneficioService;

    public BeneficioController(BeneficioService beneficioService) {
        this.beneficioService = beneficioService;
    }

    @GetMapping("/getBeneficio/{id}")
    public Beneficio getSaldo(@PathVariable("id") Long id){
        return beneficioService.getBeneficio(id);
    }

    @GetMapping("/listaBeneficios")
    public List<Beneficio> getListaBeneficios(){
        return beneficioService.getListaBeneficiosBD();
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvarAtualizar(@RequestBody Beneficio benef){
        try{
            Beneficio benefSalvo = beneficioService.salvarBeneficio(benef);
            return ResponseEntity.ok(benefSalvo);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirBeneficio(@PathVariable("id") Long id){
        try{
            beneficioService.excluirBeneficio(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferirValor(@RequestBody TransferenciaDTO dadosTransfer){
        try{
            beneficioService.transferir(
                        dadosTransfer.getIdOrigem()
                    ,   dadosTransfer.getIdDestino()
                    ,   dadosTransfer.getValor()
            );
            return ResponseEntity.ok().build();
        }catch(Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
