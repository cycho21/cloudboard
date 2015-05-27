package test.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextAreaPanel extends JPanel{
	/**
	 * 
	 */
	private static final long 				serialVersionUID = 1L;
	private JTextArea 						textArea;
	private final String					NEW_LINE = "\n";
	
	public TextAreaPanel() {
		textArea = new JTextArea(50,125);
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		this.add(scrollPane);
	}
	
	public void printText(String text){
		textArea.append(text + NEW_LINE);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

}
