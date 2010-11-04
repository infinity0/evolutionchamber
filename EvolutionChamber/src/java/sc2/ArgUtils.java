package sc2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
** Misc utilities for processing arguments.
*/
final public class ArgUtils {

	private ArgUtils() { }

	public static <T> T non_null(String desc, T o) {
		if (o == null) { throw new IllegalArgumentException(desc + " must not be null"); }
		return o;
	}

	public static <T> T[] non_null_copy(T[] a, T[] d) {
		return (a == null)? d: Arrays.copyOf(a, a.length);
	}

	public static <T> Set<T> non_null_immute_set(Set<T> set) {
		return (set == null)? Collections.<T>emptySet(): Collections.<T>unmodifiableSet(set);
	}

}
