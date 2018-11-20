
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class cpp_matching_keywords
{
	
	//sorting the array of Strings
	static void InsertionSort(String[] str)
	{
		int i=1,j;String key=null;
		for(i=1;i<str.length;i++)
		{
			key=str[i];
			for(j=i-1;j>=0;j--)
			{
				if((key.compareTo(str[j]))<0)
				{
					str[j+1]=str[j];
				}
				else break;
			}
			str[j+1]=key;
		}
	}
	
	//function to implement binary search 
	static int binarySearch(String s,String[] arr,int beg,int end)
	{
		if(beg<=end)
		{
			int mid=(beg+end)/2;
			if(arr[mid].equals(s))
				return 1;
			else if(arr[mid].compareTo(s)<0)
			{
				return binarySearch(s,arr,mid+1,end);
			}			
			return binarySearch(s,arr,beg,mid-1);
		}
		return 0;
	}
	
	
	public static void main(String a[]) throws IOException
	{
		File in1=new File("C:\\Users\\hpp\\Desktop\\HW3-unsorted-keywords.txt");
		File in2=new File("C:\\Users\\hpp\\Desktop\\HW3-input-code.cpp");
		File ou1=new File("C:\\Users\\hpp\\Desktop\\HW3-output.txt");
		
		if(!in1.exists()||!in2.exists())
		{
			System.out.println("File doesnot exist!");
			return;
		}
		
		if(!ou1.exists())
		{
			ou1.createNewFile();
		}
		
		//counting the number of lines
		LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(in1));
        lineNumberReader.skip(Long.MAX_VALUE);
        int lines = lineNumberReader.getLineNumber();
        lineNumberReader.close();
        
        //dynamic allocation
        String[] keywords=new String[lines];
        
        //reading the file for storing data in string array
        BufferedReader bf1=new BufferedReader(new FileReader(in1));
        BufferedWriter bf2=new BufferedWriter(new FileWriter(ou1));
        int i=0;
        for(;i<lines;i++)
        {
        	keywords[i]=bf1.readLine();
        }
        
        InsertionSort(keywords);
        bf1.close();
        
        int li=0,lo=0;
        bf1=new BufferedReader(new FileReader(in2));
        String s=null,temp=null;
        boolean x=false;
        while((s=bf1.readLine())!=null)
        {
        	temp="";li++;x=false;
        	for(i=0;i<s.length();i++)
        	{
        		//storing the word in a temporary variable
        		if((s.charAt(i)>='a'&& s.charAt(i)<='z')||s.charAt(i)=='_')
        		{
        			temp=temp+s.charAt(i);
        		}
        		
        		else
        		{
        			//applying binary search
        			if(binarySearch(temp,keywords,0,keywords.length-1)==1)
        			{
        				if(x==false)
        				{
        					bf2.newLine();
        					bf2.write("Line "+li+":");
        					
        					x=true;
        				}
        				lo++;
        				bf2.write(temp+"("+s.indexOf(temp)+") ");
        				        				String rp="";
        				        				

        				for(int j=0;j<temp.length();j++)
        				{
        					rp=rp+"*";
        				}
        				s=s.replaceFirst(temp,rp);
        				
        			}
        			
        			//checking for comments
        			if(s.charAt(i)=='/'&& s.charAt(i+1)=='/')
            		{
            			break;
            		}
        			temp="";
        		}
        		
        				
        	}
        	
        	//checking if any word is there before the comments
        	if(!temp.equals("")&&binarySearch(temp,keywords,0,keywords.length-1)==1)
			{
				if(x==false)
				{
					bf2.write("Line "+li+":");
					x=true;
				}
				lo++;
				bf2.write(temp+"("+s.indexOf(temp)+") ");
				
				String rp="";
				for(int j=0;j<temp.length();j++)
				{
					rp=rp+"*";
				}
				s=s.replaceFirst(temp,rp);
				
			}
        	if(x==true)
        	{
        		bf2.write("\n");
        	}
        	
        }
        
        bf2.write("Number of keywords found="+lo);
        bf1.close();
        bf2.close();
        
	}
}

