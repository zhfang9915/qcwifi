package ltd.qcwifi.backend.jobs.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 */
@SuppressWarnings("rawtypes")
public class ParameterUtil {
	
	/**
     * 分页每页显示默认数量
     */
	public final static int pagSize = 20;
	
	/**
     * 分页每页显示默认数量 关键字
     */
	public final static String limit = "limit";
	
//	/**
//     * 分页当前页 关键字
//     */
	public final static String offset = "offset";
	
	/**
     * 分页查询反馈总数量关键字
     */
	public final static String total = "total";
	
	/**
     * 分页查询反馈结果集数据关键字
     */
	public final static String rows = "rows";
	
	/**
     * 分页当前页 关键字
     */
	public final static String page = "page";
	
	/**
     * 分页总信息条数 关键字
     */
	public final static String records = "records";
	
	
	
	
    /**
     * 从request中获得参数Map，并返回可读的Map
     *
     * @param request
     * @return
     */
	public static Map<String,Object> getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map<String,Object> returnMap = new HashMap<String,Object>();
		Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
    
}

