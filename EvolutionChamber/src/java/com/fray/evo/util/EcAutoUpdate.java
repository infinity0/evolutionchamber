package com.fray.evo.util;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.SwingWorker;


public class EcAutoUpdate extends SwingWorker<Void, Void> {
	
	private static String 	downloadPrefix = "http://evolutionchamber.googlecode.com/files/evolutionchamber-version-";
	private boolean 		updateAvailable= false;
	private boolean			updating       = false;
	private String			latestVersion  = "";
	private String          jarFile        = "";
	
	public EcAutoUpdate(String ecVersion) {
		System.out.println("Checking for updates...");
		this.latestVersion	 	= getLatestVersion(ecVersion);
		
		if( !this.latestVersion.equals( ecVersion ) )
			this.updateAvailable = true;
	}
	
	public boolean isUpdateAvailable() {
		return updateAvailable;
	}
	
	public boolean isUpdating() {
		return updating;
	}
	
	private String getLatestVersion(String ecVersion) {
		String latestVersion = ecVersion;
		// This implementation assumes that each version posted to the page is incremental
		// if we miss a number (like in release 0011) the downloaded version will be 0010
		// even if newer releases are present.
		try {
			int responseCode = 200;
			while(responseCode == 200) {
				latestVersion = String.format("%04d",Integer.parseInt(latestVersion) + 1);
				URL u = new URL(downloadPrefix + latestVersion + ".jar");
				responseCode = ( (HttpURLConnection) u.openConnection() ).getResponseCode();
				if (responseCode != 200)
					latestVersion = String.format("%04d",Integer.parseInt(latestVersion) - 1);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return latestVersion;
	}
	
    @Override
    public Void doInBackground() {
        int progress = 0;
        setProgress(0);
        this.updating = true;
		try {
			// Heavily based on http://www.java2s.com/Tutorial/Java/0320__Network/SavebinaryfilefromURL.htm
			URL u 				= new URL(downloadPrefix + this.latestVersion + ".jar");
		    URLConnection uc 	= u.openConnection();
		    String contentType 	= uc.getContentType();
		    int contentLength 	= uc.getContentLength();
		    if (contentType.startsWith("text/") || contentLength == -1) {
		      throw new IOException("This is not a binary file.");
		    }
		    InputStream raw = uc.getInputStream();
		    InputStream in 	= new BufferedInputStream(raw);
		    byte[] data 	= new byte[contentLength];
		    int bytesRead 	= 0;
		    int offset 		= 0;
		    while (offset < contentLength) {
		      bytesRead = in.read(data, offset, data.length - offset);
		      if (bytesRead == -1)
		        break;
		      offset += bytesRead;
		      progress = (int)( (offset / (float)contentLength) * 100 );
		      setProgress(progress);
		    }
		    in.close();

		    if (offset != contentLength) {
		        throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
		    }

			String filename = u.getFile().substring( u.getFile().lastIndexOf('/') + 1);
			FileOutputStream out = new FileOutputStream(filename);
			out.write(data);
			out.flush();
			out.close();
			this.jarFile = filename;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }

    /*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
    	setProgress(100);
        String javaBin = System.getProperty("java.home") + "/bin/java";
        String restartCmd[] = new String[] { javaBin, "-jar", this.jarFile};
        try {
			Runtime.getRuntime().exec( restartCmd );
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.exit(0);
    }
}
