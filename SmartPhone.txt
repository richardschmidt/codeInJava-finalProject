/**
 * 
 */
package common_smphonedb;

/**
 * @author richa_000
 *
 */
public class SmartPhone {
	//DECLARE ATTRIBUTES
private long serialNumber;	//(an integer of 10 digits)
private String manufacturerAndModel;	//(e.g.: Apple iPhone 4s, Samsung Galaxy S3, etc.)
private String screen;	//(e.g.: 4.65" HD (1280 x 720) Super AMOLED)
private String size;	//(e.g.: 135.5 x 67.94 x 8.97 mm)
private String cpu;		//(e.g.: Quad-core Tegra 3 processor)
private String memory;	//(e.g.: 1 GB RAM)
private String wireless;//(e.g.: 4G/Wifibgn/Bluetooth OR 3G/4G/Wifibgn OR Wifibgn/Bluetooth, etc.)

//DECLARE CONSTRUCTORS
public SmartPhone() {
	
	serialNumber = 0;
	manufacturerAndModel = "";
	screen = "";
	size = "";
	cpu = "";
	memory = "";
	wireless = "";
}//END OF DEFAULT CONSTRUCTOR

//ANOTHER CONSTRUCTOR
public SmartPhone (long aSerialNumber, String aManufacturerAndModel) {
	serialNumber=aSerialNumber;
	manufacturerAndModel=aManufacturerAndModel;
	screen = "";
	size = "";
	cpu="";
	memory="";
	wireless="";
}//END OF ANOTHER CONSTRUCTOR

//Another constructor
//This constructor recreates a book object out of a string of book info
//The string aStrSmphoneData is of CSV format - has been verified: NOT NULL and NOT EMPTY STRING
public SmartPhone (String aStrSmartPhoneData) {
	SmartPhone aSmartPhone = recreateSmartPhoneFromString (aStrSmartPhoneData);
	
	serialNumber = aSmartPhone.getSerialNumber();
	manufacturerAndModel = aSmartPhone.getManufacturerAndModel();
	screen = aSmartPhone.getScreen();
	size = aSmartPhone.getSize();
	cpu = aSmartPhone.getCpu();
	memory = aSmartPhone.getMemory();
	wireless = aSmartPhone.getWireless();
}//END OF ANOTHER CONSTRUCTOR
//GET AND SET METHODS
public long getSerialNumber(){
	return(serialNumber);
}
public void setSerialNumber(long aSerialNumber){
	serialNumber = aSerialNumber;
}
public String getManufacturerAndModel(){
	return(manufacturerAndModel);
}
public void setManufacturerAndModel(String aManufacturerAndModel){
	manufacturerAndModel = aManufacturerAndModel;
}
public String getScreen(){
	return(screen);
}
public void setScreen(String aScreen){
	screen = aScreen;
}
public String getSize(){
	return(size);
}
public void setSize(String aSize){
	size = aSize;
}
public String getCpu(){
	return(cpu);
}
public void setCpu(String aCpu){
	cpu = aCpu;
}
public String getMemory(){
	return(memory);
}
public void setMemory(String aMemory){
	memory = aMemory;
}
public String getWireless(){
	return(wireless);
}
public void setWireless(String aWireless){
	wireless = aWireless;
}
//OTHER METHODS
//This method returns a string that stores all data fields of a Smart Phone Record
//The fields are separated by a comma ','
//The string can be used to insert a book record (a line) into a .CSV file database
//This method overrides the same method of class Object from which all Java classes inherit @Override
@Override
public String toString(){
	
	String SmartPhoneStr = Long.toString(serialNumber)
							+ "," + manufacturerAndModel
							+ "," + screen
							+ "," + size
							+ "," + cpu
							+ "," + memory
							+ "," + wireless;
	return (SmartPhoneStr);
}//END OF toSTRING
//This method recreates a SmartPhone object out of a string of CSV format.
//This string contains all data fields of the Smart Phone
//After recreating the SmartPhone, the method returns the object

public static SmartPhone recreateSmartPhoneFromString (String aStrSmartPhoneData) {
	
	SmartPhone aSmartPhone = new SmartPhone();
	
	if (aStrSmartPhoneData.isEmpty()){
		return (null);
	}
	
	int preCommaIndex = 0; //index of the preceding comma in the CSV string
	int nextCommaIndex = 0; //index of the next comma in the CSV string
	String nextSubstring = "";
	
	//Retrieve the Serial Number
	//Get the index of the 1st comma that separates serial number and manufacturer and model
	nextCommaIndex = aStrSmartPhoneData.indexOf(',');
	
	//Get the substring of the Serial Number
	//Be Careful!!!: substring (int startIndex, int endIndex) - see manual of this method
	//Actually, the substring starts at startIndex and extends to (endIndex - 1) - Not endIndex
	//So, endIndex must be nextCommaIndex, NOT nextCommaIndex - 1
	nextSubstring = aStrSmartPhoneData.substring(0, nextCommaIndex);
	
	//Convert this string to long'
	long aSerialNumber = Long.parseLong(nextSubstring);
	
	//Set Serial Number
	aSmartPhone.setSerialNumber(aSerialNumber);
	
	//Retrieve the manufacturer and model
	//Get the index of the 1st comma that separates serial number and manufacturer and model
	preCommaIndex = nextCommaIndex;
	nextCommaIndex = aStrSmartPhoneData.indexOf(',', preCommaIndex + 1);
	
	//Get the substring of manufacturer and model
	nextSubstring = aStrSmartPhoneData.substring(preCommaIndex + 1, nextCommaIndex);
	
	//Set manufacturer and model
	aSmartPhone.setManufacturerAndModel(nextSubstring);
	
	//Retrieve the screen
	//Get the index of the 1st comma that separates serial number and manufacturer and model
	preCommaIndex = nextCommaIndex;
	nextCommaIndex = aStrSmartPhoneData.indexOf(',', preCommaIndex + 1);
	//Get the substring of screen
	nextSubstring = aStrSmartPhoneData.substring(preCommaIndex + 1, nextCommaIndex);
	//Set screen
	aSmartPhone.setScreen(nextSubstring);
	
	//Retrieve the size
	//Get the index of the 1st comma that separates serial number and manufacturer and model
	preCommaIndex = nextCommaIndex;
	nextCommaIndex = aStrSmartPhoneData.indexOf(',', preCommaIndex + 1);
	//Get the substring of size
	nextSubstring = aStrSmartPhoneData.substring(preCommaIndex + 1, nextCommaIndex);
	//Set size
	aSmartPhone.setSize(nextSubstring);
	
	//Retrieve the cpu
	//Get the index of the 1st comma that separates serial number and manufacturer and model
	preCommaIndex = nextCommaIndex;
	nextCommaIndex = aStrSmartPhoneData.indexOf(',', preCommaIndex +1);
	//Get the substring of cpu
	nextSubstring = aStrSmartPhoneData.substring(preCommaIndex + 1, nextCommaIndex);
	//Set cpu
	aSmartPhone.setCpu(nextSubstring);
	
	//Retrieve the memory
	//Get the index of the 1st comma that separates serial number and manufacturer and model
	preCommaIndex = nextCommaIndex;
	nextCommaIndex = aStrSmartPhoneData.indexOf(',', preCommaIndex + 1);
	//Get the substring of memory
	nextSubstring = aStrSmartPhoneData.substring(preCommaIndex + 1, nextCommaIndex);
	//set memory
	aSmartPhone.setMemory(nextSubstring);
	
	//Retrieve the wireless
	//Get the index of the 1st comma that separates serial number and manufacturer and model
	preCommaIndex = nextCommaIndex;
	nextSubstring = aStrSmartPhoneData.substring(preCommaIndex + 1);
	
	//Set wireless
	aSmartPhone.setWireless(nextSubstring);
	
	return (aSmartPhone);
}
public void displaySmartPhoneInfo(){
	System.out.println(toString());
}//End of displaySmartPhoneInfo
}//End of class SmartPhone
