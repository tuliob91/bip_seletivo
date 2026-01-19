package com.example.ejb;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.xml.ws.WebServiceClient;
import com.example.model.Beneficio;
import java.math.BigDecimal;

@Stateless(mappedName = "BeneficioEjbService")
@Remote(BeneficioRemote.class)
public class BeneficioEjbService implements BeneficioRemote {

    @PersistenceContext(unitName = "meuPU")
    private EntityManager em;

    @Override
    public Beneficio getBeneficioById(Long id){
        System.out.println("SUCCESSO getBeneficioById");
        return em.find(Beneficio.class,id);
    }
    public void transfer(Long fromId, Long toId, BigDecimal amount) {

    }
}
