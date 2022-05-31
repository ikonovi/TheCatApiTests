### REST API Tests Automation Approach 

#### Pre-requisites
- Installed JDK 17
- Installed Maven 3.8 or higher
- Installed Allure commandline 2.17 or higher

#### Running Tests
1. Get API Key value on the web-site <https://thecatapi.com>
2. Download the project zip file and unpack it into a selected directory
3. For Windows, open Command Prompt and go to the root of the project - directory .\TheCatApiTests
4. Run the command `mvn clean test -Dapi_key=<your_API_Key>`

#### Create a Test Report
5. Run the command `allure generate --clean --report-dir ./target/allure-report ./target/allure-results && allure open --host localhost --port 8899 ./target/allure-report`
6. Have a look at the open web browser page with Allure report. 


