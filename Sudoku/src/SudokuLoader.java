import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class SudokuLoader {
	private File _file;
	
	public SudokuLoader(File file)
	{
		_file = file;
	}
	
	public byte[][] read()
	{
		byte[][] result = new byte[9][9];
		
		try {
			FileReader reader = new FileReader(_file);
			char character;
			int currentLine = 0, currentColumn = 0;
			
			while((character = (char)reader.read()) != -1)
			{
				if(Character.isDigit(character)) //0-9
				{
					result[currentColumn][currentLine] = Byte.parseByte(Character.toString(character)); //insert byte into array
					currentColumn = ++currentColumn % 9;
					if(currentColumn == 0)//Column full, next line
						currentLine++;
					if(currentLine == 9) //Sudoku full! 
						break;
				}
			}
			reader.close();
			
			if(currentLine != 9)
			{
				return null; //We didn't fill the whole grid, it's not valid. 
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
