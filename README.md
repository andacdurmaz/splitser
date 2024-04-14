# OOPP Template Project

This repository contains the template for the OOPP project. Please extend this README.md with instructions on how to run your project.

How to run the project
1) ./gradlew build
2) ./gradlew bootRun
3) run client/main (or use ./gradlew run)

The admin login can be seen in the output of the server when you run it.

Useful shortcuts in the project
--In the start up page of the application
1) Ctrl + E for creating an event
   --In the event info page
2) Ctrl + T for adding an expense tag to the event
3) Ctrl + P for adding a new participant to the event
4) Ctrl + E for adding a new expense to the event

We implemented the following HCI requirements:
1) Color Contrast
2) keyboard shortcuts as mentioned above
3) multi-modal visualization (for example the trashcan with a red delete button)
4) logical navigation
5) keyboard navigation
6) error messages
7) informative feedback
8) confirmation for key actions

to see the total sum of expenses, you need to press the statistics button in the event info.

Websockets and Long Polling:
Long Polling is used for the adding and deleting of expenses, this can mainly be seen in the file : client/src/main/java/client/utils/ServerUtils.java and server\src\main\java\server\api\ExpenseController.java\
Websockets are used for everything else and can mainly be found in the file : server/src/main/java/server/WebSocketConfig.java as well as in the server controllers and client side fxml controllers.
