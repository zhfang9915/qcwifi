package ltd.qcwifi.shiro;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qcwifi.shiro.session.ShiroSessionRepository;

/**
 * shiro 自定义 sessionDao
 * @author 张芳
 *
 */
public class CustomShiroSessionDAO extends AbstractSessionDAO{ 
	
	Logger logger = LoggerFactory.getLogger(CustomShiroSessionDAO.class);
	
    private ShiroSessionRepository shiroSessionRepository;  
   
    /**
     * 注入shiroSessionRepository
     * @param shiroSessionRepository
     */
    public void setShiroSessionRepository(  
            ShiroSessionRepository shiroSessionRepository) {  
        this.shiroSessionRepository = shiroSessionRepository;  
    }  
  
    @Override  
    public void update(Session session) throws UnknownSessionException {  
        shiroSessionRepository.saveSession(session);  
    }  
  
    @Override  
    public void delete(Session session) {  
        if (session == null) {  
        	logger.info("session is null");
            return;  
        }  
        Serializable id = session.getId();  
        if (id != null)  
            shiroSessionRepository.deleteSession(id);  
    }  
  
    @Override  
    public Collection<Session> getActiveSessions() {  
        return shiroSessionRepository.getAllSessions();  
    }  
  
    @Override  
    protected Serializable doCreate(Session session) {  
        Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);  
        shiroSessionRepository.saveSession(session);  
        return sessionId;  
    }  
  
    @Override  
    protected Session doReadSession(Serializable sessionId) {  
        return shiroSessionRepository.getSession(sessionId);  
    } }
