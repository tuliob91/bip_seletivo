package com.example.backend.beneficio;

import com.example.ejb.BeneficioRemote;
import com.example.model.Beneficio;
import com.example.ejb.BeneficioEjbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficioService{

    private final BeneficioRemote beneficioRemote;

    // Use a Interface e o @Lazy aqui
    public BeneficioService(@Lazy BeneficioRemote beneficioRemote) {
        this.beneficioRemote = beneficioRemote;
    }

    public Beneficio getBeneficio(Long id){
        Beneficio b = beneficioRemote.getBeneficioById(id);
        System.out.println("O objeto chegou no Spring? " + (b != null));
        if (b != null) {
            System.out.println("Dados do benefício: " + b.toString()); // Use um campo que exista
        }
        return b;
    }

}