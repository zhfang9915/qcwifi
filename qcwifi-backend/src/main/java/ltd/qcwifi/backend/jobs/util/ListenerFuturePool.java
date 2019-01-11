package ltd.qcwifi.backend.jobs.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ListenerFuturePool {

    private static final int QUEUE_SIZE = 5000;
    
    public static final ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(QUEUE_SIZE));
    
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger("pool");
    
    private static Map<String, String> map = new HashMap<>();

    private static Timer timer = new Timer();

    static {

        timer.schedule(new TimerTask() {
                           public void run() {
                               synchronized (map) {
                                   logger.info("线程池大小：{}", map.size());
                                   if (map.size() < 5000)
                                       logger.info("json出参：" + JSONObject.toJSONString(map));
                               }
                           }
                       }
                , 0, 1000 *60* 60);//测试可以改小如1000 * 5
    }
    
    public static void threadMethod(final BaseObject bean){
    	
    	if (null == bean)
            return;
    	ListenableFuture<String> listenableFuture = pool.submit(new Callable<String>() {
            public String call() throws Exception {
            	String success=bean.threadMain();
                return success;
            }
        });
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            public void onSuccess(String s) {
                logger.info(s);
            }

            public void onFailure(Throwable throwable) {
            	logger.error("onfaulire:"+throwable);
            }
        });
       
    }
    
    public abstract class BaseObject{
    	public abstract String threadMain();
    }

}