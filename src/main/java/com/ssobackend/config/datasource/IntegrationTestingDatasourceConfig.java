package com.ssobackend.config.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Profile("test")
@Configuration
@EnableJpaRepositories(basePackages = "com.ssobackend.domain.repository", 
entityManagerFactoryRef = "tenantEntityManager", transactionManagerRef="tenantTransactionManager")
@PropertySource("classpath:connection-pool.properties")
public class IntegrationTestingDatasourceConfig {
	
	@Autowired
	private JpaProperties jpaProperties;
	@Autowired
    private org.springframework.core.env.Environment env;
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}
	
	@Bean(name="globalDataSource")
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:hsqldb:mem:testdb");
        return dataSource;
    }
	
	@Bean(name="tenantEntityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		Map<String, Object> properties = new HashMap<>();
		properties.putAll(jpaProperties.getHibernateProperties(dataSource()));

		properties.put("org.hibernate.envers.audit_table_suffix", env.getRequiredProperty("hibernate.audit_table_suffix"));
		properties.put("org.hibernate.envers.revision_field_name", env.getRequiredProperty("hibernate.revision_field_name"));
		properties.put("org.hibernate.envers.revision_type_field_name", env.getRequiredProperty("hibernate.revision_type_field_name"));
		
		
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("com.ssobackend.domain.model");
		
		em.setJpaVendorAdapter(jpaVendorAdapter());
		em.setJpaPropertyMap(properties);
		return em;
	}
	
	@Bean(name = "tenantTransactionManager")
    public PlatformTransactionManager transactionManagerGlobal() {
          JpaTransactionManager tm = new JpaTransactionManager();
          tm.setEntityManagerFactory(entityManagerFactory().getObject());
          tm.setDataSource(dataSource());
          return tm;
    }
}
