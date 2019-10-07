# TEST PLANS
## Introduction
#### Purpose
This test plan describes the testing plan and overall framework that will drive the testing of the Carbon Android app. 
#### Project Overview
The Carbon app lets you control your finances with a few clicks. You can get
instant short-term loans for urgent needs, check your credit reports, invest
money to earn high-interest rates, recharge airtime on your mobile phone, and
make bill payments for services.
#### Scope of Testing
* Exploratory test of the application to ensure the application is stable for testing.
* Functional testing will be performed to check the functions of application.
* Performance testing is not considered in this plan.

## Test Scenario

#### **Sign In** 

##### Test Cases
* Able to access the login view
* When login credential are not entered
* Login with wrong credential
* Login with correct credential

#### **Airtime Recharge**

##### Test Cases
* User should enter amount to recharge
* Scroll down action to view more detail
* Insert the phone number to recharge
* Select the preferred network provider
* Save Payment and Select Wallet as payment option
* Edit airtime price before submission
* Verify payment with account pin
* Transaction alert is given
* confirm the wallet balance for a debit


#### **Fund Wallet**

##### Test Cases
* choose card option
* select a card
* Verify funding by account pin
* Success message is shown
* funding is reflected in the wallet balance.

#### **Wallet Transaction Filter**

##### Test Cases
* Select Fund option from dropdown list
* Click Filter menu
* Select from and end dates.
* View the last transaction from list.

### Automated Environment Setup
Maven is used as a project management tool for projects build, dependency and documentation.

 To download libraries; clean and rebuild project.

Mobile capabilities variables can be found in 'src/test/java/DeviceSpec' path and values can be 
edited to work for other android phone.

You can get the device uuid by running in the command: 
`adb devices`

You can compile and run test from command line: `mvn clean test`

Note: To run mvn CLI, download maven from https://maven.apache.org/download.cgi and add binary path to PATH.

Prerequisites:
Java JDK, Appium, Android SDK, intelliJ.

#### Test Report
Extent Report Framework has been used to generate beautiful, interactive and detailed reports for our tests.

You can view the output report by running this command `open report.html`