package ch.bbbaden.idpa.bru_eap_mey.quiz;

import java.io.IOException;


import javax.swing.JOptionPane;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Die Hauptklasse des Wirtschaftsquizzes
 */
public class Starter extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass()
					.getResource("main.fxml"));
			Parent root = loader.<Parent> load();
			
			Scene scene = new Scene(root, 600, 400);
			
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(null);
			
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			// primaryStage.setMinWidth(616); // TODO
			// primaryStage.setMinHeight(438);
			
			primaryStage.show();
		} catch(IOException e) {
			
			throw e;
		}
	}
	
	/**
	 * Die Startmethode des Wirtschaftsquiz.
	 * 
	 * @param args
	 *        die Programmargumente
	 */
	public static void main(String[] args) {
		// Ein Handhaber für Standardausnahmefehler, der ein kleines
		// Fenster anzeigt.
		Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
			JOptionPane.showMessageDialog(	null, e.getLocalizedMessage(),
											"Ein Fehler ist aufgetreten!",
											JOptionPane.ERROR_MESSAGE);
		});
		launch(args);
	}
	
}
