package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	// --------------------------------------------------------------------------------
	
	// Relation the controller class
	
	Controller controller;
	
	// --------------------------------------------------------------------------------
	
	// Constructor method
	
	/**
	 * Constructor method of the Main class.
	 */
	
	public Main() {
		
		controller = new Controller();
		
	}
	
	// --------------------------------------------------------------------------------
	
	// Start method
	
	/**
	 * @param args0 Stage that represent the user interface of the system.
	 * @throws Exception General information of the super class Exception
	 */

	@Override
	public void start(Stage arg0) throws Exception {
		
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Window.fxml"));

			fxmlLoader.setController(controller);

			Parent root = fxmlLoader.load();

			Scene scene = new Scene(root);
			
			Stage primaryStage = new Stage();

			primaryStage.setScene(scene);

			primaryStage.setTitle("Cocke younger Kasami algorithm");

			primaryStage.show();

		} catch(Exception e) {
			
			e.printStackTrace();
			
		}

	}
	
	// --------------------------------------------------------------------------------
	
	// Main method
	
	/**
	 * Principal method of the system CYK.
	 * @param args Array string information
	 */
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
	
	// --------------------------------------------------------------------------------

}
