package uk.ac.ebi.ensembl.search.gene.service.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import uk.ac.ebi.ensembl.search.gene.service.db.data.extractor.SearchGeneResultSetExtractor;

import java.util.List;

import static uk.ac.ebi.ensembl.search.gene.constants.SQLQueries.SEARCH_GENE;

public class SearchGeneDBServiceImpl implements SearchGeneDBService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchGeneDBServiceImpl.class);

    private final NamedParameterJdbcTemplate geneJdbcTemplate;
    private final SearchGeneResultSetExtractor searchGeneResultSetExtractor;

    public SearchGeneDBServiceImpl(final NamedParameterJdbcTemplate geneJdbcTemplate,
                                   final SearchGeneResultSetExtractor searchGeneResultSetExtractor) {
        this.geneJdbcTemplate = geneJdbcTemplate;
        this.searchGeneResultSetExtractor = searchGeneResultSetExtractor;
    }

    @Override
    public List<String> searchGene(final String query,
                                   final String species,
                                   final int limit) {
        LOGGER.info("Fetching data from gene_autocomplete where query: {} & species: {} having row limit: {}", query, species, limit);

        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("query", query + "%");
        parameters.addValue("species", species);
        parameters.addValue("limit", limit);

        final List<String> geneAutoCompleteSearches = geneJdbcTemplate.query(
                SEARCH_GENE,
                parameters,
                searchGeneResultSetExtractor.getGeneAutoCompleteSearchResultSetExtractor());

        LOGGER.info("Total {} no. of records fetched from gene_autocomplete", geneAutoCompleteSearches.size());
        return geneAutoCompleteSearches;
    }
}
