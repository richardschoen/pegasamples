//----------------------------------------------------------------------------------------------------
// Activity: PDFProtect1
// Desc: Add a password to an unprotected PDF file
// Activity Parms:
// PdfFileName = Input PDF file
// Password = Password to add to new PDF file
// PageName = Clipboard page name to generate to hold results
// PdfFileNameEncrypted = Output encrypted PDF file
//
// Links:
// https://pdfbox.apache.org/2.0/cookbook/encryption.html
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
String parmPdfFileName=tools.getParamValue("PdfFileName");
String parmPdfFileNameEncrypted=tools.getParamValue("PdfFileNameEncrypted");
String parmOwnerPassword=tools.getParamValue("OwnerPassword");
String parmUserPassword=tools.getParamValue("UserPassword");

// Create new empty data page of type: Code-Pega-List which is good to hold 
// parms or resultset data with one or more records.
traceid = 200;  
tracemessage= tracemessage + Integer.toString(traceid) + "-" + "Creating Results Page" + "\r\n";
lp = tools.createPage("Code-Pega-List", parmResultPageName);

// Let's process the CSV file now
try {  

       String documentPath=parmPdfFileName;
       String documentPathEncrypted=parmPdfFileNameEncrypted;
       String ownerpassword=parmOwnerPassword;
       String userpassword=parmUserPassword;
    
       traceid = 300;  
       tracemessage= tracemessage + Integer.toString(traceid) + "-" + "Starting encryption process for file " + documentPath + "\r\n";

       // Check for input file
       java.io.File fin = new java.io.File(documentPath);
       if(!fin.exists()) { 
         throw new Exception("Input PDF file " + documentPath + " does not exist.");
       }  

       // Check for output file. Remove if found
       java.io.File fout = new java.io.File(documentPathEncrypted);
       if(fout.exists()) { 
         fout.delete();
       }  
  
       // Load existing PDF file
       java.io.File file1 = new java.io.File(documentPath);
       org.apache.pdfbox.pdmodel.PDDocument doc = org.apache.pdfbox.pdmodel.PDDocument.load(file1);

       // Define the length of the encryption key.
       // Possible values are 40 or 128 (256 will be available in PDFBox 2.0).
       // 256 does not szeem to work in Page instance 8.7
       int keyLength = 128;

       // Load access permission 
       traceid = 400;  
       tracemessage= tracemessage + Integer.toString(traceid) + "-" + "Create new PDF access permissions" + "\r\n";
       org.apache.pdfbox.pdmodel.encryption.AccessPermission ap = new org.apache.pdfbox.pdmodel.encryption.AccessPermission();

       // Disable printing, everything else is allowed
       // Note: Only works when separate Owner and User passwords are set.
       ap.setCanPrint(false);
       //ap.setCanExtractContent(false);
  	   
       //setCanAssembleDocument(boolean allowAssembly)
       //Set if the user can insert/rotate/delete pages.
       //void	setCanExtractContent(boolean allowExtraction)
       //Set if the user can extract content from the document.
       //void	setCanExtractForAccessibility(boolean allowExtraction)
       //Set if the user can extract content from the document for accessibility purposes.
       //void	setCanFillInForm(boolean allowFillingInForm)
       //Set if the user can fill in interactive forms.
       //void	setCanModify(boolean allowModifications)
       //Set if the user can modify the document.
       //void	setCanModifyAnnotations(boolean allowAnnotationModification)
       //Set if the user can modify annotations.
       //void	setCanPrint(boolean allowPrinting)
       //Set if the user can print.
       //void	setCanPrintDegraded(boolean allowAssembly)
       //Set if the user can print the document in a degraded format.
       //void	setReadOnly()
 
       // https://www.windowspasswordsrecovery.com/articles/pdf/comparison-between-pdf-owner-password-and-user-password.html
       // Owner password (to open the file with all permissions) is "12345"
       // User password (to open the file but with restricted permissions, is empty here)
       // If just User password is set, a password is required to open file, but no features are secure. 
       // Note: LImiting feature access only works when separate Owner and User passwords are set.
       traceid = 500;  
       tracemessage= tracemessage + Integer.toString(traceid) + "-" + "Encrypting PDF data" + "\r\n";
       org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy spp = new org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy(ownerpassword,userpassword, ap);
       spp.setEncryptionKeyLength(keyLength);

       // Apply PDF protection
       traceid = 600;  
       tracemessage= tracemessage + Integer.toString(traceid) + "-" + "Saving encrypted PDF to " + documentPathEncrypted + "\r\n";
       doc.protect(spp);
       doc.save(documentPathEncrypted);
       doc.close();
  
       tracemessage= tracemessage + Integer.toString(traceid) + "-"+ "Done Encrypting PDF doc " + documentPath + " to " + documentPathEncrypted + "\r\n";

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
