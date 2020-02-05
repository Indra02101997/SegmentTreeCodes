/*The idea is to use Segment Tree and Binary Search to achieve time complexity O(n (logn)2).

If we have any number equal to ‘k’ in the array then the answer is 1 as GCD of k is k. Return 1.
If there is no number which is divisible by k, then GCD doesn’t exist. Return -1.
If none of the above cases is true, the length of minimum subarray is either greater than 1 or
GCD doesn’t exist. In this case, we follow following steps.
Build segment tree so that we can quicky find GCD of any subarray using the approach discussed here
After building Segment Tree, we consider every index as starting point and do binary search for 
ending point such that the subarray between these two points has GCD k

Example:

arr[] = {6, 9, 7, 10, 12, 24, 36, 27}, K = 3

// Initial value of minLen is equal to size 
// of array
minLen = 8 

No element is equal to k so result is either 
greater than 1 or doesn't exist. 
First index

GCD of subarray from 1 to 5 is 1.
GCD < k
GCD of subarray from 1 to 3 is 1.
GCD < k
GCD of subarray from 1 to 2 is 3
minLen = minimum(8, 2) = 2
Second Index

GCD of subarray from 2 to 5 is 1
GCD < k
GCD of subarray from 2 to 4 is 1
GCD < k
GCD of subarray from 6 to 8 is 3
minLen = minimum(2, 3) = 2.
.

.

.

Sixth Index

GCD of subarray from 6 to 7 is 12
GCD > k
GCD of subarray from 6 to 8 is 3
minLen = minimum(2, 3) = 2
Time Complexity: O(n (logn)2), O(n) for traversing to each index, O(logn) for each subarray and 
O(logn) for GCD of each subarray.*/
import java.util.*;
class SmallestSubarrayWithGivenGCD
{
	static int segtree[];
	static int gcd(int a,int b)
	{
		if(b>a)
		{
			int t=a;
			a=b;
			b=t;
		}
		if(b==0)
			return a;
		return gcd(b,a%b);
	}
	static void constructree(int low,int high,int pos,int arr[])
	{
		if(low==high)
		{
			segtree[pos]=arr[low];
			return;
		}
		int mid=(low+(high-low)/2);
		constructree(low,mid,2*pos+1,arr);
		constructree(mid+1,high,2*pos+2,arr);
		segtree[pos]=gcd(segtree[2*pos+1],segtree[2*pos+2]);
	}
	static int getgcdrange(int low,int high,int qlow,int qhigh,int pos)
	{
		if(low>high || qlow>high || qhigh<low)
			return 0;
		if(qlow<=low && qhigh>=high)
			return segtree[pos];
		int mid=(low+(high-low)/2);
		return gcd(getgcdrange(low,mid,qlow,qhigh,2*pos+1),getgcdrange(mid+1,high,qlow,qhigh,2*pos+2));
	}
	static int findsmallestrange(int arr[],int n,int k)
	{
		boolean found=false;
		// Find if k or its multiple is present 
		for(int i=0;i<n;i++)
		{
			// If k is present, then subarray size is 1.
			if(arr[i]==k)
				return 1;
			// Break the loop to indicate presence of a 
        	// multiple of k. 
			if(arr[i]%k==0)
				found=true;
		}
		// If there was no multiple of k in arr[], then 
    	// we can't get k as GCD.
		if(!found)
			return -1;
		// If there is a multiple of k in arr[], build 
    	// segment tree from given array
		constructree(0,n-1,0,arr);
		int res=n+1;
		// Now consider every element as starting point 
    	// and search for ending point using Binary Search 
		for(int i=0;i<n-1;i++)
		{
			// arr[i] cannot be a starting point, if it is 
        	// not a multiple of k. 
			if(arr[i]%k!=0)
				continue;
			// Initialize indexes for binary search of closest 
        	// ending point to i with GCD of subarray as k. 
			int low=i+1;
			int high=n-1;
			// Initialize closest ending point for i.
			int closest=0;
			// Binary Search for closest ending point 
        	// with GCD equal to k. 
			while(true)
			{
				// Find middle point and GCD of subarray 
            	// arr[i..mid]
				int mid=(low+(high-low)/2);
				int hcf=getgcdrange(0,n-1,i,mid,0);
				// If GCD is more than k, look further
				if(hcf>k)
					low=mid;
				// If GCD is k, store this point and look for 
            	// a closer point 
				else if(hcf==k)
				{
					high=mid;
					closest=mid;
					break;
				}
				// If GCD is less than, look closer
				else 
					high=mid;
				// If termination condition reached, set 
            	// closest 
				if(Math.abs(high-low)<=1)
				{
					if(getgcdrange(0,n-1,i,low,0)==k)
						closest=low;
					else if(getgcdrange(0,n-1,i,high,0)==k)
						closest=high;
					break;
				}
			}
			if(closest!=0)
				res=Math.min(res,closest-i+1);
		}
		return res;
	}
	public static void main(String[] args) 
	{
		int n = 8; 
    	int k = 3; 
    	int arr[] = {6, 9, 7, 10, 12, 24, 36, 27};
    	int x=(int)(Math.ceil(Math.log(n)/Math.log(2)));
    	int size=2*(int)(Math.pow(2,x))-1;
    	segtree=new int[size];
    	System.out.println(findsmallestrange(arr,n,k));
	}
}