package ltd.qcwifi.web.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

public class CustomDateFormatter implements Formatter<Date> {

	@Override
	public String print(Date arg0, Locale arg1) {
		return null;
	}

	@Override
	public Date parse(String text, Locale local) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(text);
        } catch (Exception e) {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(text);
        }
        return date;
	}

}
