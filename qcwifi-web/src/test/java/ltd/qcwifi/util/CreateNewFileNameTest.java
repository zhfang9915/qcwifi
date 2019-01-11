package ltd.qcwifi.util;

import java.util.Date;

import org.junit.Test;

import ltd.qcwifi.comm.utils.CalendarUtil;

public class CreateNewFileNameTest {

	@Test
	public void createNewName() {
		String oldName = "aapp";
		if (oldName.indexOf('.') == -1) {
			String newName = oldName + "_" + CalendarUtil.parseLongTime(new Date());
			System.out.println(newName);
		}else {
			String sub = oldName.substring(0, oldName.lastIndexOf('.'));
			String fix = oldName.substring(oldName.lastIndexOf('.'));
			String newName = sub + "_" + CalendarUtil.parseLongTime(new Date()) + fix;
			System.out.println(newName);
		}
	}
}
