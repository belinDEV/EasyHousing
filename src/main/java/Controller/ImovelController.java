package Controller;


import DTO.CorretorResponse;
import DTO.ImovelResponse;
import Model.Imovel;
import Repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@Controller
@RequestMapping("imovel")
public class ImovelController {
    private static final Logger LOGGER = Logger.getLogger(ImovelController.class.getName());

    @Autowired
    ImovelRepository repository;

    @GetMapping
    public List<ImovelResponse> getAll(){
        List<ImovelResponse> imovelList = repository.findAll().stream().map(ImovelResponse::new).toList();
        return imovelList;
    }


}
