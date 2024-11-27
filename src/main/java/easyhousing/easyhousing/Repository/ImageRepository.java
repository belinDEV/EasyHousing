package easyhousing.easyhousing.Repository;



import easyhousing.easyhousing.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i WHERE i.imovel.id = :imovelId")
    List<Image> findByImovelId(@Param("imovelId") Long imovelId);

    void deleteByImovelId(Long imovelId);
}

