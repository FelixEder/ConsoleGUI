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
	
	@FXML 
	protected TextField input;
	
	@FXML
	protected TextArea output, inventory, commands;
	
	protected static List<String> history;
	protected static int historyPointer;
	protected static String textToRead = null;
	
	private Service<Void> backgroundThread;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Console.fxml"));
		
		BorderPane root = loader.load();
		
		history = new ArrayList<>();
		historyPointer = 0;

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
	
	@FXML
	void inputAction(KeyEvent keyEvent) {
		switch (keyEvent.getCode()) {
		case ENTER: String text = input.getText();
					output.appendText(text + System.lineSeparator());
					history.add(text);
					historyPointer = history.size(); 
					input.clear();
					textToRead = text;
					break;
					
		case UP :	if(historyPointer == 0) break;
					historyPointer--;
					input.setText(history.get(historyPointer));
					input.selectAll();
					input.selectEnd(); //Does not change anything seemingly
					break;
					
		case DOWN:	if(historyPointer == history.size()-1) break;
					historyPointer++;
					input.setText(history.get(historyPointer));
					input.selectAll();
					input.selectEnd(); //Does not change anything seemingly
					break;
		}
	}
	
	@FXML
	public void initialize() {
	    //This method is called after the constructor and FXML-fields are populated
        //Use this to initialize important stuff.
	}

	/**
	 * Called when the game wants to print something to the game
	 * @param message The text to be printed to the console.
	 */
	public void printGameInfo(String message) {
		System.out.println("This method was attempted!");
		output.setText(message + System.lineSeparator());
	}
	
	/**
	 * Sets the input field to a particular value.
	 * @param message The text that should be added to the input field.
	 */
	public void addInputInfo(String message) {
		input.setText(message);
	}

	/**
	 * Waits until the field textToRead is non-null and
	 * returns it, setting the field to null afterwards.
	 * @return The current text value in the String field.
	 */
	public static String getTextField() {
	    /*
		while(textToRead == null) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		*/
		String returnText = textToRead;
		textToRead = null;
		return returnText;
	}
}