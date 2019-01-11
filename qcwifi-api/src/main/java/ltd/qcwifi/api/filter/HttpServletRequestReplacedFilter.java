package ltd.qcwifi.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ltd.qcwifi.api.request.BodyReaderHttpServletRequestWrapper;

/**
 * ServletRequest 包装为 BodyReaderHttpServletRequestWrapper
 * @author 张芳
 *
 */
public class HttpServletRequestReplacedFilter implements Filter {  
  
    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {  

    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {  
        ServletRequest requestWrapper = null;  
        if(request instanceof HttpServletRequest) {  
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);  
        }  
        if(null == requestWrapper) {  
            chain.doFilter(request, response);  
        } else {  
            chain.doFilter(requestWrapper, response);  
        }  
          
    }  
  
    @Override  
    public void destroy() {  
        //Do nothing  
    }  

}
