package easyhousing.easyhousing.DTO;

public record CorretorRequest(
        String email,
        String identificador,
        String descricao,
        String nome,
        String contato
) {}
