import java.util.TreeSet;

public class OptimizedSudoku {
	private static class Coordinates implements Comparable<Coordinates>
	{
		private int _line;
		private int _col;
		private int _frequency;
				
		public Coordinates(int line, int col, int frequency)
		{
			_line = line;
			_col = col;
			_frequency = frequency;
		}
		
		@Override
		public int compareTo(Coordinates o) {
			return this._frequency >= o._frequency ? 1:-1;
		}
	}
	
	
	private static byte[][] _Sudoku; 
	private static int setNumberCount;
	private static Coordinates[] emptyNumbers;
	
	public static boolean Solve(byte[][] Sudoku)
	{
		_Sudoku = Sudoku; //Because this is a reference, it modifies the original object. So there's no need to access _Sudoku. 
		setNumberCount = 0;
		emptyNumbers = new Coordinates[0];
		
		//Fill the list of empty numbers. 
		TreeSet<Coordinates> emptyNumbersTree = new TreeSet<Coordinates>();
		for(int line = 0; line < 9; line++)
			for(int col = 0; col < 9; col++)
			{
				if(isAvailable(col, line))
				{
					int frequency = 0;
					Byte[] availableNumbers = getAvailableNumbers(col, line);
					for(Byte b : availableNumbers)
					{
						if(b != null)
							frequency++;
					}
					//System.out.println("Adding coordinates " + col + ", " + line + " with frequency " + frequency);
					emptyNumbersTree.add(new Coordinates(line, col, frequency));
				}
			}
		emptyNumbers = emptyNumbersTree.toArray(emptyNumbers);
		return setNumber(0);
	}
	
	public static int getAttempts()
	{
		return setNumberCount;
	}

	private static boolean setNumber(int id)
	{
		if(id >= emptyNumbers.length) //last number is assigned!
			return true;
		Coordinates currentCoord = emptyNumbers[id];
		
		//System.out.println("Testing: frequency is " + currentCoord._frequency);
		int col = currentCoord._col, line = currentCoord._line;
		setNumberCount++;
		
		//Find available numbers
		Byte[] AvailableNumbers = getAvailableNumbers(col, line);
		
		//Main assignation loop
		for(Byte b : AvailableNumbers)
		{
			if(b == null)
				continue;
			//System.out.println("testing " + b + " at " + "[" + col + "][" + line + "]");
			_Sudoku[col][line] = b; //Set the value
			if(setNumber(id + 1))//set the next one in line.
				return true;  //If setNumber managed to fit the last number in, go back all the way to the first with success. 
		}
		_Sudoku[col][line] = 0; //Backing up. 
		return false;
	}

	//Checks to see if the requested box doesn't already contain a number. 
	private static boolean isAvailable(int col, int line)
	{
		return _Sudoku[col][line] == 0;
	}

	private static Byte[] getAvailableNumbers(int col, int line)
	{
		Byte[] AvailableNumbers = new Byte[]{null,1,2,3,4,5,6,7,8,9}; //Available numbers. 
		//Check line and col
		for(int i = 0; i < 9; i++)
		{
			AvailableNumbers[_Sudoku[i][line]] = null; 
			AvailableNumbers[_Sudoku[col][i]] = null;
			//This one might be a bit hard to understand, so I'll give an example:
			//say there's a Sudoku with 0 1 5 as the first 3 numbers of the first line
			//First round, it'll remove [i][line] (IE: the first element of the list) from the array: position 0. null => null 
			//Second round, it'll remove [i][line] from the array, position 1. 1 => null; the number is unavailable. 
			//Third round, same logic; remove [5], so 5 => null. ETC ETC. Does this simultaneously for line and column. 
			//Empty cases have 0, so they remove from 0, which is always null. 
		}
		//Next is block. 
		int startCol = (col / 3) * 3; //5 / 3 = 1, *3 = 3
		int startLine = (line / 3) * 3;
		for(int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++)
				AvailableNumbers[_Sudoku[x + startCol][y + startLine]] = null; //0,1,2 / 3,4,5 / 6,7,8, depending on startCol/startLine


		return AvailableNumbers;
	}


}
