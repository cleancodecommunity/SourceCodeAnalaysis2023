package lectureWorkds.week2;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;




public class Exc10_11_Visitors {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("src/main/resources/junit-tests-master");
		exc10(file);
	}

public static void exc10(File folder) throws FileNotFoundException {
		
		for(File file: folder.listFiles()) {
			if(file.isDirectory()) {
				exc10(file);
			}
			else {
				if(file.getName().endsWith(".java")) {
					
					VoidVisitor<?> mths = new  Exc10_MethodIdentification();
					CompilationUnit cu =	StaticJavaParser.parse(file);
					mths.visit(cu, null);
					
				}
			}
		}
	}
	
	
	static class Exc10_MethodIdentification  extends  VoidVisitorAdapter<Void> { 
		
		public void visit(MethodDeclaration mth, Void arg)  {
			super.visit(mth, arg);
			
			if(mth.getParentNode().get() instanceof ClassOrInterfaceDeclaration) {
				ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration) mth.getParentNode().get();
				System.out.println(claz.getNameAsString());
				System.out.println("\t"+mth.getNameAsString());
			}
		}
	}

	
	
	
	
	
	
	
	
	
	

public static void exc11(File folder) throws FileNotFoundException {
		
		for(File file: folder.listFiles()) {
			if(file.isDirectory()) {
				exc11(file);
			}
			else {
				if(file.getName().endsWith(".java")) {
					
					VoidVisitor<?> cls = new  Exc11_ClassIdentification();
					CompilationUnit cu =	StaticJavaParser.parse(file);
					cls.visit(cu, null);
					
				}
			}
		}
	}
	
	
	static class Exc11_ClassIdentification  extends  VoidVisitorAdapter<Void> { 
		
		public void visit(ClassOrInterfaceDeclaration cls, Void arg)  {
			super.visit(cls, arg);
			if(cls.getMethods().size()>2) {
				System.out.println(cls.getNameAsString()+" class with "+cls.getFields().size()+" number of parameters has "
						+cls.getMethods().size()+" number of methods");
			}
		}
	}

	
}
