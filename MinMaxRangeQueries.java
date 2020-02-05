/*Given an array arr[0 . . . n-1]. We need to efficiently find the minimum and
 maximum value from index qs (query start) to qe (query end) where 0 <= qs <= qe <= n-1.
  We are given multiple queries.

Examples:

Input : arr[] = {1, 8, 5, 9, 6, 14, 2, 4, 3, 7}
        queries = 5
        qs = 0 qe = 4
        qs = 3 qe = 7
        qs = 1 qe = 6
        qs = 2 qe = 5
        qs = 0 qe = 8
Output: Minimum = 1 and Maximum = 9 
        Minimum = 2 and Maximum = 14 
        Minimum = 2 and Maximum = 14 
        Minimum = 5 and Maximum = 14
        Minimum = 1 and Maximum = 14 */
import java.util.*;
class Node
{
	int maximum;
	int minimum;
	Node(int maximum,int minimum)
	{
		this.maximum=maximum;
		this.minimum=minimum;
	}
}
class MinMaxRangeQueries
{
	static Node segtree[];
	static void constructree(int low,int high,int arr[],int pos)
	{
		if(low==high)
		{
			segtree[pos].maximum=arr[low];
			segtree[pos].minimum=arr[low];
			return;
		}
		int mid=(low+(high-low)/2);
		constructree(low,mid,arr,2*pos+1);
		constructree(mid+1,high,arr,2*pos+2);
		segtree[pos].maximum=Math.max(segtree[2*pos+1].maximum,segtree[2*pos+2].maximum);
		segtree[pos].minimum=Math.min(segtree[2*pos+1].minimum,segtree[2*pos+2].minimum);
	}
	static Node getmaxmin(int low,int high,int qlow,int qhigh,int pos)
	{
		Node temp=new Node(Integer.MIN_VALUE,Integer.MAX_VALUE);
		if(qlow<=low && qhigh>=high)
			return segtree[pos];
		if(low>high || qlow>high || qhigh<low)
		{
			temp.maximum=Integer.MIN_VALUE;
			temp.minimum=Integer.MAX_VALUE;
			return temp;
		}
		int mid=(low+(high-low)/2);
		temp.maximum=Math.max(getmaxmin(low,mid,qlow,qhigh,2*pos+1).maximum,getmaxmin(mid+1,high,qlow,qhigh,2*pos+2).maximum);
		temp.minimum=Math.min(getmaxmin(low,mid,qlow,qhigh,2*pos+1).minimum,getmaxmin(mid+1,high,qlow,qhigh,2*pos+2).minimum);
		return temp;
	}
	public static void main(String[] args) 
	{
		int arr[] = {1, 8, 5, 9, 6, 14, 2, 4, 3, 7};
		int n=arr.length;
		int x=(int)(Math.ceil(Math.log(n)/Math.log(2)));
		int size=(int)(2*Math.pow(2,x))-1;
		segtree=new Node[size];
		for(int i=0;i<size;i++)
			segtree[i]=new Node(Integer.MIN_VALUE,Integer.MAX_VALUE);
		constructree(0,n-1,arr,0);
		Node t=getmaxmin(0,n-1,0,8,0);
		System.out.println("Maximum = "+t.maximum+" Minimum = "+t.minimum);
	}
}