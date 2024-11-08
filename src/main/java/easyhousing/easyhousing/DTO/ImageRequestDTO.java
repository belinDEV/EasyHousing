package easyhousing.easyhousing.DTO;


import javax.validation.constraints.NotNull;


public class ImageRequestDTO {

    @NotNull(message = "O link da imagem não pode ser nulo")
    private String url;

    @NotNull(message = "O id do imovel não pode ser nulo")
    private Long imovelId;

    // Getters and Setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Long getimovelId() { return imovelId; }
    public void setimovelId(Long imovelId) { this.imovelId = imovelId; }

    public void setImovelId(Long id) {
    }
}
