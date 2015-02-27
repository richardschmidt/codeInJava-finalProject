package create_smphonedb;

import java.awt.EventQueue;
import java.io.*;

import common_smphonedb.SmartPhone;
import common_smphonedb.FileIO;

import org.apache.commons.lang3.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreateSmphoneDb {

	private JFrame frmCreateSmartPhone;
	private JTextField textFieldSerialNumber;
	private JTextField textFieldManufacturerAndModel;
	private JTextField textFieldScreen;
	private JTextField textFieldSize;
	private JTextField textFieldCpu;
	private JTextField textFieldMemory;
	private JTextField textFieldWireless;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateSmphoneDb window = new CreateSmphoneDb();
					window.frmCreateSmartPhone.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateSmphoneDb() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCreateSmartPhone = new JFrame();
		frmCreateSmartPhone.setTitle("Create Smart Phone Database");
		frmCreateSmartPhone.setBounds(100, 100, 570, 468);
		frmCreateSmartPhone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCreateSmartPhone.getContentPane().setLayout(null);
		
		JLabel lblEnterSerialNumber = new JLabel("Enter Serial Number:");
		lblEnterSerialNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterSerialNumber.setBounds(32, 34, 126, 25);
		frmCreateSmartPhone.getContentPane().add(lblEnterSerialNumber);
		
		JLabel lblEnterManufactuerAnd = new JLabel("Enter Manufactuer and Model:");
		lblEnterManufactuerAnd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterManufactuerAnd.setBounds(32, 70, 184, 25);
		frmCreateSmartPhone.getContentPane().add(lblEnterManufactuerAnd);
		
		JLabel lblEnter = new JLabel("Enter Screen:");
		lblEnter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnter.setBounds(32, 106, 93, 25);
		frmCreateSmartPhone.getContentPane().add(lblEnter);
		
		JLabel lblNewLabel = new JLabel("Enter Size:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(32, 142, 65, 25);
		frmCreateSmartPhone.getContentPane().add(lblNewLabel);
		
		JLabel lblEnterCpu = new JLabel("Enter CPU:");
		lblEnterCpu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterCpu.setBounds(32, 178, 78, 25);
		frmCreateSmartPhone.getContentPane().add(lblEnterCpu);
		
		JLabel lblEnter_1 = new JLabel("Enter Memory:");
		lblEnter_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnter_1.setBounds(32, 214, 93, 25);
		frmCreateSmartPhone.getContentPane().add(lblEnter_1);
		
		JLabel lblEnterWireless = new JLabel("Enter Wireless:");
		lblEnterWireless.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterWireless.setBounds(32, 250, 93, 25);
		frmCreateSmartPhone.getContentPane().add(lblEnterWireless);
		
		textFieldSerialNumber = new JTextField();
		textFieldSerialNumber.setBounds(297, 38, 184, 20);
		frmCreateSmartPhone.getContentPane().add(textFieldSerialNumber);
		textFieldSerialNumber.setColumns(10);
		
		textFieldManufacturerAndModel = new JTextField();
		textFieldManufacturerAndModel.setBounds(297, 70, 184, 20);
		frmCreateSmartPhone.getContentPane().add(textFieldManufacturerAndModel);
		textFieldManufacturerAndModel.setColumns(10);
		
		textFieldScreen = new JTextField();
		textFieldScreen.setBounds(297, 106, 184, 20);
		frmCreateSmartPhone.getContentPane().add(textFieldScreen);
		textFieldScreen.setColumns(10);
		
		textFieldSize = new JTextField();
		textFieldSize.setBounds(297, 142, 184, 20);
		frmCreateSmartPhone.getContentPane().add(textFieldSize);
		textFieldSize.setColumns(10);
		
		textFieldCpu = new JTextField();
		textFieldCpu.setBounds(297, 178, 184, 20);
		frmCreateSmartPhone.getContentPane().add(textFieldCpu);
		textFieldCpu.setColumns(10);
		
		textFieldMemory = new JTextField();
		textFieldMemory.setBounds(297, 214, 184, 20);
		frmCreateSmartPhone.getContentPane().add(textFieldMemory);
		textFieldMemory.setColumns(10);
		
		textFieldWireless = new JTextField();
		textFieldWireless.setBounds(297, 250, 184, 20);
		frmCreateSmartPhone.getContentPane().add(textFieldWireless);
		textFieldWireless.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					btnSubmit_CLICK();
				}
				catch (Exception e){
					System.out.println(e.toString());
				}
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSubmit.setBounds(184, 315, 162, 55);
		frmCreateSmartPhone.getContentPane().add(btnSubmit);
	}//End of Method initialize()
	private void btnSubmit_CLICK(){
		//-------------- First, validate all the text fields
				// If any problem, a dialog warning pops up to stop the program
				boolean isValidated = validateTextField();
				
				if (! isValidated) return;
				
				
				//-------------- All the text fields have been validated
				FileIO fileIOHandler = new FileIO();
				
				// Declare output file database: smphoneDatabase.txt
				// MUST use \ to qualify '\' in the path of the file
				File outputFile = new File("C:\\\\JAVA\\OUTPUTS\\smphoneDatabase.txt");
				
				String strSerialNumber = textFieldSerialNumber.getText();
				long serialNumber = Long.parseLong(strSerialNumber);
				
				String strManufacturerAndModel = textFieldManufacturerAndModel.getText();
				String strScreen = textFieldScreen.getText();
				String strSize = textFieldSize.getText();
				String strCpu = textFieldCpu.getText();
				String strMemory = textFieldMemory.getText();
				String strWireless = textFieldWireless.getText();
				
				// Create a Smart Phone object
				SmartPhone aSmartPhone = new SmartPhone (serialNumber, strManufacturerAndModel);
				aSmartPhone.setScreen(strScreen);
				aSmartPhone.setSize(strSize);
				aSmartPhone.setCpu(strCpu);
				aSmartPhone.setMemory(strMemory);
				aSmartPhone.setWireless(strWireless);
				
				// Get the string of book data
				String strSmartPhoneInfo = aSmartPhone.toString();
				
				
				try {
					
					// Write the string to the book database file
					// by adding a line to the file
					fileIOHandler.appendOneLineToFile(outputFile, strSmartPhoneInfo);
				}
				catch (IOException ex){ 
		      		ex.printStackTrace();
		    	}
				
				
				// After successfully inserting a new book record to the database
				// refresh all the text fields to prepare for the next record
				textFieldSerialNumber.setText("");
				textFieldManufacturerAndModel.setText("");
				textFieldScreen.setText("");
				textFieldSize.setText("");
				textFieldCpu.setText("");
				textFieldMemory.setText("");
				textFieldWireless.setText("");
	}//End of btnSubmit_CLICK
	private boolean validateTextField(){
boolean isValidated = true;
		
		//----------- Validate ISBN text field
		
		try{
			Validate.notBlank(textFieldSerialNumber.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(frmCreateSmartPhone, "All the text fields must have valid values - Serial Number must have a Numeric Value.");
			textFieldSerialNumber.requestFocusInWindow(); // make it ready to enter the value
			textFieldSerialNumber.selectAll(); // select all text in the text field to delete it or to replace it
			isValidated = false;
		}
		
		if (! isValidated) return (isValidated);
		
		// For Serial Number, also need to verify the entered value is a valid numeric
		try{
			long tempLong = Long.parseLong(textFieldSerialNumber.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(frmCreateSmartPhone, "ISBN must have a Numeric Value.");
			textFieldSerialNumber.requestFocusInWindow(); // make it ready to enter the value
			textFieldSerialNumber.selectAll(); // select all text in the text field to delete it or to replace it
			isValidated = false;
		}
		
		if (! isValidated) return (isValidated);
		
		//----------- Validate Manufacturer and Model text field
		
		try{
			Validate.notBlank(textFieldManufacturerAndModel.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(frmCreateSmartPhone, "All the text fields must have valid values - Manufacturer and Model cannot be blank !!!.");
			textFieldManufacturerAndModel.requestFocusInWindow(); // make it ready to enter the value
			textFieldManufacturerAndModel.selectAll(); // select all text in the text field to delete it or to replace it
			isValidated = false;
		}

		if (! isValidated) return (isValidated);

		//----------- Validate Screen text field
		
		try{
			Validate.notBlank(textFieldScreen.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(frmCreateSmartPhone, "All the text fields must have valid values - Screen cannot be blank !!!.");
			textFieldScreen.requestFocusInWindow(); // make it ready to enter the value
			textFieldScreen.selectAll(); // select all text in the text field to delete it or to replace it
			isValidated = false;
		}

		if (! isValidated) return (isValidated);
		
		//----------- Validate Size text field
		
		try{
			Validate.notBlank(textFieldSize.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(frmCreateSmartPhone, "All the text fields must have valid values - Size cannot be blank !!!.");
			textFieldSize.requestFocusInWindow(); // make it ready to enter the value
			textFieldSize.selectAll(); // select all text in the text field to delete it or to replace it
			isValidated = false;
		}

		if (! isValidated) return (isValidated);
		
		//----------- Validate CPU text field
		
		try{
			Validate.notBlank(textFieldCpu.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(frmCreateSmartPhone, "All the text fields must have valid values - CPU cannot be blank !!!.");
			textFieldCpu.requestFocusInWindow(); // make it ready to enter the value
			textFieldCpu.selectAll(); // select all text in the text field to delete it or to replace it
			isValidated = false;
		}

		if (! isValidated) return (isValidated);
		
		//----------- Validate Memory text field
		
		try{
			Validate.notBlank(textFieldMemory.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(frmCreateSmartPhone, "All the text fields must have valid values - Memory cannot be blank !!!.");
			textFieldMemory.requestFocusInWindow(); // make it ready to enter the value
			textFieldMemory.selectAll(); // select all text in the text field to delete it or to replace it
			isValidated = false;
		}

		//----------- Validate Wireless text field
		try{
			Validate.notBlank(textFieldWireless.getText());
		}catch( Exception e){
			JOptionPane.showMessageDialog(frmCreateSmartPhone, "All the text fields must have valid values - Wireless cannot be blank !!!.");
			textFieldWireless.requestFocusInWindow(); //make it ready to enter the value
			textFieldMemory.selectAll(); // select all text in the text field to delete it or to replace it
			isValidated = false;
		}
		
		return (isValidated);
	}//End of btnSubmit_CLICK
}//End of Class
