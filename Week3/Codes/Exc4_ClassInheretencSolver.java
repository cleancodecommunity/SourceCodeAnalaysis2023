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
import com.github.javaparser.resolution.declarations.ResolvedClassDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;

import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;



public class Exc4_ClassInheretencSolver {

	public static void main(String[] args) throws FileNotFoundException {
		File project = new File("src/main/resources/jhotdraw/JHotDraw7/src/main/java");
		
		
		JavaParserTypeSolver javaParserTypeSolver= new JavaParserTypeSolver(project); //setting the whole project in Type Solver
		CombinedTypeSolver combinedSolver = new CombinedTypeSolver(); // it is always better to use combined type solver as there might be other information in futue to solve e.g., jar files..
		combinedSolver.add(new ReflectionTypeSolver()); 
		combinedSolver.add(javaParserTypeSolver);
		
		projectReader(project, combinedSolver);

	}
	
	
public static void projectReader(File folder, CombinedTypeSolver solver) throws FileNotFoundException {
		
		for(File file: folder.listFiles()) {
			if(file.isDirectory()) {
				projectReader(file, solver);
			}
			else {
				if(file.getName().endsWith(".java")) {
					
					//solverInheretence(file, solver);
					
					JavaSymbolSolver symbolSolver = new JavaSymbolSolver(solver); 
					StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
					
					VoidVisitor<?> claz = new  ClassInhert();
					CompilationUnit cu =	StaticJavaParser.parse(file);
					claz.visit(cu, null);
				}
			}
		}
	}

// ClassInhert Inherits VoidVisitorAdapter Inhertis VoidVisitor

static class ClassInhert  extends  VoidVisitorAdapter<Void> { 
	
	public void visit(ClassOrInterfaceDeclaration claz, Void arg)  {
		super.visit(claz, arg);
		
		if(claz.resolve().isClass()) { // note this line ensures that selected classes are not interfaces...
			
			ResolvedClassDeclaration solvedClass = (ResolvedClassDeclaration) claz.resolve();
			
			solvedClass.getAllSuperClasses().forEach(superclass -> {
				System.out.print("Class:\t"+claz.getNameAsString());
				System.out.println("\tinherets Class:\t"+superclass.describe());
			});
		}
	}
}



}
