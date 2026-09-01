package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;

public class AppView extends Application {

    private FXMLLoader loader;

    public AppView() {
        this.loader = new FXMLLoader();
        URL url = getClass().getResource("/view/app.fxml");
        this.loader.setLocation(url);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Laboratório de Deus");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public void run(String[] args) {
        Application.launch(args);
    }
}