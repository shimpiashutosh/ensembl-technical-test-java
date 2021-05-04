package uk.ac.ebi.ensembl.search.gene.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.ensembl.search.gene.service.db.SearchGeneDBService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static uk.ac.ebi.ensembl.search.gene.exception.SearchGeneException.dataNotFound;

@Validated
@RestController
public class SearchGeneController {

    private final SearchGeneDBService searchGeneDBService;

    public SearchGeneController(final SearchGeneDBService searchGeneDBService) {
        this.searchGeneDBService = searchGeneDBService;
    }

    @GetMapping("/gene-suggestions")
    public ResponseEntity<List<String>> searchGene(@NotEmpty @RequestParam final String query,
                                                   @NotEmpty @RequestParam final String species,
                                                   @NotNull @RequestParam final Integer limit) {
        final List<String> searchGenes = searchGeneDBService.searchGene(query, species, limit);
        if (searchGenes.isEmpty()) {
            throw dataNotFound();
        }
        return ok(searchGenes);
    }
}
