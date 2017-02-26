package ch.bbbaden.idpa.bru_eap_mey.quiz;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


import javax.swing.JOptionPane;


import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

/**
 * Tests für die Util-Klasse.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Theories.class)
@PrepareForTest({JOptionPane.class, Logger.class})
@SuppressWarnings({"static-method", "boxing"})
public final class UtilTest {
	
	/**
	 * Der ErrorCollector.
	 */
	@Rule
	public ErrorCollector now = new ErrorCollector();
	
	/**
	 * Überprüft, dass der Konstruktor nicht möglich ist.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test(expected = IllegalStateException.class)
	public void testConstructorThrows() throws Exception {
		//
		
		Whitebox.invokeConstructor(Util.class);
		
		//
	}
	
	/**
	 * Der Dekorator funktioniert bei leerem String korrekt.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test
	public void testDecorateMessageForDisplayEmpty() throws Exception {
		String message = "";
		String expected = "<html>";
		
		String decorated = Whitebox
				.invokeMethod(Util.class, "decorateMessageForDisplay", message);
		
		this.now.checkThat("", decorated, is(equalTo(expected)));
	}
	
	/**
	 * Der Dekorator funktioniert bei normalem String korrekt.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test
	public void testDecorateMessageForDisplaySimple() throws Exception {
		String message = "asdf";
		String expected = "<html>asdf";
		
		String decorated = Whitebox
				.invokeMethod(Util.class, "decorateMessageForDisplay", message);
		
		this.now.checkThat("", decorated, is(equalTo(expected)));
	}
	
	/**
	 * Der Dekorator funktioniert bei String mit einem Zeilenumbruch
	 * korrekt.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test
	public void testDecorateMessageForDisplayOneNewline() throws Exception {
		String message = "as\r\ndf";
		String expected = "<html>as\r<br>df";
		
		String decorated = Whitebox
				.invokeMethod(Util.class, "decorateMessageForDisplay", message);
		
		this.now.checkThat("", decorated, is(equalTo(expected)));
	}
	
	/**
	 * Der Dekorator funktioniert bei String mit zwei Zeilenumbrüchen
	 * korrekt.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test
	public void testDecorateMessageForDisplayTwoNewline() throws Exception {
		String message = "as\r\n\r\ndf";
		String expected = "<html>as\r<br>\r<br>df";
		
		String decorated = Whitebox
				.invokeMethod(Util.class, "decorateMessageForDisplay", message);
		
		this.now.checkThat("", decorated, is(equalTo(expected)));
	}
	
	/**
	 * Testet den normalen FuzzyFind.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test
	public void testFuzzyFind() throws Exception {
		Throwable t = mock(Throwable.class);
		when(t.getMessage()).thenReturn("I am the message!");
		
		String message = Whitebox.invokeMethod(	Util.class, "fuzzyFindMessage",
												t);
		
		this.now.checkSucceeds(() -> {
			verify(t).getMessage();
			return null;
		});
		this.now.checkThat(	"Normal message gets returned", message,
							is(equalTo("I am the message!")));
	}
	
	/**
	 * Testet den normalen FuzzyFind mit leerer Nachricht.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test
	public void testFuzzyFindEmptyMessage() throws Exception {
		Throwable t = mock(Throwable.class);
		when(t.getMessage()).thenReturn("");
		String errorMessage = Util.UNKNOWN_ERROR_MESSAGE;
		
		String message = Whitebox.invokeMethod(	Util.class, "fuzzyFindMessage",
												t);
		
		this.now.checkSucceeds(() -> {
			verify(t).getMessage();
			return null;
		});
		this.now.checkThat(	"Empty message is regarded as unknown message.",
							message, is(equalTo(errorMessage)));
	}
	
	/**
	 * Testet den normalen FuzzyFind mit einer {@code null}-Nachricht.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test
	public void testFuzzyFindNullMessage() throws Exception {
		Throwable t = mock(Throwable.class);
		String errorMessage = Util.UNKNOWN_ERROR_MESSAGE;
		
		String message = Whitebox.invokeMethod(	Util.class, "fuzzyFindMessage",
												t);
		
		this.now.checkSucceeds(() -> {
			verify(t).getMessage();
			return null;
		});
		this.now.checkThat(	"Nullary message is regarded as unknown message.",
							message, is(equalTo(errorMessage)));
	}
	
	/**
	 * Testet den FuzzyFind mit Text im Grund.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test
	public void testFuzzyFindInCause() throws Exception {
		Throwable t1 = mock(Throwable.class);
		Throwable t2 = mock(Throwable.class);
		when(t1.getMessage()).thenReturn(Object.class.getName());
		when(t1.getCause()).thenReturn(t2);
		when(t2.getMessage()).thenReturn("I am the deep message!");
		
		String message = Whitebox.invokeMethod(	Util.class, "fuzzyFindMessage",
												t1);
		this.now.checkSucceeds(() -> {
			verify(t1).getMessage();
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(t1).getCause();
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(t2).getMessage();
			return null;
		});
		this.now.checkThat(	"Normal message when found in cause gets returned",
							message, is(equalTo("I am the deep message!")));
	}
	
	/**
	 * Testet den FuzzyFind mit leerem Text im Grund.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test
	public void testFuzzyFindEmptyMessageInCause() throws Exception {
		Throwable t1 = mock(Throwable.class);
		Throwable t2 = mock(Throwable.class);
		when(t1.getMessage()).thenReturn(Object.class.getName());
		when(t1.getCause()).thenReturn(t2);
		when(t2.getMessage()).thenReturn("");
		String errorMessage = Util.UNKNOWN_ERROR_MESSAGE;
		
		String message = Whitebox.invokeMethod(	Util.class, "fuzzyFindMessage",
												t1);
		
		this.now.checkSucceeds(() -> {
			verify(t1).getMessage();
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(t1).getCause();
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(t2).getMessage();
			return null;
		});
		this.now.checkThat(	"Empty message (in cause) is regarded as unknown message.",
							message, is(equalTo(errorMessage)));
	}
	
	/**
	 * Testet den FuzzyFind mit {@code null}-Nachricht im Grund.
	 * 
	 * @throws Exception
	 *         Whiteboxfehler
	 */
	@Test
	public void testFuzzyFindNullMessageInCause() throws Exception {
		Throwable t1 = mock(Throwable.class);
		Throwable t2 = mock(Throwable.class);
		when(t1.getMessage()).thenReturn(Object.class.getName());
		when(t1.getCause()).thenReturn(t2);
		String errorMessage = Util.UNKNOWN_ERROR_MESSAGE;
		
		String message = Whitebox.invokeMethod(	Util.class, "fuzzyFindMessage",
												t1);
		
		this.now.checkSucceeds(() -> {
			verify(t1).getMessage();
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(t1).getCause();
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(t2).getMessage();
			return null;
		});
		this.now.checkThat(	"Nullary message (in cause) is regarded as unknown message.",
							message, is(equalTo(errorMessage)));
	}
	
	/**
	 * Testet den Levenshtein-Algorithmus bei keinem Unterschied.
	 */
	@Test
	public void testLevenshteinSame() {
		String a = "A bard's wife may indeed play better.";
		String b = "A bard's wife may indeed play better.";
		
		int distance = Util.levenshteinDistance(a, b);
		
		this.now.checkThat(	"Equal strings don't have any distance.", distance,
							is(equalTo(0)));
	}
	
	/**
	 * Testet den Levenshtein-Algorithmus bei einer Anfügung.
	 */
	@Test
	public void testLevenshteinAdd() {
		String a = "A bard's wife may indeed play better.";
		String b = "A bard'ss wife may indeed play better.";
		
		int distance = Util.levenshteinDistance(a, b);
		
		this.now.checkThat(	"An addition is a distance of one.", distance,
							is(equalTo(1)));
	}
	
	/**
	 * Testet den Levenshtein-Algorithmus bei einer Entfernung.
	 */
	@Test
	public void testLevenshteinRemove() {
		String a = "A bard'ss wife may indeed play better.";
		String b = "A bard's wife may indeed play better.";
		
		int distance = Util.levenshteinDistance(a, b);
		
		this.now.checkThat(	"A removal is a distance of one.", distance,
							is(equalTo(1)));
	}
	
	/**
	 * Testet den Levenshtein-Algorithmus bei einer Ersetzung.
	 */
	@Test
	public void testLevenshteinReplace() {
		String a = "A bard's wife may indeed play better.";
		String b = "A card's wife may indeed play better.";
		
		int distance = Util.levenshteinDistance(a, b);
		
		this.now.checkThat(	"A replacement is a distance of one.", distance,
							is(equalTo(1)));
	}
	
	@Test
	public void testLoadDataEmpty() throws Exception {
		Assume.assumeTrue("Not yet possible without impacting JaCoCo coverage. "
				+ "Waiting for PowerMock 2 to use ByteBuddy.", false);
	}
	
	/**
	 * Testet den Zufallsgenerator auf seine Grundversprechen: 4
	 * Zahlen von 0 bis 3.
	 */
	@Test
	public void testRandomShuffleSpecifications() {
		Random r = mock(Random.class);
		when(r.nextDouble()).thenReturn(0.1D, 0.2D, 0.3D, 0.4D)
				.thenThrow(new IllegalStateException("Nope. No more."));
		
		int[] answers = Util.randomShuffleOf4(r);
		Arrays.sort(answers);
		
		this.now.checkSucceeds(() -> {
			verify(r, times(4)).nextDouble();
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(r);
			return null;
		});
		this.now.checkThat(	"The array must have a length of 4.", answers.length,
							is(equalTo(4)));
		this.now.checkThat(	"Numbers 0 to 3 are in the array.", answers,
							is(equalTo(new int[] {0, 1, 2, 3})));
	}
	
	/**
	 * Testet, dass die Zufallsfunktion bei gleichem Ergebnis nochmal
	 * aufgerufen wird.
	 */
	@Test
	public void testRandomShuffleDoubles() {
		Random r = mock(Random.class);
		when(r.nextDouble())
				.thenReturn(0.1D, 0.1D, 0.2D, 0.2D, 0.3D, 0.3D, 0.4D)
				.thenThrow(new IllegalStateException("Nope. No more."));
		
		int[] answers = Util.randomShuffleOf4(r);
		Arrays.sort(answers);
		
		this.now.checkSucceeds(() -> {
			verify(r, times(7)).nextDouble();
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(r);
			return null;
		});
		this.now.checkThat(	"The array must have a length of 4.", answers.length,
							is(equalTo(4)));
		this.now.checkThat(	"Numbers 0 to 3 are in the array.", answers,
							is(equalTo(new int[] {0, 1, 2, 3})));
	}
	
	/**
	 * Überprüft alle Permutationen für das Ergebnis.
	 * 
	 * @param params
	 *        zwei int-Arrays, eines mit den Werten für den
	 *        Zufallsgenerator, das andere für das Ergebnis des
	 *        Shuffle-Algorithmus.
	 * @see ArrayOf4Supplier
	 */
	@Theory
	@Test
	public void testRandomShuffleAllOrders(@ParametersSuppliedBy(ArrayOf4Supplier.class) int[][] params) {
		Random r = mock(Random.class);
		when(r.nextDouble())
				.thenReturn((params[0][0] + 1) / 10D, (params[0][1] + 1) / 10D,
							(params[0][2] + 1) / 10D, (params[0][3] + 1) / 10D)
				.thenThrow(new IllegalStateException("Nope. No more."));
		
		int[] answers = Util.randomShuffleOf4(r);
		
		this.now.checkThat("Order is given.", answers, is(equalTo(params[1])));
	}
	
	@Test
	public void testSaveDataEmpty() throws Exception {
		Assume.assumeTrue("Not yet possible without impacting JaCoCo coverage. "
				+ "Waiting for PowerMock 2 to use ByteBuddy.", false);
	}
	
	/**
	 * Testet die Fehleranzeige beim Fortfahren mit der Ausführung.
	 */
	@Test
	public void testShowErrorExitOnNoOrCloseOnContinue() {
		String title = "title";
		String formatString = "message %s";
		String formatArg = "formatted";
		Runtime r = mock(Runtime.class);
		Runtime old = Runtime.getRuntime();
		Whitebox.setInternalState(Runtime.class, "currentRuntime", r);
		mockStatic(JOptionPane.class);
		when(JOptionPane
				.showOptionDialog(	any(), any(), any(), anyInt(), anyInt(),
									any(), any(), any()))
											.thenReturn(JOptionPane.YES_OPTION);
		
		Util.showErrorExitOnNoOrClose(title, formatString, formatArg);
		
		this.now.checkSucceeds(() -> {
			verifyStatic();
			JOptionPane.showOptionDialog(	any(),
											eq(String.format(	formatString,
																formatArg)),
											eq(title),
											eq(JOptionPane.YES_NO_OPTION),
											eq(JOptionPane.WARNING_MESSAGE),
											any(), isNull(Object[].class),
											isNull());
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(r, never()).exit(anyInt());
			return null;
		});
		// CLEANUP
		Whitebox.setInternalState(Runtime.class, "currentRuntime", old);
	}
	
	/**
	 * Testet die Fehleranzeige beim Beenden des Programms.
	 */
	@Test
	public void testShowErrorExitOnNoOrCloseOnEnd() {
		String title = "title";
		String formatString = "message %s";
		String formatArg = "formatted";
		Runtime r = mock(Runtime.class);
		Runtime old = Runtime.getRuntime();
		Whitebox.setInternalState(Runtime.class, "currentRuntime", r);
		mockStatic(JOptionPane.class);
		when(JOptionPane
				.showOptionDialog(	any(), any(), any(), anyInt(), anyInt(),
									any(), any(), any()))
											.thenReturn(JOptionPane.NO_OPTION);
		
		Util.showErrorExitOnNoOrClose(title, formatString, formatArg);
		
		this.now.checkSucceeds(() -> {
			verifyStatic();
			JOptionPane.showOptionDialog(	any(),
											eq(String.format(	formatString,
																formatArg)),
											eq(title),
											eq(JOptionPane.YES_NO_OPTION),
											eq(JOptionPane.WARNING_MESSAGE),
											any(), isNull(Object[].class),
											isNull());
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(r).exit(eq(1));
			return null;
		});
		// CLEANUP
		Whitebox.setInternalState(Runtime.class, "currentRuntime", old);
	}
	
	/**
	 * Testet die Fehleranzeige beim Schliessen der Anzeige.
	 */
	@Test
	public void testShowErrorExitOnNoOrCloseOnClose() {
		String title = "title";
		String formatString = "message %s";
		String formatArg = "formatted";
		Runtime r = mock(Runtime.class);
		Runtime old = Runtime.getRuntime();
		Whitebox.setInternalState(Runtime.class, "currentRuntime", r);
		mockStatic(JOptionPane.class);
		when(JOptionPane
				.showOptionDialog(	any(), any(), any(), anyInt(), anyInt(),
									any(), any(),
									any())).thenReturn(JOptionPane.CLOSED_OPTION);
		
		Util.showErrorExitOnNoOrClose(title, formatString, formatArg);
		
		this.now.checkSucceeds(() -> {
			verifyStatic();
			JOptionPane.showOptionDialog(	any(),
											eq(String.format(	formatString,
																formatArg)),
											eq(title),
											eq(JOptionPane.YES_NO_OPTION),
											eq(JOptionPane.WARNING_MESSAGE),
											any(), isNull(Object[].class),
											isNull());
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(r).exit(eq(1));
			return null;
		});
		// CLEANUP
		Whitebox.setInternalState(Runtime.class, "currentRuntime", old);
	}
	
	@Test
	public void testShowUncaughtError() {
		Assume.assumeTrue("Can not really mock Logger.getLogger or the "
				+ "constructors of JTextArea etc. so there will be "
				+ "LinkageErrors at runtime.", false);
	}
	
	@Test
	public void testShowUncaughtErrorWithMessage() {
		Assume.assumeTrue("Can not really mock Logger.getLogger or the "
				+ "constructors of JTextArea etc. so there will be "
				+ "LinkageErrors at runtime.", false);
	}
	
	/**
	 * Der Supplier für den
	 * {@link UtilTest#testRandomShuffleAllOrders(int[][])}-Test.
	 */
	public static class ArrayOf4Supplier extends ParameterSupplier {
		
		@Override
		public List<PotentialAssignment> getValueSources(ParameterSignature sig)
				throws Throwable {
			List<PotentialAssignment> list = new ArrayList<>();
			
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{0, 1, 2, 3}, {0, 1, 2, 3}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{0, 1, 3, 2}, {0, 1, 3, 2}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{0, 2, 1, 3}, {0, 2, 1, 3}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{0, 2, 3, 1}, {0, 3, 1, 2}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{0, 3, 1, 2}, {0, 2, 3, 1}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{0, 3, 2, 1}, {0, 3, 2, 1}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{1, 0, 2, 3}, {1, 0, 2, 3}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{1, 0, 3, 2}, {1, 0, 3, 2}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{1, 2, 0, 3}, {2, 0, 1, 3}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{1, 2, 3, 0}, {3, 0, 1, 2}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{1, 3, 0, 2}, {2, 0, 3, 1}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{1, 3, 2, 0}, {3, 0, 2, 1}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{2, 0, 1, 3}, {1, 2, 0, 3}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{2, 0, 3, 1}, {1, 3, 0, 2}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{2, 1, 0, 3}, {2, 1, 0, 3}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{2, 1, 3, 0}, {3, 1, 0, 2}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{2, 3, 0, 1}, {2, 3, 0, 1}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{2, 3, 1, 0}, {3, 2, 0, 1}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{3, 0, 1, 2}, {1, 2, 3, 0}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{3, 0, 2, 1}, {1, 3, 2, 0}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{3, 1, 0, 2}, {2, 1, 3, 0}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{3, 1, 2, 0}, {3, 1, 2, 0}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{3, 2, 0, 1}, {2, 3, 1, 0}}));
			list.add(PotentialAssignment
					.forValue(	"params",
								new int[][] {{3, 2, 1, 0}, {3, 2, 1, 0}}));
			
			return list;
		}
	}
}
