# to-do-list
The app allows the user to manage the tasks that they need to do.
Features:
*adding tasks with their deadlines to the 'to-do list'
*dragging the tasks that that have been accomplished to the 'done list'
*automatic sorting of tasks by their deadlines
*deleting tasks from the lists
*opening calendar where are registered all the tasks from 'to-do list'

https://youtu.be/Qm1GJPpOkE0 - demonstration of the app in action

to run the app on linux you need to have JavaFX SDK downloaded (19 the best), then
download the repository and type in terminal:
java --module-path /full/path/to/your/javafx-sdk-19/lib --add-modules javafx.controls,javafx.fxml -jar 
/full/path/to/ToDoList/out/artifacts/ToDoList_jar/ToDoList.jar
