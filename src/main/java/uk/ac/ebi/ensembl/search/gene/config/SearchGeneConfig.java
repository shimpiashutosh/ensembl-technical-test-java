package uk.ac.ebi.ensembl.search.gene.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import uk.ac.ebi.ensembl.search.gene.service.db.SearchGeneDBService;
import uk.ac.ebi.ensembl.search.gene.service.db.SearchGeneDBServiceImpl;
import uk.ac.ebi.ensembl.search.gene.service.db.data.extractor.SearchGeneResultSetExtractor;

@Configuration
public class SearchGeneConfig {

    @Bean
    public SearchGeneDBService searchGeneDBService(final NamedParameterJdbcTemplate geneJdbcTemplate,
                                                   final SearchGeneResultSetExtractor searchGeneResultSetExtractor) {
        return new SearchGeneDBServiceImpl(
                geneJdbcTemplate,
                searchGeneResultSetExtractor
        );
    }

    @Bean
    public SearchGeneResultSetExtractor searchGeneResultSetExtractor() {
        return new SearchGeneResultSetExtractor();
    }
}
