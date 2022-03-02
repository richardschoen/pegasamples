# Instructions for setting up activity MSWordProtect1

## Creating the Activity
Create a new activity and add the following parameters
```
WordFileName = Word docx file name to protect
Password = Password to add to Word file
PageName = Clipboard page name to generate to hold results
```

## Add steps

**Step 1  - Page-Remove for Param.Pagename**

This removes the Clipboard work page if it exists

**Step 2 - Add teh MSWordprotect1.java file to a new Java step**

This code does all the work to open the Word document, set the password and re-save the Word document.




