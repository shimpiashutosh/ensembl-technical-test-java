package uk.ac.ebi.ensembl.search.gene.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.ebi.ensembl.search.gene.service.db.SearchGeneDBService;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.ac.ebi.ensembl.search.gene.constants.DataConstants.GENE1;
import static uk.ac.ebi.ensembl.search.gene.constants.DataConstants.GENE2;
import static uk.ac.ebi.ensembl.search.gene.constants.DataConstants.GENE3;
import static uk.ac.ebi.ensembl.search.gene.constants.DataConstants.GENE4;

@WebMvcTest(controllers = SearchGeneController.class)
public class SearchGeneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchGeneDBService searchGeneDBService;

    @Test
    public void searchGene_WhenCallTheAPI_ThenReturnsListOfGenesSuccessfully() throws Exception {
        when(searchGeneDBService
                .searchGene(anyString(), anyString(), anyInt()))
                .thenReturn(List.of(
                        GENE1, GENE2, GENE3, GENE4
                ));

        this.mockMvc
                .perform(get("/gene-suggestions?query=brc&species=homo_sapiens&limit=4")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", containsInAnyOrder(
                        GENE1, GENE2, GENE3, GENE4)
                ));
    }

    @Test
    public void searchGene_WhenCallTheAPI_ThenReturnsLimitedListOfGenesSuccessfully() throws Exception {
        when(searchGeneDBService
                .searchGene(anyString(), anyString(), anyInt()))
                .thenReturn(List.of(
                        GENE1, GENE2
                ));

        this.mockMvc
                .perform(get("/gene-suggestions?query=brc&species=homo_sapiens&limit=2")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", containsInAnyOrder(
                        GENE1, GENE2)
                ));
    }

    @Test
    public void searchGene_WhenCallTheAPIWithEmptyQueryValue_ThenReturnsBadRequest() throws Exception {
        this.mockMvc
                .perform(get("/gene-suggestions?query=&species=homo_sapiens&limit=2")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchGene_WhenCallTheAPIWithInvalidLimitValue_ThenReturnsBadRequest() throws Exception {
        this.mockMvc
                .perform(get("/gene-suggestions?query=brc&species=homo_sapiens&limit=L")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
