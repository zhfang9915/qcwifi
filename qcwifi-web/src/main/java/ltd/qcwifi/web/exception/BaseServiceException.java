package ltd.qcwifi.web.exception;

/**
 *  服务层 异常
 * @author 张芳
 *
 */
public class BaseServiceException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -157859189430816888L;

	public BaseServiceException(String msg) {
		super(msg);
	}
}
