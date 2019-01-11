//package ltd.qcwifi.api.rabbit.mq;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class QueueListenter implements MessageListener {
//
//    @Override
//    public void onMessage(Message msg) {
//        try{
//            System.out.print("----"+new String(msg.getBody()));
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//}
