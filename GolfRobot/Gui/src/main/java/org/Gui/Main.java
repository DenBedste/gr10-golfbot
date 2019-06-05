package gui.application;

import golfbot.server.blackboard.BlackboardListener;
import golfbot.server.blackboard.BlackboardSample;

//import org.opencv.core.Core;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	Scene scene;
	
	FXController controller;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Stage.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			controller = loader.getController();
			
			scene = new Scene(root,1200,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			controller.init();
			controller.startRenderThread();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
		launch(args);
	}
	
}