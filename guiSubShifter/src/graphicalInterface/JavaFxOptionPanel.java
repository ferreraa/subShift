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
import kernel.JavaShifter;
import kernel.Shifter;
import kernel._Shifter;

public class JavaFxOptionPanel extends Application implements EventHandler<ActionEvent>{

	//Browse source file
	private Button browseSrcButton;
	private TextField srcField;

	//browse destination
/*	private Button browseDestButton;
	private TextField destField;
*/

	//time offset. This will be a TextField only accepting numbers.
	private TextField numberField;

	//validate button
	private Button validateButton;

	//Alerts creator. object that can generate specific alerts
	private Alerts alertsCreator;

	//stage
	private Stage primaryStage;

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

		Text scenetitle = new Text("Shift your subtitles!");
		grid.add(scenetitle, 0, 0, 2, 1);
//		scenetitle.setFont(Font.loadFont(JavaFxOptionPanel.class.getResource("CHICORY_.TFF").toExternalForm(),60));
		scenetitle.setId("title");


		//source file
		Label originalSubs = new Label("subtitles to shift:");
		grid.add(originalSubs, 0, 2);

		srcField = new TextField();
		grid.add(srcField, 1, 2);

		browseSrcButton = new Button("Browse file...");
		grid.add(browseSrcButton, 2, 2);
		browseSrcButton.setOnAction(this);


		//Destination
/*		Label targetedFolder = new Label("Generated file path:");
		grid.add(targetedFolder, 0, 4);


		browseDestButton = new Button("Browse destination file...");
		grid.add(browseDestButton, 2, 4);
		browseDestButton.setOnAction(this);

		destField = new TextField();
		grid.add(destField, 1, 4);
*/

		//Value of the requested time offset
		Label timeOffset = new Label("define your time offset (ms):");
		grid.add(timeOffset, 0, 4);


		//number Field
		numberField = new TextField() {
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

		grid.add(numberField, 1, 4);

		//faster or slower
		CheckBox cb = new CheckBox("make subtitles faster");
		cb.setIndeterminate(false);
		grid.add(cb, 2, 4);


		//validate Button
		validateButton = new Button("Validate");
		grid.add(validateButton, 1, 6);
		validateButton.setOnAction(this);;


		Scene scene = new Scene(grid, 730, 350);
		primaryStage.setScene(scene);
		scene.getStylesheets().add
		 (JavaFxOptionPanel.class.getResource("javaFxOptionPanel.css").toExternalForm());

		primaryStage.show();

	}

	@Override
	public void handle(ActionEvent e) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select subtitles File");
		fileChooser.getExtensionFilters().add
			(new ExtensionFilter("Subtitles Files", "*.srt"));

		File selectedFile;
		String currentSrc = srcField.getText();

		if(e.getSource() == browseSrcButton) {

			if (!currentSrc.equals("")) {
				fileChooser.setInitialDirectory(new File(currentSrc).getParentFile());
			}

			selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null)
				srcField.setText(selectedFile.getAbsolutePath());
		}
		else if (e.getSource() == validateButton) {

			if(numberField.getText().equals("") || currentSrc.equals(""))
				alertsCreator.incompleteForm();
			else {
				fileChooser.setInitialDirectory(new File(currentSrc).getParentFile());
				fileChooser.setInitialFileName("Untitled.srt");
				selectedFile = fileChooser.showSaveDialog(primaryStage);

				if (selectedFile != null) {
					_Shifter shifter = new JavaShifter(srcField.getText(), Integer.parseInt(numberField.getText()), selectedFile.getAbsolutePath());
					//int returnCode = Shifter.shift(srcField.getText(), Long.parseLong(numberField.getText()), selectedFile.getAbsolutePath());
					int returnCode = shifter.shift();
					System.out.println(returnCode);

					//using the Perl script leads to changing the error codes

					if(returnCode == 0) {
						alertsCreator.success();
					}
					else if (returnCode == 1){
						alertsCreator.fileOpeningIssue();
					}
					else if (returnCode == 2) {
						alertsCreator.fileCreationIssue();
					}
					else {
						alertsCreator.unexpectedError(returnCode);
					}
				}
			}
		}
	}




}
