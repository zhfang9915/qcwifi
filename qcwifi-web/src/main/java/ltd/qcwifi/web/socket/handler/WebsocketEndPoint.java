//package ltd.qcwifi.web.socket.handler;
//
//import java.util.concurrent.CopyOnWriteArraySet;
//
//import javax.websocket.Session;
//
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import ltd.qcwifi.web.socket.WebSocketTest;
//
//public class WebsocketEndPoint extends TextWebSocketHandler {  
//  
//	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//    private static int onlineCount = 0;
//
//    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
//    public static CopyOnWriteArraySet<WebsocketEndPoint> webSocketSet = new CopyOnWriteArraySet<WebsocketEndPoint>();
//
//    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private WebSocketSession session;
//    
//	@Override  
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {  
//        System.out.println("connect to the websocket success......"); 
//        this.session = session;
//        webSocketSet.add(this);     //加入set中
//        addOnlineCount();           //在线数加1
//        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
//        session.sendMessage(new TextMessage("Server:connected OK!"));  
//    }  
//	
//    @Override  
//    protected void handleTextMessage(WebSocketSession session,  
//            TextMessage message) throws Exception {  
//        super.handleTextMessage(session, message);  
//        TextMessage returnMessage = new TextMessage(message.getPayload()+" received at server");  
//        session.sendMessage(returnMessage);  
//    } 
//    
//    
//    @Override  
//    public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {  
//        if(wss.isOpen()){  
//            wss.close();  
//        }  
//       System.out.println("websocket connection closed......");  
//    }  
//  
//    @Override  
//    public void afterConnectionClosed(WebSocketSession wss, CloseStatus cs) throws Exception {  
//    	webSocketSet.remove(this);  //从set中删除
//        subOnlineCount();           //在线数减1
//        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
//    }  
//    
//    
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized void addOnlineCount() {
//    	WebsocketEndPoint.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//    	WebsocketEndPoint.onlineCount--;
//    }
//}
