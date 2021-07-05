

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ClientGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField_name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI frame = new ClientGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientGUI() {
		setTitle("Bid !!!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		JLabel JLable_welcome = new JLabel("MY BIDS!");
		JLable_welcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		JLable_welcome.setBounds(229, 27, 104, 26);
		desktopPane.add(JLable_welcome);
		
		JTextArea textArea = new JTextArea();
		textArea.setText(ClientApp.serverMsg);////////////////////////
		textArea.setBounds(94, 83, 396, 55);
		desktopPane.add(textArea);
		
		textField_name = new JTextField();
		textField_name.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_name.setBounds(139, 219, 266, 35);
		desktopPane.add(textField_name);
		textField_name.setColumns(10);
		
		JButton btnNewButton = new JButton("NEXT");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(225, 286, 89, 35);
		
		/////button NEXT
		btnNewButton.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				ClientApp.clientMsg = textField_name.getText();/////////////////////////////////
				textArea.setText(ClientApp.serverMsg);////////////////////////
				textField_name.setText(" ");
			}
		});
		
		desktopPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Enter here then press NEXT");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(201, 186, 266, 14);
		desktopPane.add(lblNewLabel);
	}
}
