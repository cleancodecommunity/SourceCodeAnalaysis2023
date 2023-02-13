package lectureWorkds.week2;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;


public class Exc2_3_4_5_JavaParserIntro {

	public static void main(String[] args) throws FileNotFoundException {
	   exc5();
	}
	
	
	
	
	
	public static void exc2() throws FileNotFoundException {
		File file = new File("src/main/resources/simpleClass.java");
	       
		
		
		CompilationUnit cu =  StaticJavaParser.parse(file);
	       
	       
	       for(int i=0; i<cu.getChildNodes().size(); i++) {
	    	   System.out.println("Child:\t"+cu.getChildNodes().get(i).getClass().getName());
	    	   System.out.println("-----");
	    	   
	    	   
	    	   for(int j=0;j<cu.getChildNodes().get(i).getChildNodes().size(); j++) {
	    		   System.out.println("\tGrand child:\t"+cu.getChildNodes().get(i).getChildNodes().get(j).getClass().getName());
	        	   System.out.println("-----");
	    	   }
	       }
	}
	
	
	
	
	
	
	public static void exc3() throws FileNotFoundException {
		File file = new File("src/main/resources/simpleClass.java");
	       CompilationUnit cu =  StaticJavaParser.parse(file);
	       
	       
	       for(int i=0; i<cu.getChildNodes().size(); i++) {
	    	   
	    	   if(cu.getChildNodes().get(i) instanceof ClassOrInterfaceDeclaration) {
	    		   
	    		   
	    		   
	    		   System.out.println("This is a class:\t"+cu.getChildNodes().get(i).getClass().getName());
		    	   System.out.println("-----");
		    	   
		    	   for(int j=0;j<cu.getChildNodes().get(i).getChildNodes().size(); j++) {
		    		   
		    		   if(cu.getChildNodes().get(i).getChildNodes().get(j) instanceof MethodDeclaration) {
			    		   System.out.println("\tThis is a method:\t"+cu.getChildNodes().get(i).getChildNodes().get(j).getClass().getName());
			        	   System.out.println("-----");
		    		   }
		    	   }
	    	   }
	       }
	}
	
	
	
	
	
	
	
	public static void exc4() throws FileNotFoundException {
		File file = new File("src/main/resources/simpleClass.java");
	       
		CompilationUnit cu =  StaticJavaParser.parse(file);
	       
	       
	       for(int i=0; i<cu.getChildNodes().size(); i++) {
	    	   
	    	   if(cu.getChildNodes().get(i) instanceof ClassOrInterfaceDeclaration) {
	    		   
	    		   ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration) cu.getChildNodes().get(i); // casting
	    		   
	    		   System.out.println("This is a class:\t"+claz.getNameAsString());
		    	   System.out.println("-----");
		    	   
		    	   for(int j=0;j<cu.getChildNodes().get(i).getChildNodes().size(); j++) {
		    		   
		    		   if(cu.getChildNodes().get(i).getChildNodes().get(j) instanceof MethodDeclaration) {
		    			   MethodDeclaration mth = (MethodDeclaration) cu.getChildNodes().get(i).getChildNodes().get(j);
			    		   
		    			   
		    			   System.out.println("\tThis is a method:\t"+mth.getNameAsString());
			        	   System.out.println("-----");
		    		   }
		    	   }
	    	   }
	       }
	}
	

	public static void exc5() throws FileNotFoundException {
		File file = new File("src/main/resources/simpleClass.java");
	    CompilationUnit cu =  StaticJavaParser.parse(file);
		
	    
	    for(int i=0; i<cu.getChildNodes().size(); i++) {
	    	
	   	   if(cu.getChildNodes().get(i) instanceof ClassOrInterfaceDeclaration) {
	   		ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration) cu.getChildNodes().get(i); // casting
 		   
	   		System.out.println("This is a class:\t"+claz.getNameAsString());
	   		
	   		claz.getMethods().forEach(mth -> {
	   			System.out.println("\tMethod Name:\t"+mth.getName());
	   			
	   			
	   			mth.getParameters().forEach(pr -> {
	   				System.out.println("\t\tParameters:\t\t"+pr.getName());
	   			});
	   			
	   			mth.getModifiers().forEach(md -> {
	   			
	   				System.out.println("\t\tModifier:\t\t"+md.toString());
	   			});
	   			
	   		});
	    	   
	   	   }
		}
	}
	
}
