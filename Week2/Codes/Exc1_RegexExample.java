package lectureWorkds.week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exc1_RegexExample {

	public static void main(String[] args) throws IOException {
		
		
		  String source = new String ( Files.readAllBytes( Paths.get("src/main/resources/BringToFrontAction.java") ) );
    
		  
	      String pattern = "public.*\\(.*\\).*\\s*\\{[^\\}]*\\}";

	     
	      Pattern r = Pattern.compile(pattern);


	      Matcher m = r.matcher(source);
	      while (m.find( )) {
	         System.out.println("------: \n" + m.group(0) );
	         System.out.println("------: \n");
	         
	      }
		
	}

}
