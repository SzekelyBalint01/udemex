package udemx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UdemxApplication {
    private static final Logger logger = LoggerFactory.getLogger(UdemxApplication.class);

    public static void main(String[] args) {

        // Logolás az alkalmazás indulása előtt
        logger.info("Az alkalmazás indul...");

        try {
            // Spring Boot alkalmazás indítása
            SpringApplication.run(UdemxApplication.class, args);

            // Logolás az alkalmazás sikeres indulása után
            logger.info("Az alkalmazás sikeresen elindult!");
        } catch (Exception e) {
            // Hiba logolása, ha valami nem sikerült
            logger.error("Hiba történt az alkalmazás indítása során.", e);
        }
    }


}
