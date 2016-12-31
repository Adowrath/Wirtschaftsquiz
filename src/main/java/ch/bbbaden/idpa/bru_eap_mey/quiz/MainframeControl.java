package ch.bbbaden.idpa.bru_eap_mey.quiz;

import java.io.IOException;


import ch.bbbaden.idpa.bru_eap_mey.quiz.controllers.BinaryController;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.QuizModel;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.BinaryQuestion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Die Hauptklasse des Wirtschaftsquizzes
 */
public class MainframeControl extends Application {
	
	
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
		Thread.setDefaultUncaughtExceptionHandler(Util::showUncaughtError);
		
		launch(args);
	}
	
}
