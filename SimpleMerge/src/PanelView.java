import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.io.*;

public class PanelView extends JPanel {
	protected PanelController pc;
	private JPanel myPanel;
	private JPanel xPanel; // filename, xbutton
	private JPanel miniPanel; // xPanel + text field
	
	public JPanel menuPanel;	// no private???
	protected JButton loadBtn;
	private JButton editBtn;
	private JButton saveBtn;
	protected JButton saveAsBtn;
	   
	private ImageIcon load_icon;
	private ImageIcon edit_icon;
	private ImageIcon save_icon;
	private ImageIcon saveAs_icon;
	private ImageIcon x_icon;
	   
	protected JEditorPane myTextArea;
	private JScrollPane scrollPane;
	private JLabel statusBar;
	
	public JLabel myfname;
	private JButton xbutton;
	private boolean xPressed;
	
	private Color panelColor;
	   
	public PanelView() throws Exception{
		pc = new PanelController();
		
		xPanel = new JPanel();
		myPanel = new JPanel();
		menuPanel = new JPanel();
		miniPanel = new JPanel();
		
		myfname = new JLabel("");
		xbutton = new JButton("X");
		xPressed = false;
		
		panelColor=new Color(0,0,0); //set color default as WHITE 
		
		// set image icon
		load_icon=new ImageIcon("res/load.png");
		edit_icon=new ImageIcon("res/edit.png");
		save_icon=new ImageIcon("res/save.png");
		saveAs_icon=new ImageIcon("res/save_as.png");
		x_icon=new ImageIcon("res/reject.png");
			
		// set size of image button
		Image load_img=load_icon.getImage();		load_img=load_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image edit_img=edit_icon.getImage();		edit_img=edit_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image save_img=save_icon.getImage(); 		save_img=save_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image saveAs_img=saveAs_icon.getImage();	saveAs_img=saveAs_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image x_img=x_icon.getImage();				x_img=x_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		
		load_icon=new ImageIcon(load_img);
		edit_icon=new ImageIcon(edit_img);
		save_icon=new ImageIcon(save_img);
		saveAs_icon=new ImageIcon(saveAs_img);
		x_icon=new ImageIcon(x_img);
			
		// set image button
		loadBtn = new JButton(load_icon);			loadBtn.setContentAreaFilled(false);
		editBtn = new JButton(edit_icon); 			editBtn.setContentAreaFilled(false);
		saveBtn = new JButton(save_icon);			saveBtn.setContentAreaFilled(false);
		saveAsBtn = new JButton(saveAs_icon); 		saveAsBtn.setContentAreaFilled(false);
		xbutton=new JButton(x_icon); 				xbutton.setContentAreaFilled(false); 
		
		// set Button Status
		loadBtn.setEnabled(true);
		editBtn.setEnabled(false);
		saveBtn.setEnabled(false);
		saveAsBtn.setEnabled(false);
			
		// make Image Button's border invisible
		loadBtn.setBorderPainted(false); 			loadBtn.setFocusPainted(false);
		editBtn.setBorderPainted(false); 			editBtn.setFocusPainted(false);
		saveBtn.setBorderPainted(false); 			saveBtn.setFocusPainted(false);
		saveAsBtn.setBorderPainted(false); 			saveAsBtn.setFocusPainted(false);
		xbutton.setFocusPainted(false); 			xbutton.setBorderPainted(false);
	
		myTextArea = new JEditorPane();
		// Dummy Text
		myTextArea.setText("Click the Load Button.");
		myTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		
		// keyboard input
		myTextArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				// DIRTY FLAG SET
				pc.setUpdated(true); 
			}
		});
		
		scrollPane = new JScrollPane(myTextArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		 
		// default
		pc.setMode(Mode.VIEW);
		statusBar = new JLabel("View Mode");
		statusBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 2, 0));
		
		
		setMode(Mode.VIEW);
		   
		editBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Edit button pressed.");
				
				// Set Mode
				setMode(Mode.EDIT);
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		// TODO Auto-generated method stub
		  		System.out.println("Save button pressed.");

		  		save();
		  		
		  		System.out.println("Save Completed.");
		  	}
				
		});
		   
		saveAsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save As button pressed.");
				
				FileDialog fd = new FileDialog(new JFrame(), "Open File", FileDialog.SAVE);
				fd.setVisible(true);
				
				String editedContent = myTextArea.getText();
				pc.setFileContent(editedContent);
				
				if (fd.getFile() != null) {
					String filePath = fd.getDirectory() + fd.getFile();
					if (pc.saveAs(filePath)) {
						setMode(Mode.VIEW);	
					}
				}
				
				myfname.setText(pc.getFile().getName());
			}
		});
		
		xbutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// can't press x button while compare mode
				if(pc.getMode()==Mode.COMPARE){
					setXpressed(false);
					return;
				}
				// TODO Auto-generated method stub
				System.out.println("xbutton pressed.");
				setXpressed(true);
				
				myfname.setText("");
				myTextArea.setText("Click the Load Button.");
				
				// Set Mode
				setMode(Mode.VIEW);
			}
		});
		
		xPanel.setLayout(new BorderLayout());
		xPanel.add(myfname, BorderLayout.CENTER);
		xPanel.add(xbutton, BorderLayout.EAST);
		
		miniPanel.setLayout(new BorderLayout());
		miniPanel.add(xPanel, BorderLayout.NORTH);
		miniPanel.add(scrollPane, BorderLayout.CENTER);
		miniPanel.add(statusBar, BorderLayout.SOUTH);
		
		menuPanel.setLayout(new GridLayout(1, 4));   
		menuPanel.add(loadBtn);
		menuPanel.add(editBtn);
		menuPanel.add(saveBtn);
		menuPanel.add(saveAsBtn);
		menuPanel.setBorder(new MatteBorder(0,0,1,0, Color.GRAY));
		
		myPanel.setLayout(new BorderLayout());
		myPanel.add(menuPanel, BorderLayout.NORTH);
		myPanel.add(miniPanel, BorderLayout.CENTER);
		   
		this.setLayout(new BorderLayout());
		this.add(myPanel);
		this.setVisible(true);
	}
	
	// xbutton control
	public boolean getXpressed(){
		return xPressed;
	}
	
	public void setXpressed(boolean tf){
		this.xPressed = tf;
	}
	
	public void setXEnabled(boolean tf){
		xbutton.setEnabled(tf);
	}

	public void setMode(Mode mode) {
		switch(mode) {
		case VIEW:
			pc.setMode(Mode.VIEW);
			statusBar.setText("View Mode");
			myTextArea.setEditable(false);
			loadBtn.setEnabled(true);
			editBtn.setEnabled(true);
			saveBtn.setEnabled(false);
			saveAsBtn.setEnabled(false);
			break;
		case EDIT:
			pc.setMode(Mode.EDIT);
			statusBar.setText("Edit Mode");
			myTextArea.setEditable(true);
			loadBtn.setEnabled(true);
			editBtn.setEnabled(false);
			saveBtn.setEnabled(true);
			saveAsBtn.setEnabled(true);
			break;
		case COMPARE:
			pc.setMode(Mode.COMPARE);
			statusBar.setText("Compare Mode");
			myTextArea.setEditable(false);
			loadBtn.setEnabled(false);
			editBtn.setEnabled(false);
			saveBtn.setEnabled(true);
			saveAsBtn.setEnabled(true);
			break;
		default:
			break;
		}
	}
	

	public PanelController getPC() {
		return pc;
	}
	
	public void save() {
		String editedContent = myTextArea.getText();
		pc.setFileContent(editedContent);
		
  		if (pc.save()) {
  			setMode(Mode.VIEW);
  		}
	}
	
	public void setPanelColor(int x, int y, int z){
		panelColor=new Color(x,y,z);
	}
	
}
