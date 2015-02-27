package search_smphonedb;

import java.awt.EventQueue;
import java.io.*;

import common_smphonedb.FileIO;
import common_smphonedb.SmartPhone;

import org.apache.commons.lang3.*;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;



public class SearchSmphoneDb {

	private JFrame frmSearchSmartPhone;
	private JComboBox comboBoxAttribute;
	private JLabel lblEnterSearchValue;
	private JTextField textFieldSearchValue;
	private JButton btnSubmit;
	private JScrollPane scrollPaneSearchResults;
	private JLabel lblSmphoneSearchResults;
	private JTextArea textAreaSearchResults;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchSmphoneDb window = new SearchSmphoneDb();
					window.frmSearchSmartPhone.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchSmphoneDb() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSearchSmartPhone = new JFrame();
		frmSearchSmartPhone.setTitle("Search Smart Phone Database");
		frmSearchSmartPhone.setBounds(100, 100, 510, 563);
		frmSearchSmartPhone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSearchSmartPhone.getContentPane().setLayout(null);
		
		JLabel lblSearchAttribute = new JLabel("Select Attribute to Search:");
		lblSearchAttribute.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchAttribute.setBounds(27, 34, 161, 21);
		frmSearchSmartPhone.getContentPane().add(lblSearchAttribute);
		
		String[] smphoneAttributeList = {"ALL", "SerialNumber", "ManufacturerandModel", "Screen", "Size", "Cpu", "Memory", "Wireless"};
		comboBoxAttribute = new JComboBox(smphoneAttributeList);
		comboBoxAttribute.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxAttribute.setBounds(293, 34, 161, 21);
		frmSearchSmartPhone.getContentPane().add(comboBoxAttribute);
		
		lblEnterSearchValue = new JLabel("Enter Value to Search:");
		lblEnterSearchValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterSearchValue.setBounds(27, 105, 161, 21);
		frmSearchSmartPhone.getContentPane().add(lblEnterSearchValue);
		
