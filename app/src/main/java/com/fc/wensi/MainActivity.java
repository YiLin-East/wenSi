package com.fc.wensi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<String> imgList = new ArrayList<>();
    List<String> time = new ArrayList<>();
    List<String> title = new ArrayList<>();
    private static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Banner banner = findViewById(R.id.banner);
        RecyclerView rc = findViewById(R.id.recyclerView2);


        OKHttp.getInstance().getEnqueue(httpUrl.url, new IGetDataListener() {
            @Override
            public void onSuccess(Object dataobj) {
                Log.e("TAG", dataobj.toString());
                try {
                    JSONObject jsonObject = new JSONObject(dataobj.toString());
                    String success = jsonObject.getString("success");
                    Log.e("TAG", success);
                    if (success.equals("true")) {
                        Gson gson = new Gson();
                        Bean bean = gson.fromJson(dataobj.toString(), Bean.class);
                        Log.e("TAG", "bean.data.get(0).title===============" + bean.data.get(0).title.toString());
                        for (int i = 0; i < bean.data.size(); i++) {
                            imgList.add(bean.data.get(i).imgUrl);
                            title.add(bean.data.get(i).title);
                            time.add(bean.data.get(i).time);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Object reasonOBJ) {
            }
        });
        banner.setDatas(imgList);
        banner.setAdapter(new BannerImageAdapter<String>(imgList) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                Glide.with(holder.itemView)
                        .load(data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        }).setIndicator(new CircleIndicator(MainActivity.this)).setLoopTime(1500);
        //  设置圆形指示点，设置循环时间




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,
                                                     LinearLayoutManager.VERTICAL, false);
        rc.setLayoutManager(linearLayoutManager);
        RcAdapter adapter = new RcAdapter(MainActivity.this);
        rc.setAdapter(adapter);
    }

    public class RcAdapter extends RecyclerView.Adapter<RcAdapter.ViewHolder>{

        Activity activity;
        TextView tv_title,tv_time;
        ImageView imageView;

        public RcAdapter(Activity activity) {
            this.activity = activity;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            tv_title.setText(time.get(0));
            tv_time.setText(time.get(1));

        }

        @Override
        public int getItemCount() {
            return time.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.news_title);
                tv_time = itemView.findViewById(R.id.news_date);
            }
        }
    }

}

