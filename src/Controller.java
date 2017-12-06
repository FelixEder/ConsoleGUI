import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The controller for the Console
 * @author Felix Eder
 * @version 2017-12-06
 */
public class Controller implements Initializable {

  @FXML
  protected TextField input;
  @FXML
  protected TextArea output, inventory, commands;

  private static List<String> history;
  private static int historyPointer;
  private static String textToRead = null;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    history = new ArrayList<>();
    historyPointer = 0;
  }

  @FXML
  void handleInput(KeyEvent keyEvent) {
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

  public static String getTextField() {
    String returnText = textToRead;
    textToRead = null;
    return returnText;
  }
   */
}