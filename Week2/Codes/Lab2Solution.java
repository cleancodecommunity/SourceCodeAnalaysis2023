package labWorkds.week1;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.statistics.HistogramDataset;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Lab2 {

	static HashMap<Integer, Integer> methods = new HashMap<Integer, Integer>();
	


	public static void main(String[] args) throws IOException {

	
		Task2();

				

	}

	

	
	
	
	
	public static void Task1() {
		File file = new File("src/main/resources/jhotdraw");

		try {
			Recursive_class_Method_Identification(file);
			
			drawing1();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static void Recursive_class_Method_Identification(File folder) throws FileNotFoundException {

		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				Recursive_class_Method_Identification(file);
			} else {
				if (file.getName().endsWith(".java")) {

					VoidVisitor<?> mths = new Method_Identification();
					CompilationUnit cu = StaticJavaParser.parse(file);
					mths.visit(cu, null);

				}
			}
		}
	}

	static class Method_Identification extends VoidVisitorAdapter<Void> {

		public void visit(ClassOrInterfaceDeclaration claz, Void arg) {
			super.visit(claz, arg);
			
			int methodNum = claz.getMethods().size();
			if (methodNum < 16) {
				if (methods.containsKey(methodNum)) {
					int upgrade = methods.get(methodNum) + 1;
					methods.put(methodNum, upgrade);
				} else {
					methods.put(methodNum, 1);
				}
			}
		}
	}

	
	
	
	public static void drawing1() throws IOException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Entry<Integer, Integer> entry : methods.entrySet()) {
			dataset.setValue(entry.getValue(), "Population of classes with 15 or less than 15 methods",
					"" + entry.getKey()); // Each bar gets an individual labal.
		}

		BarRenderer myBar = new BarRenderer();

		Color color = new Color(79, 129, 189); // you can change the color of bars here
		myBar.setSeriesPaint(0, color);
		myBar.setMaximumBarWidth(0.01); // you can change the width of the bars here.
		CategoryAxis xAxis = new CategoryAxis("Number of Methods in a Class"); // X axis label
		NumberAxis yAxis = new NumberAxis("Number of Classes"); // Y axis labeles
		CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, myBar);

		JFreeChart bars = new JFreeChart(plot);
		ImageIO.write(bars.createBufferedImage(1000, 500), "png", new File("myBar1.png")); // the bar is saved.

	}

		
	
	
	
	
	
	
	
	
	

	public static void Task2() {

		File file = new File("src/main/resources/jhotdraw");

		try {
			Recursive_class_Field_Identification(file);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void Recursive_class_Field_Identification(File folder) throws FileNotFoundException {

		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				Recursive_class_Field_Identification(file);
			} else {
				if (file.getName().endsWith(".java")) {

					class_field_Identication(file);
				}
			}
		}
	}

	public static void class_field_Identication(File file) throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(file);

		cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cl -> {
			
			int VarNumberPerClass = cl.getFields().size();
			int MethodNumberPerClass = cl.getMethods().size();
			
			if (VarNumberPerClass >10 & MethodNumberPerClass>10) {
				
				System.out.println(cl.getNameAsString());
			}
		});

	}

	
	
	

}
