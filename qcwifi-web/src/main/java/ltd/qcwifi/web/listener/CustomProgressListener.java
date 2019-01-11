package ltd.qcwifi.web.listener;

import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.ProgressListener;

import ltd.qcwifi.web.file.upload.Progress;

/**
 * 文件上传监听器
 * @author 张芳
 *
 */
public class CustomProgressListener implements ProgressListener {
	private HttpSession session;

	public CustomProgressListener() {
	}

	public CustomProgressListener(HttpSession _session) {
		session = _session;
		Progress ps = new Progress();
		session.setAttribute("upload_ps", ps);
	}

	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		Progress ps = (Progress) session.getAttribute("upload_ps");
		ps.setpBytesRead(pBytesRead);
		ps.setpContentLength(pContentLength);
		ps.setpItems(pItems);
		// 更新
		session.setAttribute("upload_ps", ps);
	}

}
