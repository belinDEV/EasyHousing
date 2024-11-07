package Controller;


import DTO.CorretorRequest;
import DTO.CorretorResponse;
import Model.Corretor;
import Repository.CorretorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@Controller
@RequestMapping("corretor")
public class CorretorController {
    private static final Logger LOGGER = Logger.getLogger(CorretorController.class.getName());

    @Autowired
    CorretorRepository repository;

    @GetMapping
    public List<CorretorResponse> getAll(){
        List<CorretorResponse> corretorList = repository.findAll().stream().map(CorretorResponse::new).toList();
        return corretorList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorretorResponse> getById(@PathVariable Long id){
        CorretorResponse corretor =  repository.findById(id) .map(CorretorResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Corretor não encontrada neste ID: " + id));
        return ResponseEntity.ok(corretor);
    }

    @PostMapping("/salvar")
    public ResponseEntity<String> salvarCorretor (@Valid @RequestBody CorretorRequest data){
        Corretor corretorData = new Corretor(data);
        repository.save(corretorData);
        return ResponseEntity.status(HttpStatus.CREATED).body("Corretor criado com sucesso!");

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarCorretor(@PathVariable Long id){
        try {
            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID não pode ser nulo.");
            }
            Optional<Corretor> CorretorOptional = repository.findById(id);
            if (CorretorOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Corretor não encontrado para o ID: " + id);
            }
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Corretor do ID: " + id + " deletado com sucesso!");
        } catch (Exception e) {
            LOGGER.info("Erro ao deletar" + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o Corretor. Por favor, tente novamente mais tarde.");
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarCorretor(@PathVariable Long id, @Valid @RequestBody CorretorRequest data) {
        try {
            Optional<Corretor> corretorOptional = repository.findById(id);
            if (corretorOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Corretor não encontrado para o ID: " + id);
            }
            Corretor corretor = corretorOptional.get();
            corretor.setNome(data.nome());
            corretor.setContato(data.contato());
            corretor.setEmail(data.email());
            corretor.setIdentificador(data.identificador());
            corretor.setDescricao(data.descricao());
            repository.save(corretor);
            return ResponseEntity.status(HttpStatus.OK).body("Corretor do ID: " + id + " atualizada com sucesso!");
        } catch (Exception e) {
            LOGGER.severe("Erro ao atualizar o Corretor com ID: " + id + ". Erro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o Corretor. Por favor, tente novamente mais tarde.");
        }
    }

}
