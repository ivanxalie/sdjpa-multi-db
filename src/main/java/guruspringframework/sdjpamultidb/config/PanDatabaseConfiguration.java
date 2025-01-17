package guruspringframework.sdjpamultidb.config;

import com.zaxxer.hikari.HikariDataSource;
import guruspringframework.sdjpamultidb.domain.pan.CreditCardPAN;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by jt on 7/1/22.
 */
@EnableJpaRepositories(basePackages = "guruspringframework.sdjpamultidb.repositories.pan",
        entityManagerFactoryRef = "panEntityManagerFactory", transactionManagerRef = "panTransactionManager")
@Configuration
public class PanDatabaseConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("spring.pan.datasource")
    public DataSourceProperties panDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.pan.datasource.hikari")
    public DataSource panDataSource(@Qualifier("panDataSourceProperties") DataSourceProperties panDataSourceProperties) {
        return panDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean panEntityManagerFactory(
            @Qualifier("panDataSource") DataSource panDataSource,
            EntityManagerFactoryBuilder builder,
            @Qualifier(AppConfig.DEFAULT_VALIDATE_MANAGER_PROPERTY_BEAN) Properties properties) {
        LocalContainerEntityManagerFactoryBean bean = builder.dataSource(panDataSource)
                .packages(CreditCardPAN.class)
                .persistenceUnit("pan")
                .build();
        bean.setJpaProperties(properties);
        return bean;
    }

    @Primary
    @Bean
    public PlatformTransactionManager panTransactionManager(
            @Qualifier("panEntityManagerFactory") LocalContainerEntityManagerFactoryBean panEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(panEntityManagerFactory.getObject()));
    }
}
