package ch.bbbaden.idpa.bru_eap_mey.quiz;

import java.io.IOException;
import java.net.URL;


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
			qm.setStage(primaryStage);
			MainController con = loader.getController();
			con.setModel(qm);
			
			Scene scene = new Scene(root, root.getPrefWidth(),
									root.getPrefHeight());
			
			loadCSS(scene);
			
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.setTitle("Wirtschaftsquiz");
			
			primaryStage.show();
		} catch(IOException e) {
			
			throw e;
		}
	}
	
	/**
	 * Lädt das CSS, wenn vorhanden, in die Szene.
	 * 
	 * @param sc
	 *        die Szene
	 */
	private static void loadCSS(Scene sc) {
		URL url = MainframeControl.class.getResource("app.css");
		if(url != null) {
			sc.getStylesheets().add(url.toExternalForm());
		} else {
			Util.showErrorExitOnNoOrClose(	"Stylesheet nicht gefunden",
											"Das Stylesheet der Applikation (app.css) "
											+ "wurde nicht gefunden. Möglicherweise "
											+ "wurde es verschoben oder gelöscht?\r\n"
											+ "Wollen Sie den Fehler ignorieren?");
		}
	}
	
	/**
	 * Lädt die Szene der Hauptansicht.
	 * 
	 * @param qm
	 *        das QuizModel
	 * @return
	 * 		die Szene, welche die Hauptansicht darstellt
	 * @throws IOException
	 *         falls die FXML-Datei nicht gefunden wurde
	 */
	public static Scene mainPage(QuizModel qm) throws IOException {
		FXMLLoader loader = new FXMLLoader(MainframeControl.class
				.getResource("main.fxml"));
		Region root = loader.<Region> load();
		MainController mc = loader.getController();
		mc.setModel(qm);
		
		Scene scene = new Scene(root, root.getPrefWidth(),
								root.getPrefHeight());
		
		loadCSS(scene);
		
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
		
		loadCSS(scene);
		
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
