package cryodex;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class BigClock {
	
	private static Stage instance;
	
	public static Stage getInstance() {
		if (instance == null) {
			instance = new Stage();
		}
		return instance;
	}
	private Label label;
	
	public BigClock() {
		instance = getInstance();
		instance.setTitle("Clock");

		var mainPanel = new BorderPane();
		mainPanel.setCenter(getBigClockLabel());
		instance.setScene(new Scene(mainPanel));
		instance.show();

	}
	
	public Label getBigClockLabel(){
		if(label == null){
			label = new Label();
		}
		
		return label;
	}
	
}
