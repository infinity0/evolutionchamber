package sc2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.List;

/**
** Misc utilities for processing arguments.
*/
final public class ArgUtils {

	private ArgUtils() { }

	public static IllegalArgumentException ia(String msg) { return new IllegalArgumentException(msg); }

	public static <T> T nonNull(T o, String desc) {
		if (o == null) { throw ia(desc + " must not be null"); }
		return o;
	}

	public static <T> T[] nullSafeCopy(T[] a, T[] d) {
		return (a == null)? d: Arrays.copyOf(a, a.length);
	}

	public static <T> Set<T> non_null_immute_set(Set<T> set) {
		return (set == null)? Collections.<T>emptySet(): Collections.<T>unmodifiableSet(set);
	}

	public static <T> T[] copyOf(T[] a) {
		return Arrays.copyOf(a, a.length);
	}

	public static <T> T[] nonEmpty(T[] arr, String desc) {
		if (nonNull(arr, desc).length == 0) { throw ia(desc + "[] must have at least one element in"); }
		return arr;
	}

	public static <E extends Enum<E>> E enumFromUncased(Class<E> enumType, String s) {
		return Enum.valueOf(enumType, s.toUpperCase());
	}

	public static <E extends Enum<E>> E enumFromFirstChar(Class<E> enumType, String s) {
		return s.length() == 0? Enum.valueOf(enumType, "\0"): Enum.valueOf(enumType, s.substring(0,1));
	}

	public static <E extends Enum<E>> E sEnumFromChar(Class<E> enumType, char c) {
		return Enum.valueOf(enumType, new String(new char[]{c}));
	}

	/**
	** Useful for dealing with e.g. split() methods that return a list with an
	** empty string, instead of an empty list.
	**
	** FAIAP stands for "For All Intents And Purposes".
	*/
	public static boolean isFAIAPEmpty(List<String> item) {
		switch (item.size()) {
		case 0: return true;
		case 1: return item.get(0).length() == 0;
		default: return false;
		}
	}

}
