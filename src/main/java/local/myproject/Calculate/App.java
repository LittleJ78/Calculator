package local.myproject.Calculate;

import java.util.Scanner;

public class App 
{
    public static void main(String[] args ) {
       
    	Scanner in = new Scanner(System.in);
    	
    	
    	String input = in.nextLine();
    	Expression expr = new Expression(input);
    	
    	System.out.println(expr.isValid() ? expr.getResult() : ""); 
    	in.close();
    }
}