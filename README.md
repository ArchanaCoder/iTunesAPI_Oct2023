iTuneAPIAuotmation Project
--------------------------

Steps to perform automation test execution:

1. Download/Clone iTuneAPIAuotmation Project from GIT to local box

2. Make sure all the dependiecies are downloaded/available to run the project. 
   Note: Maven should download all the dependencies mentioned in POM.xml. We need to download in case if Maven failed to do so.  

3. Open CLI (command line interface), Go to the directory where POM.xml resides.   
   Run the command "mvn test"

Reports: 
Detailed Report ->'cucumber-reports.html' will be generated in the target folder
