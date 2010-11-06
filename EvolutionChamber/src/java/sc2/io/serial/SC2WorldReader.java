package sc2.io.serial;

import sc2.SC2World;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;

/**
** Reads a {@link SC2World} from a file.
*/
public class SC2WorldReader {

	protected BufferedReader rd;

	public SC2WorldReader(Reader rd) throws IOException {
		this.rd = new BufferedReader(rd);
	}

	public SC2WorldReader(String path) throws IOException {
		this(new FileReader(path));
	}

	public SC2World read() throws IOException {
		SC2World world = new SC2World();
		SC2IOFactory factory = new SC2IOFactory(world);

		for (String line = rd.readLine(); line != null; line = rd.readLine()) {
			line = trimComment(line);
			if (line.length() == 0) {
				continue;
			} else if (line.charAt(0) == '[') {
				factory.setSection(line.substring(1, line.length()-1));
			} else {
				factory.addAssetType(line);
			}
		}

		factory.postprocess();
		return world;
	}

	public static String trimComment(String line) {
		int i = line.indexOf('#');
		return i < 0? line: line.substring(0, i);
	}

	public static SC2World read(String path) throws IOException {
		return (new SC2WorldReader(path)).read();
	}

}
