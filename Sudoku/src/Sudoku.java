import java.util.ArrayList;




public class Sudoku {
	private static byte[][] _Sudoku; 

	public static boolean Solve(byte[][] Sudoku)
	{
		_Sudoku = Sudoku; //Because this is a reference, it modifies the original object. So there's no need to access _Sudoku. 
		return setNumber(0, 0);
	}

	private static boolean setNumber(int col, int line)
	{
		if(col == 0 && line == 0 && !isAvailable(col, line)) //SPECIAL CASE if the first case is occupied! 
		{
			col++;
		}
		
		//Find available numbers
		Byte[] AvailableNumbers = getAvailableNumbers(col, line);
		

		//Find next coordinates
		int nextCol = col;
		int nextLine = line;

		do{ //Used a do while, since we're always at least incrementing by one. 
			nextCol = (++nextCol) % 9; //Switch to next column
			if(nextCol == 0) //If we were at column 9, the next coordinates are [0][line+1]
				nextLine++;
		} while(nextLine < 9 && !isAvailable(nextCol, nextLine)); //Next case might not be empty, in which case it should be skipped! 
		
		//DEBUG
		/*String strAvNum = "[";
		for(Byte b : AvailableNumbers)
		{
			strAvNum += b + ", ";
		}
		strAvNum += "]";
		System.out.println("Available numbers for " + col + ", " + line + ": " + strAvNum);*/
		
		
		for(Byte b : AvailableNumbers)
		{
			if(b == null)
				continue;
			//System.out.println("testing " + b + " at " + "[" + col + "][" + line + "]");
				_Sudoku[col][line] = b; //Set the value
			if(nextLine == 9) //We've reached the end, final number has been set! 
				return true; 
			if(setNumber(nextCol, nextLine))//set the next one in line.
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
