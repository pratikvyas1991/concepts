package com.concepts.myconcepts.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.concepts.myconcepts.R;
import com.concepts.myconcepts.retrofit.model.StudentData;
import com.concepts.myconcepts.retrofit.model.StudentDetails;
import com.concepts.myconcepts.retrofit.utils.ApiClient;
import com.concepts.myconcepts.retrofit.utils.ApiInterface;

import org.json.JSONObject;

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

public class StudentActivity extends Activity {
    TextView tvStudentGender,tvStudentName;
    Button btnImageDownload;
    ApiInterface apiService;
    ProgressBar pbLoad;
    StudentDetails row =new StudentDetails();
    private static final String TAGSTDACT="%%%%StudentActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        tvStudentGender = (TextView)findViewById(R.id.tvStudentGender);
        tvStudentName = (TextView)findViewById(R.id.tvStudentName);
        pbLoad = (ProgressBar)findViewById(R.id.pbLoad);
        btnImageDownload = (Button)findViewById(R.id.btnImageDownload);

        apiService= ApiClient.getClient().create(ApiInterface.class);

        String obj = getReqObject().toString();

        populateStudent(obj);


        btnImageDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage();
            }
        });
    }

    private void downloadImage() {
        if(row.getThumb()!=null&&row.getThumb().length()>0){
            downloadFile(row.getThumb());
        }
    }

    public void populateStudent(String taskData){
        Log.v(TAGSTDACT," Req : "+taskData);
        Call<StudentData> call = apiService.getStudentData(taskData);
        call.enqueue(new Callback<StudentData>() {
            @Override
            public void onResponse(Call<StudentData> call, Response<StudentData> response) {
                if(response!=null){
                    pbLoad.setVisibility(View.GONE);
                    row = response.body().getData();
                    tvStudentName.setText(row.getFullName());
                    tvStudentGender.setText(row.getGender());

                    Log.v(TAGSTDACT," Resp : Name :"+row.getFullName());
                }
            }
            @Override
            public void onFailure(Call<StudentData> call, Throwable t) {
                Log.v(TAGSTDACT," Resp : "+t.getMessage());
            }
        });
    }

    public JSONObject getReqObject(){
        JSONObject reqObj =new JSONObject();
        try {
            JSONObject taskData =new JSONObject();
            taskData.put("user_id","32");
            reqObj.put("task","myProfile");
            reqObj.put("taskData",taskData);
        }catch (Exception je){
            je.printStackTrace();
        }
        return reqObj;
    }

    public void downloadFile(String url){
        Call<ResponseBody> call = apiService.downloadPhoto(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.v(TAGSTDACT," server has file");
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                }else {
                    Log.v(TAGSTDACT," server has no files");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v(TAGSTDACT," FileError : "+t.getMessage());
            }
        });
    }

    public static boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs

            File futureStudioIconFile = new File(Environment.getExternalStorageDirectory() + "/aaaretrofittest/student.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d(TAGSTDACT, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }



}
