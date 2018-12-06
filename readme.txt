To run the Test Case:
1. Please install Ecplise with testNG
2. Download QuoineDemo Folder
3. Download QuoineData Folder
4. Please open the ExternalFileConfiguration java class in //QuoineDemo/Src/CoreFramework, then edit the location of the defaultDirectory to your current QuoineData Folder (Default is set to D:/QuoineData, if you copy QuineData to your D Drive thens kip this step)
5. You can execute by 2 method: Run the testng.xml file as an TestNGSuite or you can run the TestExecute.java as an Java Application with parrameter set to 'no' (I will Explain later).



Desciption:

When I did this excercise, I have a larger framework in mind. But I dont have enough time. I was planning to build the project as an demo for external Test Plan and Test Result will update on the Test Plan rather than using testNG report. The purpose is to take the Excel file as an external Test Management System such as TestRails (etc..) and we will replace the Excel read files to the Test Management API calls.