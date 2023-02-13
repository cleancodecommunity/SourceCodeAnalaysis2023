package lectureWorkds.week3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;


public class Exc3_methodCallSolver {

	public static void main(String[] args) throws FileNotFoundException  {
		
		File project = new File("src/main/resources/junit-tests-master/src/main/java");
		
		
		JavaParserTypeSolver javaParserTypeSolver= new JavaParserTypeSolver(project); //setting the whole project in Type Solver
		
		
		ReflectionTypeSolver reflectedSolver = new ReflectionTypeSolver();
		
		
		CombinedTypeSolver combinedSolver = new CombinedTypeSolver(); // it is always better to use combined type solver as there might be other information in futue to solve e.g., jar files..
		
		combinedSolver.add(reflectedSolver); 
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
					
					solverMethod(file, solver);
					
				}
			}
		}
	}



public static void solverMethod(File file, TypeSolver combinedSolver) throws FileNotFoundException {
    
	
	
	
	JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedSolver); 
	
	
	StaticJavaParser.getParserConfiguration().setSymbolResolver(symbolSolver); // Java parser and symbol solver
	
	
	
	
	
	
	CompilationUnit cu =	StaticJavaParser.parse(file);
	
	cu.findAll(MethodCallExpr.class).forEach(callee -> {
		
		if(callee.findAncestor(MethodDeclaration.class).isPresent()) {
			
			MethodDeclaration caller = callee.findAncestor(MethodDeclaration.class).get();
			
			System.out.println("Caller method:\t"+caller.getName());
			
			System.out.println("\tType:\t"+caller.getType().toString()); //note these parts are from JavaParser
			
			
				
				System.out.print("\tCallee method:\t"+callee.resolve().getName()); //now it is symbolsolver...
				System.out.println("\tType:\t"+callee.resolve().getReturnType().describe());  //now it is symbolsolver...
			
		}
		
	});
	
	
}

	
}
