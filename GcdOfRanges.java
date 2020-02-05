import java.util.*;
class GcdOfRanges
{
	static int segtree[];
	static int gcd(int a,int b)
	{
		if(b>a)
		{
			int temp=a;
			a=b;
			b=temp;
		}
		if(b==0)
			return a;
		return gcd(b,a%b);
	}
	static void constructtree(int low,int high,int pos,int arr[])
	{
		if(low==high)
		{
			segtree[pos]=arr[low];
			return;
		}
		int mid=(low+high)/2;
		constructtree(low,mid,2*pos+1,arr);
		constructtree(mid+1,high,2*pos+2,arr);
		segtree[pos]=gcd(segtree[2*pos+1],segtree[2*pos+2]);
	}
	static int getgcd(int low,int high,int qlow,int qhigh,int pos)
	{
		if(qlow>high || qhigh<low)
			return 0;
		if(qlow<=low && qhigh>=high)
			return segtree[pos];
		int mid=(low+high)/2;
		return gcd(getgcd(low,mid,qlow,qhigh,2*pos+1),getgcd(mid+1,high,qlow,qhigh,2*pos+2));
	}
	public static void main(String[] args) 
	{
		int[] a = {2, 3, 6, 9, 5}; 
		int n=a.length;
		int x=(int)(Math.ceil(Math.log(n)/Math.log(2)));
		int size=2*(int)(Math.pow(2,x))-1;
		segtree=new int[size];
		constructtree(0,n-1,0,a);
		System.out.println(getgcd(0,n-1,1,3,0));
	}
}