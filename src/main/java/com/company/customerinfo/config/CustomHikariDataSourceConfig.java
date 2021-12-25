package com.company.customerinfo.config;


import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariDataSource;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "customEntityManagerFactory",
        basePackages = {"com.company.customerinfo.repository"},
        transactionManagerRef = "customTransactionManager"
)
public class CustomHikariDataSourceConfig {

    @Primary
    @Bean(name = "customHikariDataSource", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource customHikariDataSource(DataSourceProperties properties) {

        MetricRegistry metricRegistry = new MetricRegistry();
        HikariDataSource hikariDataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
        hikariDataSource.setMetricRegistry(metricRegistry);
        return hikariDataSource;
    }

    @Primary
    @Bean(name = "customEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("customHikariDataSource") DataSource customHikariDataSource
    ) {
        return builder
                .dataSource(customHikariDataSource)
                .packages("com.company.customerinfo.model")
                .persistenceUnit("customHikariDataSourcePersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "customTransactionManager")
    public PlatformTransactionManager customTransactionManager (
            @Qualifier("customEntityManagerFactory")EntityManagerFactory customEntityManagerFactory,
            @Qualifier("customHikariDataSource") DataSource customHikariDataSource){

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(customEntityManagerFactory);
        jpaTransactionManager.setDataSource(customHikariDataSource);
        return jpaTransactionManager;
    }

}
