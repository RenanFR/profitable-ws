package com.profitable.ws.config;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "pg-entity-manager-factory",
		transactionManagerRef = "pg-tx",
		basePackages = "com.profitable.ws.repositories"
)
public class PostgreSQLDataSourceConfig {
	
	@Primary
	@Bean(name = "pg-data-source")
	@ConfigurationProperties(prefix = "spring.datasource.pg")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "pg-entity-manager-factory")
	public LocalContainerEntityManagerFactoryBean factoryBean(EntityManagerFactoryBuilder builder, @Qualifier("pg-data-source") DataSource dataSource) {
		Map<String, String> properties = Map.of("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		return builder
				.dataSource(dataSource)
				.properties(properties)
				.packages("com.profitable.ws.model.entity")
				.persistenceUnit("profitable-pg")
				.build();
	}
	
	@Primary
	@Bean(name = "pg-tx")
	public PlatformTransactionManager transactionManager(@Qualifier("pg-entity-manager-factory") EntityManagerFactory factory) {
		return new JpaTransactionManager(factory);
	}

}
