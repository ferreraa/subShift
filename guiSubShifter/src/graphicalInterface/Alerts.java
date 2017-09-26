package graphicalInterface;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Window;

public class Alerts {

	public static Alert createAlert(Alert.AlertType type, String message) {
	    Alert alert = new Alert(type);

	    StringBuilder sb = new StringBuilder(message);
	    for (int i = 0; i < message.length(); i += 200) {
	        sb.insert(i, "\n");
	    }

	    Label t = new Label(sb.toString());
	    alert.getDialogPane().setContent(t);
	    return alert;
	}

	public static void incompleteForm() {
		Alert alert = createAlert(AlertType.ERROR, "Please enter a source file and an offset time.");
		alert.setTitle("Incomplete form");
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public static void unexpectedError(int returnCode) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error " + returnCode);
		alert.setContentText("Unexpected error");
		alert.showAndWait();
	}

	public static void success() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.setContentText("The subtitles have been modified with success!");
		alert.showAndWait();
	}

	public static void fileOpeningIssue() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error 2");
		alert.setContentText("One of the files couldn't be opened properly.");
		alert.showAndWait();
	}

}
