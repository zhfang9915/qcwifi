package ltd.qcwifi.model.entity.cloud.push.content;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 推广内容实体类
 * 
 * @author Administrator
 *
 */
public class AdsSeq implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1809068838802407662L;

	/**
	 * 流水ID
	 */
	private String logid;
	/**
	 * 设备编码
	 */
	private String devNo;
	private String dn;
	/**
	 * 手机MAC
	 */
	private String pmac;
	private String pm;
	/**
	 * 被推送的网页的URL
	 */
	private String surl;
	private String sr;
	/**
	 * 路由器详细地址
	 */
	private String rdetail;
	private String rd;
	/**
	 * 网页标题
	 */
	private String stitle;
	private String st;
	/**
	 * 网页关键字
	 */
	private String skeyword;
	private String sk;
	/**
	 * 推送时间
	 */
	private Date push;
	/**
	 * 关闭时间
	 */
	private Date cloze;
	/**
	 * 点击时间
	 */
	private Date click;
	/**
	 * 显示时间
	 */
	private Date shew;
	/**
	 * 广告ID
	 */
	private String adId;
	/**
	 * 流水类型：1显示，2点击， 3关闭
	 */
	private Integer type;

	public String getDevNo() {
		return devNo;
	}

	public void setDevNo(String devNo) {
		this.devNo = devNo;
	}

	public String getPmac() {
		return pmac;
	}

	public void setPmac(String pmac) {
		this.pmac = pmac;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getRdetail() {
		return rdetail;
	}

	public void setRdetail(String rdetail) {
		this.rdetail = rdetail;
	}

	public String getStitle() {
		return stitle;
	}

	public void setStitle(String stitle) {
		this.stitle = stitle;
	}

	public String getSkeyword() {
		return skeyword;
	}

	public void setSkeyword(String skeyword) {
		this.skeyword = skeyword;
	}

	public Date getPush() {
		return push;
	}

	public void setPush(Date push) {
		this.push = push;
	}

	public Date getClick() {
		return click;
	}

	public void setClick(Date click) {
		this.click = click;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public Date getCloze() {
		return cloze;
	}

	public void setCloze(Date cloze) {
		this.cloze = cloze;
	}

	public Date getShew() {
		return shew;
	}

	public void setShew(Date shew) {
		this.shew = shew;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	public void setDn(String dn) {
		this.devNo = dn;
	}

	public void setPm(String pm) {
		this.pmac = pm;
	}

	public void setSr(String sr) {
		this.surl = sr;
	}

	public void setRd(String rd) {
		this.rdetail = rd;
	}

	public void setSt(String st) {
		this.stitle = st;
	}

	public void setSk(String sk) {
		this.skeyword = sk;
	}

	public String getDn() {
		return dn;
	}

	public String getPm() {
		return pm;
	}

	public String getSr() {
		return sr;
	}

	public String getRd() {
		return rd;
	}

	public String getSt() {
		return st;
	}

	public String getSk() {
		return sk;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
