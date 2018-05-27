import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainView extends JFrame{
	private JPanel mainPanel;

	// tool panel
	private JPanel toolPanel;
	private JButton compareBtn;
	private JButton upBtn;
	private JButton downBtn;
	private JButton copyToLeftBtn;
	private JButton copyToRightBtn;
	
	// image icon for ImageBtn
	private ImageIcon compare_icon;
	private ImageIcon up_icon;
	private ImageIcon down_icon;
	private ImageIcon left_icon;
	private ImageIcon right_icon;
	
	
	// specific panel (right, left)
	private JPanel holderPanel;
	private PanelView leftPV;
	private PanelView rightPV;
	
	public MainView() throws Exception {
		super("Simple Merge");
		
		mainPanel = new JPanel();
		
		toolPanel = new JPanel();
		
		// set image icon
		compare_icon=new ImageIcon("res/compare.png");
		up_icon=new ImageIcon("res/up.png");
		down_icon=new ImageIcon("res/down.png");
		left_icon=new ImageIcon("res/left.png");
		right_icon=new ImageIcon("res/right.png");
		
		// set size of image button
		Image compare_img=compare_icon.getImage(); compare_img=compare_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image up_img=up_icon.getImage(); up_img=up_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image down_img=down_icon.getImage(); down_img=down_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image left_img=left_icon.getImage(); left_img=left_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image right_img=right_icon.getImage(); right_img=right_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		
		compare_icon=new ImageIcon(compare_img);
		up_icon=new ImageIcon(up_img);
		down_icon=new ImageIcon(down_img);
		left_icon=new ImageIcon(left_img);
		right_icon=new ImageIcon(right_img);
		
		// set image button
		compareBtn = new JButton(compare_icon);
		upBtn = new JButton(up_icon);
		downBtn = new JButton(down_icon);
		copyToLeftBtn = new JButton(left_icon);
		copyToRightBtn = new JButton(right_icon);
		
		
		// make Image Button's border invisible
		compareBtn.setBorderPainted(false); compareBtn.setFocusPainted(false);
		upBtn.setBorderPainted(false); upBtn.setFocusPainted(false);
		downBtn.setBorderPainted(false); downBtn.setFocusPainted(false);
		copyToLeftBtn.setBorderPainted(false); copyToLeftBtn.setFocusPainted(false);
		copyToRightBtn.setBorderPainted(false); copyToRightBtn.setFocusPainted(false);
		
		add(compareBtn);
		add(upBtn);
		add(downBtn);
		add(copyToLeftBtn);
		add(copyToRightBtn);
		
		
		holderPanel = new JPanel();
		leftPV = new PanelView();
		rightPV = new PanelView();
		
		compareBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("compare button pressed.");
			}
			
		});
		
		upBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("up button pressed.");
			}
			
		});
		
		downBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("down button pressed.");
			}
			
		});
		
		copyToLeftBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("copy to left button pressed.");
			}
			
		});
		
		copyToRightBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("copy to right button pressed.");
			}
			
		});
		
		toolPanel.setLayout(new GridLayout(1, 5));
		toolPanel.setBackground(Color.CYAN);
		
		toolPanel.add(compareBtn);
		toolPanel.add(upBtn);
		toolPanel.add(downBtn);
		toolPanel.add(copyToLeftBtn);
		toolPanel.add(copyToRightBtn);
		
		leftPV.setBackground(Color.RED);
		rightPV.setBackground(Color.GREEN);
		
		holderPanel.setLayout(new GridLayout(1, 2, 10, 0));
		holderPanel.add(leftPV);
		holderPanel.add(rightPV);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(toolPanel, BorderLayout.NORTH);
		mainPanel.add(holderPanel, BorderLayout.CENTER);
		
		this.add(mainPanel);
		this.pack();
		this.setSize(1200, 900);
		this.setVisible(true);
	
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Start!");
		
		MainView mv = new MainView();
		
		System.out.println("End!");
	}
}