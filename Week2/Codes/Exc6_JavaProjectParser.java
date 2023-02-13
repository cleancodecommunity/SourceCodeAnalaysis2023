package lectureWorkds.week2;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class Exc6_JavaProjectParser {

	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File("src/main/resources/junit-tests-master");
		recursive(file);
	}
	
	
	public static void recursive(File folder) throws FileNotFoundException {
		
		for(File file: folder.listFiles()) {
			if(file.isDirectory()) {
				recursive(file);
			}
			else {
				if(file.getName().endsWith(".java")) {
					exc6(file);
				}
			}
		}
	}

	public static void exc6(File file) throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(file);
		
		System.out.println(file.getName());
		
		
		
		for(int i=0;i<cu.getChildNodes().size();i++) {
			
			if (cu.getChildNodes().get(i) instanceof ClassOrInterfaceDeclaration) {
				ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration) cu.getChildNodes().get(i);
				
				System.out.println("\t"+claz.getNameAsString());
			}
		}
		
	}
	
}
