/*We make a segment tree to store sum of intervals, where an interval [a, b] represents the number of 
elements present in the set, currently, in the range [a, b]. For example, if we consider the above example
, query( 3, 7) returns 2, query(4, 4) returns 1, query(5, 5) returns 0.
Insert and delete queries are simple and both can be implemented using function update(int x, int diff) 
(adds ‘diff’ at index ‘x’)

Algorithm

// adds ‘diff’ at index ‘x’
update(node, a, b, x, diff)

  // If leaf node
  If a == b and a == x
     segmentTree[node] += diff

  // If non-leaf node and x lies in its range
  If x is in [a, b]

     // Update children recursively    
     update(2*node, a, (a + b)/2, x, diff)
     update(2*node + 1, (a + b)/2 + 1, b, x, diff)

      // Update node
      segmentTree[node] = segmentTree[2 * node] + 
                          segmentTree[2 * node + 1]
The above recursive function runs in O( log( max_elem ) ) ( in this case max_elem is 10^6) and 
used for both insertion and deletion with the following calls:

Insert ‘x’ is done using update(1, 0, 10^6, x, 1). Note that root of tree is passed, start index is 
passed as 0 and end index as 10^6 so that all ranges that have x are updated.
Delete ‘x’ is done using update(1, 0, 10^6, x, -1). Note that root of tree is passed, start index is 
passed as 0 and end index as 10^6 so that all ranges that have x are updated.
Now, the function to find the index with kth ‘1’, where ‘k’ in this case will always be (n + 1) / 2,
 this is going to work a lot like binary search, you can think of it as a recursive binary search function
  on a segment tree.

If we are at a non-leaf node, we are sure that it has both children, we see if the 
left child has more or equal number of one’s as ‘k’, if yes, we are sure our index lies 
in the left subtree, otherwise, if left subtree has less number of 1’s than k, 
then we are sure that our index lies in the right subtree. We do this 
recursively to reach our index and from there, we return it.

Algorithm

1.findKth(node, a, b, k)
2.  If a != b 
3.     If segmentTree[ 2 * node ] >= k
4.       return findKth(2*node, a, (a + b)/2, k)
5.     else
6.       return findKth(2*node + 1, (a + b)/2 + 1, 
                       b, k - segmentTree[ 2 * node ])
7.     else
8.       return a
The above recursive function runs in O( log(max_elem) ). */
import java.util.*;
class InsertDeleteMedianOperationsOnSet
{
	static int maxm=Integer.MAX_VALUE;
	static int segtree[]=new int[3000005];
	static void update(int low,int high,int pos,int data,int delta)
	{
		if(low==high && low==data)
		{
			segtree[pos]+=delta;
			return;
		}
		if(data>=low && data<=high)
		{
			int mid=(low+high)/2;
			update(low,mid,2*pos,data,delta);
			update(mid+1,high,2*pos+1,data,delta);
			segtree[pos]=segtree[2*pos]+segtree[2*pos+1];
		}
	}
	static void insert(int x)
	{
		update(0,1000000,1,x,1);
	}
	static void delete(int x)
	{
		update(0,1000000,1,x,-1);
	}
	static int getmedian()
	{
		int k=(segtree[1]+1)/2;
		return getkthnode(0,1000000,1,k);
	}
	static int getkthnode(int low,int high,int pos,int k)
	{
		if(low!=high)
		{
			int mid=(low+high)/2;
			if(segtree[pos*2]>=k)
				return getkthnode(low,mid,2*pos,k);
			return getkthnode(mid+1,high,2*pos+1,k-segtree[pos*2]);
		}
		return (segtree[pos]==1)?low:-1;
	}
	public static void main(String[] args) 
	{
		insert(1); 
    	insert(4); 
    	insert(7); 
    	System.out.println(getmedian());
    	insert(8);
    	insert(9);
    	System.out.println(getmedian());
    	delete(1);
    	delete(8);
    	System.out.println(getmedian());
	}
}
// Here segtree has 1 based indexing so the maximum no of elements is the topelement in the segtree
// plus 1 divided by 2 because segtree[1] stores the no of 1's in the array.