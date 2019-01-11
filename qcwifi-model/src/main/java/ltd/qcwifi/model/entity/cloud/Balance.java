package ltd.qcwifi.model.entity.cloud;

import java.sql.Date;

/**
 * 余额数据模型
 * @author 蔡习文
 *
 */

public class Balance {
	private Long id;
	private Long userId;
	
	private float totalBalance;	//余额
	private float freezeBalance; //冻结余额
	private float availableBalance; //可用余额
	private float withdrawMinBalance; //最低提现金额
	
	private Date createTime;	//更新时间
	
	public Balance() {
		
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setTotalBalance(float totalBalance) {
		this.totalBalance = totalBalance;
	}
	
	public float getTotalBalance() {
		return this.totalBalance;
	}
	
	public void setFreezeBalance(float freezeBalance) {
		this.freezeBalance = freezeBalance;
	}
	
	public float getFreezeBalance() {
		return this.freezeBalance;
	}
	
	public void setAvailableBalance(float availableBalance) {
		this.availableBalance = availableBalance;
	}
	
	public void setWithdrawMinBalance(float withdrawMinBalance) {
		this.withdrawMinBalance = withdrawMinBalance;
	}
	
	public float getWithdrawMinBalance() {
		return this.withdrawMinBalance;
	}
	
	public float getAvailableBalance(){
		return this.availableBalance;
	} 
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
}
