package com.example.backend.beneficio;

import com.example.ejb.BeneficioRemote;
import com.example.model.Beneficio;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BeneficioService{

    private final BeneficioRemote beneficioRemote;

    // Use a Interface e o @Lazy aqui
    public BeneficioService(@Lazy BeneficioRemote beneficioRemote) {
        this.beneficioRemote = beneficioRemote;
    }

    public Beneficio getBeneficio(Long id){
        return beneficioRemote.getBeneficioById(id);
    }

    public List<Beneficio> getListaBeneficiosBD(){
        return beneficioRemote.lista();
    }

    public Beneficio salvarBeneficio(Beneficio benef){
        return beneficioRemote.salvar(benef);
    }

    public void excluirBeneficio(Long id){
        beneficioRemote.excluir(id);
    }

    public void transferir(Long idOrigin, Long idDestino, BigDecimal valor){
        beneficioRemote.transfer(idOrigin, idDestino, valor);
    }

}