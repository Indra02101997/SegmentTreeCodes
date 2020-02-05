import java.util.*;
class RangeMinimumQuery
{
	static int segtree[];
	static void constructtree(int arr[],int low,int high,int pos)
	{
		if(low==high)
		{
			segtree[pos]=arr[low];
			return;
		}
		int mid=(low+(high-low)/2);
		constructtree(arr,low,mid,2*pos+1);
		constructtree(arr,mid+1,high,2*pos+2);
		segtree[pos]=Math.min(segtree[2*pos+1],segtree[2*pos+2]);
	}
	static int getmin(int qlow,int qhigh,int low,int high,int pos)
	{
		if(qlow<=low && qhigh>=high)
			return segtree[pos];
		if(qlow>high || qhigh<low)
			return Integer.MAX_VALUE;
		int mid=(low+(high-low)/2);
		return Math.min(getmin(qlow,qhigh,low,mid,2*pos+1),getmin(qlow,qhigh,mid+1,high,2*pos+2));
	}
	public static void main(String[] args) 
	{
		int arr[] = {1, 3, 2, 7, 9, 11}; 
        int n = arr.length;
        int low=0;
        int high=n-1;
        int x=(int)(Math.ceil(Math.log(n)/Math.log(2)));
        int size=(int)(2*Math.pow(2,x))-1;
        segtree=new int[size];
        constructtree(arr,low,high,0);
        int qlow=1;
        int qhigh=5;
        System.out.println("The minimum in 1 and 5 is "+getmin(qlow,qhigh,low,high,0));
	}
}