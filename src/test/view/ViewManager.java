package test.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ViewManager extends JPanel{
	/**
	 * 
	 */
	private static final long 					serialVersionUID = 1L;
	private FrameManager						frame;
	private TextAreaPanel						textAreaPanel;
	private FrameManager monitorFrame;
	private TextAreaPanel monitorTextAreaPanel;
	private TextAreaPanel mongoDBPanel;
	private JTabbedPane tPane;
	private TextAreaPanel subPanel;
	private SequenceDiagram monitorSequenceDiagram;
	
	public ViewManager() {
		
		monitorFrame = new FrameManager("Cloudboard server monitor");
		
		monitorTextAreaPanel = new TextAreaPanel();
		monitorSequenceDiagram = new SequenceDiagram();
		mongoDBPanel		 = new TextAreaPanel();
		subPanel			 = new TextAreaPanel();
		createTabbedPane();
		
		monitorFrame.getFrame().add(BorderLayout.CENTER, tPane);
		monitorFrame.openFrame();
	}
	
	private void createTabbedPane() {
		tPane = new JTabbedPane();
		tPane.add("Main", monitorTextAreaPanel);
		tPane.add("Sequence", monitorSequenceDiagram);
		tPane.add("Dump", mongoDBPanel);
		tPane.add("Subscribe", subPanel);
	}

	public void printLogMessage(String log){
		monitorTextAreaPanel.printText(log);
	}
	
	public void printMongoDBMessage(String log) {
		mongoDBPanel.printText(log);
	}

	public void printSubscribe(String log) {
		subPanel.printText(log);
	}
}
