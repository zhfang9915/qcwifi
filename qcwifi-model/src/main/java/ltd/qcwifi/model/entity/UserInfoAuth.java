package ltd.qcwifi.model.entity;

import java.io.Serializable;

import net.sf.json.JSONObject;

/*
 *	用户资料认证
 */
public class UserInfoAuth implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3808070891610823136L;
	
	/*
	 * 用户id，跟登录用户一样
	 */
	private long userId;
	/*
	 * 认证状态：0-认证中，1-认证成功，2-认证失败
	 */
	private int status;
	/*
	 * 用户类型：1代表企业，2代表个人
	 */
	private int userType;
	
	/*
	 * 企业名称：user_type为1时不能为空
	 */
	private String company;
	
	/*
	 * 证件信息（企业工商执照或个人手持照片）
	 */
	private String papers;
	
	/*
	 * 真实姓名
	 */
	private String realName;
	
	/*
	 * 身份证号码
	 */
	private String idCard;
	
	/*
	 * 手机号码
	 */
	private String phoneNum;
	
	/*
	 * 服务区域-省份
	 */
	private String province;
	
	/*
	 * 服务区域-城市
	 */
	private String city;
	
	/*
	 * 服务区域-区县
	 */
	private String area;
	
	/*
	 * qq号码
	 */
	private String qq;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPapers() {
		return papers;
	}
	public void setPapers(String papers) {
		this.papers = papers;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}
