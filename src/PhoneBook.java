

import java.io.*;
import java.util.*;

public class PhoneBook 
{
	public ArrayList<Entry> contactList = new ArrayList<Entry>();
	
	public void AddEntry(Entry entry)
	{
		this.contactList.add(entry);
	}
	
	public void printBook()
	{
	    Collections.sort(this.contactList);
		for(Entry e : contactList)
		{
			System.out.println(e.toString());
			System.out.println();
		}
	}
}

