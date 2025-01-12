package guruspringframework.sdjpamultidb.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
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
    public SpringLiquibase liquibaseCard(@Qualifier("cardLiquibaseDataSourceProps")
                                         DataSourceProperties cardLiquibaseDataSourceProps) {
        SpringLiquibase liquibase = new SpringLiquibase();
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
    public SpringLiquibase liquibaseCardHolder(@Qualifier("cardholderLiquibaseDataSourceProps")
                                               DataSourceProperties cardholderLiquibaseDataSourceProps) {
        SpringLiquibase liquibase = new SpringLiquibase();
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
    public SpringLiquibase liquibasePan(@Qualifier("panLiquibaseDataSourceProps")
                                        DataSourceProperties panLiquibaseDataSourceProps) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(panLiquibaseDataSourceProps.initializeDataSourceBuilder().build());
        liquibase.setChangeLog("classpath:/db/changelog/pan/changelog-master.xml");
        return liquibase;
    }
}