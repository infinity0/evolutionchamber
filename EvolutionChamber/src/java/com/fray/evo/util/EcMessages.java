package com.fray.evo.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Retrieves string messages from a ResourceBundle properties file for i18n.
 * 
 * @author mike.angstadt
 * 
 */
public class EcMessages {
	/**
	 * Regex used to look for references to other properties. For example:
	 * {foo.bar}.
	 */
	private static final Pattern propRefRegex = Pattern.compile("\\{(.*?)\\}");

	private final ResourceBundle messages;

	/**
	 * Constructor.
	 * 
	 * @param bundleName the base name of the resource bundle, a fully qualified
	 * class name
	 */
	public EcMessages(String bundleName) {
		messages = ResourceBundle.getBundle(bundleName);
	}

	/**
	 * Gets a message.
	 * 
	 * @param key the message key
	 * @return the message
	 */
	public String getString(String key) {
		String message = messages.getString(key);

		//handle references to other properties
		Matcher m = propRefRegex.matcher(message);
		while (m.find()) {
			try {
				String refMessage = getString(m.group(1).trim());
				message = message.replace(m.group(0), refMessage);
			} catch (MissingResourceException e) {
				//ignore references to non-existant properties and to argument references ("{0}")
			}
		}

		return message;
	}

	/**
	 * Gets a message that has arguments.
	 * 
	 * @param key the message key
	 * @param arguments the arguments to populate the message with
	 * @return the formatted message
	 */
	public String getString(String key, Object... arguments) {
		return MessageFormat.format(getString(key), arguments);
	}
}
