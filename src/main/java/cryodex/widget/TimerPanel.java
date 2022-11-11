package cryodex.widget;

import cryodex.BigClock;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.util.Timer;


public class TimerPanel  {
	private Label timeLabel;
	private Button startTimeButton;
	private Button stopTimeButton;
	private Button resetTimeButton;
	private long timeStart = 0;
	private Spinner spinner;
	private Timer timer;
	private long timeRemaining = 0;
	private long millisInRound = 0;
	private Button expandButton;
	private BorderPane mainPanel;
	private final static java.text.SimpleDateFormat timerFormat = new java.text.SimpleDateFormat("ss");

	public TimerPanel() {
		BorderPane bottomPanel = new BorderPane();
		BorderPane buttonPanel = new BorderPane();
		FlowPane spinnerPanel = new FlowPane();
		mainPanel = new BorderPane();

		spinnerPanel.getChildren().add(new Label("Mins:"));
		spinnerPanel.getChildren().add(getSpinner());

		buttonPanel.setTop(getStartTimeButton());
		buttonPanel.setCenter(getStopTimeButton());
		buttonPanel.setBottom(getResetTimeButton());

//		panel.add(getTimeLabel(), BorderLayout.NORTH);
		bottomPanel.setTop(buttonPanel);
		bottomPanel.setCenter(getExpandButton());
		bottomPanel.setBottom(spinnerPanel);

		mainPanel.setCenter(getTimeLabel());
		mainPanel.setBottom(bottomPanel);
	}

	private Button getExpandButton() {
		if(expandButton == null){
			expandButton = new Button("Expand");
			expandButton.addEventFilter(
				MouseEvent.MOUSE_CLICKED,
				MouseEvent -> BigClock.getInstance().show()
			);
		}
		return expandButton;
	}

	public Label getTimeLabel() {
		if (timeLabel == null) {
			timeLabel = new Label(" ");
			resetTime();
		}
		return timeLabel;
	}

	public Spinner getSpinner() {
		if (spinner == null) {
			spinner = new Spinner(75, 1, 1440, 1);
			spinner.addEventFilter(
				MouseEvent.MOUSE_CLICKED,
				MouseEvent -> resetTime()

			);
		}
		return spinner;
	}

	public Button getStartTimeButton() {
		if (startTimeButton == null) {
			startTimeButton = new Button("Start");
			startTimeButton.addEventFilter(
				MouseEvent.MOUSE_CLICKED,
				MouseEvent -> startTime()
			);
		}

		return startTimeButton;
	}

	public Timer getTimer() {
		if (timer == null) {
			timer = new Timer();
		}
		return timer;
	}

	public Button getStopTimeButton() {
		if (stopTimeButton == null) {
			stopTimeButton = new Button("Stop");
			stopTimeButton.setDisable(true);
			stopTimeButton.addEventFilter(
				MouseEvent.MOUSE_CLICKED,
				MouseEvent -> stopTime()

			);
		}

		return stopTimeButton;
	}

	public Button getResetTimeButton() {
		if (resetTimeButton == null) {
			resetTimeButton = new Button("Reset");
			resetTimeButton.addEventFilter(
				MouseEvent.MOUSE_CLICKED,
				MouseEvent -> resetTime()
			);
		}

		return resetTimeButton;
	}

	private void startTime() {
		getSpinner().setDisable(true);
		getStartTimeButton().setDisable(true);
		getResetTimeButton().setDisable(false);
		getStopTimeButton().setDisable(false);

		if (millisInRound == 0) {

			Number d = (Number) getSpinner().getValue();

			millisInRound = d.longValue() * 60 * 1000;
		}

		timeStart = System.currentTimeMillis();
		// getTimer().start();
	}

	private void stopTime() {
		getSpinner().setDisable(false);
		getStartTimeButton().setDisable(false);
		getResetTimeButton().setDisable(false);
		getStopTimeButton().setDisable(true);

		// getTimer().stop();
		millisInRound = timeRemaining;
	}

	private void resetTime() {
		Number d = (Number) getSpinner().getValue();
		millisInRound = d.longValue() * 60 * 1000;
		getTimeLabel()
				.setText(
						d.longValue() + ":"
								+ timerFormat.format(new java.util.Date(0)));
	}

	private void checkTime() {

		long currentTime = System.currentTimeMillis();

		long timeElapsed = (currentTime - timeStart);

		long minutesElapsed = timeElapsed / 1000 / 60;

		long minutesForRound = (millisInRound - 1) / 1000 / 60;

		long minutesRemaining = minutesForRound - minutesElapsed;

		timeRemaining = millisInRound - timeElapsed;

		if (timeRemaining < 0) {
			getTimeLabel().setText("ROUND OVER");
			stopTime();

			startAudio();
		} else {
			getTimeLabel().setText(
					minutesRemaining
							+ ":"
							+ timerFormat.format(new java.util.Date(
									timeRemaining)));
		}
		
		if(BigClock.getInstance().isShowing()){
			// BigClock.getInstance().get.setText(getTimeLabel().getText());
		}

	}

	private void startAudio() {
		try {
			// ** add this into your application code as appropriate
			// Open an input stream to the audio file.
			// InputStream in = new FileInputStream("Cryodex.wav");
			// Create an AudioStream object from the input stream.
			// AudioStream as = new AudioStream(in);
			// Use the static class member "player" from class AudioPlayer to
			// play
			// clip.
			// AudioPlayer.player.start(as);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}