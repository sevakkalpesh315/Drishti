package com.gkbhitech.drishti.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.net.ftp.FTPClient;


import android.os.Environment;
import android.util.Log;

public class FtpFileDownloader {

	private static final String tag = "Downloader"; 
	
	public static void DownloadFile(String ip, String remoteLocation, String fileName) {
        try {
        	Log.d("Downloader", "download start");
            //File sdcard = Environment.getExternalStorageDirectory();
            File dir = new File (Constant.DRISHTI_PATH_ON_SD_CARD);
            dir.mkdirs();
            
            File file = new File(dir.getAbsolutePath()+"/"+fileName);
            
            if(!file.exists()){ 
	
	            if(Constant.log)Log.i(tag, " hidden folder created .........");
	            if(Constant.log)Log.i(tag, "ip : "+ip);
	            if(Constant.log)Log.i(tag, "remoteLocation : "+remoteLocation);
	            if(Constant.log)Log.i(tag, "fileName : "+fileName);
	            
	           /* URL u = new URL(fileURL);
	            HttpURLConnection c = (HttpURLConnection) u.openConnection();
	            c.setRequestMethod("GET");
	            c.setDoOutput(true);
	            c.connect();*/
	            FileOutputStream f = new FileOutputStream(new File(dir, fileName));
	
	            FTPClient ftpClient = new FTPClient();
	            ftpClient.connect(ip, 21);
	            ftpClient.login("gkbsap", "gkb@123");
	            ftpClient.isConnected();
	            if(Constant.log)Log.i(tag, "is ftp connected : "+ftpClient.isConnected());
	            
	            boolean downloaded  = ftpClient.retrieveFile(remoteLocation, f);
	            
	            if(Constant.log)Log.i(tag, "is downloaded : "+downloaded);
	            /*InputStream in = c.getInputStream();
	
	            byte[] buffer = new byte[1024];
	            int len1 = 0;
	            while ((len1 = in.read(buffer)) > 0) {
	                f.write(buffer, 0, len1);
	            }*/
	            f.close();
	            if(Constant.log)Log.d("Downloader", "download completed");
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	if(Constant.log)Log.i("Downloader", e.getMessage());
        }
    }

}
