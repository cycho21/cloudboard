package test.view;

import javax.swing.JFrame;

public class FrameManager {
	private JFrame						frame;
	private int							frameWidth = 1400;
	private int 						frameheight = 800;
	
	
	public FrameManager() {
		this.frame = new JFrame();
		frame.setSize(this.frameWidth, this.frameheight);
	}
	
	public FrameManager(String title){
		this.frame = new JFrame(title);
		frame.setSize(this.frameWidth, this.frameheight);
	}

	public void openFrame(){
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	public int getFrameheight() {
		return frameheight;
	}

	public void setFrameheight(int frameheight) {
		this.frameheight = frameheight;
	}
}
