package lectureWorkds.week3;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;



public class Exc1_ShallowMethodCall {

	public static int myFiled = 8;
	
	
	public static void main(String [] args) throws FileNotFoundException {
		File file = new File("src/main/resources/junit-tests-master");
		projectReader(file);
	}
	
	
public static void projectReader(File folder) throws FileNotFoundException {
		
		for(File file: folder.listFiles()) {
			if(file.isDirectory()) {
				projectReader(file);
			}
			else {
				if(file.getName().endsWith(".java")) {
					
					VoidVisitor<?> mths = new  MethodCalls();
					CompilationUnit cu =	StaticJavaParser.parse(file);
					mths.visit(cu, null);
					
				}
			}
		}
	}


static class MethodCalls  extends  VoidVisitorAdapter<Void> { 
	
	public void visit(MethodDeclaration caller, Void arg)  {
		super.visit(caller, arg);
		
		
		caller.findAll(MethodCallExpr.class).forEach(callee -> {
			System.out.println(caller.getName()+"\tcalls\t"+callee.getName());
			
			
		}); 
		
	}
}





	
}
