import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
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
	
	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = FXMLLoader.load(getClass().getResource("Console.fxml"));
		
		output = new TextArea();
		input = new TextField();
		inventory = new TextArea();
		commands = new TextArea();
		
		history = new ArrayList<>();
		historyPointer = 0;

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("MyConsoleFXGUI"); //Could later be changed so that the actual game title is displayed here.
		stage.show();
		
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

		default: break;
		}
	}
	

	/**
	 * Called when the game wants to print something to the game
	 * @param message The text to be printed to the console.
	 */
	public void printGameInfo(String message) {
		output.appendText(message + System.lineSeparator());
	}
	
	
	/**
	 * Sets the input field to a particular value.
	 * @param message The text that should be added to the input field.
	 */
	/*
	public void addInputInfo(String message) {
		input.setText(message);
	}
	
	*/
	
	/**
	 * Waits until the field textToRead is non-null and
	 * returns it, setting the field to null afterwards.
	 * @return The current text value in the String field.
	 * @throws InterruptedException 
	 */
	public static String getTextField() {
		while(textToRead == null) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		String returnText = textToRead;
		textToRead = null;
		return returnText;
}

}
