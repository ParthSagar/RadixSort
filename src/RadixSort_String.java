import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Scanner;


public class RadixSort_String {

	public static void main(String[] args) {
		System.out.println("Please Specify the Input File (default = f.txt)");
		Scanner in_userinput = new Scanner(System.in);	
		String str_inputfile = in_userinput.nextLine();
		
		String inputfile = "f.txt";
		if(!str_inputfile.isEmpty())
		{
			inputfile = str_inputfile;
		}
		
		//check if the input file exist at the location.
		File file_in = new File(inputfile);
		if(file_in.exists() && !file_in.isDirectory())
		{
			System.out.println("Please Specify the Output File (default = g.txt)");
			Scanner out_output = new Scanner(System.in);
			String str_screenoutput = out_output.nextLine();
			
			String outputfile = "g.txt";
			if(!str_screenoutput.isEmpty())
			{
				outputfile = str_screenoutput;
			}
			
			//load the input file into 2-dim array
			char[][] S = null;
			try {
				S = loadInputFile(file_in);
				
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
			//perform the radix sort
			radixSort(S, outputfile);
		}
		else
		{
			//file does not exists
			System.out.println("Error: Input File not found");
			System.exit(0);
			in_userinput.close();	
		}

	}

	private static void radixSort(char[][] inputarray, String outputfile)
	{		
		String[] aux = new String[inputarray.length];
		
		for (int d = 20; d >= 0; d--) {

            // calculate frequency counts
            int[] count = new int[257];
            for (int i = 0; i < inputarray.length; i++)
            {
            		count[inputarray[i][d] + 1]++;
            }
            
            // count cumulates
            for (int r = 0; r < 256; r++)
                count[r+1] += count[r];

            // rearrange the values
            for (int i = 0; i < inputarray.length; i++)            	
                aux[count[inputarray[i][d]]++] = String.valueOf(inputarray[i]);

            // move them to its correct position in original array
            for (int i = 0; i < inputarray.length; i++)
                inputarray[i] = aux[i].toCharArray();
        
		}
		
		System.out.println("Output is:-");
		for(int i = 0; i<inputarray.length; i++)
		{
			for(int j = 0; j<inputarray[i].length; j++)
			{					
				System.out.print(inputarray[i][j]);
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("Saving output to the file...");
		
		//Save output to the file
		int outputsuccess = saveOutputToFile(inputarray, outputfile);
		if(outputsuccess == 1)
		{
			System.out.println("Success! Output file has been generated.");
		}
		else if(outputsuccess == 0)
		{
			System.out.println("Error! Unable to create the output file. Please check the output file path...");
		}
	}
	
	private static int saveOutputToFile(char[][] outputarray, String file)
	{
		try {
			PrintWriter writer = new PrintWriter(file);
			for(int i = 0; i<outputarray.length; i++)
			{
				for(int j = 0; j<outputarray[i].length; j++)
				{					
					writer.print(outputarray[i][j]);
				}
				writer.println();
			}
			writer.close();
		} catch (FileNotFoundException e) {
			return 0;
		}	
		return 1;
	}
	
	private static char[][] loadInputFile(File file_in) throws IOException {
		
		//read number of lines in the input file
		LineNumberReader  lnr = new LineNumberReader(new FileReader(file_in));
		lnr.skip(Long.MAX_VALUE);
		int no_of_lines = lnr.getLineNumber() + 1;
		int k = 21;
		lnr.close();
		
		char[][] input_array = new char[no_of_lines][k];
		FileReader filereader = new FileReader(file_in);
		BufferedReader bufferedreader = new BufferedReader(filereader);
		String line = null;
		int lineindex = 0;
		while((line = bufferedreader.readLine()) != null)
		{
			for(int i = 0; i<line.length(); i++)
			{				
				input_array[lineindex][i] = line.charAt(i);				
			}	
			lineindex++;
		}
		bufferedreader.close();
		
		return input_array;
	}

}
