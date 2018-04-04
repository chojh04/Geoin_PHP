package kr.co.kpcard.messenger.app;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory 
											implements ApplicationContextAware{

	
	private transient AutowireCapableBeanFactory beanFactory;

	@Override
	public void setApplicationContext(final ApplicationContext context) throws BeansException 
	{
		// TODO Auto-generated method stub
		beanFactory = context.getAutowireCapableBeanFactory();
	}
	
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception
	{
		// TODO Auto-generated method stub
		final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        return job;
	}

}
