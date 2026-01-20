package com.example.ejb;

import com.example.model.Beneficio;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.List;

@Stateless(mappedName = "BeneficioEjbService")
@Remote(BeneficioRemote.class)
public class BeneficioEjbService implements BeneficioRemote {

    @PersistenceContext(unitName = "meuPU")
    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Beneficio getBeneficioById(Long id){

        System.out.println("EJB consultando saldo para ID: " + id);
        Beneficio benef = em.find(Beneficio.class,id);
        System.out.println("Beneficio recuperado do banco: " + benef.toString());

        return benef;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Beneficio salvar(Beneficio benef) {
        if(benef.getId()==null){
            em.persist(benef);
            return benef;
        }else{
            return em.merge(benef);
        }
    }

    @Override
    public List<Beneficio> lista() {
        return em.createQuery("SELECT b FROM Beneficio b",Beneficio.class).getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void excluir(Long id) {
        Beneficio benef = em.find(Beneficio.class,id);
        if(benef!=null) {
            em.remove(benef);
        }else{
            throw new EJBException("Não existe um registro com esse ID para ser excluido!");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Beneficio benefOrigin = em.find(Beneficio.class,fromId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio benefDest = em.find(Beneficio.class,toId, LockModeType.PESSIMISTIC_WRITE);

        if (benefOrigin == null || benefDest == null) {
            throw new EJBException("Conta de origem ou destino não encontrada.");
        }
        if (benefOrigin.getSaldo().compareTo(amount) < 0) {
            throw new EJBException("Saldo insuficiente para transferência!");
        }
        if(!benefOrigin.getAtivo() || !benefDest.getAtivo()){
            throw new EJBException("Impossível transferir entre Beneficios Inativos.");
        }

        benefOrigin.setSaldo(benefOrigin.getSaldo().subtract(amount));
        benefDest.setSaldo(benefDest.getSaldo().add(amount));
    }

}
