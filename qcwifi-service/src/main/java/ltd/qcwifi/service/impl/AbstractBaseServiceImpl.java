package ltd.qcwifi.service.impl;

import java.util.List;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.service.BaseService;

public abstract class AbstractBaseServiceImpl<T, PK> implements BaseService<T, PK> {

	/**
	 * 创建
	 * @param role
	 * @return
	 */
	public ExecuteResult<String> create(T t){
		return new ExecuteResult<>(false, "default");
	}
	
	/**
     * 删除
     * @param roleId
     */
	public ExecuteResult<String> delete(Object id){
		return new ExecuteResult<>(false, "default");
	}
	
	/**
	 * 批量删除
	 * @param permissions
	 * @return
	 */
	public ExecuteResult<String> batchDelete(List<T> ts){
		return new ExecuteResult<>(false, "default");
	}
	
	/**
     * 编辑
     * @param permission
     * @return
     */
	@Override
	public ExecuteResult<String> update(T t){
		return new ExecuteResult<>(false, "default");
	}
    
    /**
     * 表格展示
     * @param param
     * @return
     */
    public TableResult<T> tables(PK pk){
    	return new TableResult<>(0, null);
    }
    
    /**
     * 根据ID获取
     * @param id
     * @return
     */
    public ExecuteResult<T> searchById(Object id){
    	return new ExecuteResult<>(false, null);
    }
	
}
