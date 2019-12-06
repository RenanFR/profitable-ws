package com.profitable.ws.config;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class H2DataSourceConfig {
	
	@Bean(name = "h2-data-source")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "h2-entity-manager-factory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
		EntityManagerFactoryBuilder builder,
		@Qualifier("h2-data-source") DataSource dataSource
	) {
		Map<String, String> properties = Map.of("hibernate.dialect", "org.hibernate.dialect.H2Dialect", "hibernate.hbm2ddl.auto", "create-drop");
		return
			builder
				.dataSource(dataSource)
			    .properties(properties)
			    .persistenceUnit("profitable-h2")
			    .build();
	}	
	  
	@Bean(name = "h2-tx")
	public PlatformTransactionManager transactionManager(
		@Qualifier("h2-entity-manager-factory") EntityManagerFactory factory
	) {
	    return new JpaTransactionManager(factory);
	}	
	
}
