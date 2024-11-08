package easyhousing.easyhousing.Model;

import easyhousing.easyhousing.DTO.ImovelRequest;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "imovel")
@Entity(name = "Imovels")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter private Integer qtdQuarto;
    @Setter private Boolean garagem;
    @Setter private Integer qtdBanheiro;
    @Setter private String status;
    @Setter private String cep;
    @Setter private String tipo;
    @Setter private Boolean financiamento;
    @Setter private String descricao;
    @Setter private String contrato;
    @Setter private String urn;
    @Column(precision = 10, scale = 8) @Setter private BigDecimal latitude;
    @Column(precision = 10, scale = 8) @Setter private BigDecimal longitude;

    @ManyToOne
    @JoinColumn(name = "corretor_id")
    @Setter
    private Corretor corretor;  // Corrigido para ser do tipo Corretor, não Long

    public Imovel(ImovelRequest data, Corretor corretor) {
        this.qtdQuarto = data.getQtdQuarto();
        this.garagem = data.getGaragem();
        this.qtdBanheiro = data.getQtdBanheiro();
        this.status = data.getStatus();
        this.cep = data.getCep();
        this.tipo = data.getTipo();
        this.financiamento = data.getFinanciamento();
        this.descricao = data.getDescricao();
        this.contrato = data.getContrato();
        this.urn = data.getUrn();
        this.latitude = data.getLatitude();
        this.longitude = data.getLongitude();
        this.corretor = corretor;
    }
}
