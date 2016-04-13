package business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class FileHelper {

	public boolean OpenFile()
	{
		try
		{
			File yourFile = new File("D:\\exception.txt");
			if(!yourFile.exists()) {
			    yourFile.createNewFile();
			} 
			FileWriter fw = new FileWriter(yourFile,true); //the true will append the new data
		    fw.write("\nadd a line\n");//appends the string to the file
		    fw.close();  
			
			return true;
		}catch(Exception ex)
		{
			return false;
		}
		
	}
}
