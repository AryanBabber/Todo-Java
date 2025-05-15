package com.example.todolist;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class HelloController {
    @FXML
    private TextField taskInput;
    @FXML
    private VBox taskList;

    @FXML
    public void initialize() {
        taskInput.setOnKeyPressed(this::handleEnterKeyPressed);
    }

    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleAddTask();
        }
    }

    @FXML
    private void handleAddTask() {
        String taskText = taskInput.getText().trim();
        if (!taskText.isEmpty()) {
            Task task = new Task(taskText);
            addTaskToUI(task);
            taskInput.clear();
        }
    }

    private void addTaskToUI(Task task) {
        Label taskLabel = new Label(task.getDescription());
        taskLabel.getStyleClass().add("task-label");
        taskLabel.getStyleClass().add("pending-task");

        taskLabel.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                task.setCompleted(!task.isCompleted());
                updateTaskStyle(task, taskLabel);
            }
        });

        taskLabel.setOnContextMenuRequested(event -> taskList.getChildren().remove(taskLabel));

        taskList.getChildren().add(taskLabel);
    }

    private void updateTaskStyle(Task task, Label taskLabel) {
        if (task.isCompleted()) {
            taskLabel.getStyleClass().add("completed-task");
            taskLabel.getStyleClass().remove("pending-task");
        } else {
            taskLabel.getStyleClass().add("pending-task");
            taskLabel.getStyleClass().remove("completed-task");
        }
    }
}