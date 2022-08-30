package toy.AllAttributeOfJPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class AllAttributeOfJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllAttributeOfJpaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

			String loginId = (String) attr.getRequest().getSession().getAttribute("loginId");

			if (loginId != null) {
				return Optional.of(loginId);
			}
			else {
				return Optional.of("Anonymous");
			}
		};
	}
}
