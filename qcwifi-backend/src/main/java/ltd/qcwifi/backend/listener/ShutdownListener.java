package ltd.qcwifi.backend.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import ltd.qcwifi.backend.jobs.util.ListenerFuturePool;

/**
 * @Description:Tomcat的shutdown的事件监听
 * @Author:zhfang
 * @Date:2017/12/28
 */
public class ShutdownListener implements ServletContextListener {

    private static Logger logger = Logger.getLogger(ShutdownListener.class);

    public void contextDestroyed(ServletContextEvent arg0) {
        logger.debug("web server contextDestroyed");

        try {
        	ListenerFuturePool.pool.shutdown();
        } catch (Throwable e) {
            logger.fatal("关闭共用线程池失败", e);
        }
        logger.debug("DBPool shutdown done");
    }

    public void contextInitialized(ServletContextEvent arg0) {
        logger.debug("web server contextInitialized");
    }


}
