package kr.co.kpcard.messenger.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
@Configuration
@MapperScan(basePackages="kr.co.kpcard.messenger.app.repository.common", sqlSessionFactoryRef="commonSqlFactory")
@EnableTransactionManagement
public class CommonDatabaseConfig {

	private Logger logger = LoggerFactory.getLogger(CommonDatabaseConfig.class);
	
    @Bean(name = "commonDataSource")  
    @ConfigurationProperties(prefix = "spring.db2.datasource.hikari")
	public DataSource backOfficeDataSource(@Qualifier("commonHikariConfig") HikariConfig hikariConfig) {
	    	HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
	    	logger.debug("commonDataSource getMaximumPoolSize : {} " , hikariDataSource.getMaximumPoolSize());
	    	logger.debug("commonDataSource getConnectionTimeout : {} " , hikariDataSource.getConnectionTimeout());
	    	logger.debug("getJdbcUrl : {} " , hikariDataSource.getJdbcUrl());
	    	logger.debug("getUsername() : {} " , hikariDataSource.getUsername());
	    	logger.debug("getPassword() : {} " , hikariDataSource.getPassword());
	        return hikariDataSource;
	}


    @Bean(name = "commonSqlFactory")    
    public SqlSessionFactory backOfficeSqlSessionFactory(@Qualifier("commonDataSource") DataSource backOfficeDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(backOfficeDataSource);
        return sessionFactory.getObject();
    }

    @Bean(name = "commonSqlTemplate")    
    public SqlSessionTemplate backOfficeSqlSessionTemplate(SqlSessionFactory backOfficeSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(backOfficeSqlSessionFactory);
    }
	
	@Bean(name = "commonHikariConfig")  
    @ConfigurationProperties(prefix = "spring.db2.datasource.hikari")
    public HikariConfig hikariConfig() 
	{
    	return new HikariConfig();
    } 
}
