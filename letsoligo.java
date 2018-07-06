import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.util.InputMismatchException;
import java.lang.Object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.Random;

import javax.swing.*;


public class letsoligo
{ 
	public letsoligo()throws Exception
	{	
		int printIterationIndices = 0;
		int printCostofEachOligo = 0;
		int printOptimalCosts = 0;
		int printFinalSequence = 1;
		int drawOligos = 1;
		



		/*uncomment to test oligo_cost function
		 int[] deCodons = new int[]{ 1, 1, 1, 3, 2, 1, 1, 1 };
		 System.out.println(oligo_cost(0, 7, 4, deCodons));
		//*/
		 
		 int min_s = 50, max_s = 80, min_o = 30, max_o = 40, n = 300, i, start, overlap, j, k, oligoCost, iteration = 0, lastOligoCost = 0;
		 int[] cost = new int[n];
		 //overlap cannot be >= min size


		
		/*//Load in the minimum number of degenerate codons for sets of amino acids
		minDecodon info[] = importMinDecodons();
		System.out.println("Done importing");
		System.out.flush();
		//*/
		//Pre-process the number of degenerate codons needed for each position in the sequence
		
		/*//int[] deCodons = getPosCosts(info,n); // Comment out this line if we are making our own test case below.
		System.out.println("Done with creating deCodons");
		System.out.flush();
		//*/
		 //Uncomment if we want to test small cases 
		 int[] deCodons = new int[n]; //test array 
		 for(i = 0; i < n; i++){
		 	deCodons[i] = 1; //filled with 1s!
		 }
		 // 

		deCodons[70] = 3;

		 System.out.print("degenerate codons at: ");
		 for(i = 0; i < n; i++){
		 	if (deCodons[i] != 1)
		 		System.out.print(i + " (" + (deCodons[i]) + "), "); //filled with 1s!
		 }
		 System.out.println(" ");


		 Node[] ptr = new Node[n];
		 ptr[0] = new Node(-1, 0);

		 //====== Initialize array cost[] =======//

		 for(i = 0; i < min_s-1; i++){ //an oligo cannot end earlier than the minimum size
		 	cost[i] = Integer.MAX_VALUE; //initialized to 0
		 }

		 //====== Beginnning of Nested For-Loops ========//

		 for (i = min_s-1; i <n; i ++){ //go from the 1st possible end of an oligo to end of the sequence
		 	if (printIterationIndices == 1)
		 		System.out.println("i: " + i);
		 	cost[i] = Integer.MAX_VALUE; //initialize as infinity... and beyond
		 	
		 	for(j = i-min_s; j>= i-max_s; j--){//start from the latest possible beginning point for this oligo to the earliest
		 		if (printIterationIndices == 1)
		 			System.out.print("j: " + j);
		 		if (j < (min_s - max_o)) //if starting point is < earliest possible starting point for a 2nd oligo, we are dealing with 1st oligo
		 		{
		 			start = 0;	//set starting point to 0 instead

		 			j = i-max_s - 1; //we only need to check this once!
		 		}
		 		else
		 			start = j; //set starting point to j

		 		
		 		for(k = max_o; k >= min_o; k--){
		 			
		 			if (i < (2*min_s - k)|| start== 0) //if we are dealing with the 1st oligo, which cannot possibly have an overlap
		 			{
		 				overlap = 0; //the overlap is 0
		 				k = 0; //and we only need to check this once

		 				if (start!= 0) //oligos besides the first one must have a nonzero overlap
		 					continue; 
		 			}
		 			else if ( (start + k - 1) < (min_s - 1) ) { // subtracting 1 to debug
		 				continue;
		 			}	
		 			else
		 				overlap = k; //otherwise its k
		 			
		 			oligoCost = oligo_cost(start, i, overlap, deCodons);

		 			if (printIterationIndices == 1)
		 				System.out.print(" k: " + k);
		 			if (printCostofEachOligo== 1){
		 				System.out.print(" - cost of oligo ending at " + i + " starting at " + start + " with an overlap of size " + overlap + " is: ");
		 				System.out.println(oligoCost);
		 			}

		 			if ( (start + overlap) == 0) // if we're looking at the first oligo. there is no prior oligo
		 			{
		 				lastOligoCost = 0;
		 			}
		 			else
		 			{
		 				lastOligoCost = cost[start+overlap-1]; // Subtract 1 to get where the last oligo ends
		 			}

		 			if (printCostofEachOligo== 1)
		 				System.out.println("cost (" + oligoCost + ") plus last oligo cost ("  + lastOligoCost + ") = " + (oligoCost + lastOligoCost));

		 			if ((oligoCost + lastOligoCost) < cost[i])
		 			{
		 				if (printCostofEachOligo== 1)
		 					System.out.print("...This is smaller than cost[i], so replace it\n");
		 				cost[i] = (oligoCost + lastOligoCost);
		 				ptr[i] = new Node ((start+overlap-1), overlap); // Subtract 1 to get where the last oligo ends
		 			}
		 			
		 		}
				 			
		 	}
		 	if (printOptimalCosts== 1)
				System.out.println("Optimal Cost for an oligo ending at " + i  + ": " + cost[i]);
		 }
				
				  
		
		 


		
		if (printFinalSequence == 1){
			int pos = n-1;
			while(pos >= 0)
			{
				
				System.out.println(pos + " with overlap " + ptr[pos].getOverlap());
				pos = ptr[pos].getParent();
			}
		}
		if (drawOligos == 1){
			int pos = n-1;
			int previous = 0;
			int o; //= 0;
			LineComponent lineComponent = new LineComponent(n+200, 200);
			while(pos >= 0)
			{
				o = ptr[pos].getOverlap();
				previous = pos;
				pos = ptr[pos].getParent();
				//if (pos > 0)
					

				//System.out.println("drawing line from " + previous + " to " + (pos-o + 1));
				lineComponent.addOligo(previous, pos-o+1);
				
				

			}
			JOptionPane.showMessageDialog(null, lineComponent, "Oli-WHOA", JOptionPane.PLAIN_MESSAGE);
		}


}

	
	public int getAAIndex(ArrayList<Character> set)
	{
		int[] bits = new int[20];

		for (int i = 0; i < set.size(); i++)
		{
			System.out.print(set.get(i));
			switch(set.get(i))
			{
				case 'V': bits[19] = 1;
					break;	
				case 'Y': bits[18] = 1;
					break;
				case 'W': bits[17] = 1;
					break;							
				case 'T': bits[16] = 1;
					break;
				case 'S': bits[15] = 1;
					break;		
				case 'P': bits[14] = 1;
					break;
				case 'F': bits[13] = 1;
					break;	
				case 'M': bits[12] = 1;
					break;
				case 'K': bits[11] = 1;
					break;							
				case 'L': bits[10] = 1;
					break;
				case 'I': bits[9] = 1;
					break;						
				case 'H': bits[8] = 1;
					break;	
				case 'G': bits[7] = 1;
					break;	
				case 'E': bits[6] = 1;
					break;	
				case 'Q': bits[5] = 1;
					break;	
				case 'C': bits[4] = 1;
					break;	
				case 'D': bits[3] = 1;
					break;	
				case 'N': bits[2] = 1;
					break;	
				case 'R': bits[1] = 1;
					break;	
				case 'A': bits[0] = 1;
					break;																										
			}	
		}

		System.out.println();
		StringBuilder b = new StringBuilder();
		for (int i = 19; i >= 0; i--)
		{
			b.append(bits[i]);
		}
		return Integer.parseInt(b.toString(),2); 
	}

