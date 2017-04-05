import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Console extends Application {
	protected static TextArea output;
	protected static TextArea inventory;
	protected static TextArea commands;
	protected static TextField input;
	
	
	protected static List<String> history;
	protected static int historyPointer;
	protected static String textToRead = null;
	
	static Stage classStage = new Stage();
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	
	public void start(Stage stage) throws Exception {
		output = new TextArea();
		input = new TextField();
		inventory = new TextArea();
		commands = new TextArea();
		
		history = new ArrayList<>();
		historyPointer = 0;
	
		output.setEditable(false);
		inventory.setEditable(false);
		commands.setEditable(false);
		input.requestFocus();
		
		BorderPane.setAlignment(output, Pos.CENTER);
		BorderPane.setAlignment(input, Pos.BOTTOM_CENTER); //Can be changed when needed
		BorderPane.setAlignment(commands, Pos.BOTTOM_LEFT);
		BorderPane.setAlignment(inventory, Pos.TOP_LEFT);
		
		
		BorderPane base = new BorderPane(output, null, commands, input, inventory); //BorderPane(Node center, Node top, Node right, Node bottom, Node left)
		
		input.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent keyEvent) {
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
		});

		//Settings for the borderPane, can be changed later
		base.setPrefSize(800, 800);
		base.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
		
		Scene scene = new Scene(base);
		stage.setScene(scene);
		stage.setTitle("Felix own Console GUI"); //Could later be changed so that the actual game title is displayed here.
		stage.show();
	}
	
	/**
	 * Called when the game wants to print something to the game
	 * @param message The text to be printed to the console.
	 */
	public static void printGameInfo(String message) {
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
