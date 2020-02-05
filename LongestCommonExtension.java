import java.util.*;
class LongestCommonExtension
{
	public static void main(String[] args) 
	{
		String str = "abbababba"; 
    	int n = str.length();
    	int l=0;
    	int r=5;
    	int len=0;
    	while((r+len)<n && (l+len)<n)
    	{
    		if(str.charAt(l+len)==str.charAt(r+len))
    			len++;
    	}
    	System.out.println(len);
	}
}