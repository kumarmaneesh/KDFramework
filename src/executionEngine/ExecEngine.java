package executionEngine;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ExecEngine {

	private JFrame frame;
	private JTextField textField;
	
	public static String folderLocation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExecEngine window = new ExecEngine();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ExecEngine() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 244);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnTestResults = new JButton("Test Results");
		btnTestResults.setVisible(false);
		btnTestResults.setToolTipText("Click here to view Test Results!");
		btnTestResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File("D:\\Project\\KDFramework\\AutomationReport.html");						
				try {
					Desktop.getDesktop().open(f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnTestResults.setForeground(Color.BLUE);
		btnTestResults.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnTestResults.setBackground(Color.WHITE);
		btnTestResults.setBounds(31, 172, 112, 23);
		frame.getContentPane().add(btnTestResults);
		
		JLabel lblSelectProject = new JLabel("SELECT PROJECT FOLDER LOCATION:");
		lblSelectProject.setBackground(Color.BLACK);
		lblSelectProject.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectProject.setForeground(Color.BLACK);
		lblSelectProject.setBounds(31, 33, 242, 14);
		frame.getContentPane().add(lblSelectProject);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(31, 58, 296, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		/*JLinkButton lbtnResults = new JLinkButton("Test Results");
		btnBrowse.setBounds(337, 58, 67, 23);
		frame.getContentPane().add(btnBrowse);*/
		//setLayout(new GridBagLayout());
	    //JButton browse = new JButton("...");
	    JButton btnBrowse = new JButton("Browse");
	    btnBrowse.setBounds(337, 56, 78, 25);
	    btnBrowse.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	            JFileChooser chooser = new JFileChooser();
	            chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Browse the folder to process");
	            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(false);

	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	                System.out.println("getCurrentDirectory(): "+ chooser.getCurrentDirectory());
	                System.out.println("getSelectedFile() : "+ chooser.getSelectedFile());
	                String tempLoc = chooser.getSelectedFile().toString();
	                tempLoc = tempLoc.replace("/","//");
	                folderLocation = tempLoc;
	                System.out.println(folderLocation);
	                textField.setText(folderLocation);
	            } else {
	                System.out.println("No Selection ");
	            }
	        }
	    });
	    frame.getContentPane().add(btnBrowse);
		
	    
	    JButton btnRun = new JButton("START");
	    btnRun.setToolTipText("Click to start Test Execution!");
	    btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTestResults.setVisible(false);
				if(textField.getText().length()==0){
					System.out.println("No folder selected!");
					msgbox("No folder selected!");
		}else{
				ExecEngine.runClient();
				btnTestResults.setVisible(true);
		}
			}
		});
		btnRun.setBounds(31, 100, 75, 25);
		frame.getContentPane().add(btnRun);
		
	}

private static void runClient() {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            String[] args1={"10"};
            try {
				DriverScript.main(args1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    });
}

private void msgbox(String msg){
	   JOptionPane.showMessageDialog(null, msg);
	}
}
