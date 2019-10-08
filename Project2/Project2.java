import java.util.Scanner;

public class Project2 
{
	public static void displayMethods()
	{
		System.out.println("1) Bisection");
		System.out.println("2) False Position");
		System.out.println("3) Newton-Raphson");
		System.out.println("4) Secant");
		System.out.println("5) Modified Secant");
		System.out.println("6) Exit");
		System.out.print("Enter a choice: ");
	}
	
	public static void main(String[] args)
	{
		System.out.println("CS 3010.01 Programming Project 2 - Daniel Norment");
		
		Scanner kb = new Scanner(System.in);
		int choice;
		
		System.out.print("Enter desired error: ");
		float error = kb.nextFloat();
		
		System.out.print("Choose method (a) = 1, (b) = 2: ");
		choice = kb.nextInt();
		char method = (choice == 1) ? 'A' : 'B';
		
		do
		{
			displayMethods();
			choice = kb.nextInt();
			
			double a, b, x;
			
			switch(choice)
			{
			case 1: //bisection
				System.out.print("Enter the starting left and right bounds: ");
				a = kb.nextDouble();
				b = kb.nextDouble();
				FindZeros.bisection(method, a, b, error);
				break;
			case 2: //false position
				System.out.print("Enter the starting left and right bounds: ");
				a = kb.nextDouble();
				b = kb.nextDouble();
				FindZeros.falsePosition(method, a, b, error);
				break;
			case 3: //newton
				System.out.print("Enter the starting value: ");
				x = kb.nextDouble();
				FindZeros.newtonRaphson(method, x, error);
				break;
			case 4: //secant
				System.out.print("Enter the starting left and right bounds: ");
				a = kb.nextDouble();
				b = kb.nextDouble();
				FindZeros.secant(method, a, b, error);
				break;
			case 5: //modified secant
				System.out.print("Enter the starting value: ");
				x = kb.nextDouble();
				FindZeros.modifiedSecant(method, x, error);				
				break;
			default: break;
			}
			
		} while (choice > 0 && choice < 6);
		
		kb.close();
	}
}