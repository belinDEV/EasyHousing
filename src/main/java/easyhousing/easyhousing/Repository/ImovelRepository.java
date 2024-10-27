package easyhousing.easyhousing.Repository;

import easyhousing.easyhousing.Model.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImovelRepository extends JpaRepository<Imovel, Long> {
    Optional<Imovel> findByUrn(String urn);
}
