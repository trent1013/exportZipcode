import java.io.*;
import java.util.*;
import java.text.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.BuiltinFormats;
//

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* This class retrieves data from a table and and puts it into .xlsx format.
* 
*
*/
public class DownloadServlet extends HttpServlet 
{
   private static final String _filePath = "/var/lib/tomcat/webapps/exportZipcode/upload/";
   private _FIELD_MAP _fm = new _FIELD_MAP();

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
      throws ServletException, IOException,NullPointerException
    { 
        DateFormat df = new SimpleDateFormat("MM:dd:yy:HH:mm:ss");
        Date dateobj = new Date();
        String time = df.format(dateobj).toString();

       	String fileName = _filePath + "zipcode" + time + ".xlsx";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write("<html><head><link rel=\"stylesheet\" href=\"/fonts/cantarell/stylesheet.css\" type=\"text/css\" />");
	    out.write("<div id=\"logo\"></div><link rel=\"icon\" href=\"/icon.png\" type=\"image/png\"/>");
	    out.write("<img src=\"./images/logo.png\" alt=\"TransTouch LLC\" width=\"300\" height=\"70\" />");
	    out.write("<style>");
	    out.write("a{color: #F26522}");
	    out.write("#servlet{text-align: center; margin-left: 150px;margin-right: 150px;background: #f2f2f2;font-family: 'CantarellRegular', 'Helvetica Neue', 'Helvetica', 'Arial', sans-serif; font-size: 14px; color: #4D4D4D;}");
        out.write("</style>");
	    out.write("</head><body id=\"servlet\">");
	    Connection con = null;
        try
        {
            String url = "jdbc:postgresql://localhost/zipcode";
            String user = "trent";
            String password = "sadatay7";
	        Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
     
	        Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from ziptable;");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFCellStyle dateForm = workbook.createCellStyle();
            XSSFSheet spreadsheet = workbook.createSheet("zipcode db");

            int rowNumber = 0;

            XSSFRow row = spreadsheet.createRow(rowNumber);
            XSSFCell cell;
            createHeader(row);
            
            rowNumber++;
            while(resultSet.next())
            {
                row = spreadsheet.createRow(rowNumber);
                iterateThroughTable(row, resultSet, rowNumber, out);
                rowNumber++;
            }
            FileOutputStream output = new FileOutputStream(new File(fileName));
            workbook.write(output);
            output.close();
      	    String name = fileName.split("\\/")[7];  //this was a dicey move, but changed this from 7 //7 to 6
            out.write("<br><br><br><br>");
            out.write("Postal Service spreadsheet can be downloaded using link below: <br>");
            out.write("<br>");
            out.write("<a href=\"upload/"+name+"\" download>Download "+name+"</a>");
            out.write("<br><br><br><a href=\"http://173.255.243.80:8080/importZipcode/index.html\">Link to Import Page</a>");
        }
        catch(Exception ex)
        {
          	Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
          	out.write("<br>Error with prepared statement.<br>" + ex.getMessage());
        }  
        finally
        {
            try
            {
                  if (con != null)
                  {
                      con.close();
                  }

            }
            catch (SQLException ex)
            {
                Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
    	        out.write("Error in SQL");
            }                    
        } //close
    }

    public void createHeader(XSSFRow row)
    {
        XSSFCell cell;
        for (int columnNumber = 0; columnNumber < _fm._fieldMap.size(); columnNumber++)
        {
            cell=row.createCell(columnNumber);
            cell.setCellValue(_fm._fieldMap.get(columnNumber));
        }
    } 

    public void iterateThroughTable(XSSFRow row, ResultSet resultSet, int rowNumber, PrintWriter out) throws SQLException
    {
        XSSFCell cell;
        for (int columnNumber = 0; columnNumber < _fm._fieldMap.size(); columnNumber++)
        {
            try
            {
                cell = row.createCell(columnNumber);
                cell.setCellValue(resultSet.getString(_fm._fieldMap.get(columnNumber)));
            }
            catch(SQLException ex)
            {
                Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
                out.write("Error in SQL:<br>" + ex.getMessage());
            }
            
        }
    }
}

