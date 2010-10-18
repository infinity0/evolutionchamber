package com.fray.evo.util;

import java.io.File;
import java.io.IOException;

public class EcFileSystem
{

	public static String getTempPath()
			throws IOException
	{
		String rootPath;
		rootPath = createTempDirectory().getName();
		return rootPath;
	}

	private static File createTempDirectory() throws IOException {
		File createTempFile = File.createTempFile("amg", "tmp");
		createTempFile.delete();
		createTempFile = new File(createTempFile.getParentFile(),"etc");
		createTempFile.mkdirs();
		createTempFile.mkdir();
		createTempFile.deleteOnExit();
		return createTempFile;
	}
}
