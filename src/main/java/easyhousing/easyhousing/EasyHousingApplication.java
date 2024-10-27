package easyhousing.easyhousing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"easyhousing/easyhousing/Model"})
@EnableJpaRepositories("easyhousing.easyhousing.Repository")  // Pacote dos repositórios
public class EasyHousingApplication {
	public static void main(String[] args) {
		SpringApplication.run(EasyHousingApplication.class, args);
	}
}