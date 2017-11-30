package graphicalInterface.shifter;

import javafx.application.Application;
import kernel.shifter.JavaShifter;
import kernel.shifter._Shifter;

public class Main {

	public static void main(String[] args) {
		switch(args.length) {
		case 0:
			Application.launch(JavaFxOptionPanel.class, args);
			break;
		case 3:
			_Shifter shifter = new JavaShifter(args[0], Integer.parseInt(args[1]), args[2]);
			System.out.println("return code = " + shifter.shift());
			break;
		default:
			System.out.println("Wrong number of parameters");
		}

	}

}
