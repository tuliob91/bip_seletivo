package com.example.ejb;


import com.example.model.Beneficio;

import java.math.BigDecimal;
import java.util.List;

public interface BeneficioRemote {

    Beneficio getBeneficioById(Long id);

    Beneficio salvar(Beneficio benef);

    List<Beneficio> lista();

    void transfer(Long idOrigem, Long idDestino, BigDecimal valor);

    void excluir(Long id);

}
