# OpenCart API Automation Test

This repository contains the source code for the OpenCart API Automation using Java and Rest Assured. Please check the README.md for installation and configuration instructions.

## Prerequisites

Before you begin, please ensure that you have the following installed:

- [**JDK**](https://www.oracle.com/java/technologies/downloads/#java17). To check your Java version, open cmd and run command: `java -version`
- [**Apache Maven**](https://maven.apache.org/install.html). To check Maven version, open cmd and run command: `mvn -v`
- [**XAMPP with MySQL Database**](https://www.apachefriends.org/download.html)
- [**OpenCart application**](https://www.opencart.com/?route=cms/download) installed locally and fully set up

Make sure that after installing, OpenCart website can be accessed from local host via: http://localhost/opencart/upload/

Access the Admin Dashboard to set up the API with _**username: admin**_ and _**password: admin**_ via: http://localhost/opencart/upload/admin

## API Configuration

On the Navigation bar, click on System > User > API

![img1](https://github.com/nguyenthanhthao2111/opencart-api-automation-test/assets/92444135/5373d5c7-dabd-4996-8454-7a34d4038ffd)

In the API list page, click on the Add button to add a new API

![img2](https://github.com/nguyenthanhthao2111/opencart-api-automation-test/assets/92444135/cf25d310-fc93-4669-9399-81c62f0a16f2)

Before adding a new API, open cmd and run command: ipconfig to check the IPv4 Address

![img3](https://github.com/nguyenthanhthao2111/opencart-api-automation-test/assets/92444135/e802dd28-db48-46d9-944a-33c41e6d1683)

Add a new API with information as below. Note that each time the IP Address has changed, it must be included into the API in order to call this API successfully

API Username | API Key | Status | IP Address
--- | --- | --- | --- 
opencart_user | snSMak2KBhLfZFZDSetQhdo4jkDIxQ0LFAiw1OqqCYOvppXAJc2pBdn4KiLcKOtw3PYJoefNa54ZvdkNEzqxbONpeHRvJMZpttH3AyEuodWpx7SjGAYKu1i8AqBEbn6b37daLIL0ukjfKl2dnqpxvTActnl05cR2OEtjZeOKbGNiNgVA1 xSn2iHddt2kLPrPnkvRMtiTEQL8HHpSRuiRS1LYpwRNrWEWXagvq4Et3WeyYcAtrp tbhfNyppdhzREB | Enable | {{Your IPv4 Address}} 

## API Automation Configuration

Firstly, turn on the XAMPP on your computer. Open the OpenCart API Automation source code and check the **Constants.java** class. Add your new API _username_, _apikey_, and _IP Address_ as mentioned above

![img4](https://github.com/nguyenthanhthao2111/opencart-api-automation-test/assets/92444135/a3016e83-6650-4e41-b2e5-584ff51b1865)

Then, check the **Routes.java** class and modify the IP Address in _base_url_ variable as the IP Address in this current session. Run `ipconfig` in cmd to check IP Address again

![img5](https://github.com/nguyenthanhthao2111/opencart-api-automation-test/assets/92444135/831647e1-b2eb-444d-8c03-10578a34010b)

Besides, it is required to install and check whether those following dependencies are in your maven project or not

![img6](https://github.com/nguyenthanhthao2111/opencart-api-automation-test/assets/92444135/4638843f-86c5-4f3c-9724-01dd106fd147)

## API Automation Execution

There are 14 APIs in total, which are distributed into 14 packages ranging from OC001 to OC014. Each package contains several classes of test cases for this certain API. After strictly following the previous configuration, to execute API automation script of OpenCart case study, there are some ways to carry out

![img7](https://github.com/nguyenthanhthao2111/opencart-api-automation-test/assets/92444135/8994c2f3-ecb8-4070-80fd-4313c8d839ab)

To run a single test case of an API, select a class of OC001 to OC014 packages. For example, select **LoginUserTest.java** class of API OC001. Execute the first test case TC00101 Login with opencart user successfully by clicking on _Run ‘loginUserSuccessfully()’_

![img8](https://github.com/nguyenthanhthao2111/opencart-api-automation-test/assets/92444135/104fb2f7-b5f9-41d1-b796-d4d5fe66a08d)

To run a class which contains a list of test cases, for example, select **AddPaymentAddressTest.java** of API OC008 and execute it by clicking on _‘Run AddPaymentAddressTest’_

![img9](https://github.com/nguyenthanhthao2111/opencart-api-automation-test/assets/92444135/4ca66685-4162-4e97-9f39-bc048e5058fd)

To run regression testing, check the **testing.xml** file. In here, there is a list of classes which include test cases for APIs. Add classes that you intend to execute in <classes> tag and right click to choose _‘Run testing.xml’_

![img10](https://github.com/nguyenthanhthao2111/opencart-api-automation-test/assets/92444135/9d3fb9ab-3186-49f8-bc3a-53da29b56ac8)

## Related Document
- [OpenCart Manual Test Scenarios and Test Cases](https://docs.google.com/spreadsheets/d/1NhXVUkACwgQklnJYUHEzTCEmXYq0Ylno/edit?usp=sharing&ouid=104719892845518283158&rtpof=true&sd=true)

- [OpenCart API Postman collection](https://drive.google.com/file/d/1nPaFgJ8cGmTwlU3HiLPPRoFTgSWq_vPy/view?usp=sharing)

--- _**THE END**_ ---
