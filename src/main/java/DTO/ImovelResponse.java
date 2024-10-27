package DTO;

import Model.Corretor;
import Model.Imovel;

import java.math.BigDecimal;

public record ImovelResponse(
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
        Corretor corretor) {

    public ImovelResponse(Imovel imovel){
        this(imovel.getId(),
              imovel.getQtdQuarto(),
              imovel.getGaragem(),
              imovel.getQtdBanheiro(),
              imovel.getStatus(),
              imovel.getCep(),
              imovel.getTipo(),
              imovel.getFinanciamento(),
              imovel.getDescricao(),
              imovel.getContrato(),
              imovel.getLatitude(),
              imovel.getLongitude(),
              imovel.getCorretor()
        );
    }

}
