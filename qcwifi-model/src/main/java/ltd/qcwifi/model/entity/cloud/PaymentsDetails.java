package ltd.qcwifi.model.entity.cloud;

import java.sql.Date;

/**
 * 收支明细数据模型
 * @author 蔡习文
 *
 */

public class PaymentsDetails {

	private Long id;
	private Long userId;
	private String transactionSerial;	//交易流水号
	private int transactionMoney;		//交易金额
	private int transactionType;		//交易类型
	private String remarks;				//备注
	private Date createTime;			//时间
	
	public PaymentsDetails() {
		
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
	
	public void setTransactionSerial(String transactionSerial) {
		this.transactionSerial = transactionSerial;
	}
	
	public String getTransactionSerial() {
		return this.transactionSerial;
	}
	
	public void setTransactionMoney(int transactionMoney) {
		this.transactionMoney = transactionMoney;
	}
	
	public int getTransactionMoney() {
		return this.transactionMoney;
	}
	
	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}
	
	public int getTransactionType() {
		return this.transactionType;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
}
