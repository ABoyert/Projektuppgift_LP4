package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class iMat extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/imat_main.fxml"));
        primaryStage.setTitle("iMat");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(1300);
        primaryStage.setMinHeight(800);
        primaryStage.show();

        //UtilityMethods um = new UtilityMethods();
        //System.out.println(um.getCategory(UtilityMethods.Categories.FRUKT_GRONT).toString());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
