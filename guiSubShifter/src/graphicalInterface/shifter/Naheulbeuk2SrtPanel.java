package graphicalInterface.shifter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Naheulbeuk2SrtPanel extends Application implements EventHandler<ActionEvent> {

	private Stage primaryStage;
	private Alerts alertsCreator;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.alertsCreator = new Alerts(primaryStage);

		primaryStage.setTitle("Subtitles Shifter");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(20);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Turn a 'Donjon de Naheulbeuk' script to a srt file!");
		grid.add(scenetitle, 0, 0, 2, 1);
//		scenetitle.setFont(Font.loadFont(JavaFxOptionPanel.class.getResource("CHICORY_.TFF").toExternalForm(),60));
		scenetitle.setId("title");

	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
