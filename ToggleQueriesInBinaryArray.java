/*Given a size n in which initially all elements are 0. The task is to perform multiple 
multiple queries of following two types. The queries can appear in any order.

toggle(start, end) : Toggle (0 into 1 or 1 into 0) the values from range ‘start’ to ‘end’.
count(start, end) : Count the number of 1’s within given range from ‘start’ to ‘end’.
Input : n = 5       // we have n = 5 blocks
        toggle 1 2  // change 1 into 0 or 0 into 1
        Toggle 2 4
        Count  2 3  // count all 1's within the range
        Toggle 2 4
        Count  1 4  // count all 1's within the range
Output : Total number of 1's in range 2 to 3 is = 1
         Total number of 1's in range 1 to 4 is = 2 */
import java.util.*;
class ToggleQueriesInBinaryArray
{
	static int segtree[];
	static boolean lazy[];
	static void toggle(int low,int high,int qlow,int qhigh,int pos)
	{
		if(low>high)
			return;
		if(lazy[pos]!=false)
		{
			segtree[pos]=(high-low+1)-segtree[pos];
			if(low!=high)
			{
				lazy[2*pos+1]=lazy[pos];
				lazy[2*pos+2]=lazy[pos];
			}
			lazy[pos]=false;
		}
		if(qlow>high || qhigh<low)
			return;
		if(qlow<=low && qhigh>=high)
		{
			segtree[pos]=(high-low+1)-segtree[pos];
			if(low!=high)
			{
				lazy[pos*2+1]=!lazy[pos];
				lazy[2*pos+2]=!lazy[pos];
			}
			return ;
		}
		int mid=(low+(high-low)/2);
		toggle(low,mid,qlow,qhigh,2*pos+1);
		toggle(mid+1,high,qlow,qhigh,2*pos+2);
		segtree[pos]=segtree[2*pos+1]+segtree[2*pos+2];
	}
	static int countquery(int low,int high,int qlow,int qhigh,int pos)
	{
		if(lazy[pos]!=false)
		{
			segtree[pos]=(high-low+1)-segtree[pos];
			if(low!=high)
			{
				lazy[2*pos+1]=lazy[pos];
				lazy[2*pos+2]=lazy[pos];
			}
			lazy[pos]=false;
		}
		if(qlow>high || qhigh<low || low>high)
			return 0;
		if(qlow<=low && qhigh>=high)
			return segtree[pos];
		int mid=(low+(high-low)/2);
		return (countquery(low,mid,qlow,qhigh,2*pos+1)+countquery(mid+1,high,qlow,qhigh,2*pos+2));
	}
	public static void main(String[] args) 
	{
		int n=5;
		int x=(int)(Math.ceil(Math.log(n)/Math.log(2)));
		int size=2*(int)(Math.pow(2,x))-1;
		segtree=new int[size];
		lazy=new boolean[size];
		Arrays.fill(segtree,0);

		toggle(0,n-1,1,2,0);
		toggle(0,n-1,2,4,0);
		System.out.println(countquery(0,n-1,2,3,0));
		toggle(0,n-1,2,4,0);
		System.out.println(countquery(0,n-1,1,4,0));
	}
}