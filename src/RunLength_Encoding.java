import java.util.ArrayList;
import java.util.List;
public class RunLength_Encoding { 
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
	
    public static int[] encode(int[] arr) 
    { 
    	List<Integer> res = new ArrayList<Integer>();
        //int[] res = new int[arr.length*2];
        int index = 0;
        for (int i = 0; i < arr.length; i++) { 
  
            // Count occurrences of current character 
            int count = 1;
            //int j = i;
            while (i < arr.length-1 && arr[i] == arr[i+1]) { 
                count++; 
                i++; 
            }
            res.add(count);
            res.add(arr[i]);
            
        } 
        int[] result = convertIntegers(res);;
        return result;
    }
    
    public static int[] decode(int[] arr) 
    { 
    	List<Integer> res = new ArrayList<Integer>();
        //int[] res = new int[arr.length*2];
        int index = 0;
        for (int i = 0; i < arr.length-1; i+=2) { 
        	
            // Count occurrences of current character 
            int count = arr[i];
            int num = arr[i+1];
            //int j = i;
            int j = 0;
            while (j < count) { 
            	res.add(num); 
                j++; 
            }
//            res.add(num);
            //res.add(count);
            
            
        } 
        int[] result = convertIntegers(res);;
        return result;
    }
    
    
} 