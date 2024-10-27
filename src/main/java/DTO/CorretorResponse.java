package DTO;

import Model.Corretor;



public record CorretorResponse(Long id,
                               String nome,
                               String email,
                               String identificador,
                               String contato,
                               String descricao) {
    public CorretorResponse (  Corretor corretor){
        this(corretor.getId(),
                corretor.getNome(),
                corretor.getEmail(),
                corretor.getIdentificador(),
                corretor.getContato(),
                corretor.getDescricao());
    }


}
