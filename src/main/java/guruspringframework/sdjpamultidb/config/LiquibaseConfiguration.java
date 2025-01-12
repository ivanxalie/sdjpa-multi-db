package guruspringframework.sdjpamultidb.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jt on 7/1/22.
 */
@Configuration
public class LiquibaseConfiguration {

    @Bean
    @ConfigurationProperties("spring.card.liquibase")
    public DataSourceProperties cardLiquibaseDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSourceClosingSpringLiquibase liquibaseCard(@Qualifier("cardLiquibaseDataSourceProps")
                                                          DataSourceProperties cardLiquibaseDataSourceProps) {
        DataSourceClosingSpringLiquibase liquibase = new DataSourceClosingSpringLiquibase();
        liquibase.setDataSource(cardLiquibaseDataSourceProps.initializeDataSourceBuilder().build());
        liquibase.setChangeLog("classpath:/db/changelog/card/changelog-master.xml");
        return liquibase;
    }

    @Bean
    @ConfigurationProperties("spring.cardholder.liquibase")
    public DataSourceProperties cardholderLiquibaseDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSourceClosingSpringLiquibase liquibaseCardHolder(@Qualifier("cardholderLiquibaseDataSourceProps")
                                                                DataSourceProperties cardholderLiquibaseDataSourceProps) {
        DataSourceClosingSpringLiquibase liquibase = new DataSourceClosingSpringLiquibase();
        liquibase.setDataSource(cardholderLiquibaseDataSourceProps.initializeDataSourceBuilder().build());
        liquibase.setChangeLog("classpath:/db/changelog/cardholder/changelog-master.xml");
        return liquibase;
    }

    @Bean
    @ConfigurationProperties("spring.pan.liquibase")
    public DataSourceProperties panLiquibaseDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSourceClosingSpringLiquibase liquibasePan(@Qualifier("panLiquibaseDataSourceProps")
                                                         DataSourceProperties panLiquibaseDataSourceProps) {
        DataSourceClosingSpringLiquibase liquibase = new DataSourceClosingSpringLiquibase();
        liquibase.setDataSource(panLiquibaseDataSourceProps.initializeDataSourceBuilder().build());
        liquibase.setChangeLog("classpath:/db/changelog/pan/changelog-master.xml");
        return liquibase;
    }
}