package easyhousing.easyhousing.DTO;

import easyhousing.easyhousing.Model.Imovel;
import java.math.BigDecimal;
import java.util.List;

public record ImovelResponse(
        Long id,
        Integer qtdQuarto,
        Boolean garagem,
        Integer qtdBanheiro,
        String status,
        String cep,
        String tipo,
        Boolean financiamento,
        String descricao,
        String contrato,
        BigDecimal latitude,
        BigDecimal longitude,
        Long corretorId, // apenas o ID do corretor
        String urn,
        List<ImageResponseDTO> images
) {
    public ImovelResponse(Imovel imovel, List<ImageResponseDTO> images) {
        this(
                imovel.getId(),
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
                imovel.getCorretor().getId(), // Assumindo que Imovel possui um Corretor
                imovel.getUrn(),
                images
        );
    }
}
