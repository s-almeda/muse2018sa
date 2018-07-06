public class getAA
{
	public getAA()
	{
		String input = "00010001001000000010";

		char[] bits = input.toCharArray();

		StringBuilder b = new StringBuilder();

		if (bits[19] == '1')
		{
			b.append('A');
		}
		if (bits[18] == '1')
		{
			b.append('R');
		}	
		if (bits[17] == '1')
		{
			b.append('N');
		}
		if (bits[16] == '1')
		{
			b.append('D');
		}
		if (bits[15] == '1')
		{
			b.append('C');
		}
		if (bits[14] == '1')
		{
			b.append('Q');
		}
		if (bits[13] == '1')
		{
			b.append('E');
		}
		if (bits[12] == '1')
		{
			b.append('G');
		}
		if (bits[11] == '1')
		{
			b.append('H');
		}	
		if (bits[10] == '1')
		{
			b.append('I');
		}
		if (bits[9] == '1')
		{
			b.append('L');
		}
		if (bits[8] == '1')
		{
			b.append('K');
		}
		if (bits[7] == '1')
		{
			b.append('M');
		}
		if (bits[6] == '1')
		{
			b.append('F');
		}	
		if (bits[5] == '1')
		{
			b.append('P');
		}
		if (bits[4] == '1')
		{
			b.append('S');
		}	
		if (bits[3] == '1')
		{
			b.append('T');
		}
		if (bits[2] == '1')
		{
			b.append('W');
		}
		if (bits[1] == '1')
		{
			b.append('Y');
		}
		if (bits[0] == '1')
		{
			b.append('V');
		}													

		System.out.println(b.toString());
	}

	public static void main(String args[])
	{
		getAA app = new getAA();
	}
}