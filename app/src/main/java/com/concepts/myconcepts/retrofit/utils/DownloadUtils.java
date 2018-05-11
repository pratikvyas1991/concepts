package com.concepts.myconcepts.retrofit.utils;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tasol on 10/5/18.
 */

public class DownloadUtils {
    private static final String TAGSTDACT = "%%%%DownloadUtils";
    private static ApiInterface apiService;

    public DownloadUtils() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public static void downFile(String url, final String name) {
        Call<ResponseBody> call = apiService.downloadFileByUrl(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAGSTDACT, "Got the body for the file");

                    new AsyncTask<Void, Long, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            saveToDisk(response.body(),name);
                            return null;
                        }
                    }.execute();

                } else {
                    Log.d(TAGSTDACT, "Connection failed " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAGSTDACT, t.getMessage());
            }
        });
    }

    public static void saveToDisk(ResponseBody body,String fileName) {
        try {
            new File(Environment.getExternalStorageDirectory() + "/aaaretrofittest/").mkdir();
            File destinationFile = new File(Environment.getExternalStorageDirectory() + "/aaaretrofittest/"+fileName);
            InputStream is = null;
            OutputStream os = null;
            try {
                Log.d(TAGSTDACT, "File Size=" + body.contentLength());
                is = body.byteStream();
                os = new FileOutputStream(destinationFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                while ((count = is.read(data)) != -1) {
                    os.write(data, 0, count);
                    progress += count;
                    Log.d(TAGSTDACT, "Progress: " + progress + "/" + body.contentLength() + " >>>> " + (float) progress / body.contentLength());
                }
                os.flush();
                Log.d(TAGSTDACT, "File saved successfully!");
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAGSTDACT, "Failed to save the file!");
                return;
            } finally {
                if (is != null) is.close();
                if (os != null) os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAGSTDACT, "Failed to save the file!");
            return;
        }
    }
}
