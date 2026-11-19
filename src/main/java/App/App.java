package App;

import Util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        HibernateUtil.getInstance();

        Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/Login.fxml")
        );

        Scene scene = new Scene(root);

        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {

        HibernateUtil.shutdown();
        System.out.println("App Closed - Hibernate Shutdown Successfully!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}