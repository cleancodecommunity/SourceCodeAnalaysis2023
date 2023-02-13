package lectureWorkds.week3;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import lectureWorkds.week3.Exc1_ShallowMethodCall.MethodCalls;

public class Exc0_Review {

	public static void main(String [] args) throws FileNotFoundException {
		File file = new File("src/main/resources/jhotdraw");
		projectReader(file);
	}
	
	
public static void projectReader(File folder) throws FileNotFoundException {
		
		for(File file: folder.listFiles()) {
			if(file.isDirectory()) {
				projectReader(file);
			}
			else {
				if(file.getName().endsWith(".java")) {
					
					VoidVisitor<?> mths = new  detections();
					CompilationUnit cu =	StaticJavaParser.parse(file);
					mths.visit(cu, null);
					
				}
			}
		}
	}


static class detections  extends  VoidVisitorAdapter<Void> { 
	
	public void visit(MethodDeclaration mth, Void arg)  {
		super.visit(mth, arg);
		
		int c = 0;
		for (int i= 0 ; i< mth.getModifiers().size(); i++) {
			if (mth.getModifiers().get(i).toString().contains("static"))
				c++;
			if (mth.getModifiers().get(i).toString().contains("public"))
				c++;
			
		}
		
		if(c==2) {
			System.out.println(mth.getNameAsString());
		}
		
	}
}
	
	
	
	
}
