package processing;

public class StringUtils {
	/**
	 * stringArrayContainsAll - check if a given collection of strings is
	 * present in another collection of strings, irrespective of order.
	 * 
	 * @param collection
	 *            The collection of strings to check inside.
	 * @param query
	 *            The collection of strings to look for.
	 * @return Returns true if the query strings were all found inside the
	 *         collection, otherwise false.
	 */
	public static boolean stringArrayContainsAll(String[] collection, String[] query) {

		for (String which : query) {
			if (!stringArrayContains(collection, which)) {
				return false;
			}
		}

		return true;
	}

	public static int findWhichStringContainsSubstrings(String[] strings, String[] substrings) {
		for (int i = 0; i < strings.length; i++) {
			boolean foundIt = true;
			for (int ssi = 0; ssi < substrings.length; ssi++) {
				if (!(strings[i].indexOf(substrings[ssi]) > -1)) {
					foundIt = false;
				}
			}
			if (foundIt)
				return i;
		}
		return -1;
	}

	/**
	 * stringArrayContains - check if a given string is present in a collection
	 * of strings.
	 * 
	 * @param collection
	 *            The collection of strings to check inside.
	 * @param query
	 *            The string to look for.
	 * @return Returns true if the query string was present in the collection,
	 *         otherwise false.
	 */
	public static boolean stringArrayContains(String[] collection, String query) {

		for (String which : collection) {
			if (which.equals(query)) {
				return true;
			}
		}

		return false;
	}

}
