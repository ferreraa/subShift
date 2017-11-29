package graphicalInterface.shifter;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Window;

public class Alerts {


	private Window owner;

	public Alerts(Window owner) {
		this.owner = owner;
	}

	public Alert createAlert(Alert.AlertType type, String message) {
	    Alert alert = new Alert(type);

	    alert.initOwner(owner);

	    StringBuilder sb = new StringBuilder(message);
	    for (int i = 0; i < message.length(); i += 200) {
	        sb.insert(i, "\n");
	    }

	    Label t = new Label(sb.toString());
	    alert.getDialogPane().setContent(t);
	    return alert;
	}

	public void incompleteForm() {
		Alert alert = createAlert(AlertType.ERROR, "Please enter a source file and an offset time.");
		alert.setTitle("Incomplete form");
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public void unexpectedError(int returnCode) {
		Alert alert = createAlert(AlertType.ERROR,"Unexpected error");
		alert.setTitle("Error");
		alert.setHeaderText("Error " + returnCode);
		alert.showAndWait();
	}

	public void success() {
		Alert alert = createAlert(AlertType.INFORMATION, "The subtitles have been modified with success!");
		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public void fileOpeningIssue() {
		Alert alert = createAlert(AlertType.ERROR, "One of the files couldn't be opened properly.");
		alert.setTitle("Error");
		alert.setHeaderText("Error 1");
		alert.showAndWait();
	}

	public void fileCreationIssue() {
		Alert alert = createAlert(AlertType.ERROR, "One or several files couldn't be created properly.");
		alert.setTitle("Error");
		alert.setHeaderText("Error 2");
		alert.showAndWait();
	}

	public void renameFailed() {
		Alert alert = createAlert(AlertType.WARNING, "I failed to add automatically add the .srt extension to the resulting file");
		alert.setTitle("Renamin failure");
		alert.setHeaderText(null);
		alert.showAndWait();
	}

}
