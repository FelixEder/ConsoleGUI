import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

	private Service<Void> backgroundThread;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Console.fxml"));
		
		BorderPane root = loader.load();

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("MyConsoleFXGUI"); //Could later be changed so that the actual game title is displayed here.
		stage.show();
		
		//Starts a new Javafx thread and launches the game on it.
		System.out.println("Stage has been set up");
		backgroundThread = new Service<Void>() {
		
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					
					@Override
					protected Void call() throws Exception {
						System.out.println("Game has been init, time to play!");
						return null;
					}
				};
			}
		};
		backgroundThread.restart();
		//When the game is completed on the other thread, this is called.
		backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				try {
					Platform.exit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}