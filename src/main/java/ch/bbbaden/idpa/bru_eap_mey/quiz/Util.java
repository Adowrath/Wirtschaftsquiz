package ch.bbbaden.idpa.bru_eap_mey.quiz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


import org.eclipse.jdt.annotation.Nullable;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;

/**
 * Eine Reihe verschiedener Utility Methoden.
 */
public class Util {
	
	/**
	 * Der Standardinhalt für eine leere XML-Datei.
	 */
	private static final String defaultText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ System.getProperty("line.separator") + "<game>"
			+ System.getProperty("line.separator") + "</game>";
	
	/**
	 * Gibt nicht abgefangene Fehlermeldungen auf den Error-Stream aus
	 * und öffnet ein JOptionPane mit der Fehlermeldung.
	 * <br>
	 * Sollte nicht direkt aufgerufen werden.
	 * <br>
	 * Zusammenklamüsert aus
	 * <a href="http://stackoverflow.com/a/1149712/5236247">einer</a>
	 * und einer <a href=
	 * "http://stackoverflow.com/a/14011536/5236247">anderen</a>
	 * SO-Antwort.
	 * 
	 * @param t
	 *        der Thread, in dem der Throwable auftrat
	 * @param e
	 *        der Throwable
	 */
	public static void showUncaughtError(Thread t, Throwable e) {
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		String stackTrace = errors.toString();
		
		System.err.print(stackTrace);
		
		JTextArea errorArea = new JTextArea(stackTrace);
		JScrollPane errorPane = new JScrollPane(errorArea) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(480, 320);
			}
		};
		String s = fuzzyFindMessage(e);
		JLabel errorMessage = new JLabel(s, SwingConstants.CENTER);
		
		JPanel jp = new JPanel(new BorderLayout(0, 10));
		jp.add(errorMessage, BorderLayout.PAGE_START);
		jp.add(errorPane, BorderLayout.CENTER);
		
		JOptionPane.showMessageDialog(	null, jp,
										String.format(	"Error in Thread \"%s\"",
														t),
										JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Sucht anhand etwas fuzzy definierter Regeln nach der
	 * "Top-Most" Fehler-Nachricht
	 * 
	 * @param t
	 *        das Trowable-Objekt
	 * @return
	 * 		die Nachricht, wenn eine gefunden wurde. Ansonsten
	 *         die allerletzte Message.
	 */
	@SuppressWarnings("all")
	private static @Nullable String fuzzyFindMessage(@Nullable Throwable t) {
		if(t == null)
			return "Kein Fehler";
		String s = "";
		do {
			try {
				s = t.getMessage();
				if(s != null && !s.isEmpty()) {
					s = s.trim();
					Class.forName(s, false, null);
				}
				t = t.getCause();
			} catch(ClassNotFoundException e) {
				e.getClass(); // Einfach damit Eclipse nicht meckert.
				break;
			}
		} while(t != null);
		return s;
	}
	
	/**
	 * Gibt ein gemischtes Array von Array-Indezes von 0 bis 3 zurück.
	 * Verwendet ein Sortiernetzwerk (Sortiernetzwerke beim mischen?
	 * Finde die Source leider nicht mehr, aber es funktioniert
	 * einwandfrei)
	 * 
	 * @return
	 * 		ein gemischtes Array von 0 bis 3
	 */
	public static int[] randomShuffleOf4() {
		int[] nums = new int[4];
		for(int i = 0; i < 4; ++i) {
			int value = (int) (Math.random() * 1_000_000);
			for(int j = 0; j < i; ++j) {
				if(value == nums[j]) {
					value = (int) (Math.random() * 1_000_000);
					j--;
				}
			}
			nums[i] = value;
		}
		int[] sort = {0, 1, 2, 3};
		boolean swap = false;
		int temp;
		
		swap = nums[sort[0]] > nums[sort[2]];
		if(swap) {
			temp = sort[0];
			sort[0] = sort[2];
			sort[2] = temp;
		}
		swap = nums[sort[1]] > nums[sort[3]];
		if(swap) {
			temp = sort[1];
			sort[1] = sort[3];
			sort[3] = temp;
		}
		swap = nums[sort[0]] > nums[sort[1]];
		if(swap) {
			temp = sort[0];
			sort[0] = sort[1];
			sort[1] = temp;
		}
		swap = nums[sort[2]] > nums[sort[3]];
		if(swap) {
			temp = sort[2];
			sort[2] = sort[3];
			sort[3] = temp;
		}
		swap = nums[sort[1]] > nums[sort[2]];
		if(swap) {
			temp = sort[1];
			sort[1] = sort[2];
			sort[2] = temp;
		}
		
		return sort;
	}
	
	/**
	 * Lädt Spieldaten in die zwei gegebenen Listen.
	 * 
	 * @param gameFile
	 *        der Pfad zur XML-Spieldatei
	 * @param categoryList
	 *        eine Liste der Kategorien. Sollte leer sein, da neue
	 *        Elemente hinzugefügt werden.
	 * @param questionList
	 *        eine Liste der Fragen. Sollte leer sein, da neue
	 *        Elemente hinzugefügt werden.
	 */
	public static void loadData(URL gameFile,
								List<Category> categoryList,
								List<Question<?>> questionList) {
		try {
			File f = new File(gameFile.toURI());
			if(!f.exists()) {
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
					bw.write(defaultText);
				} catch(IOException e) {
					throw new RuntimeException(	"Konnte Spieldatei nicht initialisieren.",
												e);
				}
				return;
			}
			Document doc = new SAXBuilder().build(gameFile);
			
			List<@Nullable Element> cats = doc.getRootElement()
					.getChildren("category");
			categoryList.addAll(cats.stream().map(element -> {
				assert element != null;
				
				Element nameElement = element.getChild("name");
				Element descElement = element.getChild("description");
				
				if(nameElement == null) {
					showErrorExitOnNoOrClose(	"Falsch formatierte Kategorie",
												"Eine Kategorie hat keinen Namen. "
														+ "Wenn die Daten gespeichert werden, "
														+ "wird diese Kategorie mitsamt Fragen "
														+ "nicht gespeichert und damit effektiv "
														+ "gelöscht. Fortfahren?");
					return null;
				}
				if(descElement == null) {
					showErrorExitOnNoOrClose(	"Falsch formatierte Kategorie",
												"Eine Kategorie hat keine Beschreibung. "
														+ "Wenn die Daten gespeichert werden, "
														+ "wird diese Kategorie mitsamt Fragen "
														+ "nicht gespeichert und damit effektiv "
														+ "gelöscht. Fortfahren?");
					return null;
				}
				
				List<Question<?>> questions = new LinkedList<>();
				
				element.getChildren("question").forEach(el -> {
					assert el != null;
					
					Question<?> loadedQuestion = Question.loadFromElement(el);
					
					if(loadedQuestion == null)
						return;
					
					questions.add(loadedQuestion);
				});
				Category c = new Category(nameElement.getText()
						.replaceAll("[^ \\S]+", " "), descElement.getText()
								.replaceAll("[^ \\S]+", " "), questions);
				return c;
			}).filter(c -> c != null).collect(Collectors.toList()));
			
			questionList.addAll(categoryList.stream()
					.map(Category::getQuestions).flatMap(List::stream)
					.collect(Collectors.toList()));
			
		} catch(JDOMException e) {
			throw new RuntimeException(e);
		} catch(IOException e) {
			throw new RuntimeException(e);
		} catch(URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Speichert die gegebenen Kategorien (inklusive der beinhaltenen
	 * Fragen) in die gegebene Datei.
	 * 
	 * @param gameFile
	 *        die Datei, in die geschrieben wird.
	 * @param categoryList
	 *        die Liste der Kategorien
	 */
	public static void saveData(File gameFile, List<Category> categoryList) {
		try {
			
			Element game = new Element("game");
			Document doc = new Document(game);
			game.addContent(categoryList.stream()
					.map(cat -> new Element("category")
							.addContent(new Element("name")
									.setText(cat.getName()))
							.addContent(new Element("description")
									.setText(cat.getDescription()))
							.addContent(cat.getQuestions().stream()
									.map(Question::save)
									.collect(Collectors.toList())))
					.collect(Collectors.toList()));
			
			
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			
			try(Writer fw = new FileWriter(gameFile)) {
				xmlOutput.output(doc, fw);
			}
			
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Die sogenannte <a href=
	 * "http://de.wikipedia.org/wiki/Levenshtein-Distanz">Levenshtein-Distanz</a>
	 * ist ein Mass der Verschiedenheit zweier gegebenen
	 * Zeichenketten.
	 * <br>
	 * Diese Verschiedenheit wird in <em>Schritten</em> gemessen, die
	 * nötig sind, um eine Kette in eine andere umzuwandeln.
	 * <br>
	 * Diese Schritte sind unterteilt in:
	 * <ul>
	 * <li>Einfügen eines Zeichens (tor &rArr;
	 * tor<strong>n</strong>),</li>
	 * <li>Entfernen eines Zeichens (<strong>f</strong>ate &rArr;
	 * ate), <em>oder</em></li>
	 * <li>Ersetzen eines Zeichens (<strong>B</strong>ug &rArr;
	 * <strong>Z</strong>ug)</li>
	 * </ul>
	 * , wobei jeder der Schritte einzeln gleich gewichtet wird.
	 * 
	 * @param lhs
	 *        der erste String
	 * @param rhs
	 *        der zweite String
	 * @return
	 * 		die Distanz zwischen {@code lhs} und {@code rhs}
	 * 
	 * @see <a href=
	 *      "https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java">Wikibooks-Implementation
	 *      (Source)</a>
	 */
	public static int levenshteinDistance(CharSequence lhs, CharSequence rhs) {
		int len0 = lhs.length() + 1;
		int len1 = rhs.length() + 1;
		
		// the array of distances
		int[] cost = new int[len0];
		int[] newcost = new int[len0];
		
		// initial cost of skipping prefix in String s0
		for(int i = 0; i < len0; i++) {
			cost[i] = i;
		}
		
		// dynamically computing the array of distances
		
		// transformation cost for each letter in s1
		for(int j = 1; j < len1; j++) {
			// initial cost of skipping prefix in String s1
			newcost[0] = j;
			
			// transformation cost for each letter in s0
			for(int i = 1; i < len0; i++) {
				// matching current letters in both strings
				int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;
				
				// computing cost for each transformation
				int cost_replace = cost[i - 1] + match;
				int cost_insert = cost[i] + 1;
				int cost_delete = newcost[i - 1] + 1;
				
				// keep minimum cost
				newcost[i] = Math.min(	Math.min(cost_insert, cost_delete),
										cost_replace);
			}
			
			// swap cost/newcost arrays
			int[] swap = cost;
			cost = newcost;
			newcost = swap;
		}
		
		// the distance is the cost for transforming all letters in
		// both strings
		return cost[len0 - 1];
	}
	
	
	/**
	 * Öffnet einen Dialog mit der Fehlermeldung. Wenn auf "Nein"
	 * gedrückt wird oder das Fenster geschlossen wird, wird das
	 * Programm beendet.
	 * 
	 * @param windowTitle
	 *        der Fenstertitel
	 * @param message
	 *        die anzuzeigende Fehlermeldung
	 * @param formatArgs
	 *        Argumente für {@link String#format(String, Object...)},
	 *        werden mit {@code message} verbunden.
	 */
	public static void showErrorExitOnNoOrClose(String windowTitle,
												String message,
												Object... formatArgs) {
		int c = JOptionPane
				.showOptionDialog(	null, String.format(message, formatArgs),
									windowTitle, JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE, null, null,
									null);
		if(c == JOptionPane.NO_OPTION || c == JOptionPane.CLOSED_OPTION) {
			System.exit(1);
		}
	}
}
