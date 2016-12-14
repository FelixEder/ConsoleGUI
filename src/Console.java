import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class Console extends BorderPane {
	protected final JFXPanel fxPanel = new JFXPanel();
	protected final TextArea output = new TextArea();
	protected final TextField input = new TextField();
	
	protected final List<String> history = new ArrayList<>();
	protected int historyPointer = 0;
	
	private Consumer<String> onMessageReceivedHandler;
	
	public Console() {
		output.setEditable(false);
		setCenter(output);
		
		input.addEventHandler(KeyEvent.KEY_RELEASED,  keyEvent -> {
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
}
