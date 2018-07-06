import java.util.Scanner;
import java.io.*;
import java.io.BufferedWriter;

public class modFasta{

	public static void main(String args[]) throws Exception{
		File inp = new File("P42212.fasta.txt");
		File changes = new File("changes.txt");

		File out = new File ("GFP.fasta");
		BufferedWriter bw = new BufferedWriter(new FileWriter(out));

		Scanner scanSeq = new Scanner(inp);
		Scanner scanChange = new Scanner(changes);


		// scan first change
		String changeLine = scanChange.nextLine();
		int modPos = Integer.parseInt(changeLine.substring(0,changeLine.indexOf(' ')));
		String aas = changeLine.substring(changeLine.indexOf(' ')+1);

		bw.write(scanSeq.nextLine()); // copy first line of FASTA
		bw.write("\n");

		int pos = 1;
		while (scanSeq.hasNextLine())  
		{
			String line = scanSeq.nextLine();
			if (line.equals(""))
				break;

			for (int i = 0; i < line.length(); i++)
			{
				if (pos == modPos)
				{
					bw.write("[" + aas + "]"); // write changes to file

					// get next change, if there is one
					if (scanChange.hasNextLine())
					{
						changeLine = scanChange.nextLine();
						if (!changeLine.equals(""))
						{
							modPos = Integer.parseInt(changeLine.substring(0,changeLine.indexOf(' ')));
							aas = changeLine.substring(changeLine.indexOf(' ')+1);	
						}			
					}
				}
				else
				{
					bw.write(line.charAt(i));
				}
				pos++;
			}
		}

		bw.close();
		scanSeq.close();
		scanChange.close();
	}
}