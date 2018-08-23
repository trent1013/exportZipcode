import java.util.*;

public final class _MONTHMAP {
	
	protected HashMap<String, Integer> monthMap = new HashMap<String, Integer>();
	
	protected _MONTHMAP()
	{
		createMap();
	}
	
	private void createMap()
	{
		monthMap.put("01", Calendar.JANUARY);
		monthMap.put("02", Calendar.FEBRUARY);
		monthMap.put("03", Calendar.MARCH);
		monthMap.put("04", Calendar.APRIL);
		monthMap.put("05", Calendar.MAY);
		monthMap.put("06", Calendar.JUNE);
		monthMap.put("07", Calendar.JULY);
		monthMap.put("08", Calendar.AUGUST);
		monthMap.put("09", Calendar.SEPTEMBER);
		monthMap.put("10", Calendar.OCTOBER);
		monthMap.put("11", Calendar.NOVEMBER);
		monthMap.put("12", Calendar.DECEMBER);
	}
}

