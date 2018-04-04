package kr.co.kpcard.messenger.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import kr.co.kpcard.messenger.app.job.SendingMessageJob;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class SchedulerConfiguration {

	private static final Logger logger 
		= LoggerFactory.getLogger(SchedulerConfiguration.class);

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) 
	{
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory
			, @Qualifier("SendingMessageJobTrigger") Trigger sendingMessageJobTrigger
			) throws IOException {
		
		logger.debug("========= schedulerFactoryBean start ==========");
		logger.info("database :" +dataSource);
		
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setAutoStartup(true);
		//factory.setDataSource(dataSource);
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());
		
		List<Trigger> triggers = new ArrayList<>();
        
        triggers.add(sendingMessageJobTrigger);
		
		factory.setTriggers(triggers.toArray(new Trigger[triggers.size()]));
		
		logger.debug("========= schedulerFactoryBean End ==========");
		
		return factory;
	}
	
	@Bean(name = "SendingMessageJobTrigger")
	public CronTriggerFactoryBean SendingMessageJobTrigger(@Qualifier("SendingMessageJobDetail") JobDetail jobDetail
						, @Value("${cronJob.expression}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}
	
	@Bean
	public JobDetailFactoryBean SendingMessageJobDetail()
	{
		return createJobDetail(SendingMessageJob.class);
	}
	
	@Bean
	public Properties quartzProperties() throws IOException 
	{
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
	
	@SuppressWarnings("rawtypes")
	private static JobDetailFactoryBean createJobDetail(Class jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        // job has to be durable to be stored in DB:
        factoryBean.setDurability(true);
        return factoryBean;
    }

    // Use this method for creating cron triggers instead of simple triggers:
    private static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
    	
    	logger.info("cronExpressionCHECK=>"+cronExpression);
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        return factoryBean;
    }

}
