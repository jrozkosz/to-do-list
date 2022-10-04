package com.example.todolist;

import com.example.todolist.models.Task;
import com.example.todolist.models.ToDoAndDoneModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class ToDoMenuController {
    @FXML
    private ListView<Task> toDoListView;
    @FXML
    private ListView<Task> doneListView;
    private ToDoAndDoneModel dataModel;

    public void displayAllTasks(ToDoAndDoneModel dataModel){
        this.dataModel = dataModel;
        toDoListView.setItems(dataModel.getToDoList());
        doneListView.setItems(dataModel.getDoneList());
        initializeListeners();
    }

    private void initializeListeners() {
        toDoListView.setOnDragDetected(event -> {
            System.out.println("setOnDragDetected");
            Dragboard dragBoard = toDoListView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(toDoListView.getSelectionModel().getSelectedItem().toString());
            dragBoard.setContent(content);
        });

        AtomicBoolean isOverDoneList = new AtomicBoolean(false);

        toDoListView.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                System.out.println("setOnDragDone");
            }
        });

        doneListView.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                System.out.println("setOnDragEntered");
                doneListView.setBlendMode(BlendMode.DIFFERENCE);
            }
        });

        doneListView.setOnDragExited(dragEvent -> {
            System.out.println("setOnDragExited");
            isOverDoneList.set(false);
            doneListView.setBlendMode(null);
        });

        doneListView.setOnDragOver(dragEvent -> {
            System.out.println("setOnDragOver");
            isOverDoneList.set(true);
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        });

        doneListView.setOnDragDropped(dragEvent -> {
            System.out.println("setOnDragDropped");
            String stringTask = dragEvent.getDragboard().getString();
            String[] descAndDateString = stringTask.split(" -> ");
            doneListView.getItems().addAll(new Task(descAndDateString[0],
                    Date.valueOf(descAndDateString[1]).toLocalDate()));
            if(isOverDoneList.get()){
                for(Task task : dataModel.getToDoList()){
                    if(task.getDescription().equals(descAndDateString[0]) &&
                            task.getDeadline().toString().equals(descAndDateString[1])){
                        dataModel.getToDoList().remove(task);
                        break;
                    }
                }
            }
            dragEvent.setDropCompleted(true);
        });
    }

    @FXML
    public void onPlusButtonClick(ActionEvent event) throws IOException {
        // """ opens addingToDo Scene """ //
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addingToDo.fxml"));
        Parent root = loader.load();
        com.example.todolist.AddingToDoController addingController = loader.getController();
        addingController.setModel(dataModel);
        Stage newStage = new Stage();
        newStage.setTitle("Adding to-do task");
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}