	public int[] getPosCosts(minDecodon[] info, int n) throws Exception
	{ 
		int[] costs = new int[n];

		File f = new File("testcases/GFP.fasta");
		try(Scanner scan = new Scanner(f)) {
		
		scan.nextLine(); // disregard first line of FASTA

		int pos = 0;
		boolean bracket = false;
		ArrayList<Character> set = new ArrayList<Character>();		

		while(scan.hasNextLine())
		{
			String line = scan.nextLine();

			for (int i = 0; i < line.length(); i++)
			{
				char c = line.charAt(i);

				if (bracket)
				{
					if (c == ']')
					{
						bracket = false;

						int index = getAAIndex(set);
						costs[pos] = (info[index].getMin());
						//System.out.println(c + ": " + costs[pos]);

						set.clear();
						pos++;
					}
					else
					{
						set.add(c);
					}
				}
				else if (c == '[')
				{
					bracket = true;
				}
				else
				{
					costs[pos] = 1;
					//System.out.println(c + ": " + 1);
					pos++;
				}
			} 
		}  

		scan.close(); }
		catch (IOException | InputMismatchException ex) {
            ex.printStackTrace();
        }

		return costs;
	}

	public minDecodon[] importMinDecodons() throws Exception
	{


		minDecodon info[] = new minDecodon[1048576]; // 2^20

		File f = new File("AA_subset_decodons.txt");
		Scanner scan = new Scanner(f);
		

		while (scan.hasNextLine())  
		{
			

			int minNum = scan.nextInt(); // minimum number of degenerate codons
			String aa = scan.next(); // binary number specifying which amino acids
			int index = Integer.parseInt(aa,2); 
			int i;

			String[] example = new String[6];
			for (i = 0; i < minNum; i++)
			{
				example[i] = scan.next();
			}
			//System.out.print(example[i-1]);
			

			info[index] = new minDecodon(minNum,example);


			//System.out.print(example[i-1]);
		}
		
		scan.close();


		return info;
	}


	

