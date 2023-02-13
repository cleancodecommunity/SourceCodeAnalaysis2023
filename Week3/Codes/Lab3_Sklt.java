package lectureWorkds.week3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedClassDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;

import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;



public class Lab3 {

	
	static int staticTostatic=0;
	static HashMap<String, Integer> dependecies = new HashMap<String, Integer>();
	static HashMap<String, Integer> dependeciesInhr = new HashMap<String, Integer>();
	
	public static void main(String[] args) throws IOException {
		File project = new File("src/main/resources/jhotdraw/JHotDraw7/src/main/java");
		
		
		JavaParserTypeSolver javaParserTypeSolver= new JavaParserTypeSolver(project); //setting the whole project in Type Solver
		CombinedTypeSolver combinedSolver = new CombinedTypeSolver(); // it is always better to use combined type solver as there might be other information in futue to solve e.g., jar files..
		combinedSolver.add(new ReflectionTypeSolver()); 
		combinedSolver.add(javaParserTypeSolver);
		
		projectReader(project, combinedSolver);
		drawingPie(dependeciesInhr);
		//drawingPie(dependecies);
		System.out.println("Finish");
	}
	
	
	
	
	
public static void projectReader(File folder, CombinedTypeSolver solver) throws FileNotFoundException {
		
		for(File file: folder.listFiles()) {
			if(file.isDirectory()) {
				projectReader(file, solver);
			}
			else {
				if(file.getName().endsWith(".java")) {
					
					JavaSymbolSolver symbolSolver = new JavaSymbolSolver(solver); 
					StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
					
					VoidVisitor<?> claz = new  ClassInhert();
					//VoidVisitor<?> claz = new  MethodCalls();
					
					CompilationUnit cu =	StaticJavaParser.parse(file);
					claz.visit(cu, null);
					
				}
			}
		}
	}



static class ClassInhert  extends  VoidVisitorAdapter<Void> { 
	
	public void visit(ClassOrInterfaceDeclaration claz, Void arg)  {
		super.visit(claz, arg);
		
		if(claz.resolve().isClass()) { // note this line ensures that selected classes are not interfaces...
			
			
			/**TODO
			 * 
			 */
			
		}
	}
}

	static class MethodCalls  extends  VoidVisitorAdapter<Void> { 
		
		public void visit(MethodDeclaration caller, Void arg)  {
			super.visit(caller, arg);
			try {
				if(caller.hasModifier(Keyword.STATIC)) {
					

					/**TODO
					 * 
					 */
			}	
		   }	
		catch (UnsolvedSymbolException e) {
				//handle the exception
			}
			

		}
	
	
}

public static void drawingPie(HashMap dependecies) throws IOException {
		
		DefaultPieDataset dataset = new DefaultPieDataset( );
		
		dependecies.forEach((a, b) -> {
			dataset.setValue( a+"" , (Number) b );  
		});
		
	      
	      JFreeChart chart = ChartFactory.createPieChart(      
	    	         "Class Dependecy",   // chart title 
	    	         dataset,          // data    
	    	         true,             // include legend   
	    	         true, 
	    	         false);
	      
	      ImageIO.write(chart.createBufferedImage(1000, 500), "png", new File("pie.png")); // the pie is saved.
	}

}
