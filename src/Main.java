import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
	@FXML 
	protected TextField input;
	
	@FXML
	protected TextArea output, inventory, commands;
	
	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = FXMLLoader.load(getClass().getResource("Console.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("The Expedition"); //Could later be changed so that the actual game title is displayed here.
		stage.show();
		
	}

}
