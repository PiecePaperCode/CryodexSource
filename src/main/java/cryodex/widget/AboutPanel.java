package cryodex.widget;

import javax.swing.JDialog;
import cryodex.Main;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class AboutPanel {
	private Button closeButton;
    static JDialog manualModificationPanel = new JDialog(Main.getInstance(),"About", true);

	public static void showAboutPanel() {

		JFXPanel panel = new JFXPanel();
		manualModificationPanel.getContentPane().add(panel);
		new AboutPanel(panel);
		manualModificationPanel.setLocationRelativeTo(Main.getInstance());
		manualModificationPanel.pack();
		manualModificationPanel.setVisible(true);
	}

	private AboutPanel(JFXPanel panel) {
        VBox root = new VBox();
		root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
		String aboutText = """
            This program was created for the Campaign Against
            Cancer Tournament. A special thanks to Chad Hoefle and
            Anthony Lullig for their encouragement and testing during
            that time. I would also like to thank all of those who
            encouraged me to make it better and distribute it after that
            tournament was complete. My goal is to have a program that #1
            follows the rules and #2 is easy to use. You are welcome to
            contact me with any comments or concerns you have about the
            program. My email is Chris.Brown.SPE@gmail.com. You can also
            use that email to send a donation via Paypal if you feel so
            inclined.""".trim();

		var aboutLabel = new Label(aboutText);
		aboutLabel.setMaxWidth(1000);
		root.getChildren().add(aboutLabel);
        root.getChildren().add(getCloseButton());
        panel.setScene(new Scene(root));
	}

	private Button getCloseButton() {
		if (closeButton == null) {
			closeButton = new Button("Close");
			closeButton.addEventFilter(
                    MouseEvent.MOUSE_CLICKED,
                    MouseEvent -> manualModificationPanel.setVisible(false)
			);
		}
		return closeButton;
	}
}
