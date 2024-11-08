package easyhousing.easyhousing.DTO;

import java.math.BigDecimal;

public class ImovelRequest {
    private Long id;
    private Integer qtdQuarto;
    private Boolean garagem;
    private Integer qtdBanheiro;
    private String status;
    private String cep;
    private String tipo;
    private Boolean financiamento;
    private String descricao;
    private String contrato;
    private String urn;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long corretorId; // ID do corretor

    public ImovelRequest() {}

    // Getters e Setters

    public Long getId() { return id; }

    public Boolean getGaragem() { return garagem; }

    public Integer getQtdQuarto() { return qtdQuarto; }

    public Integer getQtdBanheiro() { return qtdBanheiro; }

    public String getStatus() { return status; }

    public String getCep() { return cep; }

    public String getTipo() { return tipo; }

    public Boolean getFinanciamento() { return financiamento; }

    public String getDescricao() { return descricao; }

    public String getContrato() { return contrato; }

    public String getUrn() { return urn; }

    public BigDecimal getLatitude() { return latitude; }

    public BigDecimal getLongitude() { return longitude; }

    public Long getCorretorId() { return corretorId; }

    public void setCorretorId(Long corretorId) { this.corretorId = corretorId; }
}
