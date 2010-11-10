package com.fray.evo.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

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
		} catch (UnknownHostException e) {
			// If this happens then our network connection is probably down.
			// We return the current version as there is no way to download any updates.
			System.out.println("Update check failed - check your internet connection");
			latestVersion = ecVersion;
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
		    
		    String filename = u.getFile().substring( u.getFile().lastIndexOf('/') + 1);
			FileOutputStream out = new FileOutputStream(filename);
		    InputStream raw = uc.getInputStream();
		    InputStream in 	= new BufferedInputStream(raw);
		    byte[] buf = new byte[1024];
		    int bytesRead = 0;
		    int offset = 0;
		    while ((bytesRead = in.read(buf)) != -1){
		    	out.write(buf, 0, bytesRead);
		    	offset += bytesRead;
		    	progress = (int)( (offset / (float)contentLength) * 100 );
			    setProgress(progress);
		    }
		    in.close();
		    out.close();

		    if (offset != contentLength) {
		        throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
		    }
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
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String restartCmd[] = new String[] { javaBin, "-jar", this.jarFile};
        try {
			Runtime.getRuntime().exec( restartCmd );
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.exit(0);
    }
}
