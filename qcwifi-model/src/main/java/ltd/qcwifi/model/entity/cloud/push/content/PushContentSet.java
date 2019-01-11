package ltd.qcwifi.model.entity.cloud.push.content;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 推广内容定向设置
 * 
 * @ClassName: PushContentSet
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年1月14日 下午1:16:05
 */
public class PushContentSet implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 3273223287225510568L;

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 投放开始日期
	 */
	private Date start;
	/**
	 * 投放介绍日期
	 */
	private Date end;
	/**
	 * 区域
	 */
	private String area;
	/**
	 * 行业
	 */
	private String category;
	/**
	 * 商铺
	 */
	private String shop;
	/**
	 * 渠道
	 */
	private Integer channel;
	/**
	 * 终端
	 */
	private String terminal;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
