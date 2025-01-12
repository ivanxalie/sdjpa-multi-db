package guruspringframework.sdjpamultidb.config;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class AppConfig {
    public static final String DEFAULT_VALIDATE_MANAGER_PROPERTY_BEAN = "defaultManagerPropertyBean";

    @Bean(DEFAULT_VALIDATE_MANAGER_PROPERTY_BEAN)
    public Properties emValidateDefaultProperties() {
        Properties properties = new Properties();
        properties.put(AvailableSettings.HBM2DDL_AUTO, "validate");
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        return properties;
    }
}
