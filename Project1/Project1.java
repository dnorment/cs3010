import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Project1
{
    public static void displayMainMenu()
    {
    	System.out.println();
        System.out.println("1. Enter matrix manually");
        System.out.println("2. Read matrix from file");
        System.out.println("3. Exit");
        System.out.print("Enter a number: ");
    }

    public static void displayCalcChoices()
    {
    	System.out.println("1. Gaussian elimination with scaled partial pivoting");
        System.out.println("2. Jacobi method");
        System.out.println("3. Gauss-Siedel method");
    }
    
    public static float absMax(float a[])
    {
    	float max = 0;
    	for (int i=0; i<a.length-1; i++) //don't include b vector
    	{
    		if (Math.abs(a[i]) > max)
    		{
    			max = Math.abs(a[i]);
    		}
    	}
    	return max;
    }
    
    public static float l2VectorNorm(float prev[], float curr[])
    {
		float norm = 0;
		
		for (int i=0; i<prev.length; i++)
		{
			norm += Math.pow(curr[i] - prev[i], 2); //sum of squares of differences
		}
		
		return (float)Math.pow(norm, 0.5); //root of sum
    }
    
    public static void swapRows(float a[][], int x, int y)
    {
    	if (x != y)
    	{
	    	for (int i=0; i<a[0].length; i++)
	    	{
	    		float temp = a[x][i];
	    		a[x][i] = a[y][i];
	    		a[y][i] = temp;
	    	}
    	}
    }
    
    public static void arrangeREF(float a[][])
    {
    	for (int i=0; i<a.length; i++) //for num of rows
    	{
    		for (int j=i; j<a.length; j++)
    		{
    			if (a[j][i] != 0)
    			{
    				swapRows(a, i, j);
    			}
    		}
    	}
    }
    
    public static void printArray(float a[][])
    {
    	int n = a.length;
		for (int i=0; i<n; i++)
		{
			System.out.println(Arrays.toString(a[i]));
		}
		System.out.println();
    }
    
    public static void solve(float a[][])
    {
    	Scanner kb = new Scanner(System.in);
    	int option = 0;
    	while (option < 1 || option > 3)
    	{
    		Project1.displayCalcChoices();
    		System.out.print("Enter a method number: ");
    		option = kb.nextInt();
    	}
    	float e = -1;
    	switch (option)
    	{
    	case 1:
    		Project1.solvePartialPivoting(a);
    		break;
	    case 2:
			while (e < 0) {
				System.out.print("Enter desired error: ");
				e = kb.nextFloat();
			}
			Project1.solveJacobi(a, e);
			break;
		case 3:
			while (e < 0) {
				System.out.print("Enter desired error: ");
				e = kb.nextFloat();
			}
			Project1.solveJacobi(a, e);
			break;
		default:
			break;
    	}
    }
    
    public static void solvePartialPivoting(float a[][])
    {
    	int n = a.length;
		float[] scale = new float[a.length]; //initial absolute max values of coefficient matrix
		float[] currScale = new float[a.length]; //current scaling array to determine pivot row 
		boolean[] used = new boolean[a.length]; //if row has been used
		
		//initial scaling vector (max vals)
		for (int i=0; i<n; i++)
		{
			scale[i] = Project1.absMax(a[i]);
		}
		
		//print initial array
		Project1.printArray(a);
		
		for (int i=0; i<n; i++)
		{
			//get current scale
			for (int j=0; j<n; j++)
			{
				currScale[j] = Math.abs(a[j][i]) / scale[j];
			}
			System.out.println("Current scale: " + Arrays.toString(currScale));
			
			//find pivot row
			int pivotRow = 0;
			float unusedMax = 0;
			for (int j=0; j<n; j++)
			{
				if (currScale[j] > unusedMax && !used[j]) 
				{
					pivotRow = j;
					unusedMax = currScale[j];
				}
			}
			System.out.println("Chosen row as pivot: " + pivotRow);

			//set row as used
			used[pivotRow] = true;
			
			//forward elim
			for (int j=0; j<n; j++) //foreach row
			{ 
				if(!used[j]) //if row not used
				{
					for (int k=i; k<n+1; k++) //foreach column > i-1, including b vector
					{
						a[j][k] -= (a[j][i] / a[pivotRow][i]) * a[pivotRow][k]; //this is the problem
					}
				}
			}
			
			//print intermediate matrix
			Project1.printArray(a);
		}
		
		//put into row echelon form
		Project1.arrangeREF(a);
		
		//do backsub
		try {
			float[] x = new float[n];
			for (int i=x.length-1; i>=0; i--)
			{
				float sum = 0;
				for (int j=i; j<n; j++)
				{
					sum += a[i][j] * x[j];
				}
				if (a[i][i] == 0) throw new ArithmeticException();
				x[i] = (a[i][a.length] - sum) / a[i][i];
			}
			
			//print x vector
			System.out.println("Final solution: ");
			for (int i=0; i<a.length; i++)
			{
				System.out.println("x" + i + " = " + x[i]);
			}
		} catch (ArithmeticException e) {
			System.out.println("Diagonal is zero - possible no solution");
		}
    }
    
    public static void solveJacobi(float a[][], float err)
    {
		float[] prev = new float[a.length]; //initial previous estimate is 0 vector
		float[] curr = new float[a.length];
		float currError = Integer.MAX_VALUE; //initial error undefined
		
		int iter = 0;
		System.out.println(iter + ") Current estimate: " + Arrays.toString(curr));
    	try {
    		
			while (iter <= 50 && currError > err) //max 50 iterations, while error higher than specified
			{
				iter++;
				for (int i=0; i<a.length; i++) //for each equation
				{
					if (a[i][i] == 0) throw new ArithmeticException();
					float est = a[i][a.length]; //b_i
					for (int j=0; j<a.length; j++) //for each item
					{
						if (i != j) 
						{
							est -= (a[i][j] * prev[j]); //-a_ij*x_j(k-1)
						}
					}
					curr[i] = est / a[i][i]; ///a_ii
				}
				float prevError = currError;
				currError = Project1.l2VectorNorm(prev, curr);
				if (prevError < currError)
				{
					System.out.println("Error is diverging - no solution");
					return;
				}
				System.out.println(iter + ") Current estimate: " + Arrays.toString(curr) + ", error = " + currError);
				//print current estimate, error
				for (int k=0; k<a.length; k++) prev[k] = curr[k]; //move current estimate to previous estimate
			}
		} catch (ArithmeticException e) {
			System.out.println("Diagonal elements must be nonzero - rearrange and try again"); //catch divide by 0
			return;
		}
    	
    	//print final
		System.out.println("Final solution: ");
    	for (int i=0; i<a.length; i++)
    	{
    		System.out.println("x" + i + " = " + curr[i]);
    	}
		System.out.println("Final error: " + currError);
    }
    
    public static void solveGaussSiedel(float a[][], float err)
    {
		float[] prev = new float[a.length]; //initial previous estimate is 0 vector
		float[] curr = new float[a.length];
		float currError = Integer.MAX_VALUE; //initial error undefined
		
		int iter = 0;
		System.out.println(iter + ") Current estimate: " + Arrays.toString(curr));
    	try {
    		
			while (iter <= 50 && currError > err) //max 50 iterations, while error higher than specified
			{
				iter++;
				for (int i=0; i<a.length; i++) //for each equation
				{
					if (a[i][i] == 0) throw new ArithmeticException();
					float est = a[i][a.length]; //b_i
					for (int j=0; j<a.length; j++) //for each item
					{
						if (j > i) 
						{
							est -= (a[i][j] * prev[j]); //-a_ij*x_j(k-1)
						}
						else if (j < i) 
						{
							est -= (a[i][j] * curr[j]); //-a_ij*x_j(k)
						}
					}
					curr[i] = est / a[i][i]; ///a_ii
				}
				float prevError = currError;
				currError = Project1.l2VectorNorm(prev, curr);
				if (prevError < currError)
				{
					System.out.println("Error is diverging - no solution");
					return;
				}
				System.out.println(iter + ") Current estimate: " + Arrays.toString(curr) + ", error = " + currError);
				//print current estimate, error
				for (int k=0; k<a.length; k++) prev[k] = curr[k]; //move current estimate to previous estimate
			}
		} catch (ArithmeticException e) {
			System.out.println("Diagonal elements must be nonzero - rearrange and try again"); //catch divide by 0
			return;
		}
    	
    	//print final
		System.out.println("Final solution: ");
    	for (int i=0; i<a.length; i++)
    	{
    		System.out.println("x" + i + " = " + curr[i]);
    	}
		System.out.println("Final error: " + currError);
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        System.out.println("Programming Project 1 - Daniel Norment - CS 3010.01");

        Scanner kb = new Scanner(System.in);

        int option = 0;

        while (option >= 0 && option <= 2)
        {
            displayMainMenu();
            option = kb.nextInt();
            
            float[][] matrix;
            int cols;
            int rows;
            
            switch(option)
            {
            case 1:
            	//read matrix from user
            	rows = 0;
            	
				while (rows <= 0) {
					System.out.print("Enter number of equations: ");
					rows = kb.nextInt();
					kb.nextLine();
				}
            	
				cols = rows + 1;
				
            	matrix = new float[rows][cols]; 
            	
				try {
					System.out.println("Enter the coefficients separated by spaces with corresponding b vector value:");
					for (int i=0; i<rows; i++)
					{
						//read in row
						String rowIn = kb.nextLine();
						String[] vals = rowIn.split(" ");
						//convert row to floats and enter into matrix
						for (int j=0; j<vals.length; j++)
						{
							matrix[i][j] = Float.parseFloat(vals[j]);
						}
					}
				} 
				catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
					System.out.println("Enter only the number of coefficients equal to the row plus one");
				}
				
				Project1.solve(matrix);
				
            	break;
            case 2:
            	//get filename and open file
            	Scanner inFile;
            	String filename;
            	try {
            		kb = new Scanner(System.in);
					System.out.print("Enter the filename of the matrix: ");
					filename = kb.next();
					inFile = new Scanner(new File(filename));
				} catch (FileNotFoundException e) {
					System.out.println("File not found, try again");
					break;
				}
            	
            	//get matrix dimensions            	
            	String rowIn = inFile.nextLine();
            	rows = 1;
            	while (inFile.hasNextLine())
            	{
            		inFile.nextLine();
            		rows++;
            	}
            	String[] row = rowIn.split(" ");
            	cols = row.length;
            	
            	//stop if matrix is wrong size
            	if (!(rows == cols - 1))
            	{
            		System.out.println("Matrix in file should be square with added b column vector");
            		break;
            	}
            	
            	//reset reader
            	inFile.close();
            	inFile = new Scanner(new File(filename));
            	
            	matrix = new float[rows][cols];
            	
            	for (int i=0; i<rows; i++)
				{
					//read in row
					rowIn = inFile.nextLine();
					String[] vals = rowIn.split(" ");
					//convert row to floats and enter into matrix
					for (int j=0; j<vals.length; j++)
					{
						matrix[i][j] = Float.parseFloat(vals[j]);
					}
				}
            	
            	Project1.solve(matrix);
            	
            	break;
            default:
            	break;            		
            }
        }
        kb.close();

    }
}