import java.util.Scanner;
import java.io.File;

public class importMinDecodons
{
	public static void main(String args[]) throws Exception
	{
		minDecodon info[] = new minDecodon[2097152]; // 2^21

		File f = new File("AA_subset_decodons.txt");
		Scanner scan = new Scanner(f);

		while (scan.hasNextLine())  
		{

			int minNum = scan.nextInt(); // minimum number of degenerate codons
			String aa = scan.next(); // binary number specifying which amino acids
			int index = Integer.parseInt(aa,2); 

			String[] example = new String[6];
			for (int i = 0; i < minNum; i++)
			{
				example[i] = scan.next();
			}

			info[index] = new minDecodon(minNum,example);
		}
		scan.close();

		// testing that it works. convert the binary number in the .txt to decimal for the index.
		
		// System.out.println(info[471397].toString());
		// should output 6 AAC AYG CAB GMA TGG TWC 
	}	
}