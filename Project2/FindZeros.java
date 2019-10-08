public class FindZeros 
{
	private final static double DELTA = 0.01;
	private final static int MAX_ITERATIONS = 100;
	
	private static double functionA(double x)
	{
		return (2*Math.pow(x, 3) - 11.7*Math.pow(x, 2) + 17.7*x - 5);
	}
	
	private static double functionB(double x)
	{
		return (x + 10 - x*Math.cosh(50/x));
	}
	
	private static double f(char s, double x)
	{
		if (s == 'A')
		{
			return functionA(x);
		}
		else if (s == 'B')
		{
			return functionB(x);
		}
		else
		{
			throw new IllegalArgumentException("Possible functions are A or B");
		}
	}
	
	private static double dfunctionA(double x)
	{
		return (6*Math.pow(x, 2) - 23.4*x + 17.7);
	}
	
	private static double dfunctionB(double x)
	{
		return ((50*Math.sinh(50/x)/x) - Math.cosh(50/x) + 1);
	}
	
	private static double df(char s, double x)
	{
		if (s == 'A')
		{
			return dfunctionA(x);
		}
		else if (s == 'B')
		{
			return dfunctionB(x);
		}
		else
		{
			throw new IllegalArgumentException("Possible functions are A or B");
		}
	}
	
	public static void bisection(char s, double a, double b, float error)
	{
		if (f(s,a) * f(s,b) > 0)
		{
			System.out.println("Bounds do not contain only one root");
			return;
		}
		
		System.out.println("Bisection:");
		System.out.println(" n        a         b         c       f(a)       f(b)       f(c)       ea");
		for (int i=0; i<73; i++) System.out.print("-");
		System.out.println();
		
		double estRoot = 0;
		double pc = Double.MAX_VALUE;
		
		for (int n=0; n<MAX_ITERATIONS; n++)
		{
			if (f(s,a) * f(s,b) > 0)
			{
				System.out.println("Bounds do not contain only one root");
				return;
			}
			
			double cc = (a + b) / 2;
			double e = Math.abs((cc - pc) / cc);
			
			if (n == 0) System.out.printf("%3d   %+5f %+5f %+5f %+5f %+5f %+5f%n", n+1, a, b, cc, f(s,a), f(s,b), f(s,cc));
			if (n > 0) System.out.printf("%3d   %+5f %+5f %+5f %+5f %+5f %+5f %5f%n", n+1, a, b, cc, f(s,a), f(s,b), f(s,cc), e);
			
			if (e < error) break;
			
			if (f(s,a) * f(s,cc) < 0)
			{
				b = cc;
			}
			else if(f(s,b) * f(s,cc) < 0)
			{
				a = cc;
			}
			pc = cc;
			estRoot = cc;
			
			if (Double.isNaN(estRoot))
			{
				System.out.println("Method is diverging from the root");
				return;
			}
		}
		
		System.out.println();
		System.out.printf("Root: (%f, %f)%n", estRoot, f(s,estRoot));
		System.out.println();
	}
	
	public static void falsePosition(char s, double a, double b, float error)
	{
		if (f(s,a) * f(s,b) > 0)
		{
			System.out.println("Bounds do not contain only one root");
			return;
		}
		
		System.out.println("False Position:");
		System.out.println(" n        a         b         c       f(a)       f(b)       f(c)       ea");
		for (int i=0; i<73; i++) System.out.print("-");
		System.out.println();
		
		double estRoot = 0;
		double pc = Double.MAX_VALUE;
		
		for (int n=0; n<MAX_ITERATIONS; n++)
		{
			if (f(s,a) * f(s,b) > 0)
			{
				System.out.println("Bounds do not contain only one root");
				return;
			}
			
			double cc = a - f(s,a)*( (b - a) / (f(s,b) - f(s,a)));
			double e = Math.abs((cc - pc) / cc);
			
			if (n == 0) System.out.printf("%3d   %+5f %+5f %+5f %+5f %+5f %+5f%n", n+1, a, b, cc, f(s,a), f(s,b), f(s,cc));
			if (n > 0) System.out.printf("%3d   %+5f %+5f %+5f %+5f %+5f %+5f %5f%n", n+1, a, b, cc, f(s,a), f(s,b), f(s,cc), e);
			
			if (e < error) break;
			
			if (f(s,a) * f(s,cc) < 0)
			{
				b = cc;
			}
			else if(f(s,b) * f(s,cc) < 0)
			{
				a = cc;
			}
			
			pc = cc;
			estRoot = cc;
			
			if (Double.isNaN(estRoot))
			{
				System.out.println("Method is diverging from the root");
				return;
			}
		}
		
		System.out.println();
		System.out.printf("Root: (%f, %f)%n", estRoot, f(s,estRoot));
		System.out.println();
	}
	
	public static void newtonRaphson(char s, double x, float error)
	{
		System.out.println("Newton-Raphson:");
		System.out.println(" n       x         f(x)         f'(x)          ea");
		for (int i=0; i<50; i++) System.out.print("-");
		System.out.println();
		
		double estRoot = 0;
		double pe = Double.MAX_VALUE;
		double px = x;
		
		for (int n=0; n<MAX_ITERATIONS; n++)
		{
			if (df(s,px) == 0) {
				System.out.println("Zero slope at x=" + px);
				return;
			}
			double cx = px - f(s,px) / df(s,px);
			double e = Math.abs((cx - px) / cx);
			
			if (n == 0) System.out.printf("%3d   %+5f %+5f %+5f%n", n+1, px, f(s,px), df(s,px));
			if (n > 0) System.out.printf("%3d   %+5f %+5f %+5f %5f%n", n+1, px, f(s,px), df(s,px), pe);
			
			if (pe < error) break;
			
			px = cx;
			pe = e;
			estRoot = cx;
			
			if (Double.isNaN(estRoot))
			{
				System.out.println("Method is diverging from the root");
				return;
			}
		}
		
		System.out.println();
		System.out.printf("Root: (%f, %f)%n", estRoot, f(s,estRoot));
		System.out.println();
	}
	
	public static void secant(char s, double x0, double x1, float error)
	{
		System.out.println("Secant:");
		System.out.println(" n      x(n-1)     x(n)     f(x(n-1))   f(x(n))     ea");
		for (int i=0; i<64; i++) System.out.print("-");
		System.out.println();
		
		double estRoot = 0;
		double px = x1;
		double pe = Double.MAX_VALUE;
		
		for (int n=0; n<MAX_ITERATIONS; n++)
		{
			double nx = x1 - f(s,x1)*((x1 - x0) / (f(s,x1) - f(s,x0)));
			
			double e = Math.abs((nx - px) / nx);
			
			if (n == 0) System.out.printf("%3d   %+5f %+5f %+5f %+5f%n", n+1, x0, x1, f(s,x0), f(s,x1));
			if (n > 0) System.out.printf("%3d   %+5f %+5f %+5f %+5f %5f%n", n+1, x0, x1, f(s,x0), f(s,x1), pe);
			
			x0 = x1;
			x1 = nx;
			
			if (pe < error) break;
			
			px = nx;
			pe = e;
			estRoot = nx;
			
			if (Double.isNaN(estRoot))
			{
				System.out.println("Method is diverging from the root");
				return;
			}
		}
		
		
		
		System.out.println();
		System.out.printf("Root: (%f, %f)%n", estRoot, f(s,estRoot));
		System.out.println();
	}
	
	public static void modifiedSecant(char s, double x, float error)
	{
		if (x == 0) {
			System.out.println("Method does not work with initial x=0");
			return;
		}
		
		System.out.println("Modified Secant:");
		System.out.println("Secant:");
		System.out.println(" n         x      f(x(n))      ea");
		for (int i=0; i<44; i++) System.out.print("-");
		System.out.println();
		
		double estRoot = 0;
		double px = x;
		double pe = Double.MAX_VALUE;
		
		for (int n=0; n<MAX_ITERATIONS; n++)
		{
			double nx = px - f(s,px) * (DELTA*px / (f(s,px + DELTA*px) - f(s,px)));
			
			double e = Math.abs((nx - px) / nx);
			
			if (n == 0) System.out.printf("%3d   %+5f %+5f%n", n+1, px, f(s,px));
			if (n > 0) System.out.printf("%3d   %+5f %+5f %5f%n", n+1, px, f(s,px), pe);
			
			if (pe < error) break;
			
			px = nx;
			pe = e;
			estRoot = nx;
			
			if (Double.isNaN(estRoot))
			{
				System.out.println("Method is diverging from the root");
				return;
			}
		}
		
		
		System.out.println();
		System.out.printf("Root: (%f, %f)%n", estRoot, f(s,estRoot));
		System.out.println();
	}
}