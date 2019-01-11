package ltd.qcwifi.model.entity.cloud.platform;

/**
 * 商铺标签
 * 
 * @author 张芳
 *
 */
public class ShopMark {

	private long id;

	private String name;

	private long createBy;

	public ShopMark() {
		super();
	}

	public ShopMark(String name, long createBy) {
		super();
		this.name = name;
		this.createBy = createBy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}

}
