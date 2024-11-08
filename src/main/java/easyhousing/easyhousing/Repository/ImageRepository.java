package easyhousing.easyhousing.Repository;



import easyhousing.easyhousing.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    static List<Image> findByImovelId(Long imovelId) {
        return null;
    }

    void deleteByImovelId(Long imovelId);
}
