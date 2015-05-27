package test.view;

import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SequenceDiagram extends JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2751428534404490348L;
	private JLabel label;

	public SequenceDiagram() {
		label = new JLabel("Sequence");
		add(label);
	}
	
	public void paintComponent(Graphics g){
	}

}
