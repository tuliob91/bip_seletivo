package com.example.ejb;

import com.example.model.Beneficio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BeneficioEjbServiceTest {

    private EntityManager em;

    private BeneficioEjbService benService;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks manualmente se o ExtendWith não funcionar
        this.em = mock(EntityManager.class);
        this.benService = new BeneficioEjbService();

        // Usa setter que criamos antes
        this.benService.setEntityManager(this.em);
    }

    @Test
    void deveImpedirTransferenciaSaldoInferior() throws Exception{

        Beneficio bOrigem = new Beneficio();
        bOrigem.setSaldo(new BigDecimal("100.00"));
        bOrigem.setAtivo(true);
        bOrigem.setId(1L);

        Beneficio bDestino = new Beneficio();
        bDestino.setSaldo(new BigDecimal("50.00"));
        bDestino.setAtivo(true);
        bDestino.setId(2L);

        when(em.find(eq(Beneficio.class), anyLong(), eq(LockModeType.PESSIMISTIC_WRITE)))
                .thenReturn(bOrigem)
                .thenReturn(bDestino);

        Exception erroCap = assertThrows(Exception.class,()->{
            benService.transfer(1L,2L,new BigDecimal(50000));
        });
        String msgEsperada = "Saldo insuficiente para transferência!";
        assertEquals(msgEsperada, erroCap.getMessage());

        verify(em, never()).merge(any());
    }

    @Test
    void deveImpedirTransferenciaSemRegistroEncontradoBD() throws  Exception{
        Beneficio bOrigem = new Beneficio();
        bOrigem.setSaldo(new BigDecimal("100.00"));
        bOrigem.setAtivo(true);
        bOrigem.setId(1L);

        when(em.find(eq(Beneficio.class), anyLong(), eq(LockModeType.PESSIMISTIC_WRITE)))
                .thenReturn(bOrigem)
                .thenReturn(null);

        Exception erroCap = assertThrows(Exception.class,()->{
            benService.transfer(1L,2L,new BigDecimal(10));
        });
        String msgEsperada = "Conta de origem ou destino não encontrada.";
        assertEquals(msgEsperada, erroCap.getMessage());

        verify(em, never()).merge(any());
    }

    @Test
    void deveImpedirTransferenciaEntreBeneficiosInativos() throws  Exception{
        Beneficio bOrigem = new Beneficio();
        bOrigem.setSaldo(new BigDecimal("100.00"));
        bOrigem.setAtivo(true);
        bOrigem.setId(1L);

        Beneficio bDestino = new Beneficio();
        bDestino.setSaldo(new BigDecimal("50.00"));
        bDestino.setAtivo(false);
        bDestino.setId(2L);

        when(em.find(eq(Beneficio.class), anyLong(), eq(LockModeType.PESSIMISTIC_WRITE)))
                .thenReturn(bOrigem)
                .thenReturn(bDestino);

        Exception erroCap = assertThrows(Exception.class,()->{
            benService.transfer(1L,2L,new BigDecimal("10.00"));
        });
        String msgInativo = "Impossível transferir entre Beneficios Inativos.";
        assertEquals(msgInativo, erroCap.getMessage());

        verify(em, never()).merge(any());

    }

    @Test
    void deveRealizarTransferenciaComSucesso() throws Exception{
        Beneficio bOrigem = new Beneficio();
        bOrigem.setSaldo(new BigDecimal("500.00"));
        bOrigem.setAtivo(true);
        bOrigem.setId(1L);

        Beneficio bDestino = new Beneficio();
        bDestino.setSaldo(new BigDecimal("500.00"));
        bDestino.setAtivo(true);
        bDestino.setId(2L);

        when(em.find(eq(Beneficio.class), anyLong(), eq(LockModeType.PESSIMISTIC_WRITE)))
                .thenReturn(bOrigem)
                .thenReturn(bDestino);
        benService.transfer(1L, 2L,new BigDecimal("50.00") );
        // Origem: 500.00 - 50.00 = 450.00
        assertEquals(new BigDecimal("450.00"), bOrigem.getSaldo(), "O saldo da conta de origem não foi subtraído corretamente.");

        // Destino: 500.00 + 50.00 = 550.00
        assertEquals(new BigDecimal("550.00"), bDestino.getSaldo(), "O saldo da conta de destino não foi somado corretamente.");
    }
}
