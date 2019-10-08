import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

public class Project3 {
	
	public static void main(String[] args) throws IOException, BadLocationException
	{
		//get contents of rtf so input is not garbage, emulate .txt reading
		RTFEditorKit rtf = new RTFEditorKit();
	    Document doc = rtf.createDefaultDocument();
	    
	    System.out.println();
	    System.out.print("Enter filename (.rtf): ");
	    Scanner kb = new Scanner(System.in);
	    String file = kb.nextLine();
	    kb.close();
	    
	    FileInputStream ins = new FileInputStream(file);
	    rtf.read(ins,doc,0);
		String content = doc.getText(0, doc.getLength());
		
		//get the 2 lines of input and parse into two float arrays
		Scanner input = new Scanner(content);
		
		String[] inx = input.nextLine().split(" ");
		String[] infx = input.nextLine().split(" ");
		
		input.close();
		
		float[] x = new float[inx.length];
		float[] fx = new float[infx.length];
		
		//convert string[] to float[]
		for (int i=0; i<inx.length; i++)
		{
			x[i] = Float.valueOf(inx[i]);
			fx[i] = Float.valueOf(infx[i]);
		}
		
		System.out.println(Arrays.toString(x));
		System.out.println(Arrays.toString(fx));
		
		//print table and polynomials
		DividedDifference dd = new DividedDifference(x, fx);
		dd.doPrint();
	}
	
}
