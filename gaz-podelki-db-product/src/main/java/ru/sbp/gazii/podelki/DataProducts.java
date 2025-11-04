package ru.sbp.gazii.podelki;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.sbp.gazii.podelki.repository.ClientRepository;
import ru.sbp.gazii.podelki.repository.OrderRepository;
import ru.sbp.gazii.podelki.repository.ProductRepository;

@SpringBootApplication
@Slf4j
public class DataProducts {
    public static void main(String[] args) {
        SpringApplication.run(DataProducts.class, args);
        log.info("Gaz Podelki Database Application started successfully!");
    }

    @Bean
    public CommandLineRunner demoData(ClientRepository clientRepo,
                                      ProductRepository productRepo,
                                      OrderRepository orderRepo) {
        return args -> {
            log.info("Demo data initialized successfully");
        };
    }
}