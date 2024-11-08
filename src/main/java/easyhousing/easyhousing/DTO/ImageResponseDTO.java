package easyhousing.easyhousing.DTO;


import easyhousing.easyhousing.Model.Imovel;

import java.util.List;

public class ImageResponseDTO {
    private String image;

    public ImageResponseDTO(String image) {
        this.image = image;
    }

    public ImageResponseDTO(Imovel imovel, List<ImageResponseDTO> imageResponseDTOs) {
    }

    // Getters e setters
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
