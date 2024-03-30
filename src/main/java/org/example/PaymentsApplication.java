package org.example;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.model.entities.Category;
import org.example.model.entities.Payment;
import org.example.model.entities.PaymentCategory;
import org.example.model.entities.PaymentMethod;
import org.example.model.statistic.user.Period;
import org.hibernate.cfg.Configuration;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class PaymentsApplication {

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(PaymentsApplication.class, args);
	}
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		var conf = new Configuration()
				.configure("hibernate-postgres.cfg.xml")
				.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5434/teamscore_Java23")
				.setProperty("hibernate.connection.username", "postgres")
				.setProperty("hibernate.connection.password", "root");
		return conf
				.addAnnotatedClass(Category.class)
				.addAnnotatedClass(Payment.class)
				.addAnnotatedClass(PaymentMethod.class)
				.addAnnotatedClass(PaymentCategory.class)
				.buildSessionFactory();
	}

	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		//entityManagerFactory.close();
		return entityManagerFactory.createEntityManager();
	}
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
				// LOOSE - мягкое связывание для вложенной сущности Item
				// (проверяет совпадение по имени поля)
				// При этом price есть и в OrderItem, и в Item - будет взят из "верхней" сущности OrderItem        modelMapper.addConverter(itemStatusConverter);
				.setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;
	}
}
