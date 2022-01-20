package com.example.demo.configuration;

import javax.persistence.EntityManagerFactory;

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

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "odbEntityManager",
        transactionManagerRef = "odbTransactionManager",
        basePackages = "com.example.demo.repository"
)
public class InitJpaOdbConnection {
	
	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.common")
	public DataSourceProperties firstDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.common")
	public HikariDataSource firstDataSource() {
		return firstDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
	
	@Primary
    @Bean(name = "odbEntityManager")
    public LocalContainerEntityManagerFactoryBean odbEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                    .dataSource(firstDataSource())
                    .packages("com.example.demo.model")
                    .persistenceUnit("odbPU")
                    .build();
    }
 
	@Primary
    @Bean(name = "odbTransactionManager")
    public PlatformTransactionManager odbTransactionManager(@Qualifier("odbEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
