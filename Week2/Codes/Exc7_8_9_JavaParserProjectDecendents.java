package lectureWorkds.week2;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;

public class Exc7_8_9_JavaParserProjectDecendents {

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
					exc8(file);
					
				}
			}
		}
	}

	public static void exc7(File file) throws FileNotFoundException {
		
		CompilationUnit cu = StaticJavaParser.parse(file);
		
		System.out.println(file.getName());
		
		cu.findAll(MethodDeclaration.class).forEach(mth -> {
			
			System.out.println("\t"+mth.getNameAsString());
			
		});
	}
	
  public static void exc8(File file) throws FileNotFoundException {
		
		CompilationUnit cu = StaticJavaParser.parse(file);
		
		System.out.println(file.getName());
		
		cu.findAll(VariableDeclarationExpr.class).forEach(vr -> {
			
			System.out.println("\t"+vr.toString());
			
		});
	}
	
  public static void exc9(File file) throws FileNotFoundException {
		
		CompilationUnit cu = StaticJavaParser.parse(file);
		
		System.out.println(file.getName());
		
		cu.findAll(VariableDeclarationExpr.class).forEach(vr -> {
			
			vr.findAll(SimpleName.class).forEach(nm -> {
				System.out.println("\t"+nm.toString());
			});
			
		});
	}
	
	
}
