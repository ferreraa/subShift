package graphicalInterface;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class JavaFxOptionPanel extends Application implements EventHandler<ActionEvent>{

	//Browse source file
	private Button browseSrcButton;
	private TextField srcField;

	//browse destination
	private Button browseDestButton;
	private TextField destField;

	//validate button
	private Button validateButton;

	//stage
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		primaryStage.setTitle("Subtitles Shifter");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Options");
		scenetitle.setId("options");
		grid.add(scenetitle, 0, 0, 2, 1);


		//source file
		Label originalSubs = new Label("subtitles to shift:");
		grid.add(originalSubs, 0, 1);

		srcField = new TextField();
		grid.add(srcField, 1, 1);

		browseSrcButton = new Button("Browse file...");
		grid.add(browseSrcButton, 2, 1);
		browseSrcButton.setOnAction(this);


		//Destination
		Label targetedFolder = new Label("Generated file path:");
		grid.add(targetedFolder, 0, 4);


		browseDestButton = new Button("Browse destination file...");
		grid.add(browseDestButton, 2, 4);
		browseDestButton.setOnAction(this);

		TextField destField = new TextField();
		grid.add(destField, 1, 4);


/*		Label _srt = new Label(".srt");
		grid.add(_srt, 3, 4);
*/

		//Value of the requested time offset
		Label timeOffset = new Label("define your time offset (ms)");
		grid.add(timeOffset, 0, 6);


		//number Field
		TextField numberField = new TextField() {
			@Override public void replaceText(int start, int end, String text) {
				if (text.matches("[0-9]*")) {
					super.replaceText(start, end, text);
				}
			}

			@Override public void replaceSelection(String text) {
				if (text.matches("[0-9]*")) {
					super.replaceSelection(text);
				}
			}
	    };

		grid.add(numberField, 1, 6);

		//faster or slower
		CheckBox cb = new CheckBox("make subtitles faster");
		cb.setIndeterminate(false);
		grid.add(cb, 2, 6);


		//validate Button
		validateButton = new Button("Validate");
		grid.add(validateButton, 1, 8);


		Scene scene = new Scene(grid, 350, 300);
		primaryStage.setScene(scene);
		scene.getStylesheets().add
		 (JavaFxOptionPanel.class.getResource("javaFxOptionPanel.css").toExternalForm());

		primaryStage.show();

	}

	@Override
	public void handle(ActionEvent e) {

		if(e.getSource() == browseSrcButton || e.getSource() == browseDestButton) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select subtitles File");
			fileChooser.getExtensionFilters().addAll(
			         new ExtensionFilter("Subtitles Files", "*.srt"));

			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null) {
				String path = selectedFile.getAbsolutePath();
				if (e.getSource() == browseSrcButton)
					srcField.setText(path);
				else
					destField.setText(path);
			}
		}
		else if (e.getSource() == validateButton) {
			//tralala
		}
	}

}
