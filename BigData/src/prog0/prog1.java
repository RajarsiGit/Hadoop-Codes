package prog0;

import java.io.*;
import java.math.*;

public class prog1
{
	public static void main(String[] args)throws IOException
	{
		int i,n;
		String str;
		BigInteger a=new BigInteger("0"),b=new BigInteger("1"),c=new BigInteger("0");
		double startTime,elapsedTime;
		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		RandomAccessFile raf=new RandomAccessFile("file1.txt","rw");
		raf.setLength(0);
		
		System.out.println("\nEnter number of elements: ");
		n=Integer.parseInt(br.readLine());

		startTime=System.nanoTime();
	    for(i=0;i<n;i++)
	    {
	    	str=a.toString();
			raf.writeUTF(str);
			c=a.add(b);
			a=b;
			b=c;
	    }
	    elapsedTime=System.nanoTime()-startTime;

	    raf.seek(0);
	    System.out.println("\nFibonacci series: ");
		for(i=0;i<n;i++)
			System.out.print(raf.readUTF()+"\t");

		System.out.println("\n\nTotal series generation time: "+elapsedTime/1000000000+"s");

		raf.close();
	}
}