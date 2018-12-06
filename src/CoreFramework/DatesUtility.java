package CoreFramework;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DatesUtility {
	public static String getTodayDate() {
		Calendar calendar = Calendar.getInstance();
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat(ExternalFileConfiguration.DateformatMMDDYYYY);
		return ft.format(dNow);
	}

	public static String getTime(Date date) {
		date = new Date();
		SimpleDateFormat simpDate;
		simpDate = new SimpleDateFormat(ExternalFileConfiguration.DateformatKKMMSS);
		return simpDate.format(date);
	}

	public static String getTotalTimeExecutionInSecs(GregorianCalendar startTime, GregorianCalendar endTime) {
		// Time Difference Calculations Begin
		long milliSec1 = startTime.getTimeInMillis();
		long milliSec2 = endTime.getTimeInMillis();

		long timeDifInMilliSec;
		if (milliSec1 >= milliSec2) {
			timeDifInMilliSec = milliSec1 - milliSec2;
		} else {
			timeDifInMilliSec = milliSec2 - milliSec1;
		}
		float timeDifSeconds = timeDifInMilliSec / 1000;
		float timeInmins = timeDifSeconds / 60;
		// return
		return String.valueOf(timeInmins);
	}

	public static String convertMillisecondsToHHMMSS(String input) {
		long millis = Long.parseLong(input);
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

		return hms;
	}

	public static List<String> sortDateList(List<String> dateStringList) {
		List<Date> stringToDateList = new ArrayList<>();
		List<String> sortedDateStringList = new ArrayList<>();
		DateFormat format = new SimpleDateFormat("dd/MM/yy h:mm a");

		for (String dateString : dateStringList) {
			// Convert date String to DATE object
			try {
				stringToDateList.add(format.parse(dateString));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// Sort all converted DATE objects in descending order (LAST EDIT DTAE)
		Collections.sort(stringToDateList, Collections.reverseOrder());

		// Convert all Sorted DATE objects to Strings
		for (Date date : stringToDateList) {
			String sortedDateString = format.format(date);
			sortedDateStringList.add(sortedDateString);
		}
		// return the sorted date strings
		return sortedDateStringList;
	}
}
