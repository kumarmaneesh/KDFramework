package executionEngine;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExecEngine {

	private JFrame frame;

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSelectProject = new JLabel("SELECT PROJECT:");
		lblSelectProject.setBounds(43, 33, 109, 14);
		frame.getContentPane().add(lblSelectProject);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(169, 30, 158, 20);
		frame.getContentPane().add(comboBox);
		
		JButton btnRun = new JButton("START");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExecEngine.runClient();
			}
		});
		btnRun.setBounds(169, 139, 89, 23);
		frame.getContentPane().add(btnRun);
		
		JLabel lblNewLabel = new JLabel("EXECUTION ENGINE:");
		lblNewLabel.setBounds(43, 78, 109, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(169, 75, 158, 20);
		frame.getContentPane().add(comboBox_1);
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

}
