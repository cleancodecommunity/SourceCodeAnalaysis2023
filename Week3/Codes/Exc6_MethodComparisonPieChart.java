package lectureWorkds.week3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedClassDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;

import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;


public class Exc6_MethodComparisonPieChart {

	
	static HashMap<String, Integer> methods = new HashMap<String, Integer> ();
	
	public static void main(String[] args) throws IOException {
		File project = new File("src/main/resources/jhotdraw/JHotDraw7/src/main/java");
		
		
		JavaParserTypeSolver javaParserTypeSolver= new JavaParserTypeSolver(project); //setting the whole project in Type Solver
		CombinedTypeSolver combinedSolver = new CombinedTypeSolver(); // it is always better to use combined type solver as there might be other information in futue to solve e.g., jar files..
		combinedSolver.add(new ReflectionTypeSolver()); 
		combinedSolver.add(javaParserTypeSolver);
		
		
		methods.put("void", 0);
		methods.put("int", 0);
		methods.put("double", 0);
		methods.put("String", 0);
		projectReader(project, combinedSolver);
		drawingPie(methods);
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
					
					VoidVisitor<?> claz = new  methods();
					CompilationUnit cu =	StaticJavaParser.parse(file);
					claz.visit(cu, null);
				}
			}
		}
	}



static class methods  extends  VoidVisitorAdapter<Void> { 
	
	public void visit(MethodDeclaration method, Void arg)  {
		super.visit(method, arg);
		
		if(method.getTypeAsString().contains("void"))
			methods.put("void", methods.get("void")+1);
		if(method.getTypeAsString().contains("int"))
			methods.put("int", methods.get("int")+1);
		if(method.getTypeAsString().contains("double"))
			methods.put("double", methods.get("double")+1);
		if(method.getTypeAsString().contains("String"))
			methods.put("String", methods.get("String")+1);
		

		
		
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
    	         true);
	
		PiePlot plot = (PiePlot) chart.getPlot();
      //PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1}");
      PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
    		    "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0.0%"));
      plot.setLabelGenerator(labelGenerator);

      ImageIO.write(chart.createBufferedImage(1000, 500), "png", new File("pie.png")); // the pie is saved.
}

}
