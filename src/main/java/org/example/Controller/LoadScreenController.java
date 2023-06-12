package org.example.Controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import org.example.App;

import java.io.IOException;

public class LoadScreenController {
    @FXML
    private ProgressBar progressbar;
    @FXML
    private ProgressIndicator indicator;

    @FXML
    private void initialize() {

        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30); // Simula el tiempo de carga entre cada paso (30 ms)
                    updateProgress(i, 100); // Actualiza el progreso del Task
                }

                return null;
            }
        };
        // Vincula el progreso del Task con el ProgressBar y ProgressIndicator
        progressbar.progressProperty().bind(loadingTask.progressProperty());
        indicator.progressProperty().bind(loadingTask.progressProperty());


        loadingTask.setOnSucceeded(event -> {
            try {
                App.setRoot("home");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread loadingThread = new Thread(loadingTask);
        loadingThread.start();
    }
}

