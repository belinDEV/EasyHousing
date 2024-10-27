package easyhousing.easyhousing.Model;

import easyhousing.easyhousing.DTO.CorretorRequest;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "corretor")
@Entity(name = "Corretores")
public class Corretor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String email;

    @Setter
    private String identificador;

    @Setter
    private String descricao;

    @Setter
    private String nome;

    @Setter
    private String contato;
    public Corretor(CorretorRequest data) {
        this.email = data.email();
        this.identificador = data.identificador();
        this.nome = data.nome();
        this.contato = data.contato();
        this.descricao = data.descricao();
    }



}
