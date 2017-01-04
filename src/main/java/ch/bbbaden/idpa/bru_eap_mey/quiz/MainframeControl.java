package ch.bbbaden.idpa.bru_eap_mey.quiz;

import java.io.IOException;


import ch.bbbaden.idpa.bru_eap_mey.quiz.controllers.MainController;
import ch.bbbaden.idpa.bru_eap_mey.quiz.controllers.QuestionController;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.QuizModel;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Die Hauptklasse des Wirtschaftsquizes
 */
public class MainframeControl extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass()
					.getResource("main.fxml"));
			Region root = loader.<Region> load();
			
			QuizModel qm = new QuizModel();
			qm.loadData();
			MainController con = loader.getController();
			con.setModel(qm);
			
			Scene scene = new Scene(root, root.getPrefWidth(),
									root.getPrefHeight());
			
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			
			primaryStage.show();
		} catch(IOException e) {
			
			throw e;
		}
	}
	
	/**
	 * Lädt die Szene der Hauptansicht.
	 * 
	 * @return
	 * 		die Szene, welche die Hauptansicht darstellt
	 * @throws IOException
	 *         falls die FXML-Datei nicht gefunden wurde
	 */
	public static Scene mainPage() throws IOException {
		FXMLLoader loader = new FXMLLoader(MainframeControl.class
				.getResource("main.fxml"));
		Region root = loader.<Region> load();
		
		Scene scene = new Scene(root, root.getPrefWidth(),
								root.getPrefHeight());
		
		
		return scene;
	}
	
	/**
	 * Lädt die Ansicht einer Frage und initialisiert den
	 * dazugehörigen Controller mit den erforderlichen Daten.
	 * 
	 * @param <T>
	 *        der Antworttyp der Frage.
	 * @param qm
	 *        das QuizModel, wird für den Controller benötigt, siehe
	 *        {@link QuestionController#setModel(QuizModel)}
	 * @param question
	 *        die Frage, deren Ansicht geladen werden soll
	 * @return
	 * 		eine Szene der Fragenansicht
	 * @throws IOException
	 *         wenn die Datei der Ansicht nicht gefunden wird
	 */
	public static <T> Scene loadQuestion(QuizModel qm, Question<T> question)
			throws IOException {
		FXMLLoader loader = new FXMLLoader(question.getClass()
				.getResource(question.getFilename()));
		Region root = loader.<Region> load();
		
		QuestionController<Question<T>> q = loader.getController();
		q.setModel(qm);
		q.setQuestion(question);
		
		Scene scene = new Scene(root, root.getPrefWidth(),
								root.getPrefHeight());
		
		return scene;
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
