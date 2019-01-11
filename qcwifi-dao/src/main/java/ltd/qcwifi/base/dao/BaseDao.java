package ltd.qcwifi.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 基础持久化接口
 * 	本接口类中定义的操作接口方法并不强制要求在对应的 xxxDao.xml中实现具体的sql操作
 * 	仅需实现需要用的的方法即可
 * 	
 * @author 张芳
 *
 * @param <T>
 * @param <PK>
 */
public interface BaseDao<T extends Object, PK> {

	/**
	 * 根据ID查询对象
	 * @param id
	 * @return
	 */
	T selectOneById(@Param("id")Object id);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<T> selectAll();
	
	/**
	 * 分页查询
	 * @return
	 */
	List<T> selectByPage(PK pk);
	/**
	 * 分页查询总数
	 * @return
	 */
	int selectByPageCount(PK pk);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(@Param("id")Object id);
	
	/**
	 * 批量删除
	 * @param ts
	 * @return
	 */
	int batchDelete(List<T> ts);

	/**
	 * 插入
	 * @param t
	 * @return
	 */
    int insert(T t);
    
    /**
     * 更新
     * @param t
     * @return
     */
    int update(T t);


}
