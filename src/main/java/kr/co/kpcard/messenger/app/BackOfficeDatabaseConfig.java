package kr.co.kpcard.messenger.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages="kr.co.kpcard.messenger.app.repository.backoffice", sqlSessionFactoryRef="backOfficeSqlFactory")
@EnableTransactionManagement
public class BackOfficeDatabaseConfig {

	private Logger logger = LoggerFactory.getLogger(BackOfficeDatabaseConfig.class);
	
	@Primary
    @Bean(name = "backOfficeDataSource")  
    @ConfigurationProperties(prefix = "spring.db1.datasource.hikari")
	public DataSource backOfficeDataSource(@Qualifier("backOfficeHikariConfig") HikariConfig hikariConfig) {
	    	HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
	    	logger.debug("backOfficeDataSource getMaximumPoolSize : {} " , hikariDataSource.getMaximumPoolSize());
	    	logger.debug("backOfficeDataSource getConnectionTimeout : {} " , hikariDataSource.getConnectionTimeout());
	    	logger.debug("getJdbcUrl : {} " , hikariDataSource.getJdbcUrl());
	    	logger.debug("getUsername() : {} " , hikariDataSource.getUsername());
	    	logger.debug("getPassword() : {} " , hikariDataSource.getPassword());
	        return hikariDataSource;
	}


	@Primary
    @Bean(name = "backOfficeSqlFactory")    
    public SqlSessionFactory backOfficeSqlSessionFactory(@Qualifier("backOfficeDataSource") DataSource backOfficeDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(backOfficeDataSource);
        return sessionFactory.getObject();
    }

	@Primary
    @Bean(name = "backOfficeSqlTemplate")    
    public SqlSessionTemplate backOfficeSqlSessionTemplate(SqlSessionFactory backOfficeSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(backOfficeSqlSessionFactory);
    }
	
	@Primary
	@Bean(name = "backOfficeHikariConfig")  
    @ConfigurationProperties(prefix = "spring.db1.datasource.hikari")
    public HikariConfig hikariConfig() 
	{
    	return new HikariConfig();
    } 
}
