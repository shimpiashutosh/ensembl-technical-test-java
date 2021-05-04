package uk.ac.ebi.ensembl.search.gene.constants;

public interface SQLQueries {
    // @formatter:off
    String SEARCH_GENE =
            "SELECT " +
            "  display_label, " +
            "  species " +
            "FROM " +
            "  gene_autocomplete " +
            "WHERE " +
            "  lower(display_label) like lower(:query) " +
            "AND " +
            "  lower(species) = lower(:species) " +
            "ORDER BY " +
            "  display_label ASC " +
            "LIMIT :limit";
    // @formatter:on
}
