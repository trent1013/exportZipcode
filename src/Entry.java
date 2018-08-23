
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Entry implements Comparable<Entry> 
{
	private String firstName, lastName, address, phoneNumber;
	private int zipcode;
	private String date; 
	
	public Entry()
	{
		
	}
	
	public Entry(String firstName, String lastName, String address, int zipcode, String phoneNumber, String date)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.zipcode = zipcode;
		this.phoneNumber = phoneNumber;
		this.date = date;		
	}
	
	public void setFirstName(String name)
	{
		this.firstName = name;
	}
	
	public void setLastName(String name)
	{
		this.lastName = name;
	}
	
	public void setZip(String zip)
	{
		this.zipcode = Integer.parseInt(zip.split("\\.")[0]);
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public void setPhone(String phone)
	{
		this.phoneNumber = phone;
	}
	
	public void setDate(String time)
	{
		/*String[] strArr = time.split("\\:");
		time = strArr[0]+" "+strArr[1]+" "+strArr[2];
		System.out.println("date string, doctored: "+time); */
		DateFormat out = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat in = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
		try {
		    Date effectiveDate = in.parse(time);
		    this.date = out.format(effectiveDate);
		 //   System.out.println("Date: " + out.format(effectiveDate));
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
	}

	public String getFirst()
	{
	     return this.firstName;
	}

	public String getLast()
 	{
	    return this.lastName;
	}

	public String getAddress()
	{
	    return this.address;
	}

	public int getZip()
	{
	    return this.zipcode;
	}

	public String getPhone()
	{
	    return this.phoneNumber;
	}

	public String getDate()
	{
	    return this.date;
	}

	public String toString()
	{
		return this.lastName+", " +  this.firstName +"\n"+ this.address +" "+ this.zipcode +"\n"+ this.phoneNumber + "\n" + this.date;
	}

	public int compareTo(Entry e)
	{
	    if(this.getLast().toLowerCase().compareTo(e.getLast().toLowerCase())==0)
 	    {
	        return this.getFirst().toLowerCase().compareTo(e.getFirst().toLowerCase());
	    }
	return this.getLast().toLowerCase().compareTo(e.getLast().toLowerCase());
	}
}

