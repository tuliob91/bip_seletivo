package com.example.backend.beneficio;

import com.example.model.Beneficio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@Tag(name = "Beneficios", description = "Operações financeiras de benefícios")
public class BeneficioController {

    private final BeneficioService beneficioService;

    public BeneficioController(BeneficioService beneficioService) {
        this.beneficioService = beneficioService;
    }

    @Operation(summary = "Consulta Beneficio Individual", description = "Realiza Consulta individual de um Benefício por ID")
    @ApiResponse(responseCode = "200", description = "Resultado sem erro")
    @GetMapping("/getBeneficio/{id}")
    public Beneficio getSaldo(@PathVariable("id") Long id){
        return beneficioService.getBeneficio(id);
    }

    @Operation(summary = "Consulta Lista de Beneficios ", description = "Realiza Consulta de Lista de Beneficio")
    @ApiResponse(responseCode = "200", description = "Resultado sem erro")
    @GetMapping("/listaBeneficios")
    public List<Beneficio> getListaBeneficios(){
        return beneficioService.getListaBeneficiosBD();
    }

    @Operation(summary = "Salva Beneficio no BD", description = "Realiza Save/Update do Beneficio no BD")
    @ApiResponse(responseCode = "200", description = "Resultado sem erro")
    @PostMapping("/salvar")
    public ResponseEntity<?> salvarAtualizar(@RequestBody Beneficio benef){
        try{
            Beneficio benefSalvo = beneficioService.salvarBeneficio(benef);
            return ResponseEntity.ok(benefSalvo);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(summary = "Exclui Beneficio no BD", description = "Realiza DELETE do Beneficio no BD")
    @ApiResponse(responseCode = "200", description = "Resultado sem erro")
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirBeneficio(@PathVariable("id") Long id){
        try{
            beneficioService.excluirBeneficio(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(summary = "Transferencia de Valores", description = "Realiza Transferencia de valores entre Benefícios Ativos que  possuem saldo suficiente")
    @ApiResponse(responseCode = "200", description = "Resultado sem erro")
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
