package nl.quintor.app.config;

import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@PropertySource(value = {
        "classpath:application.properties",
        "classpath:application.${spring.profiles.active}.properties"}, ignoreResourceNotFound = true)

@EnableJpaRepositories(basePackages = {"nl.quintor.app.repository"},
        entityManagerFactoryRef = "applicationEntityManagerFactory",
        transactionManagerRef= "applicationTransactionManager")

public class RepositoryConfig {
    @Autowired
    Environment env;

    @Autowired
    Logger logger;

    @Bean
    @ConfigurationProperties(value = "spring.datasource")
    public DataSource dataSource(){
        DataSource dataSource = DataSourceBuilder.create().build();
        logger.debug("Data source created");
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean applicationEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("nl.quintor.app.model")
                .build();
    }

    @Bean
    public PlatformTransactionManager applicationTransactionManager(
            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean applicationEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(applicationEntityManagerFactory.getObject()));
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(this.dataSource());
        liquibase.setChangeLog("classpath:db/changelog-master.yml");

        return liquibase;
    }
}