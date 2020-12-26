package prog0;

import java.io.*;
import java.lang.Math;

public class prog0
{
	public static void main(String[] args)throws IOException
	{
		int i,n;
		double startTime,elapsedTime;
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		RandomAccessFile raf1=new RandomAccessFile("file0.txt","rw");
		RandomAccessFile raf2=new RandomAccessFile("file1.txt","rw");
		raf2.setLength(0);
		
		System.out.println("\nEnter number of elements: ");
		n=Integer.parseInt(br.readLine());

		for(i=0;i<n;i++)
			raf1.writeInt(((int)(10000.0*Math.random())));

		raf1.seek(0);

		startTime=System.nanoTime();
	    quicksort(raf1,raf2,0,n-1);
	    elapsedTime=System.nanoTime()-startTime;

	    raf2.seek(0);
	    System.out.println("\nSorted elements: ");
		for(i=0;i<n;i++)
			System.out.print(raf2.readInt()+"\t");

		System.out.println("\n\nTotal sort time: "+elapsedTime/1000000000);

		raf1.close();
		raf2.close();
	}
	public static void quicksort(RandomAccessFile raf1, RandomAccessFile raf2, int first, int last)throws IOException
	{
	   	int i,j,pivot,p,q,r;

	   	if(first<last)
	   	{
	      	pivot=first;
	      	i=first;
	      	j=last;

	      	raf1.seek(4*i);
      		p=raf1.readInt();
      		raf1.seek(4*pivot);
      		q=raf1.readInt();
      		raf1.seek(4*j);
      		r=raf1.readInt();
	      		
	      	while(i<j)
	      	{
	      		raf1.seek(4*i);
	      		p=raf1.readInt();
	      		raf1.seek(4*pivot);
	      		q=raf1.readInt();
	      		raf1.seek(4*j);
	      		r=raf1.readInt();
	         	while(p<=q&&i<last)
	         	{
	            	i++;
	            	raf1.seek(4*i);
	      			p=raf1.readInt();
	         	}
	         	while(r>q)
	         	{
	            	j--;
	            	raf1.seek(4*j);
	      			r=raf1.readInt();
	         	}
	         	if(i<j)
	         	{
	            	raf2.seek(4*i);
	            	raf2.writeInt(r);
	            	raf2.seek(4*j);
	            	raf2.writeInt(p);
	         	}
	      	}
	      	raf2.seek(4*pivot);
	      	raf2.writeInt(r);
	      	raf2.seek(4*j);
	      	raf2.writeInt(q);

	      	quicksort(raf1,raf2,first,j-1);
	      	quicksort(raf1,raf2,j+1,last);
	   	}
	}
}