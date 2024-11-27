package easyhousing.easyhousing.Controller;

import easyhousing.easyhousing.DTO.ImageRequestDTO;
import easyhousing.easyhousing.Model.Image;
import easyhousing.easyhousing.Model.Imovel;
import easyhousing.easyhousing.Repository.ImageRepository;
import easyhousing.easyhousing.Repository.ImovelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/image")
@Controller

public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    // Definindo o diretório de upload(onde será salvo as imagens)
    private static final String UPLOAD_DIR = Paths.get(System.getProperty("user.dir"), "uploads").toString();

    // Verificar e criar o diretório de upload, se necessário
    static {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }
    @PostMapping("/salvar")
    public ResponseEntity<List<ImageRequestDTO>> save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("imovelId") Long imovelId) {
        try {
            Imovel imovel = imovelRepository.findById(imovelId)
                    .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado para o ID: " + imovelId));

            // Salvar o arquivo localmente
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.write(filePath, file.getBytes());

            // Salvar a URL da imagem no banco de dados
            Image image = new Image();
            String relativePath = "/uploads/" + fileName;
            image.setUrl(relativePath);
            image.setImovel(imovel);
            image = imageRepository.save(image);

            // Atualizar o campo image do imóvel
            imovel.setImage(image);
            imovelRepository.save(imovel);

            // Retornar a resposta
            ImageRequestDTO dto = new ImageRequestDTO();
            dto.setUrl(image.getUrl());
            dto.setImovelId(image.getImovel().getId());

            return new ResponseEntity<>(List.of(dto), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

