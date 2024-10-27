package Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "imovel")
@Entity(name = "Imoveis")
public class Imovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Number qtdQuarto;
    private Boolean garagem;
    private Number qtdBanheiro;
    private String status;
    private Number cep;
    private String tipo;
    private Boolean financiamento;
    private String descricao;
    private String contrato;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;
    @Column(precision = 10, scale = 8)
    private BigDecimal longitude;
    @ManyToOne
    @JoinColumn(name = "corretor-id")
    private Corretor corretor;


}
