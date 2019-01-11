package ltd.qcwifi.service;

import java.util.List;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.entity.cloud.platform.Plugin;

public interface BaseService<T, PK> {

	/**
	 * 创建
	 * @param role
	 * @return
	 */
	ExecuteResult<String> create(T t);
	
	/**
     * 删除
     * @param roleId
     */
	ExecuteResult<String> delete(Object id);
	
	/**
	 * 批量删除
	 * @param permissions
	 * @return
	 */
	ExecuteResult<String> batchDelete(List<T> ts);
	
	/**
     * 编辑
     * @param permission
     * @return
     */
    ExecuteResult<String> update(T t);
    
    /**
     * 表格展示
     * @param param
     * @return
     */
    TableResult<T> tables(PK pk);
    
    /**
     * 根据ID获取
     * @param id
     * @return
     */
    ExecuteResult<T> searchById(Object id);

}
