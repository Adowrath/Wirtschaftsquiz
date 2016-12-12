package ch.bbbaden.idpa.bru_eap_mey.quiz;

/**
 * Eine Reihe verschiedener Utility Methoden.
 */
public class Util {
	
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
	 * <li>Einfügen eines Zeichens (tor &rArr; tor<strong>n</strong>),</li>
	 * <li>Entfernen eines Zeichens (<strong>f</strong>ate &rArr; ate), <em>oder</em></li>
	 * <li>Ersetzen eines Zeichens (<strong>B</strong>ug &rArr; <strong>Z</strong>ug)</li>
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
}
