package ltd.qcwifi.socket.config;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	//在topic和user这两个域上可以向客户端发消息
        config.enableSimpleBroker("/topic");
        //给指定用户发送（一对一）的主题前缀是“/user/”
        config.setUserDestinationPrefix("/user/");
        //客户端向服务端发送时的主题上面需要加"/app"作为前缀
        config.setApplicationDestinationPrefixes("/soc");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/qcsocket")
        		.addInterceptors(new HandshakeInterceptor() {
                    /**
                     * websocket握手
                     */
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        ServletServerHttpRequest req = (ServletServerHttpRequest) request;
                        //获取token认证
                        String token = req.getServletRequest().getParameter("token");
                        String login = req.getServletRequest().getHeader("login");
                        //解析token获取用户信息
                        Principal user = parseToken(token);
                        if(user == null){   //如果token认证失败user为null，返回false拒绝握手
                            return true;
                        }
                        //保存认证用户
                        attributes.put("user", user);
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

                    }
                })
        		.setHandshakeHandler(new DefaultHandshakeHandler(){
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        //设置认证用户
                        return (Principal)attributes.get("user");
                    }
                })
        		.setAllowedOrigins("*")//允许跨域
        		.withSockJS();//指定使用SockJS协议
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
    	registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String jwtToken = accessor.getFirstNativeHeader("login");
                    System.out.println(jwtToken);
                }
                return message;
            }
        });
    }
    /**
     * 根据token认证授权
     * @param token
     */
    private Principal parseToken(String token){
        //TODO 解析token并获取认证用户信息
        return null;
    }
}