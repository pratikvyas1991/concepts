package com.concepts.myconcepts.optimization;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.concepts.myconcepts.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasol on 22/6/18.
 */

public class OptimizedRecyclerAdapter extends Activity {
    private String TAGRETROFIT = "%%%OptAdapter";
    final String url = "https://api.androidhive.info/json/glide.json";
    private RequestQueue queue;
    List<MoviePojo> movieList = new ArrayList<>();
    OptAdapter optAdapter =new OptAdapter();
    LinearLayoutManager linearLayoutManager;
    RecyclerView rvMovieList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Try using Using DiffUtil in Android RecyclerView to handle data change
        setContentView(R.layout.activity_optimizeadapter);
        queue = Volley.newRequestQueue(OptimizedRecyclerAdapter.this);
        linearLayoutManager = new LinearLayoutManager(OptimizedRecyclerAdapter.this);
        rvMovieList = (RecyclerView)findViewById(R.id.rvMovieList);
        rvMovieList.setLayoutManager(linearLayoutManager);
        rvMovieList.setHasFixedSize(true);
        rvMovieList.setItemViewCacheSize(20);
        rvMovieList.setDrawingCacheEnabled(true);
        rvMovieList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        getData();
    }

    public class OptAdapter extends RecyclerView.Adapter<OptAdapter.OptHolder>{
        @Override
        public OptHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(OptimizedRecyclerAdapter.this).inflate(R.layout.item_adapter_optimize,parent,false);
            return new OptHolder(view);
        }

        @Override
        public void onBindViewHolder(OptHolder holder, int position) {
            MoviePojo moviePojo = movieList.get(position);
            holder.mTVTitle.setText(moviePojo.getName());
            Glide.with(OptimizedRecyclerAdapter.this)
                    .load(moviePojo.getSmall())
                    .into(holder.mIVThumb);
        }

        @Override
        public void onViewRecycled(OptHolder holder) {
            if(holder!=null){
                Glide.clear(holder.mIVThumb);
            }
            super.onViewRecycled(holder);
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public class OptHolder extends RecyclerView.ViewHolder{
            TextView mTVTitle;
            ImageView mIVThumb;
            public OptHolder(View itemView) {
                super(itemView);
                mTVTitle = (TextView)itemView.findViewById(R.id.mTVTitle);
                mIVThumb = (ImageView)itemView.findViewById(R.id.mIVThumb);
            }
        }
    }

    public void getData(){
        Log.v(TAGRETROFIT," getDataCalled ");
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array =new JSONArray(response);
                    if(array!=null){
                        movieList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String name = object.getString("name");
                            String timsStamp= object.getString("timestamp");
                            String small = "";
                            JSONObject url = object.getJSONObject("url");
                            small = url.getString("small");
                            movieList.add(new MoviePojo(name,timsStamp,small));
                        }
                        if(movieList.size()>0){
                            rvMovieList.setAdapter(optAdapter);
                        }
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(stringRequest);

    }
}
