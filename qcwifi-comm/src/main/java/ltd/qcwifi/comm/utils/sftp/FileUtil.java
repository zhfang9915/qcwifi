package ltd.qcwifi.comm.utils.sftp;

import java.util.Date;

import ltd.qcwifi.comm.utils.CalendarUtil;

public class FileUtil {

	/**
	 * 获取新文件名字
	 * @return
	 */
	public static String createNewName(String oldName) {
		if (oldName.indexOf('.') == -1) {
			return oldName + "_" + CalendarUtil.parseLongTime(new Date());
		} else {
			String sub = oldName.substring(0, oldName.lastIndexOf('.'));
			String fix = oldName.substring(oldName.lastIndexOf('.'));
			return sub + "_" + CalendarUtil.parseLongTime(new Date()) + fix;
		}
	}
}
