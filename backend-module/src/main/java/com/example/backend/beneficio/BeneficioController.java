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
        return beneficioService.getBeneficio(id);
    }
}
