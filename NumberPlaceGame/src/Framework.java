import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Framework {

	private JFrame frame;
	private JLabel labelText;
	private JLabel background;
	private static String headerFileExtension = "src/images/header.png";
	private static String blankFileExtension = "src/images/blank.png";
	private GameBoardPiece [][] gameboardPiece;
	private int gridPieceDimension = 40;
	private String solution;
	private Algorithm algorithm;
	private JButton buttonCheckIfCorrect;
	
	public Framework () {
		algorithm = new Algorithm();
		
		createAndShowGUI();
	}
	
	public void createAndShowGUI () {
		frame = new JFrame("Number Place Game");
		
		frame.setSize(430,700);
		frame.setLocation(768,200); //Set location of application on screen
		frame.setLayout(null); //Set layout to absolute (Must use setBounds(x,y,width,height)
		addComponentsToPane(); //No need for setLayout(new BorderLayout());
//		frame.pack(); //Pack Application to smallest possible area
		frame.setVisible(true); //Set frame visible
		frame.setResizable(false); //Prevent frame resize
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set frame close operation
	}
	
	private void addComponentsToPane() {
		addBackground();
		addHeader();
		addGameboard();
		addLabelText();
		addStartButton();
		addCheckIfCorrectButton();
	}
	
	private void addBackground() {
		//Background
		background = new JLabel(new ImageIcon("src/images/background.png"));
		background.setBounds(0, 0, 430, 700);
	    frame.add(background);
//		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
	}
		
	private void addHeader() {
		//Header
		JLabel labelHeader = new JLabel();
		labelHeader.setIcon(new ImageIcon( headerFileExtension ));
		labelHeader.setBounds(20, 20, 384, 72);
		background.add(labelHeader);
	}
	
	private void addGameboard() {
        //Gameboard
        JLabel gameboard = new JLabel();
//        gameboard.setBackground(Color.WHITE);
//        gameboard.setOpaque(true);
//        gameboard.setBorder( BorderFactory.createLineBorder(Color.black) );
        gameboard.setBounds(20, 112, 384, 384);
        background.add(gameboard);
         
        gameboardPiece = new GameBoardPiece[9][9];
        for (int row = 0; row < 9; row++) 
		{
			for (int col = 0; col < 9; col++)
			{
				gameboardPiece[row][col] = new GameBoardPiece(row,col);
		        gameboardPiece[row][col].setForeground( new Color(0, 0, 0) );
		        gameboardPiece[row][col].setOpaque(true);
		        gameboardPiece[row][col].setBounds((gridPieceDimension*col)+(col*3), (gridPieceDimension*row)+(row*3), gridPieceDimension, gridPieceDimension);
		        gameboard.add(gameboardPiece[row][col]);
			}
		}
	}
        
	private void addLabelText() {
        labelText = new JLabel("Welcome");
        labelText.setBounds(20, 500, 384, 40);
        labelText.setHorizontalAlignment(JLabel.CENTER);
		Font font = new Font("SansSerif", Font.BOLD, 20);
		labelText.setFont(font);
		background.add(labelText);
	}
	
	
	private void addStartButton() {
		JButton buttonStart = new JButton("Start");
		buttonStart.setBounds(20, 550, 80, 40);
		buttonStart.setHorizontalAlignment(JLabel.CENTER);
		Font font = new Font("SansSerif", Font.BOLD, 20);
		buttonStart.setFont(font);
//		buttonStart.setBackground(Color.BLACK);
		buttonStart.setFocusPainted(false);
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGame();
				loadSolution();
				buttonStart.setEnabled(false);
				buttonCheckIfCorrect.setEnabled(true);
			}
		});
		background.add(buttonStart);
	}
	
	private void addCheckIfCorrectButton() {
		
		buttonCheckIfCorrect = new JButton("How Am I Doing?");
		buttonCheckIfCorrect.setBounds(20, 600, 220, 40);
		buttonCheckIfCorrect.setHorizontalAlignment(JLabel.CENTER);
		Font font = new Font("SansSerif", Font.BOLD, 20);
		buttonCheckIfCorrect.setFont(font);
//		buttonStart.setBackground(Color.BLACK);
		buttonCheckIfCorrect.setFocusPainted(false);
		buttonCheckIfCorrect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean isCorrect = algorithm.checkIfCorrect(gameboardPiece, solution);
				boolean isFinshed = algorithm.checkIfFinished(gameboardPiece, solution);
				
				if ( !isFinshed )
				{
					if (isCorrect)
					{
						labelText.setText("Everything Looks Good");
					}
					else
					{
						labelText.setText("You Have Made At Least One Mistake");
					}
				}
				else
				{
					labelText.setText("All Done, Good Job");
					for (int row = 0; row < 9; row++) 
					{
						for (int col = 0; col < 9; col++)
						{
							gameboardPiece[row][col].setEnabled(false);
						}
					}
				}
			}
		});
		buttonCheckIfCorrect.setEnabled(false);
		background.add(buttonCheckIfCorrect);
	}
	
    
	
	private void loadGame () {
		
		BufferedReader br = null;
		FileReader fr = null;
		
		String sampleGame = "src/games/sampleEasyGame";
		
		String gameString = new String();
		int index = 0;
		
		try {

			fr = new FileReader(sampleGame);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(sampleGame));

			while ((sCurrentLine = br.readLine()) != null) { //Read game from file and Save into string
				gameString = gameString + sCurrentLine;
			}
			
			for (int row = 0; row < 9; row++)
			{
				for (int col = 0; col < 9; col++)
				{				
					int currentValue = Character.getNumericValue(gameString.charAt(index)); //Convert character of gameString to int
					
					//Load game into gameboardPieces
					if (currentValue == 0)
					{
						gameboardPiece[row][col].initializeType(true);
					}
					else
					{
						gameboardPiece[row][col].initializeType(false);
						gameboardPiece[row][col].setValue(currentValue);
					}
					
					index++;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally  { //Close BufferedReader and FileReader
			try {
				if (br != null)
					br.close();

				if (fr != null)
					fr.close();
			} catch (IOException ex) {

				ex.printStackTrace();
			}
		}
	}
	
	private void loadSolution () {
		
		BufferedReader br = null;
		FileReader fr = null;
		
		String sampleGameSolution = "src/games/sampleEasyGameSolution";
		
		solution = new String();
		
		try {

			fr = new FileReader(sampleGameSolution);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(sampleGameSolution));

			while ((sCurrentLine = br.readLine()) != null) { //Read game from file and Save into string
				solution = solution + sCurrentLine;
			}
			
//			System.out.println(solution);

		} catch (IOException e) {
			e.printStackTrace();
		} finally  { //Close BufferedReader and FileReader
			try {
				if (br != null)
					br.close();

				if (fr != null)
					fr.close();
			} catch (IOException ex) {

				ex.printStackTrace();
			}
		}
	}
	
	
}
