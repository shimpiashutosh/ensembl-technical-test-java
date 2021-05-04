package uk.ac.ebi.ensembl.search.gene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SearchGeneApplication {
    public static void main(String... args) {
        SpringApplication.run(SearchGeneApplication.class, args);
    }
}
