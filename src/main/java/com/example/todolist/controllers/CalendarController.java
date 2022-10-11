package com.example.todolist;

import com.example.todolist.ToDoMenuController;
import com.example.todolist.models.Task;
import com.example.todolist.models.ToDoAndDoneModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class CalendarController {
    @FXML
    private GridPane calendarTable;
    @FXML
    private Label monthLabel;
    @FXML
    private ImageView leftButton;
    @FXML
    private ImageView rightButton;
    @FXML
    private ImageView goBackButton;
    private final ArrayList<Label> currentMonthNumbers = new ArrayList<>();
    private final ArrayList<Label> currentToDoTasks = new ArrayList<>();
    private ToDoAndDoneModel dataModel;

    public void showCalendarData(int monthNumber, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(java.sql.Date.valueOf((LocalDate.parse(String.valueOf(year)+ "-" +
                String.format("%02d" , monthNumber) +"-01"))));
        int firstDayOfMonth = (cal.get(Calendar.DAY_OF_WEEK)+5)%7;
        calendarTable.getChildren().removeAll(currentMonthNumbers);
        currentMonthNumbers.clear();
        calendarTable.getChildren().removeAll(currentToDoTasks);
        currentToDoTasks.clear();
        int i = 1;
        for(int row = 1; row < 7 && i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); row++){
            for(int col = 0; col < 7 && i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); col++){
                if(row == 1 && col < firstDayOfMonth) { continue; }
                for(Task task : dataModel.getToDoList()){
                    if( task.getDeadline().equals(LocalDate.parse(String.valueOf(year)+ "-" +
                            String.format("%02d" , monthNumber) + "-" + String.format("%02d" , i))) ){
                        Label toDoTask = new Label(task.getDescription());
                        currentToDoTasks.add(toDoTask);
                        toDoTask.setWrapText(true);
                        toDoTask.setTextAlignment(TextAlignment.CENTER);
                        toDoTask.setStyle("-fx-text-fill: navy");
                        GridPane.setHalignment(toDoTask, HPos.CENTER);
                        GridPane.setValignment(toDoTask, VPos.CENTER);
                        calendarTable.add(toDoTask, col, row);
                        break;
                    }
                }
                Label numOfDay = new Label(String.valueOf(i));
                currentMonthNumbers.add(numOfDay);
                GridPane.setMargin(numOfDay, new Insets(5, 0, 0, 5));
                GridPane.setHalignment(numOfDay, HPos.LEFT);
                GridPane.setValignment(numOfDay, VPos.TOP);
                calendarTable.add(numOfDay, col, row);
                i++;
            }
        }
    }

    public void displayToDoTasks(ToDoAndDoneModel dataModel){
        this.dataModel = dataModel;
        Calendar cal = Calendar.getInstance();
        monthLabel.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) +
                " " + cal.get(Calendar.YEAR));
        showCalendarData(cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR));
    }

    private int convertMonthNameToNumber(String monthName) throws ParseException {
        Date date = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(monthName);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    @FXML
    public void onLeftButtonClick() throws ParseException {
        String[] monthAndYear = monthLabel.getText().split(" ");
        int currentMonthNumber = convertMonthNameToNumber(monthAndYear[0]);
        int currentYear = Integer.parseInt(monthAndYear[1]);
        if( currentMonthNumber == 1){
            currentYear--;
            currentMonthNumber = 12;
        } else {
            currentMonthNumber -= 1;
        }
        monthLabel.setText(new DateFormatSymbols().getMonths()[currentMonthNumber-1] +
                " " + String.valueOf(currentYear));
        showCalendarData(currentMonthNumber, currentYear);
    }

    @FXML
    public void onRightButtonClick() throws ParseException {
        String[] monthAndYear = monthLabel.getText().split(" ");
        int currentMonthNumber = convertMonthNameToNumber(monthAndYear[0]);
        int currentYear = Integer.parseInt(monthAndYear[1]);
        if( currentMonthNumber == 12 ){
            currentYear++;
            currentMonthNumber = 1;
        } else {
            currentMonthNumber += 1;
        }
        monthLabel.setText(new DateFormatSymbols().getMonths()[currentMonthNumber-1] +
                " " + String.valueOf(currentYear));
        showCalendarData(currentMonthNumber, currentYear);
    }

    @FXML
    public void onGoBackButtonClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = loader.load();
        ToDoMenuController controller = loader.getController();
        controller.displayAllTasks(dataModel);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Sign In");
        stage.show();
    }
}