	public static void main(String args[]) throws Exception
	{
		try
		{

			letsoligo app = new letsoligo();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}





	/*==================================================/
	|	 												/
	|	public void oligo_cost(int j, int i, int k)  	/
	|		takes as arguments 							/
	|		j - start index of oligo					/
	|		k - length of overlap						/
	|		i - end of oligo							/
	|		deCodons - array with cost of each			/
	|		returns the cost of the oligo 				/
	| 	 												/
	====================================================*/

	public int oligo_cost(int j, int i, int k, int[] deCodons){
		int x;
		int cost = 1, overlapCost = 1;

		for(x = j; x < (j+k); x++){
			overlapCost *= deCodons[x];	
		}
		if (overlapCost > 1)
		{
			overlapCost *= 2;
		}
		
		for(x = j+k; x <= i; x++){ //from the end of overlap to the end of the oligo
		
			cost *= deCodons[x];
		}


		return cost * overlapCost * ((i+1) - (j));

	}  /*===== end of oligo_cost() function ==========*/

	



	


}

class LineComponent extends JComponent {

    ArrayList<Line2D.Double> lines, smallLines;
    int alternate = 0;
    //int leftMargin = 50, topMargin = 100;

    LineComponent(int width, int height) {
        super();
        setPreferredSize(new Dimension(width,height));
        lines = new ArrayList<Line2D.Double>();
        smallLines = new ArrayList<Line2D.Double>();
    }

    public void addOligo(int start, int end) {
        int width = (int)getPreferredSize().getWidth();
        int height = (int)getPreferredSize().getHeight();
        int leftMargin = 50, topMargin = 100;
        if (alternate == 0){
        	topMargin -=5;

        	Line2D.Double line2 = new Line2D.Double(start+leftMargin, topMargin, start+leftMargin, topMargin+10);
        	smallLines.add(line2);
        	Line2D.Double line3 = new Line2D.Double(end+leftMargin, topMargin, end+leftMargin, topMargin+10);
       		smallLines.add(line3);

        	
        	alternate = 1;
        }
        else{
        	topMargin +=5;
        	alternate = 0;
        	Line2D.Double line2 = new Line2D.Double(start+leftMargin, topMargin, start+leftMargin, topMargin-10);
       		smallLines.add(line2);

       		

       		Line2D.Double line3 = new Line2D.Double(end+leftMargin, topMargin, end+leftMargin, topMargin-10);
       		smallLines.add(line3);
        }
        Line2D.Double line = new Line2D.Double(start+leftMargin, topMargin, end+leftMargin, topMargin);
        
        lines.add(line);
        
        repaint();
    }
    public void paintComponent(Graphics g) {
        g.setColor(Color.white);
        Random rand = new Random();
        g.fillRect(0, 0, getWidth(), getHeight());
        Dimension d = getPreferredSize();

        
        for (Line2D.Double line : lines) {

        float r = rand.nextFloat();
		float g2 = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomColor = new Color(r, g2, b);
        g.setColor(randomColor);

        		int x1 = (int)line.getX1();
                int y1 = (int)line.getY1();
                int x2 =  (int)line.getX2();
                int y2 = (int)line.getY2();
            g.drawLine(x1, y1, x2, y2);
            g.drawString(String.valueOf(x1-50), x1, 90);
            g.drawString(String.valueOf(x2-50), x2, 120);
            //g.drawString(String.valueOf(x2), x2, y2-5);
        }
          for (Line2D.Double line : smallLines) {
          	 g.setColor(Color.black);
        		int x1 = (int)line.getX1();
                int y1 = (int)line.getY1();
                int x2 =  (int)line.getX2();
                int y2 = (int)line.getY2();
            g.drawLine(x1, y1, x2, y2);
            //g.drawString(String.valueOf(x2), x2, y2-5);
        }

    }
}