		textFieldSearchValue = new JTextField();
		textFieldSearchValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldSearchValue.setBounds(293, 105, 161, 21);
		frmSearchSmartPhone.getContentPane().add(textFieldSearchValue);
		textFieldSearchValue.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					btnSubmit_CLICKED();
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}
		});
		btnSubmit.setBounds(84, 189, 309, 60);
		frmSearchSmartPhone.getContentPane().add(btnSubmit);
		
		scrollPaneSearchResults = new JScrollPane();
		scrollPaneSearchResults.setViewportBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPaneSearchResults.setBounds(27, 288, 431, 225);
		frmSearchSmartPhone.getContentPane().add(scrollPaneSearchResults);
		
		lblSmphoneSearchResults = new JLabel("Smart Phone Search Results");
		lblSmphoneSearchResults.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		scrollPaneSearchResults.setColumnHeaderView(lblSmphoneSearchResults);
		
		textAreaSearchResults = new JTextArea();
		textAreaSearchResults.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		scrollPaneSearchResults.setViewportView(textAreaSearchResults);
	}//End of initialize()
	private void btnSubmit_CLICKED(){
		// Refresh the text area of search results --> make it blank
				textAreaSearchResults.setText("");

				//-------------- First, validate all the text fields
				// If any problem, a dialog warning pops up to stop the program
				boolean isValidated = validateTextFields();
				
				if (! isValidated) return;
						
						
				//-------------- All the text fields have been validated
				FileIO fileIOHandler = new FileIO();
				
				// Declare the target file database: smphoneDatabase.txt
				// MUST use \ to qualify '\' in the path of the file
				File fileDB = new File("C:\\\\JAVA\\OUTPUTS\\smphoneDatabase.txt");		
				
				int numSmartPhoneInDb = 0; // number of Smart Phone in DB
				int numFoundSmartPhone = 0; // number of found Smart Phone
				String[] smartPhoneDbArray; // Array of all Smart Phone records in DB --> array of String
				SmartPhone [] foundSmartPhoneArray = new SmartPhone[FileIO.getMaxNumLines()]; // Array of found Smart Phone --> array of Smart Phone
						
				try {			
					// Read all the records from Smart Phone file database 
					numSmartPhoneInDb = fileIOHandler.readLinesFromFile(fileDB);
					smartPhoneDbArray = fileIOHandler.getStrLinesArray();
					
					// Get the selected item from ComboBox
					String strSelectedAttribute = (String) comboBoxAttribute.getSelectedItem();
					
					// Get the search value
					String strSerialNumber = textFieldSearchValue.getText();
					
					// Invoke searchSmartPhone()
					numFoundSmartPhone = searchSmartPhone (numSmartPhoneInDb, smartPhoneDbArray, foundSmartPhoneArray, strSelectedAttribute, strSerialNumber);
					
					// If no matched Smart Phone is found, display warning
					if (numFoundSmartPhone == 0) {
						textAreaSearchResults.append("No matched smart phone is found in the database. \n");
					}

				}
				catch (IOException ex){ 
		      		ex.printStackTrace();
		    	}
	}//End of btnSubmit
	
	/****************************
	 * Name: validateTextFields
	 * Parameters: None
	 * Return: boolean
	 * --> TRUE: The search value text field is successfully validate
	 * --> FALSE: The text field has failed the validation
	 * Description:
	 * --> This method verify to be sure the search value text field contains valid data: 
	 * --> Valid data: not null, not zero-size data, not empty String, not filled only with blank space
	 * --> If ISBN is selected as search attribute: valid data must also be numeric, i.e. only consisting of digits
	 * 
	 ****************************/
	
	private boolean validateTextFields() {
		
		boolean isValidated = true;
		
		//----------- Validate the text field of search value
		// Need to validate for every search attribute except for "ALL"
		
		if( ! comboBoxAttribute.getSelectedItem().equals("ALL")) {
			
			try{
				Validate.notBlank(textFieldSearchValue.getText());
			}catch(Exception e){
				JOptionPane.showMessageDialog(frmSearchSmartPhone, "The text field of search value must have valid values - Cannot be blank !!!.");
				textFieldSearchValue.requestFocusInWindow(); // make it ready to enter the value
				textFieldSearchValue.selectAll(); // select all text in the text field to delete it or to replace it
				isValidated = false;
			}
		}
		
		// If any problem, stop the program
		if (! isValidated) return (isValidated);
		
		// For Serial Number, also need to verify the entered value is a valid numeric
		if(comboBoxAttribute.getSelectedItem().equals("SerialNumber")){
    		try{
    			Long.parseLong(textFieldSearchValue.getText());
    		}catch(Exception e){
    			JOptionPane.showMessageDialog(frmSearchSmartPhone, "ISBN must have a Numeric Value.");
				textFieldSearchValue.requestFocusInWindow(); // make it ready to enter the value
				textFieldSearchValue.selectAll(); // select all text in the text field to delete it or to replace it
				isValidated = false;
    		}
    	}
		
		return (isValidated);
		
	} // validateTextFields

	
	
	
	
	/*******************
	 * Name: searchSmart Phone
	 * Parameters:
	 * --> numBooks: number of Smart Phones in the smartPhoneArray
	 * --> smphoneArray: array of strings, each string is a Smart Phone record (in DB) of CSV format
	 * --> foundSmphoneArray: array of class Book elements that are found in the database
	 * --> aStrSearchAttr: a String that represents an attribute that is used to search
	 * --> aStrSearchValue: a String that represents the value of the attribute used to search
	 * Return
	 * --> This method returns an array of Smart Phone objects - smart phones that are found in the database
	 * Description:
	 * This method performs a search for Smart Phones whose attribute has the value that is 
	 * matched with Smart Phones in the smartPhoneArray
	********************/
	
	private int searchSmartPhone(int numSmartPhone, 
								String[] smartPhoneArray, 
								SmartPhone[] foundSmartPhoneArray, 
								String aStrSearchAttr, 
								String aStrSearchValue) {
		
		int numFoundSmartPhone = 0;
		
		if (aStrSearchAttr.equals("ALL")) {
			
			// ALL is used to search for all Smart Phones
			numFoundSmartPhone = searchSmartPhoneByAll(numSmartPhone, smartPhoneArray, foundSmartPhoneArray);
			
		} // End of if (All)
		
		if (aStrSearchAttr.equals("SerialNumber")) {
		
			// Serial Number is used to search for Smart Phones
			numFoundSmartPhone = searchSmartPhoneBySerialNumber(numSmartPhone, aStrSearchValue, smartPhoneArray, foundSmartPhoneArray);
			
		} // End of if (Serial Number)
			
		if (aStrSearchAttr.equals("ManufacturerAndModel")) {
				
			// Manufacturer and Model is used to search for Smart Phones
			numFoundSmartPhone = searchSmartPhoneByManufacturerAndModel(numSmartPhone, aStrSearchValue, smartPhoneArray, foundSmartPhoneArray);
				
				
		} // End of if (Manufacturer and Model)
			
			
		if (aStrSearchAttr.equals("Screen")) {
				
			// Screen is used to search for Smart Phones
			numFoundSmartPhone = searchSmartPhoneByScreen(numSmartPhone, aStrSearchValue, smartPhoneArray, foundSmartPhoneArray);
				
		} // End of if (Screen)
			
			
		if (aStrSearchAttr.equals("Size")) {
				
			// Size is used to search for Smart Phones
			numFoundSmartPhone = searchSmartPhoneBySize(numSmartPhone, aStrSearchValue, smartPhoneArray, foundSmartPhoneArray);
			
		} // End of if (Size)
			
			
		if (aStrSearchAttr.equals("Cpu")) {
				
			// CPU is used to search for Smart Phones
			numFoundSmartPhone = searchSmartPhoneByCpu(numSmartPhone, aStrSearchValue, smartPhoneArray, foundSmartPhoneArray);
				
		} // End of if (CPU)
			
			
		if (aStrSearchAttr.equals("Memory")) {
				
			// Memory is used to search for Smart Phones
			numFoundSmartPhone = searchSmartPhoneByMemory(numSmartPhone, aStrSearchValue, smartPhoneArray, foundSmartPhoneArray);
				
		} // End of if (Memory)
			
		if (aStrSearchAttr.equals("Wireless")) {
			
			//Wireless is used to search for Smart Phones
			numFoundSmartPhone = searchSmartPhoneByWireless(numSmartPhone, aStrSearchValue, smartPhoneArray, foundSmartPhoneArray);
			
		}// End of if (Wireless)
		
		return (numFoundSmartPhone);
		
		
	} // End of searchSmartPhone
	
	
	private int searchSmartPhoneByAll(int numSmartPhone, String[] smartPhoneArray, SmartPhone[] foundSmartPhoneArray) {
		
		int numFoundSmartPhone = 0;
		String aStrSmartPhoneRecord = "";
		
		for (int i = 0; i < numSmartPhone; i++) {
			
			aStrSmartPhoneRecord = smartPhoneArray[i];
	
			// a SmartPhonek is found --> add SmartPhone into found-SmartPhone array
			SmartPhone aFoundSmartPhone = new SmartPhone (aStrSmartPhoneRecord);
				
			foundSmartPhoneArray[numFoundSmartPhone] = aFoundSmartPhone;
				
			// Increment numFoundSmartPhone to indicate one more Smart Phone is found
			numFoundSmartPhone++;
							
		} // End of for (scan SmartPhone array)

		// Write Smart Phone record of each found Smart Phone into the text area (Smart Phone search results)
		for (int j = 0; j < numFoundSmartPhone; j++) {
			
			// Append a Smart Phone record into the search results text area
			textAreaSearchResults.append((foundSmartPhoneArray[j]).toString() + "\n"); 
			
		}
		
		
		return (numFoundSmartPhone);

		
	} // End of searchSmarPhoneByAll
	
	
	private int searchSmartPhoneBySerialNumber(int numSmartPhone, String strSerialNumber, String[] smartPhoneArray, SmartPhone[] foundSmartPhoneArray) {
		
		SmartPhone aSmartPhone;
		int numFoundSmartPhone = 0;
		String aStrSmartPhoneRecord = "";
		
		for (int i = 0; i < numSmartPhone; i++) {
			
			aStrSmartPhoneRecord = smartPhoneArray[i];
			aSmartPhone = SmartPhone.recreateSmartPhoneFromString(aStrSmartPhoneRecord);
	
			// First convert string value to long
			long aSerialNumber = Long.parseLong(strSerialNumber);
			
			if (aSerialNumber == aSmartPhone.getSerialNumber()){
				// a Smart Phone is found --> add Smart Phone into found-SmartPhone array
				SmartPhone aFoundSmartPhone = new SmartPhone (aStrSmartPhoneRecord);
				
				foundSmartPhoneArray[numFoundSmartPhone] = aFoundSmartPhone;
				
				// Increment numFoundSmartPhone to indicate one more Smart Phone is found
				numFoundSmartPhone++;
			}			
		
		} // End of for (scan SmartPhone array)

		// Write Smart Phone record of each found Smart Phone into the text area (Smart Phone search results)
		for (int j = 0; j < numFoundSmartPhone; j++) {
			
			// Append a Smart Phone record into the search results text area
			textAreaSearchResults.append((foundSmartPhoneArray[j]).toString() + "\n"); 
			
		}
		
		
		return (numFoundSmartPhone);
		
		
	} // End of searchBookBySerialNumber
	

	private int searchSmartPhoneByManufacturerAndModel(int numSmartPhone, String strManufacturerAndModel, String[] smartPhoneArray, SmartPhone[] foundSmartPhoneArray) {
		
		SmartPhone aSmartPhone;
		String strDbSmartPhoneManufacturerAndModel = ""; // Manufacturer and Model of Smart Phone in database
		int numFoundSmartPhone = 0;
		String aStrSmartPhoneRecord = "";
		
		for (int i = 0; i < numSmartPhone; i++) {
			
			aStrSmartPhoneRecord = smartPhoneArray[i];
			aSmartPhone = SmartPhone.recreateSmartPhoneFromString(aStrSmartPhoneRecord);
	
			strDbSmartPhoneManufacturerAndModel = aSmartPhone.getManufacturerAndModel(); // Get Manufacturer and Model of the Smart Phone in db
			
			if ((strDbSmartPhoneManufacturerAndModel.equals(strManufacturerAndModel))){
				// a Smart Phone is found --> add Smart Phone into found-SmartPhone array
				SmartPhone aFoundSmartPhone = new SmartPhone (aStrSmartPhoneRecord);
				
				foundSmartPhoneArray[numFoundSmartPhone] = aFoundSmartPhone;
				
				// Increment numFoundSmartPhone to indicate one more Smart Phone is found
				numFoundSmartPhone++;
			}			
		
		} // End of for (scan SmartPhone array)

		
		// Write Smart Phone record of each found Smart Phone into the text area (Smart Phone search results)
		for (int j = 0; j < numFoundSmartPhone; j++) {
			
			// Append a Smart Phone record into the search results text area
			textAreaSearchResults.append((foundSmartPhoneArray[j]).toString() + "\n"); 
			
		}

		
		
		return (numFoundSmartPhone);
		
		
	} // End of searchSmartPhoneByManufacturerAndModel
	

	private int searchSmartPhoneByScreen(int numSmartPhone, String strScreen, String[] smartPhoneArray, SmartPhone[] foundSmartPhoneArray) {
		
		SmartPhone aSmartPhone;
		String strDbSmartPhoneScreen = ""; // Screen size of Smart Phone in database
		int numFoundSmartPhone = 0;
		String aStrSmartPhoneRecord = "";
		
		for (int i = 0; i < numSmartPhone; i++) {
			
			aStrSmartPhoneRecord = smartPhoneArray[i];
			aSmartPhone = SmartPhone.recreateSmartPhoneFromString(aStrSmartPhoneRecord);
	
			strDbSmartPhoneScreen = aSmartPhone.getScreen(); // Get Screen size of the Smart Phone in db
			
			if ((strDbSmartPhoneScreen.equals(strScreen))){
				// a Smart Phone is found --> add Smart Phone into found-SmartPhone array
				SmartPhone aFoundSmartPhone = new SmartPhone (aStrSmartPhoneRecord);
				
				foundSmartPhoneArray[numFoundSmartPhone] = aFoundSmartPhone;
				
				// Increment numFoundSmartPhone to indicate one more SmartPhone is found
				numFoundSmartPhone++;
			}			
		
		} // End of for (scan SmartPhone array)
		
		// Write Smart Phone record of each found Smart Phone into the text area (Smart Phone search results)
		for (int j = 0; j < numFoundSmartPhone; j++) {
			
			// Append a Smart Phone record into the search results text area
			textAreaSearchResults.append((foundSmartPhoneArray[j]).toString() + "\n"); 
			
		}

		return (numFoundSmartPhone);
		
		
	} // End of searchSmartPhoneByScreen	


	private int searchSmartPhoneBySize(int numSmartPhone, String strSize, String[] smartPhoneArray, SmartPhone[] foundSmartPhoneArray) {
		
		SmartPhone aSmartPhone;
		String strDbSmartPhoneSize = ""; // Size of Smart Phone in database
		int numFoundSmartPhone = 0;
		String aStrSmartPhoneRecord = "";
		
		for (int i = 0; i < numSmartPhone; i++) {
			
			aStrSmartPhoneRecord = smartPhoneArray[i];
			aSmartPhone = SmartPhone.recreateSmartPhoneFromString(aStrSmartPhoneRecord);
	
			strDbSmartPhoneSize = aSmartPhone.getSize(); // Get Size of the Smart Phone in db
			
			if ((strDbSmartPhoneSize.equals(strSize))){
				// a book is found --> add Smart Phone into found-SmartPhone array
				SmartPhone aFoundSmartPhone = new SmartPhone (aStrSmartPhoneRecord);
				
				foundSmartPhoneArray[numFoundSmartPhone] = aFoundSmartPhone;
				
				// Increment numFoundSmartPhone to indicate one more Smart Phone is found
				numFoundSmartPhone++;
			}			
		
		} // End of for (scan SmartPhone array)

		// Write Smart PHone record of each found Smart Phone into the text area (Smart Phone search results)
		for (int j = 0; j < numFoundSmartPhone; j++) {
			
			// Append a Smart Phone record into the search results text area
			textAreaSearchResults.append((foundSmartPhoneArray[j]).toString() + "\n"); 
			
		}

		return (numFoundSmartPhone);
		
		
	} // End of searchSmartPhoneBySize	


	private int searchSmartPhoneByCpu(int numSmartPhone, String strCpu, String[] smartPhoneArray, SmartPhone[] foundSmartPhoneArray) {
		
		SmartPhone aSmartPhone;
		String strDbSmartPhoneCpu = ""; // CPU of Smart Phone in database
		int numFoundSmartPhone = 0;
		String aStrSmartPhoneRecord = "";
		
		for (int i = 0; i < numSmartPhone; i++) {
			
			aStrSmartPhoneRecord = smartPhoneArray[i];
			aSmartPhone = SmartPhone.recreateSmartPhoneFromString(aStrSmartPhoneRecord);
	
			strDbSmartPhoneCpu = aSmartPhone.getCpu(); // Get CPU of the Smart Phone in db
			
			if ((strDbSmartPhoneCpu.equals(strCpu))){
				// a Smart Phone is found --> add Smart Phone into found-SmartPhone array
				SmartPhone aFoundSmartPhone = new SmartPhone (aStrSmartPhoneRecord);
				
				foundSmartPhoneArray[numFoundSmartPhone] = aFoundSmartPhone;
				
				// Increment numFoundSmartPhone to indicate one more Smart Phone is found
				numFoundSmartPhone++;
			}			
		
		} // End of for (scan SmartPhone array)


		// Write Smart Phone record of each found Smart Phone into the text area (Smart Phone search results)
		for (int j = 0; j < numFoundSmartPhone; j++) {
			
			// Append a Smart Phone record into the search results text area
			textAreaSearchResults.append((foundSmartPhoneArray[j]).toString() + "\n"); 
			
		}

		return (numFoundSmartPhone);
		
		
	} // End of searchSmartPhoneByCpu	


	private int searchSmartPhoneByMemory(int numSmartPhone, String strMemory, String[] smartPhoneArray, SmartPhone[] foundSmartPhoneArray) {
		
		SmartPhone aSmartPhone;
		String strDbSmartPhoneMemory = ""; // Memory of Smart Phone in database
		int numFoundSmartPhone = 0;
		String aStrSmartPhoneRecord = "";
		
		for (int i = 0; i < numSmartPhone; i++) {
			
			aStrSmartPhoneRecord = smartPhoneArray[i];
			aSmartPhone = SmartPhone.recreateSmartPhoneFromString(aStrSmartPhoneRecord);
	
			strDbSmartPhoneMemory = aSmartPhone.getMemory(); // Get Memory of the Smart Phone in db
			
			if ((strDbSmartPhoneMemory.equals(strMemory))){
				// a Smart Phone is found --> add book into found-SmartPhone array
				SmartPhone aFoundSmartPhone = new SmartPhone (aStrSmartPhoneRecord);
				
				foundSmartPhoneArray[numFoundSmartPhone] = aFoundSmartPhone;
				
				// Increment numFoundSmartPhone to indicate one more Smart Phone is found
				numFoundSmartPhone++;
			}			
		
		} // End of for (scan Smart Phone array)

				
		// Write Smart Phone record of each found Smart Phone into the text area (Smart Phone search results)
		for (int j = 0; j < numFoundSmartPhone; j++) {
			
			// Append a Smart Phone record into the search results text area
			textAreaSearchResults.append((foundSmartPhoneArray[j]).toString() + "\n"); 
			
		}

		return (numFoundSmartPhone);
	}
		private int searchSmartPhoneByWireless(int numSmartPhone, String strWireless, String[] smartPhoneArray, SmartPhone[] foundSmartPhoneArray) {
			
			SmartPhone aSmartPhone;
			String strDbSmartPhoneWireless = ""; // Wireless of Smart Phone in database
			int numFoundSmartPhone = 0;
			String aStrSmartPhoneRecord = "";
			
			for (int i = 0; i < numSmartPhone; i++) {
				
				aStrSmartPhoneRecord = smartPhoneArray[i];
				aSmartPhone = SmartPhone.recreateSmartPhoneFromString(aStrSmartPhoneRecord);
		
				strDbSmartPhoneWireless = aSmartPhone.getWireless(); // Get Wireless of the Smart Phone in db
				
				if ((strDbSmartPhoneWireless.equals(strWireless))){
					// a Smart Phone is found --> add Smart Phone into found-SmartPhone array
					SmartPhone aFoundSmartPhone = new SmartPhone (aStrSmartPhoneRecord);
					
					foundSmartPhoneArray[numFoundSmartPhone] = aFoundSmartPhone;
					
					// Increment numFoundSmartPhone to indicate one more Smart phone is found
					numFoundSmartPhone++;
				}			
			
			} // End of for (scan Smart Phone array)

					
			// Write Smart Phone record of each found Smart Phone into the text area (Smart Phone search results)
			for (int j = 0; j < numFoundSmartPhone; j++) {
				
				// Append a Smart Phone record into the search results text area
				textAreaSearchResults.append((foundSmartPhoneArray[j]).toString() + "\n"); 
				
			}

			return (numFoundSmartPhone);
					
	} // End of searchSmartPhoneByWireless	


}//End of Class 
