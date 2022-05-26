# Activity/PdfBoxProtect1
This activity can be called to password protect a PDF document using PDFBox java API included in Pega 8.x instances. 

## Instructions for setting up activity PdfBoxProtect1

## Creating the Activity
Create a new activity and add the following parameters:

**PdfFileName** = PDF file that will get encrypted. Original PDF file.

**PdfFileNameEncrypted** = PDF file that will get created after encryption is complete. Output PDF file.

**OwnerPassword** = Owner password (Only set if restricting certain user functionalitities.)

**UserPassword** = User password to open file in Acrobat or other PDF reader.

**PageName** = Clipboard page name to generate to hold results (String)

## Add activity steps

**Step 1  - Page-Remove for Param.Pagename**

This removes the Clipboard work page if it exists

**Step 2 - Add the PdfBoxProtect1.java file to a new Java step**

This code does all the work to open the PDF document, set the password and re-save the PDF document.

## Files

This is a list of files for the activity definition and testing.

**PdfBoxProtect1-edit-1.png** - Configure activity parms.

**PdfBoxProtect1-edit-2.png** - Configure activity steps.

**PdfBoxProtect1-edit-3.png** - Configure activity java step by pasting java code.

**PdfBoxProtect1.java** - Java code for activity step 3.

## Notes

You will need to make sure you have read/write access to the file system from within your Pega instance so you can modify the PDF document that you create from Pega or upload for password protection. 
