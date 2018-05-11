package com.concepts.myconcepts.retrofit;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.concepts.myconcepts.R;
import com.concepts.myconcepts.retrofit.model.DestinationDetails;
import com.concepts.myconcepts.retrofit.model.DestinationList;
import com.concepts.myconcepts.retrofit.utils.ApiClient;
import com.concepts.myconcepts.retrofit.utils.ApiInterface;
import com.concepts.myconcepts.retrofit.utils.DownloadUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tasol on 9/5/18.
 */

public class RetrofitActivity extends Activity {
    ApiInterface apiService;
    RecyclerView rvDestinations;
    List<DestinationDetails> destinations = new ArrayList<>();
    private static final String TAGRETROFIT = "%%%%%Retrofit  : ";
    DestinationAdapter destinationAdapter;
    LinearLayoutManager linearLayoutManager;
    DownloadUtils downloadUtils = new DownloadUtils();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        rvDestinations = (RecyclerView) findViewById(R.id.rvDestinations);
        linearLayoutManager = new LinearLayoutManager(RetrofitActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDestinations.setLayoutManager(linearLayoutManager);
        destinationAdapter = new DestinationAdapter();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String obj = getReqObject().toString();
        Log.v(TAGRETROFIT, " Obj : " + obj);
        getDestinationsData(obj);

    }

    public JSONObject getReqObject() {
        JSONObject reqObj = new JSONObject();
        try {
            JSONObject taskData = new JSONObject();
            taskData.put("tour_management_id", "1");
            reqObj.put("task", "getDestinationManagentData");
            reqObj.put("taskData", taskData);
        } catch (Exception je) {
            je.printStackTrace();
        }
        return reqObj;
    }

    public void getDestinationsData(String taskData) {
        Call<DestinationList> call = apiService.getAllDestinations(taskData);
        call.enqueue(new Callback<DestinationList>() {
            @Override
            public void onResponse(Call<DestinationList> call, Response<DestinationList> response) {
                if (response != null) {
                    destinations.clear();
                    if (response.body() != null) {
                        destinations = response.body().getData();
                        if (destinations != null) {
                            rvDestinations.setAdapter(destinationAdapter);
                            for (int i = 0; i < destinations.size(); i++) {
                                DestinationDetails row = destinations.get(i);
                                Log.v(TAGRETROFIT, " Name " + row.getDestinationName() + " Lat : " + row.getLatitude() + " Long : " + row.getLongitude());
                            }
                        }
                    }
                } else {
                    Log.v(TAGRETROFIT, " Its Null response buddy");
                }
            }

            @Override
            public void onFailure(Call<DestinationList> call, Throwable t) {
                Log.v(TAGRETROFIT, " Error " + t.toString());
            }
        });
    }

    public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder> {
        @Override
        public DestinationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_adapter_destination, parent, false);
            return new DestinationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DestinationViewHolder holder, int position) {
            final DestinationDetails row = destinations.get(position);
            if (row != null) {
                holder.tvDesttitle.setText(row.getDestinationName());
                if (row.getDestinationImage() != null || row.getDestinationImage().length() > 2) {
//                    holder.btnImage.setVisibility(View.VISIBLE);

                    String name = row.getDestinationName()+".png";
                    DownloadUtils.downFile(row.getDestinationImage(),name);

                } else {
//                    holder.btnImage.setVisibility(View.GONE);
                }
            }

//            holder.btnImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    DownloadUtils.downFile();
//                    String url ="uploads/destination/1/1521436725_haveli-mansion-ahmedabad.jpg";
//                    String name = row.getDestinationName()+".png";
//                    DownloadUtils.downFile(row.getDestinationImage(),name);
////                    downFile(row.getDestinationImage(),name);
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return destinations.size();
        }

        public class DestinationViewHolder extends RecyclerView.ViewHolder {
            TextView tvDesttitle;
            Button btnImage;

            public DestinationViewHolder(View itemView) {
                super(itemView);
                btnImage = (Button) itemView.findViewById(R.id.btnImage);
                tvDesttitle = (TextView) itemView.findViewById(R.id.tvDesttitle);
            }
        }
    }
}
