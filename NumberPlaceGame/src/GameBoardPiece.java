import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.text.NumberFormatter;

public class GameBoardPiece extends JLabel{
	
	int row, col;
	int value;
	JFormattedTextField textField;
	boolean isTextField;
	
	GameBoardPiece(int rowNumber, int colNumber) {
		row = rowNumber;
		col = colNumber;
	}
	
	public void initializeType (boolean isInstanceOfTextField) {
		isTextField = isInstanceOfTextField;
		
		if (isTextField)
		{
			createTextField();
		}
		else
		{
			createStaticPiece();
		}
	}
	
	private void createTextField () {
		
		//Set integer format
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(1);
	    formatter.setMaximum(9);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true); // If you want the value to be committed on each keystroke instead of focus lost
		
		//JFormattedTextField
		textField = new JFormattedTextField(formatter);
		textField.setBounds(0,0,40,40);
		textField.setBorder( BorderFactory.createEmptyBorder() );
		textField.setHorizontalAlignment(JFormattedTextField.CENTER);
		Font font = new Font("lucida handwriting", Font.BOLD, 30);
		textField.setForeground(new Color(173, 173, 234));
		this.setBackground(Color.WHITE);
		textField.setFont(font);
//		textField.setCaretColor(Color.WHITE);
//		textField.addMouseListener(new MouseAdapter() { //Add mouse listener
//            @Override
//            public void mouseClicked(MouseEvent e) { //When grid piece clicked
//            	textField.setValue(null);
//            }
//        });
		textField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) //Allow backspace to clear textfield
			    {  
			        textField.setValue(null);
			    }
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		this.add(textField);
	}
	
//	public void setTextField(int v) {
//		textField.setValue(new Double(v));
//	}
	
	public int getValue() {
		if (isTextField)
		{
			if (textField.getValue() != null)
			{
				return (int) textField.getValue();
			}
			else
			{
				return -9;
			}
		}
		if ( !isTextField )
		{
			return value;
		}
		return -1;
	}
	
	
	private void createStaticPiece () {
		this.setHorizontalAlignment(JLabel.CENTER);
		Font font = new Font("SansSerif", Font.BOLD, 30);
		this.setFont(font);
		this.setForeground(new Color(86, 86, 117));
		this.setBackground(Color.WHITE);
	}
	
	public void setValue (int v) {
		if ( !isTextField )
		{
			this.setText( String.valueOf(v) );
			value = v;
		}
	}
	
}
