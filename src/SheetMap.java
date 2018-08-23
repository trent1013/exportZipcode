
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SheetMap 
{	
	protected HashMap<Integer, ArrayList<String>> mockup = new HashMap<Integer, ArrayList<String>>();
	protected ArrayList<String> messageList = new ArrayList<String>();	
	
	public void init(InputStream is) throws IOException
	{
		Workbook workbook = new XSSFWorkbook(is);

        Sheet firstSheet = workbook.getSheetAt(0);

        int last = firstSheet.getLastRowNum();
        if(validColumns(firstSheet));
        {
        	for (int j = 0; j<= last; j++)
        	{  
        		Row row = firstSheet.getRow(j);
        		ArrayList<String> rows = new ArrayList<String>();
        		for(int i = 0; i<=5; i++)
        		{
        			Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);           	   
        			switch (cell.getCellType()) 
           			{
           				case Cell.CELL_TYPE_STRING:
           					String place = cell.toString();
           					rows.add(place);	
           					break;
           				case Cell.CELL_TYPE_BLANK:
           					String value = "";
           					rows.add(value); 
           					break;
           				case Cell.CELL_TYPE_NUMERIC:
           					if(HSSFDateUtil.isCellDateFormatted(cell))
           					{
           						//System.out.println(cell.getDateCellValue());
           						rows.add(cell.getDateCellValue().toString());
           					}
           					String number = cell.toString();
           					rows.add(number); 
           					break;	
           				}      	   
        		}
        		mockup.put(j, rows);
        		workbook.close();
        	}
        }
	}
	
	public void validatePhone()
	{
		for(int i : mockup.keySet())
		{
			if(i>0)
			{
				ArrayList<String> rowContent = mockup.get(i);
				String phone = rowContent.get(4);
				try
				{
					String area = phone.substring(1, 4);
					String three = phone.substring(5, 8);
					String four = phone.substring(9, 13);
					String finalNumber = area + three + four;
			//		System.out.println(finalNumber);
					Long.parseLong(finalNumber);
					if (finalNumber.length()<10)
					{
						messageList.add("Incorrect phone number in cell "+(i+1)+"E. Phone number must be 10 digits in length.");
					}
				}
				catch(Exception e)
				{
					messageList.add("Incorrect phone number in cell "+(i+1)+"E. Please format as (XXX)XXX-XXXX, where X is numeric.");
				}
			}
		}
	}
	
	public void validateDate()
	{
		for(int i : mockup.keySet())
		{
			if(i>0)
			{
				ArrayList<String> rowContent = mockup.get(i);
				String time = rowContent.get(5);
				DateFormat out = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat in = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
				try {
				    Date effectiveDate = in.parse(time);
				   // System.out.println("Date: " + out.format(effectiveDate));
				} catch (Exception e) {
					messageList.add("Error in date format for cell "+(i+1)+"F.");
				}
			}
		}
	}
	
	public void validateZipcode()
	{
		for(int i : mockup.keySet())
		{
			if(i>0)
			{
				ArrayList<String> row = mockup.get(i);
				String zip = row.get(3);
				if(zip.split("\\.")[0].length() == 5)
					try
					{
						Integer.parseInt(zip.split("\\.")[0]);							
					}
					catch(Exception e)
					{
						messageList.add("Error for zipcode in cell "+(i+1)+"D. Zipcode must be a number.");
					}
				else
				{
					messageList.add("Error for zipcode in cell "+(i+1)+"D. Zipcode must be five digits");
				}
			}
		}
	}
	
	public boolean validColumns(Sheet sheet)
	{
		Row row = sheet.getRow(0);
		int j = row.getLastCellNum();
	//	System.out.println("number of columns: " + j);
		if(j == 6)
		{
			return true;			
		}
		else
		{
			messageList.add("Incorrect number of columns. Columns must be labelled 'last_name', 'first_name', \n'address', 'zipcode','phone_number', and 'date'.");
			messageList.add("Fix columns and upload again.");
			messageList.add(" ");
		}
		return false;
	}

	public void clear()
	{
		this.mockup.clear();
        }
}

