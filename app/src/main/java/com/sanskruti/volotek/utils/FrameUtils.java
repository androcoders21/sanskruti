package com.sanskruti.volotek.utils;

import static androidx.core.content.ContextCompat.startActivity;
import static com.sanskruti.volotek.utils.MyUtils.unzipFile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.custom.poster.model.Sticker_info;
import com.sanskruti.volotek.custom.poster.model.Text_infoposter;
import com.sanskruti.volotek.model.BusinessItem;
import com.sanskruti.volotek.model.FrameModel;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FrameUtils {

    public static String realX = "";
    public static String realY = "";
    public static String calcWidth = "";
    public static String calcHeight = "";
    public static ArrayList<Sticker_info> stickerInfoArrayList = new ArrayList<>();
    public static ArrayList<Text_infoposter> textInfoArrayList = new ArrayList<>();
    static int templateRealWidth = 0;
    static int templateRealHeight = 0;
    public static OnFrameStatus listner;

    static PreferenceManager preferenceManager;


    private static ArrayList<String> fontNames = new ArrayList<>();
    private static int downloadId;


    public void getOnFrameStatus(OnFrameStatus status) {

        listner = status;
    }


    public FrameUtils(Activity context, FrameModel frameModel, OnFrameStatus onFrameStatus) {

        preferenceManager = new PreferenceManager(context);

        listner = onFrameStatus;

        File path = new File(MyUtils.getFolderPath(context, context.getString(R.string.poster_frame)), frameModel.getTitle());


        if (path.exists()) {

            proccessFrame(context, frameModel, path.getAbsolutePath(), listner);

        } else {


            downloadTemplate(context, frameModel);

        }


    }


    public class DownloadZipTask extends AsyncTask<String, Void, Boolean> {

        private String LOCAL_STORAGE_PATH = "";
        private String ZIP_FILE_NAME = "testframe1.zip";
        private FrameModel yourModel;
        private Activity context;

        private String title_without = "";

        public DownloadZipTask(Activity context, FrameModel frameModel, String title) {
            this.yourModel = frameModel;
            this.context = context;
            LOCAL_STORAGE_PATH = String.valueOf(MyUtils.getFolderPath(context, context.getString(R.string.poster_frame)));
            ZIP_FILE_NAME = String.valueOf(title + ".zip");
            title_without = title;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String downloadUrl = params[0];

            try {
                File directory = new File(MyUtils.getDownloadFolderPath(context, "Sanskruti"));
                if (!directory.exists()) {
                    directory.mkdirs();
                } else {
                    // Create the file within the pictures directory
                    File outputFile = new File(directory, ZIP_FILE_NAME);
                    Log.i("checkErrordata", "1234 : file path: " + outputFile.getAbsolutePath());
                    Log.i("checkErrordata", "1234 : picturesDirectory path: " + directory.getAbsolutePath());

                    Log.i("checkErrordata", "Full File Address: " + outputFile.getAbsolutePath());
                    Log.i("checkErrordata", "Download url : " + downloadUrl);
                    URL url = new URL(downloadUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    FileOutputStream outputStream = new FileOutputStream(outputFile);

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    inputStream.close();
                    urlConnection.disconnect();
                    return true;

                }

                Log.i("checkErrordata", "123 Download completed: " + directory.getAbsolutePath() + "/" + ZIP_FILE_NAME);
            } catch (IOException e) {
                Log.i("checkErrordata", "Error during download: " + e.getMessage());
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean check) {
            Log.i("checkErrordata", "123456789 download complete  = " + String.valueOf(check));
            try {


                File targetDirectory = new File(MyUtils.getDownloadFolderPath(context, "Sanskruti"));

                File file = new File(targetDirectory, ZIP_FILE_NAME);

                Log.i("checkErrordata", "Final : zip file  path: " + file.getAbsolutePath() + " unzip file path = " + targetDirectory.getAbsolutePath());

//                UnzipFileTask unzipTask = new UnzipFileTask(context);
//                unzipTask.execute(file.getAbsolutePath(), targetDirectory.getAbsolutePath());

                // Check and request MANAGE_EXTERNAL_STORAGE permission if needed
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    context.startActivity(intent);
                    return; // Wait for permission grant before proceeding
                }
                unzipFile(file.getAbsolutePath(), targetDirectory.getAbsolutePath(), path -> {
                    Log.i("checkErrordata", "Final : file path: " + path);
                    Log.i("checkErrordata", "Final : Title path: " + yourModel.getTitle()+" real file name = "+title_without);
                    Log.i("checkErrordata", "Final : path: " + new File(path, yourModel.getTitle()).getAbsolutePath());
                    proccessFrame(context, yourModel, new File(path, yourModel.getTitle()).getAbsolutePath(), listner);

                });
            } catch (Exception e) {
                Toast.makeText(context, "Download Failed.", Toast.LENGTH_SHORT).show();
                Log.i("checkErrordata", "error response = " + String.valueOf(e.getMessage()));
                TemplateUtils.dismissDialog();
//                throw new RuntimeException(e);
            }
        }
    }

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 5;

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private void downloadTemplate(Activity context, FrameModel frameModel) {


        new DownloadZipTask(context, frameModel, generateRandomString()).execute(frameModel.getFrame_zip().replace(" ", ""));


    }


    public static void proccessFrame(Activity context, FrameModel model, String path, OnFrameStatus onFrameStatus) {


        stickerInfoArrayList.clear();
        textInfoArrayList.clear();

        fontNames.clear();

        listner = onFrameStatus;

        try {

            JSONObject jsonObject = new JSONObject(MyUtils.readFromFile(new File(path, "json/" + model.getTitle() + ".json").getAbsolutePath()));

            JSONArray jsonArrayLayers = jsonObject.getJSONArray("layers");

            for (int i = 0; i < jsonArrayLayers.length(); i++) {

                JSONObject jsonObject1 = jsonArrayLayers.getJSONObject(i);
                Log.i("checkErrordata", "Final : jsonObject1: " + String.valueOf(jsonObject1));
                processJson(context, model, i, jsonObject1,path);

                File file = new File(Configure.GetFileDir(context).getPath() + File.separator + "font");

                // find fonts and copy to font directory.

                for (int i1 = 0; i1 < fontNames.size(); i1++) {

                    try {

                        File sourceFile = new File(path, "fonts/" + fontNames.get(i1) + ".ttf");

                        File dest = new File(file.getPath(), fontNames.get(i1) + ".ttf");

                        if (sourceFile.exists()) {

                            FileUtils.copyFile(sourceFile, dest);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

            }

            /// TemplateUtils.dismissDialog();

            listner.onFrameLoaded(stickerInfoArrayList, textInfoArrayList);
        } catch (Exception e) {

            e.printStackTrace();
            Log.d("onFrameLoaded__", e.getMessage());
        }
    }

    public interface OnLogoDownloadListener {
        void onLogoDownloaded(String logoPath);

        void onLogoDownloadError();
    }

    public static void processJson(Activity context, FrameModel model, int i, JSONObject jsonObject1,String path) throws Exception {


        String businessLogo = preferenceManager.getString(Constant.BUSINESS_LOGO_PATH);

        String type = jsonObject1.getString("type");
        String name = jsonObject1.getString("name");

        String width = String.valueOf(jsonObject1.getInt("width"));
        String height = String.valueOf(jsonObject1.getInt("height"));

        if (i == 0) {
            templateRealWidth = Integer.parseInt(width);
            templateRealHeight = Integer.parseInt(height);
        }

        String x = String.valueOf(jsonObject1.getInt("x"));
        String y = String.valueOf(jsonObject1.getInt("y"));


        String calcWidth = "";
        String calcHeight = "";

        String realX = String.valueOf(((int) Math.round(Float.parseFloat(x)) * 100) / templateRealWidth);
        String realY = String.valueOf((int) Math.round(Float.parseFloat(y) * 100) / templateRealHeight);

        calcWidth = String.valueOf(Integer.parseInt(width) * 100 / templateRealWidth + Integer.parseInt(realX));
        calcHeight = String.valueOf(Integer.parseInt(height) * 100 / templateRealHeight + Integer.parseInt(realY));

        if (type != null) {

            if (type.contains("image")) {

                Sticker_info info = new Sticker_info();
                info.setSticker_id(String.valueOf(i));

                String stickerUrl = MyUtils.getDownloadFolderPath(context, context.getString(R.string.poster_frame)) + File.separator + model.getTitle() + File.separator + jsonObject1.getString("src").replace("../", "");
                Log.i("checkErrordata", "Final : path: " + String.valueOf(path));
                Log.i("checkErrordata", "Final : stickerUrl: " + String.valueOf(stickerUrl));

                if (name.equals("logo")) {

                    if (model.getFrame_category().equals("personal")) {

                        info.setSt_image(preferenceManager.getString(Constant.USER_IMAGE_PATH));

                    } else {

                        info.setSt_image(businessLogo);
                    }

                } else {
                    info.setSt_image(stickerUrl);
                }

                info.setNAME(name);


                info.setSt_order(String.valueOf(i));
                info.setSt_height(calcHeight);
                info.setSt_width(calcWidth);
                info.setSt_x_pos(realX);
                info.setSt_y_pos(realY);
                info.setSt_rotation("0");
                stickerInfoArrayList.add(info);

            }
            else if (type.contains("text")) {

                String color = jsonObject1.getString("color");
                String text = jsonObject1.getString("text");
                String size = jsonObject1.getString("size");

                String weight = "";
                if (jsonObject1.has("weight")) {
                    weight = jsonObject1.getString("weight");
                }

                String justification = jsonObject1.getString("justification");

                String font = jsonObject1.getString("font");

                fontNames.add(font);

                if (!jsonObject1.has("rotation")) {

                    //    jsonObject1.put("size", Integer.parseInt(size) + 15);
                    //  jsonObject1.put("y", Integer.parseInt(y) + 2);


                    jsonObject1.put("size", (int) Math.round(Integer.parseInt(size) + Integer.parseInt(size) * 0.5));
                    jsonObject1.put("y", (int) Math.round(Integer.parseInt(y) + Integer.parseInt(y) * 0.002));

                    y = jsonObject1.getString("y");

                    size = jsonObject1.getString("size");
                    String calSizeHeight = String.valueOf(Integer.parseInt(size) - Integer.parseInt(height)).replace("-", "");
                    String calRealY = String.valueOf(Integer.parseInt(y) - Integer.parseInt(calSizeHeight));
                    realY = String.valueOf((int) Math.round(Float.parseFloat(calRealY) * 100) / templateRealHeight);
                    calcHeight = String.valueOf(Integer.parseInt(size) * 100 / templateRealHeight + Integer.parseInt(realY));

                }

                String rotation = "0";
                if (jsonObject1.has("rotation")) {
                    rotation = jsonObject1.getString("rotation");
                }

                Text_infoposter textInfo = new Text_infoposter();
                textInfo.setText_id(String.valueOf(i));
                textInfo.setText(text);


                BusinessItem businessItem = Constant.getBusinessItem(context);


                if (name.equals("email")) {
                    if (model.getFrame_category().equals("personal")) {
                        textInfo.setText(preferenceManager.getString(Constant.USER_EMAIL));
                    } else {
                        textInfo.setText(businessItem != null ? businessItem.getEmail() : "");

                    }
                } else if (name.equals("address")) {
                    textInfo.setText(businessItem != null ? businessItem.getAddress() : "");
                } else if (name.equals("website")) {
                    textInfo.setText(businessItem != null ? businessItem.getWebsite() : "");
                } else if (name.equals("company")) {
                    textInfo.setText(businessItem != null ? businessItem.getName() : "");
                } else if (name.equals("name")) {
                    if (model.getFrame_category().equals("personal")) {
                        textInfo.setText(preferenceManager.getString(Constant.USER_NAME));
                    } else {

                        textInfo.setText(businessItem != null ? businessItem.getName() : "");
                    }
                } else if (name.equals("number") || name.equals("phone") || name.equals("mobile")) {
                    if (model.getFrame_category() != null && model.getFrame_category().equals("personal")) {
                        textInfo.setText(preferenceManager.getString(Constant.USER_PHONE));
                    } else {
                        textInfo.setText(businessItem != null ? businessItem.getPhone() : "");
                    }
                } else if (name.equals("whatsapp")) {

                    //Whatsapp
                    textInfo.setText(preferenceManager.getString(Constant.USER_PHONE));

                } else if (name.equals("facebook")) {

                    textInfo.setText(businessItem != null ? businessItem.getSocial_facebook() : "");

                } else if (name.equals("twitter")) {

                    textInfo.setText(businessItem != null ? businessItem.getSocial_twitter() : "");


                } else if (name.equals("youtube")) {

                    textInfo.setText(businessItem != null ? businessItem.getSocial_youtube() : "");

                } else if (name.equals("instagram")) {

                    textInfo.setText(businessItem != null ? businessItem.getSocial_instagram() : "");


                }


                textInfo.setTxt_height(calcHeight);
                textInfo.setTxt_width(calcWidth);
                textInfo.setTxt_x_pos(realX);

                textInfo.setTxt_y_pos(realY);
                textInfo.setTxt_rotation(rotation);
                textInfo.setTxt_color(color.replace("0x", "#"));
                textInfo.setTxt_order("" + i);
                textInfo.setFont_family(font);
                textInfo.setTxt_weight(weight);
                textInfo.setTxt_justification(justification);

                textInfoArrayList.add(textInfo);

            }
        }
    }

    public interface OnFrameStatus {
        void onFrameLoaded(ArrayList<Sticker_info> stickerInfos, ArrayList<Text_infoposter> textInfos);
    }


}
