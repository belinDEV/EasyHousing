package Model;


import DTO.CorretorRequest;
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
    @ManyToOne
    private Long id;
    @Setter
    private String email;
    @Setter
    private String identificador;
    @Setter
    private String descricao;
    private String nome;
    @Setter
    @ManyToOne
    private String contato;


    public Corretor(CorretorRequest data) {
        this.id = getId();
        this.email = getEmail();
        this.identificador = getIdentificador();
        this.nome = getNome();
        this.contato = getContato();
        this.descricao = getDescricao();
    }

    public void setNome(String nome) {
    }
}
