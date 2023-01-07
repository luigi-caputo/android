package gpsoft.pocketfileserver;

import gpsoft.pocketfileserver.net.MyServerSocket;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

//Copyright GicoPiro 2013

public class Main extends JFrame{

	static Main window;
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 300, HEIGHT = 420;
	public final static String title = "Pocket File server";
	private JPanel panel;
	public JButton butListen;
	private JButton butFolder, butClose;
	private JTextField txtPort;
	public JLabel lblPort, lblAbout;
	private JFileChooser dialog;
	private Rectangle rListen, rPort, rClose, rlblPort, rFolder, rList, rSettings, rAbout;
	private JTable list;
	private JCheckBox chkSettings;
	public String downloadFolder = "";
	public DefaultTableModel model;
	private MyServerSocket socket;
	private MouseMover move;
	
	public Main(){
		move = new MouseMover();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		panel = new JPanel();
		add(panel);
		pack();
		panel.setLayout(null);
		setTitle(title);
		try {
			BufferedImage icon = ImageIO.read(Main.class.getResource("/icon/icon.png"));
			setIconImage(icon);
		} catch (IOException e) {}
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		drawButtons();
	}
	
	public static void main(String[] args){
		window = new Main();
	}
	
	public synchronized void clearTable(){
	    int numrows = model.getRowCount(); 
	    for(int i = numrows - 1; i >=0; i--){
	        model.removeRow(i);
	    }
	}
	
	private void drawButtons(){
		
		rListen = new Rectangle(WIDTH-90,HEIGHT/20,80,20);
		rPort = new Rectangle(5,HEIGHT/20,WIDTH-95,20);
		rlblPort = new Rectangle((WIDTH-250)/2,0,200,20);
		rFolder = new Rectangle(10,HEIGHT-60,275,20);
		rList = new Rectangle(5,HEIGHT/8,285,280);
		rSettings = new Rectangle(10,340,100,20);
		rClose = new Rectangle(WIDTH-165,340,150,20);
		rAbout = new Rectangle(10,HEIGHT-45,275,20);
		
		lblAbout = new JLabel("GP Soft");
		lblAbout.setBounds(rAbout);
		panel.add(lblAbout);
		
		butListen = new JButton("Listen");
		butListen.setBounds(rListen);
		panel.add(butListen);
		
		butClose = new JButton("Close connection");
		butClose.setBounds(rClose);
		butClose.setEnabled(false);
		panel.add(butClose);
		
		txtPort = new JTextField("6556");
		txtPort.setBounds(rPort);
		panel.add(txtPort);
		
		txtPort.setText(Config.loadPort());
		downloadFolder = Config.loadPath();
		
		lblPort = new JLabel("Port (NO PHONE CONNECTED):");
		lblPort.setBounds(rlblPort);
		panel.add(lblPort);
		
		butFolder = new JButton("Set download folder");
		butFolder.setBounds(rFolder);
		panel.add(butFolder);

			    list = new JTable();
			    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			    
			    model = new DefaultTableModel(){
					private static final long serialVersionUID = 1L;
					public boolean isCellEditable(int a, int c){
			    		return false;
			    	}
			    };
			    
			    model.addColumn("File");
			    model.addColumn("KBytes");
			    model.addColumn("State");
			    list.setModel(model);
			    
	    JScrollPane scrollPane = new JScrollPane(list);
	    scrollPane.setBounds(rList);
		panel.add(scrollPane);
		
		chkSettings = new JCheckBox("Save settings");
		chkSettings.setBounds(rSettings);
		panel.add(chkSettings);
		
		chkSettings.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(chkSettings.isSelected()){
					Config.saveConfig(downloadFolder, txtPort.getText());
				}else{
					Config.unLoad();
				}
			}
			
		});
		
		butListen.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(isANumber(txtPort.getText()) && downloadFolder != ""){
					socket = new MyServerSocket(Integer.parseInt(txtPort.getText()), window);
					move.start();
					butListen.setEnabled(false);
					butClose.setEnabled(true);
					butFolder.setEnabled(false);
					txtPort.setEnabled(false);
				}else{
					JOptionPane.showOptionDialog(window, "You have entered no port or no path !", title, 
							-1, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				}
			}
			
		});
		
		butClose.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				    socket.Restart = false;
				    socket.stop();
				    move.stop();
				    clearTable();
					butListen.setEnabled(true);
					butClose.setEnabled(false);
					butFolder.setEnabled(true);
					txtPort.setEnabled(true);
					window.setTitle(title);
					lblPort.setText("Port (NO PHONE CONNECTED):");
			}
			
		});
		
		butFolder.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				dialog = new JFileChooser();
				dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dialog.setAcceptAllFileFilterUsed(false);
				dialog.setCurrentDirectory(new File("."));
				dialog.setMultiSelectionEnabled(false);
				dialog.setDialogTitle("Choose your download folder");
				
				if(dialog.showOpenDialog(window) == JFileChooser.APPROVE_OPTION){
					downloadFolder = dialog.getSelectedFile().getPath();
					butFolder.setText(downloadFolder);
				}else{
					JOptionPane.showOptionDialog(window, "No folder selected !", title, 
							-1, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				}
			}
			
		});
		
		repaint();
		
		if(isANumber(txtPort.getText()) && downloadFolder != ""){
			butFolder.setText(downloadFolder);
			socket = new MyServerSocket(Integer.parseInt(txtPort.getText()), this);
			move.start();
			butListen.setEnabled(false);
			butClose.setEnabled(true);
			butFolder.setEnabled(false);
			txtPort.setEnabled(false);
		}
	}
	
	private boolean isANumber(String text){
		try{
			Integer.parseInt(text);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
}
