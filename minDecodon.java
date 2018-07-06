public class minDecodon
{
	int min;
	String[] exampleCodons; 

	public minDecodon(int m, String[] ec)
	{
		min = m;
		exampleCodons = ec;
	}

	public int getMin()
	{
		return min;
	}

	public String[] getExample()
	{
		return exampleCodons;
	}

	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append(min);
		s.append(" ");

		for (int i = 0; i < min; i++)
		{
			s.append(exampleCodons[i]);
			s.append(" ");
		}
		return s.toString();
	}
}