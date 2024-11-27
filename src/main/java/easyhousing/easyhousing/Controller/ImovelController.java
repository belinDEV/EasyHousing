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
    public List<ImovelResponse> getAll() {
        List<Imovel> imoveis = imovelRepository.findAll();
        return imoveis.stream()
                .map(imovel -> {
                    // Busca as imagens associadas
                    List<Image> imagens = imageRepository.findByImovelId(imovel.getId());

                    // Transforma as imagens em DTOs ou retorna uma lista vazia
                    List<ImageResponseDTO> imageResponseDTOs = Optional.ofNullable(imagens)
                            .orElse(List.of()) // Retorna uma lista vazia se imagens for null
                            .stream()
                            .map(image -> new ImageResponseDTO(image.getUrl()))
                            .toList();

                    // Cria a resposta para cada imóvel
                    return new ImovelResponse(imovel, imageResponseDTOs);
                })
                .toList();
    }


    // Buscar a lista de imagens associadas ao imovel
    @GetMapping("/{id}")
    public ResponseEntity<ImovelResponse> getById(@PathVariable Long id) {
        try {
            // Busca o imóvel pelo ID
            Imovel imovel = imovelRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado com ID: " + id));

            // Busca as imagens associadas ao imóvel
            List<Image> imagens = imageRepository.findByImovelId(id);

            // Mapeia as imagens para o DTO
            List<ImageResponseDTO> imageResponseDTOs = Optional.ofNullable(imagens)
                    .orElse(List.of()) // Garante uma lista vazia se imagens for null
                    .stream()
                    .map(image -> new ImageResponseDTO(image.getUrl()))
                    .toList();

            // Cria a resposta do imóvel com as imagens
            ImovelResponse imovelResponse = new ImovelResponse(imovel, imageResponseDTOs);

            // Retorna a resposta com status 200
            return ResponseEntity.ok(imovelResponse);
        } catch (EntityNotFoundException e) {
            // Retorna 404 caso o imóvel não seja encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Retorna 500 em caso de erro inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @PostMapping("/salvar")
    public ResponseEntity<ImovelResponse> salvarImovel(@Valid @RequestBody ImovelRequest data) {
        try {
            // Verifica e obtém o corretor associado
            Long corretorId = data.getCorretorId();
            Corretor corretor = corretorRepository.findById(corretorId)
                    .orElseThrow(() -> new EntityNotFoundException("Corretor não encontrado com ID: " + corretorId));

            // Cria e salva o imóvel
            Imovel imovel = new Imovel(data, corretor);
            Imovel savedImovel = imovelRepository.save(imovel);

            // Busca imagens associadas ao imóvel
            List<Image> imagens = imageRepository.findByImovelId(savedImovel.getId());
            List<ImageResponseDTO> imageResponseDTOs = Optional.ofNullable(imagens)
                    .orElse(List.of()) // Garante uma lista vazia caso não existam imagens
                    .stream()
                    .map(image -> new ImageResponseDTO(image.getUrl()))
                    .toList();

            // Cria a resposta do imóvel com as imagens associadas
            ImovelResponse response = new ImovelResponse(savedImovel, imageResponseDTOs);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (EntityNotFoundException e) {
            // Retorna erro caso o corretor ou outro recurso associado não seja encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Log do erro para depuração
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarImovel(
            @PathVariable Long id,
            @Valid @RequestBody ImovelRequest data) {
        try {
            // Verifica se o imóvel existe
            Imovel imovelExistente = imovelRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado com ID: " + id));

            // Atualiza os dados do imóvel
            Long corretorId = data.getCorretorId();
            Corretor corretor = corretorRepository.findById(corretorId)
                    .orElseThrow(() -> new EntityNotFoundException("Corretor não encontrado com ID: " + corretorId));

            imovelExistente.setQtdQuarto(data.getQtdQuarto());
            imovelExistente.setGaragem(data.getGaragem());
            imovelExistente.setQtdBanheiro(data.getQtdBanheiro());
            imovelExistente.setStatus(data.getStatus());
            imovelExistente.setCep(data.getCep());
            imovelExistente.setTipo(data.getTipo());
            imovelExistente.setFinanciamento(data.getFinanciamento());
            imovelExistente.setDescricao(data.getDescricao());
            imovelExistente.setContrato(data.getContrato());
            imovelExistente.setUrn(data.getUrn());
            imovelExistente.setLatitude(data.getLatitude());
            imovelExistente.setLongitude(data.getLongitude());
            imovelExistente.setCorretor(corretor);

            // Salva as alterações
            Imovel updatedImovel = imovelRepository.save(imovelExistente);

            // Retorna a resposta com imagens associadas
            List<Image> imagens = imageRepository.findByImovelId(updatedImovel.getId());

            List<ImageResponseDTO> imageResponseDTOs = Optional.ofNullable(imagens)
                    .orElse(List.of())
                    .stream()
                    .map(image -> new ImageResponseDTO(image.getUrl()))
                    .toList();

            ImovelResponse response = new ImovelResponse(updatedImovel, imageResponseDTOs);
            return ResponseEntity.ok().body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o imóvel. Por favor, tente novamente mais tarde.");
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarImovel(@PathVariable Long id) {
        System.out.println("ID recebido para exclusão: " + id);
        if (id == null) {
            return ResponseEntity.badRequest().body("ID não pode ser nulo.");
        }
        try {
            Imovel imovel = imovelRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado com ID: " + id));
            imovelRepository.delete(imovel);
            return ResponseEntity.ok("Imóvel com ID " + id + " deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar imóvel: " + e.getMessage());
        }
    }

}




