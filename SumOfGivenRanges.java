import java.util.*;
class SumOfGivenRanges
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
		segtree[pos]=segtree[2*pos+1]+segtree[2*pos+2];
	}
	static int getsum(int qlow,int qhigh,int low,int high,int pos)
	{
		if(qlow<=low && qhigh>=high)
			return segtree[pos];
		if(qlow>high || qhigh<low)
			return 0;
		int mid=(low+(high-low)/2);
		return getsum(qlow,qhigh,low,mid,2*pos+1)+getsum(qlow,qhigh,mid+1,high,2*pos+2);
	}
	static void updateutil(int low,int high,int diff,int i,int pos)
	{
		if(i<low || i>high)
			return;
		segtree[pos]+=diff;
		if(low!=high)
		{
			int mid=(low+(high-low)/2);
			updateutil(low,mid,diff,i,2*pos+1);
			updateutil(mid+1,high,diff,i,2*pos+2);
		}
	}
	static void update(int arr[],int n,int i,int newval)
	{
		if(i<0 || i>=n)
		{
			System.out.println("Invalid Input ");
			return;
		}
		int diff=newval-arr[i];
		arr[i]=newval;
		updateutil(0,n-1,diff,i,0);
	}
	public static void main(String[] args) 
	{
		int arr[] = {1, 3, 5, 7, 9, 11}; 
        int n = arr.length;
        int low=0;
        int high=n-1;
        int x=(int)(Math.ceil(Math.log(n)/Math.log(2)));// next nearest power of 2
        int size=2*(int)(Math.pow(2,x))-1;
        segtree=new int[size];
        constructtree(arr,low,high,0);
        int qlow=1;
        int qhigh=3;
        System.out.println(getsum(qlow,qhigh,low,high,0));
        update(arr,n,1,10);
        System.out.println(getsum(qlow,qhigh,low,high,0));
	}
}