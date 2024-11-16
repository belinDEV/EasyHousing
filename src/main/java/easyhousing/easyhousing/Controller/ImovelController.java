package easyhousing.easyhousing.Controller;

import easyhousing.easyhousing.DTO.ImovelRequest;
import easyhousing.easyhousing.DTO.ImageResponseDTO;
import easyhousing.easyhousing.DTO.ImovelResponse;
import easyhousing.easyhousing.Model.Corretor;
import easyhousing.easyhousing.Model.Image;
import easyhousing.easyhousing.Model.Imovel;
import easyhousing.easyhousing.Repository.CorretorRepository;
import easyhousing.easyhousing.Repository.ImageRepository;
import easyhousing.easyhousing.Repository.ImovelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@RestController
@RequestMapping("imovel")
public class ImovelController {
    @Autowired private ImovelRepository imovelRepository;
    @Autowired private ImageRepository imageRepository;
    @Autowired private CorretorRepository corretorRepository;

    @GetMapping
    public List<ImageResponseDTO> getAll() {
        List<Imovel> imovels = imovelRepository.findAll();
        return imovels.stream()
                .map(imovel -> {
                    List<Image> imagens = ImageRepository.findByImovelId(imovel.getId());
                    List<ImageResponseDTO> imageResponseDTOs = imagens.stream()
                            .map(image -> new ImageResponseDTO(image.getUrl()))
                            .collect(Collectors.toList());
                    return new ImageResponseDTO(imovel, imageResponseDTOs);
                })
                .collect(Collectors.toList());
    }

    // Buscar a lista de imagens associadas ao imovel
    @GetMapping("/{id}")
    public ResponseEntity<ImovelResponse> getById(@PathVariable Long id) {
        Optional<Imovel> imovelOptional = imovelRepository.findById(id);
        if (imovelOptional.isPresent()) {
            Imovel imovel = imovelOptional.get();
            List<Image> imagens = ImageRepository.findByImovelId(id);
            List<ImageResponseDTO> imageResponseDTOs = imagens.stream()
                    .map(image -> new ImageResponseDTO(image.getUrl()))
                    .toList();
            ImovelResponse imovelResponse = new ImovelResponse(imovel, imageResponseDTOs);
            return ResponseEntity.ok(imovelResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/salvar")
    public ResponseEntity<ImovelResponse> salvarImovel(@Valid @RequestBody ImovelRequest data) {
        Long corretorId = data.getCorretorId();
        Corretor corretor = corretorRepository.findById(corretorId)
                .orElseThrow(() -> new EntityNotFoundException("Corretor não encontrado com ID: " + corretorId));

        Imovel imovel = new Imovel(data, corretor);
        Imovel savedImovel = imovelRepository.save(imovel);

        List<Image> imagens = ImageRepository.findByImovelId(savedImovel.getId());
        List<ImageResponseDTO> imageResponseDTOs = Optional.ofNullable(imagens)
                .orElse(List.of()) // Garante uma lista vazia se imagens for null
                .stream()
                .map(image -> new ImageResponseDTO(image.getUrl()))
                .toList();

        ImovelResponse response = new ImovelResponse(savedImovel, imageResponseDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}

