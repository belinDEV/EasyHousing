package easyhousing.easyhousing.Repository;

import easyhousing.easyhousing.Model.Corretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorretorRepository extends JpaRepository<Corretor, Long> {

}
