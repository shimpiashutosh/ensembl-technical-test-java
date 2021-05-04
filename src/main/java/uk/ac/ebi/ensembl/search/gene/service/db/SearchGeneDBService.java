package uk.ac.ebi.ensembl.search.gene.service.db;

import java.util.List;

public interface SearchGeneDBService {
    /**
     * Method search for genes in database based on input provided
     *
     * @param query gene to search
     * @param species type of species
     * @param limit no. records to return
     *
     * @return list of gene names
     */
    List<String> searchGene(String query, String species, int limit);
}
