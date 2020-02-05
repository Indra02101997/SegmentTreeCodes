/*Given an array of integers, evaluate queries of the form LCM(l, r). 
There might be many queries, hence evaluate the queries efficiently.

LCM (l, r) denotes the LCM of array elements
           that lie between the index l and r
           (inclusive of both indices) 

Mathematically, 
LCM(l, r) = LCM(arr[l],  arr[l+1] , ......... ,
                                  arr[r-1], arr[r])
Examples:

Inputs : Array = {5, 7, 5, 2, 10, 12 ,11, 17, 14, 1, 44}
         Queries: LCM(2, 5), LCM(5, 10), LCM(0, 10)
Outputs: 60 15708 78540
Explanation : In the first query LCM(5, 2, 10, 12) = 60, 
              similarly in other queries. */
import java.util.*;
class RangeLCMQueries
{
	static final int MAX = 1000; 
  
    // allocate space for tree  
    static int segtree[] = new int[4 * MAX]; 
  
    // declaring the array globally  
    static int arr[] = new int[MAX];
    static int gcd(int a,int b)
    {
    	if(b%a==0)
    		return a;
    	return gcd(b,a%b);
    }
    static int lcm(int a,int b)
    {
    	return (a*b)/gcd(a,b);
    }
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
    	segtree[pos]=lcm(segtree[2*pos+1],segtree[2*pos+2]);
    }
    static int getlcm(int low,int high,int qlow,int qhigh,int pos)
    {
    	if(low>high)
    		return 1;
    	if(qlow<=low && qhigh>=high)
    		return segtree[pos];
    	if(qlow>high || qhigh<low)
    		return 1;
    	int mid=(low+(high-low)/2);
    	return lcm(getlcm(low,mid,qlow,qhigh,2*pos+1),getlcm(mid+1,high,qlow,qhigh,2*pos+2));
    }
	public static void main(String[] args) 
	{
		arr[0] = 5; 
        arr[1] = 7; 
        arr[2] = 5; 
        arr[3] = 2; 
        arr[4] = 10; 
        arr[5] = 12; 
        arr[6] = 11; 
        arr[7] = 17; 
        arr[8] = 14; 
        arr[9] = 1; 
        arr[10] = 44; 
        constructtree(arr,0,10,0);
        System.out.println(getlcm(0,10,2,5,0));
        System.out.println(getlcm(0,10,5,10,0));
        System.out.println(getlcm(0,10,0,10,0));
	}
}