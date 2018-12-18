package com.ssobackend.config.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EntityScan("com.ssobackend.domain.model") 
@EnableJpaRepositories(basePackages = "com.ssobackend.domain.repository",
entityManagerFactoryRef = "globalEntityManagerFactory", transactionManagerRef = "globalTransactionManager")
@PropertySource("classpath:connection-pool.properties")
@Profile("!test")
public class GlobalSchemaHibernateConfig {
	@Autowired
	private JpaProperties jpaProperties;
	@Autowired
    private Environment env;
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}
	
	@Primary
	@Bean(name="globalDataSource")
	public DataSource globalDataSource() {
		HikariConfig config = new HikariConfig();
		String globalSchemaName = env.getProperty("datasource.globalSchemaName");
		
		config.setJdbcUrl(env.getProperty("datasource.url")+globalSchemaName);
		config.setUsername(env.getProperty("datasource.username"));
		config.setPassword(env.getProperty("datasource.password"));
		
		config.setDriverClassName(env.getProperty("globalCP.driverClassName"));
		config.setPoolName(env.getProperty("globalCP.poolName"));
		config.setMaximumPoolSize(Integer.parseInt(env.getProperty("globalCP.maximumPoolSize")));
		config.setIdleTimeout(Long.parseLong(env.getProperty("globalCP.idleTimeout")));
		config.setMinimumIdle(Integer.parseInt(env.getProperty("globalCP.minimumIdle")));
		
		HikariDataSource datasource = new HikariDataSource(config);
		return datasource;
	}
	
	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean globalEntityManagerFactory() {
		Map<String, Object> properties = new HashMap<>();
		properties.putAll(jpaProperties.getHibernateProperties(globalDataSource()));
		properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.naming-strategy", env.getRequiredProperty("hibernate.naming-strategy"));
		properties.put("javax.persistence.schema-generation.create-source", "metadata");
		properties.put("javax.persistence.schema-generation.scripts.action", "create");
		properties.put("javax.persistence.schema-generation.scripts.create-target",
				"src/main/resources/META-INF/sql/ddl-global.sql");

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(globalDataSource());
		em.setPackagesToScan("com.ssobackend.domain");
		//em.setPa
		
		em.setJpaVendorAdapter(jpaVendorAdapter());
		em.setJpaPropertyMap(properties);
		return em;
	}
	
	@Primary
	@Bean(name = "globalTransactionManager")
    public PlatformTransactionManager transactionManagerGlobal() {
          JpaTransactionManager tm = new JpaTransactionManager();
          tm.setEntityManagerFactory(globalEntityManagerFactory().getObject());
          tm.setDataSource(globalDataSource());
          return tm;
    }
}