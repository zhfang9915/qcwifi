package ltd.qcwifi.backend.jobs;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

@Component("qcwifiJobFactory")
public class QcwifiJobFactory extends AdaptableJobFactory implements ApplicationContextAware {

	ApplicationContext applicationContext;

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		// 调用父类的方法
		Object jobInstance = super.createJobInstance(bundle);

		// 进行注入
		AutowireCapableBeanFactory aaa = applicationContext.getAutowireCapableBeanFactory();
		aaa.autowireBeanProperties(jobInstance, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
		return jobInstance;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

}
