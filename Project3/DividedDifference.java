public class DividedDifference {

	private float[] x;
	private float[] fx;
	private final int MAX_POINTS = 50;
	
	public DividedDifference(float[] x, float[] fx)
	{
		if (x.length > MAX_POINTS)
		{
			System.out.println("Too many points, errors may occur");
		}
		
		this.x = x;
		this.fx = fx;
	}

	public void doPrint()
	{
		//print table		
		System.out.println();
		System.out.print("    x      f[0]  ");
		for (int i=1; i<x.length; i++)
		{
			System.out.printf("  f[%d]  ", i);
		}
		System.out.println();
		
		for(int i=0; i<x.length; i++)
		{
			System.out.printf(" % .4f ", x[i]);
			for (int j=i; j<x.length; j++)
			{
				System.out.printf("% .4f ", divDiff(i,j));
			}
			System.out.println();
		}

		System.out.println();
		System.out.println();
		
		
		//print interpolating polynomial
		System.out.println("Interpolating polynomial is: ");
		System.out.print("f(x) = ");
		
		for (int i=0; i<x.length; i++)
		{
			//print coeff=divdiff
			System.out.printf("% 2.2f", divDiff(0, i));
			
			//print x terms
			for (int j=0; j<i; j++)
			{
				if (x[j] != 0) 
				{
					System.out.printf("(x%+2.2f)", -1*x[j]);
				}
				else
				{
					System.out.printf("x");
				}
			}
			
			if (i <= x.length - 2) System.out.print(" + ");
		}
		
		System.out.println();
		System.out.println();
		
		
		//print simplified polynomial
		System.out.println("Simplified polynomial is: ");
		System.out.print("f(x) = ");
		
		Polynomial[] expandedInter = new Polynomial[x.length];
		
		for (int k=0; k<expandedInter.length; k++)
		{
			expandedInter[k] = new Polynomial(new float[] { divDiff(0,k) }); //each cell initially divided diff
			for (int i=0; i<k; i++)
			{
				//multiply out each (x-xi)
				expandedInter[k].multiply(new Polynomial(new float[] { -1*x[i], 1 }));
			}
		}
		
		//add each expanded term together
		Polynomial simple = expandedInter[0];
		for (int i=1; i<expandedInter.length; i++)
		{
			simple.add(expandedInter[i]);
		}
		
		System.out.print(simple);
		
	}
	
	public float divDiff(int a, int b)
	{
		if (a > b)
		{
			int t = a;
			a = b;
			b = t;
		}
		
		if (a==b)
		{
			return fx[a];
		}
		else
		{
			return (divDiff(a+1, b) - divDiff(a, b-1))/(x[b] - x[a]);
		}
	}
	
}
