import java.util.*;
class LargestRectangularAreaInHistoGram
{
	public static void main(String[] args) 
	{
		int hist[] = { 6, 2, 5, 4, 5, 1, 6 };
		int n=hist.length;
		Stack<Integer> stack=new Stack<Integer>();
		int max=0,height=0;
		int i;
		for(i=0;i<n;)
		{
			if(stack.isEmpty() || hist[i]>=hist[stack.peek()])
				stack.push(i++);
			else
			{
				int j=stack.pop();
				if(stack.isEmpty())
					height=hist[j]*i;
				else
					height=hist[j]*(i-stack.peek()-1);
			}
			max=Math.max(max,height);
		}
		while(!stack.isEmpty())
		{
			int j=stack.pop();
			if(stack.isEmpty())
				height=hist[j]*i;
			else
				height=hist[j]*(i-stack.peek()-1);
			max=Math.max(max,height);
		}
		System.out.println(max);
	}
}