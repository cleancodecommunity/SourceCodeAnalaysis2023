package lectureWorkds.week3;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;



public class Exc2_ShallowFieldAcc {

	
	
	
	
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
					
					VoidVisitor<?> claz = new  VarAccess_Version2();
					CompilationUnit cu =	StaticJavaParser.parse(file);
					claz.visit(cu, null);
					
				}
			}
		}
	}


static class VarAccess_Version1  extends  VoidVisitorAdapter<Void> { 
	
	public void visit(FieldAccessExpr filedAcc, Void arg)  {
		super.visit(filedAcc, arg);
		
		
		if(filedAcc.findAncestor(ClassOrInterfaceDeclaration.class).isPresent()) {
			System.out.println("Class:\t"+filedAcc.findAncestor(ClassOrInterfaceDeclaration.class).get().getName()+"\t using\t"+
			filedAcc.getNameAsString()+"\tfiled");
		}

	}
}

static class VarAccess_Version2  extends  VoidVisitorAdapter<Void> { 
	
	public void visit(ClassOrInterfaceDeclaration claz, Void arg)  {
		super.visit(claz, arg);
		
		claz.findAll(FieldAccessExpr.class).forEach(field -> {
			System.out.println(claz.getNameAsExpression()+"\taccesses\t"+field.getNameAsString());
		});
		
		

	}
}
	
}
