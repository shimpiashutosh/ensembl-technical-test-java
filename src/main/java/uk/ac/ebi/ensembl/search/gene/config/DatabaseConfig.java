package uk.ac.ebi.ensembl.search.gene.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean("gene_datasource_properties")
    @ConfigurationProperties("datasource.gene")
    public DataSourceProperties geneDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("gene_datasource")
    @ConfigurationProperties("datasource.gene.hikari")
    public DataSource geneDataSource(@Qualifier("gene_datasource_properties") final DataSourceProperties dataSourceProperties) {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("gene_jdbc_template")
    public NamedParameterJdbcTemplate geneJdbcTemplate(@Qualifier("gene_datasource") final DataSource geneDataSource) {
        return new NamedParameterJdbcTemplate(geneDataSource);
    }

    @Bean("gene_transaction_manager")
    public DataSourceTransactionManager geneTransactionManager(@Qualifier("gene_datasource") final DataSource geneDataSource) {
        return new DataSourceTransactionManager(geneDataSource);
    }
}
