
public class Algorithm {

	public boolean checkIfCorrect (GameBoardPiece[][] gameboardPiece, String solution) {
		
		int index = 0;
		
		for (int row = 0; row < 9; row++) 
		{
			for (int col = 0; col < 9; col++)
			{
				if (gameboardPiece[row][col].getValue() != -9 && gameboardPiece[row][col].getValue() != Character.getNumericValue(solution.charAt(index)) )
				{
					return false;
				}
				
				index++;
			}
		}
		
		return true;
	}
	
	public boolean checkIfFinished (GameBoardPiece[][] gameboardPiece, String solution) {
		
		int index = 0;
		
		for (int row = 0; row < 9; row++) 
		{
			for (int col = 0; col < 9; col++)
			{
				if (gameboardPiece[row][col].getValue() != Character.getNumericValue(solution.charAt(index)) )
				{
					return false;
				}
				
				index++;
			}
		}
		
		return true;
	}
}
