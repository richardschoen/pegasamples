//----------------------------------------------------------------------------------------------------
// Activity: MSWordProtect1
// Desc: Add a password to an unprotected MS Word document. (docx format only)
// Activity Parms:
// WordFileName = Word docx file name to protect
// Password = Password to add to Word file
// PageName = Clipboard page name to generate to hold results
//
// Links:
// https://www.tabnine.com/code/java/methods/org.apache.poi.poifs.crypt.Encryptor/confirmPassword
// https://stackoverflow.com/questions/39570189/add-word-doc-file-with-password-apache-poi
// https://stackoverflow.com/questions/51875706/open-docx-with-apache-poi-and-save-it-with-password
// https://collaborate.pega.com/question/how-convert-pagelist-property-java-list-object
// https://collaborate.pega.com/question/creating-page-list-property-using-java
//----------------------------------------------------------------------------------------------------

ClipboardPage lp = null;
int traceid=100;
String tracemessage="";
String activityName="";

// Get activity incoming parameters
traceid = 100;  
String parmResultPageName=tools.getParamValue("PageName");;
String parmWordFileName=tools.getParamValue("WordFileName");
String parmPassword=tools.getParamValue("Password");

// Create new empty data page of type: Code-Pega-List which is good to hold 
// parms or resultset data with one or more records.
traceid = 200;  
tracemessage= tracemessage + Integer.toString(traceid) + "-" + "Creating Results Page" + "\r\n";
lp = tools.createPage("Code-Pega-List", parmResultPageName);

// Let's process the CSV file now
try {  

      String documentPath=parmWordFileName;
      String password=parmPassword;
  
      traceid = 300;  
      tracemessage= tracemessage + Integer.toString(traceid) + "-" + "Starting encryption process" + "\r\n";
      org.apache.poi.poifs.filesystem.POIFSFileSystem fs = new org.apache.poi.poifs.filesystem.POIFSFileSystem();

      traceid = 400;  
      tracemessage= tracemessage + Integer.toString(traceid) + "-" + "Confirming password" + "\r\n";
      // Use standard encryption. agile does not allow re-saved files to open.
      org.apache.poi.poifs.crypt.EncryptionInfo info = new org.apache.poi.poifs.crypt.EncryptionInfo(org.apache.poi.poifs.crypt.EncryptionMode.standard); 
      org.apache.poi.poifs.crypt.Encryptor enc = info.getEncryptor();
      enc.confirmPassword(password);

      traceid = 500;  
      tracemessage= tracemessage + Integer.toString(traceid) + "-" + "Opening document " + documentPath + "\r\n";
      org.apache.poi.openxml4j.opc.OPCPackage opc = org.apache.poi.openxml4j.opc.OPCPackage.open(new java.io.File(documentPath), org.apache.poi.openxml4j.opc.PackageAccess.READ_WRITE);

       traceid = 600;
       tracemessage= tracemessage + Integer.toString(traceid) + "-" + "Encrypting document info" + "\r\n";
       java.io.OutputStream os = enc.getDataStream(fs); //perform encryption 
       opc.save(os); //save package
       opc.close();
       //https://stackoverflow.com/questions/67098429/apache-poi-docs-encryption-by-adding-a-password
       os.close(); // Make sure to run this to avoid errors when opeing document
  
       traceid = 700;
       java.io.FileOutputStream fos = new java.io.FileOutputStream(documentPath); 
       fs.writeFilesystem(fos); //write the file back to file system
       fos.close();
 
       tracemessage= tracemessage + Integer.toString(traceid) + "-"+ "Done Encrypting Word Doc " + documentPath + "\r\n";

      // Output individual properties to clipboard page 
      lp.putString("Exception", tracemessage);  
      lp.putString("StackTrace","");  
      lp.putString("Success", "true");    
                 
} catch(Exception e) {  
    System.out.println(e);
    //traceid=999999;
    tracemessage = tracemessage + Integer.toString(traceid) + "-" + "Exception:" + e.getMessage() + "\r\n";	 
    // Output individual properties to clipboard page 
    lp.putString("Exception", tracemessage);  
    lp.putString("StackTrace", e.getStackTrace().toString());  
    lp.putString("Success", "false");    
}
