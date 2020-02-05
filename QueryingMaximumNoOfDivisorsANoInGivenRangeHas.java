/*Below are steps to solve the problem.

Firstly, let’s see how many number of divisors does a number n = p1k1 * p2k2 * … * 
pnkn (where p1, p2, …, pn are prime numbers) has; the answer is (k1 + 1)*(k2 + 1)*…*(kn + 1). 
How? For each prime number in the prime factorization, we can have its ki + 1 possible powers 
in a divisor (0, 1, 2,…, ki).
Now let’s see how can we find the prime factorization of a number, we firstly build an array,
 smallest_prime[], which stores the smallest prime divisor of i at ith index, we divide a number 
 by its smallest prime divisor to obtain a new number (we also have the smallest prime divisor of 
 this new number stored), we keep doing it until the smallest prime of the number changes, 
 when the smallest prime factor of the new number is different from the previous number’s, 
 we have ki for the ith prime number in the prime factorization of the given number.
Finally, we obtain the number of divisors for all the numbers and store these in a segment tree 
that maintains the maximum numbers in the segments. We respond to each query by querying the segment tree. */
import java.util.*;
class QueryingMaximumNoOfDivisorsANoInGivenRangeHas
{
	static int maxn=1000005;
	static int inf=99999999;
	static int smallestprime[]=new int[maxn];
	static int divisors[]=new int[maxn];
	static int segtree[]=new int[4*maxn];
	static void getsmallestprime()
	{
		Arrays.fill(smallestprime,inf);
		for(int i=2;i<maxn;i++)
		{
			if(smallestprime[i]==inf)
			{
				smallestprime[i]=i;
				for(int j=(2*i);j<maxn;j+=i)
				{
					if(smallestprime[j]>i)
						smallestprime[j]=i;
				}
			}
			//System.out.print(smallestprime[i]+" ");
		}
	}
	static void getdivisors()
	{
		for(int i=1;i<maxn;i++)
		{
			divisors[i]=1;
			int p=smallestprime[i];
			int k=0;
			int n=i;
			while(n>1)
			{
				n=n/p;
				k++;
				if(smallestprime[n]!=p)
				{
					divisors[i]=divisors[i]*(k+1);
					k=0;
				}
				p=smallestprime[n];
			}
		}
	}
	static void constructtree(int low,int high,int pos)
	{
		if(low==high)
		{
			segtree[pos]=divisors[low];
			return;
		}
		int mid=(low+high)/2;
		constructtree(low,mid,2*pos+1);
		constructtree(mid+1,high,2*pos+2);
		segtree[pos]=Math.max(segtree[2*pos+1],segtree[2*pos+2]);
	}
	static int getmax(int low,int high,int qlow,int qhigh,int pos)
	{
		if(low>high || qlow>high || qhigh<low)
			return -1;
		if(qlow<=low && qhigh>=high)
			return segtree[pos];
		int mid=(low+high)/2;
		return Math.max(getmax(low,mid,qlow,qhigh,2*pos+1),getmax(mid+1,high,qlow,qhigh,2*pos+2));
	}
	public static void main(String[] args) 
	{
		getsmallestprime();
		getdivisors();
		constructtree(1,maxn-1,1);
		System.out.println(getmax(1,maxn-1,1,100,1));
		System.out.println(getmax(1,maxn-1,10,48,1));
		System.out.println(getmax(1,maxn-1,1,10,1));
	}
}