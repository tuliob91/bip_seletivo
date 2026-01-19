package com.example.backend.beneficio;

import com.example.ejb.BeneficioRemote;
import com.example.model.Beneficio;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    private final BeneficioService beneficioService;

    public BeneficioController(BeneficioService beneficioService) {
        this.beneficioService = beneficioService;
    }


    @GetMapping("/getBeneficio/{id}")
    public Beneficio getSaldo(@PathVariable("id") Long id){
        Beneficio benef = beneficioService.getBeneficio(id);
        if (benef != null) {
            System.out.println(">>> SUCESSO: Objeto chegou no Spring. Saldo: " + benef.getSaldo());
        } else {
            System.out.println(">>> ERRO: Objeto chegou NULO no Spring.");
        }
        return benef;
    }
}
