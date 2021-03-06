import java.util.*;
class RangeMinimumQueryWithLazyPropagation
{
	static int segtree[];
	static int lazy[];
	static void constructree(int arr[],int low,int high,int pos)
	{
		if(low==high)
		{
			segtree[pos]=arr[low];
			return;
		}
		int mid=(low+(high-low)/2);
		constructree(arr,low,mid,2*pos+1);
		constructree(arr,mid+1,high,2*pos+2);
		segtree[pos]=Math.min(segtree[2*pos+1],segtree[2*pos+2]);
	}
	static void updatelazy(int low,int high,int qlow,int qhigh,int pos,int delta)
	{
		if(low>high)
			return;
		if(lazy[pos]!=0)
		{
			segtree[pos]+=lazy[pos];
			if(low!=high)
			{
				lazy[2*pos+1]+=lazy[pos];
				lazy[2*pos+2]+=lazy[pos];
			}
			lazy[pos]=0;
		}
		if(qlow>high || qhigh<low)
			return;
		if(qlow<=low && qhigh>=high)
		{
			segtree[pos]+=delta;
			if(low!=high)
			{
				lazy[2*pos+1]+=delta;
				lazy[2*pos+2]+=delta;
			}
			return;
		}
		int mid=(low+(high-low)/2);
		updatelazy(low,mid,qlow,qhigh,2*pos+1,delta);
		updatelazy(mid+1,high,qlow,qhigh,2*pos+2,delta);
		segtree[pos]=Math.min(segtree[2*pos+1],segtree[2*pos+2]);
	}
	static int getmin(int low,int high,int qlow,int qhigh,int pos)
	{
		if(low>high)
			return 0;
		if(lazy[pos]!=0)
		{
			segtree[pos]+=lazy[pos];
			if(low!=high)
			{
				lazy[2*pos+1]+=lazy[pos];
				lazy[2*pos+2]+=lazy[pos];
			}
			lazy[pos]=0;
		}
		if(qlow<=low && qhigh>=high)
			return segtree[pos];
		if(qlow>high || qhigh<low)
			return Integer.MAX_VALUE;
		int mid=(low+(high-low)/2);
		return Math.min(getmin(low,mid,qlow,qhigh,2*pos+1),getmin(mid+1,high,qlow,qhigh,2*pos+2));
	}
	public static void main(String[] args) 
	{
		int arr[] = {1, 3, 5, 7, 9, 11}; 
        int n = arr.length;
        int x=(int)(Math.ceil(Math.log(n)/Math.log(2)));
        int size=2*(int)(Math.pow(2,x))-1;
        segtree=new int[size];
        lazy=new int[size];
        constructree(arr,0,n-1,0);
        System.out.println(getmin(0,n-1,1,3,0));
        updatelazy(0,n-1,0,1,0,3);
        System.out.println(getmin(0,n-1,1,3,0));
	}
}