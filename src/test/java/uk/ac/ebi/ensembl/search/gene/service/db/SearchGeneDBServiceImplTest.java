package uk.ac.ebi.ensembl.search.gene.service.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import uk.ac.ebi.ensembl.search.gene.config.SearchGeneConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.ensembl.search.gene.constants.DataConstants.GENE1;
import static uk.ac.ebi.ensembl.search.gene.constants.DataConstants.GENE2;
import static uk.ac.ebi.ensembl.search.gene.constants.DataConstants.GENE3;
import static uk.ac.ebi.ensembl.search.gene.constants.DataConstants.GENE4;

@Import(SearchGeneConfig.class)
@JdbcTest
public class SearchGeneDBServiceImplTest {

    @Autowired
    private SearchGeneDBService searchGeneDBService;

    @Sql({"/jdbc/search/gene/clean_database.sql", "/jdbc/search/gene/gene_autocomplete_schema.sql", "/jdbc/search/gene/gene_autocomplete_dataset.sql"})
    @Test
    public void searchGene_WhenCall_ThenReturnListOfGenesSuccessfully() {
        //Given: schema created & data loaded through .sql script

        //Test: following method to be tested
        final List<String> searchGenes = searchGeneDBService.searchGene("BRCA", "homo_sapiens", 4);

        //Assert
        assertThat(searchGenes)
                .isNotNull()
                .hasSize(4)
                .containsOnly(GENE1, GENE2, GENE3, GENE4);

    }

    @Sql({"/jdbc/search/gene/clean_database.sql", "/jdbc/search/gene/gene_autocomplete_schema.sql", "/jdbc/search/gene/gene_autocomplete_dataset.sql"})
    @Test
    public void searchGene_WhenCallWithCaseInSensitiveData_ThenReturnListOfGenesSuccessfully() {
        //Given: schema created & data loaded through .sql script

        //Test: following method to be tested
        final List<String> searchGenes = searchGeneDBService.searchGene("BrcA", "homo_sapiens", 4);

        //Assert
        assertThat(searchGenes)
                .isNotNull()
                .hasSize(4)
                .containsOnly(GENE1, GENE2, GENE3, GENE4);

    }

    @Sql({"/jdbc/search/gene/clean_database.sql", "/jdbc/search/gene/gene_autocomplete_schema.sql", "/jdbc/search/gene/gene_autocomplete_dataset.sql"})
    @Test
    public void searchGene_WhenCallWithLimit_ThenReturnLimitedListOfGenesSuccessfully() {
        //Given: schema created & data loaded through .sql script

        //Test: following method to be tested
        final List<String> searchGenes = searchGeneDBService.searchGene("BRCA", "homo_sapiens", 2);

        //Assert
        assertThat(searchGenes)
                .isNotNull()
                .hasSize(2)
                .containsOnly(GENE1, GENE2);

    }

    @Sql({"/jdbc/search/gene/clean_database.sql", "/jdbc/search/gene/gene_autocomplete_schema.sql", "/jdbc/search/gene/gene_autocomplete_dataset.sql"})
    @Test
    public void searchGene_WhenCallWithMissingData_ThenReturnEmptyList() {
        //Given: schema created & data loaded through .sql script

        //Test: following method to be tested
        final List<String> searchGenes = searchGeneDBService.searchGene("wrong-data", "homo_sapiens", 2);

        //Assert
        assertThat(searchGenes)
                .isNotNull()
                .isEmpty();
    }
}
