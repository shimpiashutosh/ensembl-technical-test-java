package uk.ac.ebi.ensembl.search.gene.service.db.data.extractor;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.ArrayList;
import java.util.List;

public class SearchGeneResultSetExtractor {
    /**
     * ResultSet extractor to extract data
     *
     * @return ResultSetExtractor object
     */
    public ResultSetExtractor<List<String>> getGeneAutoCompleteSearchResultSetExtractor() {
        return resultSet -> {
            final List<String> geneAutoCompleteSearches = new ArrayList<>();
            while (resultSet.next()) {
                geneAutoCompleteSearches.add(resultSet.getString("display_label"));
            }
            return geneAutoCompleteSearches;
        };
    }
}
