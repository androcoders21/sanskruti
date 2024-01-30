package com.sanskruti.volotek;

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadZipTask extends AsyncTask<String, Void, Void> {

    private static final String LOCAL_STORAGE_PATH = "/storage/emulated/0/Pictures/Sanskruti/Sanskruti";
    private static final String ZIP_FILE_NAME = "testframe1.zip";

    @Override
    protected Void doInBackground(String... params) {
        String downloadUrl = params[0];

        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            FileOutputStream outputStream = new FileOutputStream(LOCAL_STORAGE_PATH + "/" + ZIP_FILE_NAME);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}