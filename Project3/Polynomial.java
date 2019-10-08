public class Polynomial {

	private float[] coeffs;
	
	public Polynomial(float[] c)
	{
		coeffs = c;
	}
	
	public void add(Polynomial p)
	{
		float[] newc = new float[Math.max(coeffs.length, p.getLength())];
		
		for (int i=0; i<newc.length; i++)
		{
			if (i < coeffs.length) newc[i] += coeffs[i];
			if (i < p.getLength()) newc[i] += p.toArray()[i];
		}
		
		coeffs = newc;
	}
	
	public void multiply(Polynomial p)
	{
		float[] newc = new float[coeffs.length + p.getLength() - 1];
		
		for (int i=0; i<coeffs.length; i++)
		{
			for (int j=0; j<p.getLength(); j++)
			{
				newc[i+j] += coeffs[i] * p.toArray()[j];
			}
		}
		
		coeffs = newc;
	}
	
	public float[] toArray() { return coeffs; };
	
	public String toString()
	{
		String result = "";
		result += String.format("% 2.2f ", coeffs[0]);
		for (int i=1; i<coeffs.length; i++)
		{
			if (i == 1 && coeffs[i] != 0) {
				result += String.format("+ % 2.2fx ", coeffs[i]);
			}
			else if (coeffs[i] != 0) 
			{
				result += String.format("+ % 2.2fx^%d ", coeffs[i], i);
			}
		}
		return result;
	}
	
	public int getLength() { return coeffs.length; };
	
}
