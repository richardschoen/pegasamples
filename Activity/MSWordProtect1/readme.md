# Activity/MSWordProtect1
This activity can be called to password protect a MS Word document in docx format.

## Instructions for setting up activity MSWordProtect1

## Creating the Activity
Create a new activity and add the following parameters:

**WordFileName** = Word docx file name to protect (String)

**Password** = Password to add to Word file (String)

**PageName** = Clipboard page name to generate to hold results (String)

## Add activity steps

**Step 1  - Page-Remove for Param.Pagename**

This removes the Clipboard work page if it exists

**Step 2 - Add the MSWordprotect1.java file to a new Java step**

This code does all the work to open the Word document, set the password and re-save the Word document.

## Files

This is a list of files for the activity definition and testing.

**MSWordProtect1-edit-1.png** - Configure activity steps.

**MSWordProtect1-edit-2.png** - Configure activity parms.

**MSWordProtect1-edit-3.png** - Configure activity type.

**MSWordProtect1-run.png** - Running the activity to test.

**MSWordProtect1-statuspage.png** - Status page after running activity. Should always return true. Results will be in clipboard page. 

**MSWordProtect1-viewclipboard.png** - Clipboard view after successful conversion.

**MSWordProtect1.java** - Java code for activity step 2.

**WordDocIn.docx** - Sample Word docx document file. 

## Notes

You will need to make sure you have read/write access to the file system from within your Pega instance so you can modify the Word document that you create from Pega or upload for password protection. 



