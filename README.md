# BudgetAppGUI-JFrame
Final year project for CS course

Instructions on how to set up
NetBeans 8.2 or 8.1 preferable

Glassfish server needs to be installed(netbeans automatically installs this unless you uncheck it)

After opening NetBeans go to the Services tab and click Databases
Then right click Java DB and create a database named BudgetAPPDB
Username: appuser
Password: appuser

Now you should see under databases jdbc:derby://localhost:1527/BudgetAppDB [appuser on APPUSER]
Right click that and press connect

After connecting, Right click Tables under APPUSER Schema and click Execute Command

In the command window run these commands: (Ctrl + Shift + E to run commands)

CREATE TABLE TRANSACTIONS (
	TransactionID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
	DATETIME DATE,
	USERNAME VARCHAR(255),
	TRANSACTIONNAME VARCHAR(255),
	TYPE VARCHAR(255),
	AMOUNT DOUBLE,
        CATEGORY VARCHAR (255),
        MONTH VARCHAR (255),
        YEARS INTEGER,
	PRIMARY KEY (TransactionID)
);

--------------
CREATE TABLE USERS (
	USERNAME VARCHAR(255),
	PASSWORD VARCHAR(255),
	USERTYPE VARCHAR(255),
	PRIMARY KEY (USERNAME)
);

Now you may run the program and register as a user, then login and use the app
