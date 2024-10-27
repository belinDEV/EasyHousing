package DTO;

import Model.Corretor;

import java.math.BigDecimal;

public record ImovelRequest(
     Long id,
     Number qtdQuarto,
     Boolean garagem,
     Number qtdBanheiro,
     String status,
     Number cep,
     String tipo,
     Boolean financiamento,
     String descricao,
     String contrato,
     BigDecimal latitude,
     BigDecimal longitude,
     Corretor corretor
) {
}
