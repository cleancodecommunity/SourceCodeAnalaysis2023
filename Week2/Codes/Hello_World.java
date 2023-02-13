package lectureWorkds.week2;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class Hello_World {

	public static void main(String[] args) throws FileNotFoundException {
		
		
		File file = new File("src/main/resources/test.java");
		
		CompilationUnit cu =  StaticJavaParser.parse(file);
		
		for(int i=0;i<cu.getChildNodes().size();i++) {
			System.out.println(cu.getChildNodes().get(i).toString());
			System.out.println("\t*****");
			System.out.println(cu.getChildNodes().get(i).getClass().getName());
			for(int j=0; j<cu.getChildNodes().get(i).getChildNodes().size();j++) {
				System.out.println(cu.getChildNodes().get(i).getChildNodes().get(j).toString());
				System.out.println("\t-----------");
				System.out.println(cu.getChildNodes().get(i).getChildNodes().get(j).getClass().getName());
			}
		}
	}

}
