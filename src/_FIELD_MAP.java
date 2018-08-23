import java.util.*;


/**
*  This class represents the column numbers of the table that DownloadServlet is reading
*/
public final class _FIELD_MAP 
{
	
	protected HashMap<Integer, String> _fieldMap = new HashMap<Integer, String>();
	
	protected _FIELD_MAP()
	{
		createMap();
	}
	
	private void createMap()
	{
		_fieldMap.put(0, "zip");
		_fieldMap.put(1, "city");
		_fieldMap.put(2, "state");
		_fieldMap.put(3, "county");		
	}
}