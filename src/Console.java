import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Console extends Application {
	protected TextArea output;
	protected TextField input;
	
	protected List<String> history;
	protected int historyPointer;
	
	private Consumer<String> onMessageReceivedHandler;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	
	public void start(Stage stage) throws Exception {
		output = new TextArea();
		input = new TextField();
		history = new ArrayList<>();
		historyPointer = 0;
		
		output.setEditable(false);
		
		BorderPane.setAlignment(output, Pos.CENTER);
		BorderPane.setAlignment(input, Pos.BOTTOM_CENTER); //Kan ändras vid behov
		
		BorderPane base = new BorderPane(output, null, null, input, null); //BorderPane(Node center, Node top, Node right, Node bottom, Node left)
		
		input.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			public void handle(KeyEvent keyEvent) {
				switch (keyEvent.getCode()) {
				case ENTER: String text = input.getText();
							output.appendText(text + System.lineSeparator());
							history.add(text);
							historyPointer++;
							if (onMessageReceivedHandler != null) {
								onMessageReceivedHandler.accept(text);
							}
							input.clear();
							break;
							
				case UP :	if(historyPointer == 0) break;
							historyPointer--;
							input.setText(history.get(historyPointer));
							input.selectAll();
							break;
							
				case DOWN:	if(historyPointer == history.size()-1) break;
							historyPointer++;
							input.setText(history.get(historyPointer));
							input.selectAll();
							break;
					
				default: break;
			}

		}
		});
		
		
		
		
		//Settings for the borderPane, can be changed later
		base.setPrefSize(400, 400);
		base.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
		
		Scene scene = new Scene(base);
		stage.setScene(scene);
		stage.setTitle("Felix own Console GUI"); //Could later be changed so that the actual game-title is displayed here.
		stage.show();
	}
	/*
	public Console() {
		
		input.addEventHandler(EventType<KeyEvent.KEY_RELEASED> keyEvent){
			switch (keyEvent.getCode()) {
				case ENTER: String text = input.getText();
							output.appendText(text + System.lineSeparator());
							history.add(text);
							historyPointer++;
							if (onMessageReceivedHandler != null) {
								onMessageReceivedHandler.accept(text);
							}
							input.clear();
							break;
							
				case UP :	if(historyPointer == 0) break;
							historyPointer--;
							input.setText(history.get(historyPointer));
							input.selectAll();
							break;
							
				case DOWN:	if(historyPointer == history.size()-1) break;
							historyPointer++;
							input.setText(history.get(historyPointer));
							input.selectAll();
							break;
					
				default: break;
			}
		});
		setBottom(input);
	}
	

	
	@Override
	public void requestFocus() {
		super.requestFocus();
		input.requestFocus();
	}
	
	public void setOnMessageReceivedHandler(final Consumer<String> onMessageReceivedHandler) {
		this.onMessageReceivedHandler = onMessageReceivedHandler;
	}
	
	public void clear() {
		output.clear();
	}
	
	public void print(final String text) {
		Objects.requireNonNull(text, "text");
		output.appendText(text);
	}
	
	public void println(final String text) {
		Objects.requireNonNull(text, "text");
		output.appendText(text + System.lineSeparator());
	}
	
	public void println() {
		output.appendText(System.lineSeparator());
	}
	*/
}
