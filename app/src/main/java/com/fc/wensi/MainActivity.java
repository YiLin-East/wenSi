package com.fc.wensi;

import static com.fc.wensi.httpUrl.url;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Context context = MainActivity.this;

    List<String> imgList = new ArrayList<>();
    List<Map<String, Object>> newsList = new ArrayList<>();
    private static final String TAG = "MainActivity";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Banner banner = findViewById(R.id.banner);
        RecyclerView rc = findViewById(R.id.recyclerView2);
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        setRC(rc);
                        break;
                }
            }
        };

        OKHttp.getInstance().getEnqueue(url, new IGetDataListener() {
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
                            Map<String, Object> map = new HashMap<>();
                            map.put("title", bean.data.get(i).title);
                            map.put("url", bean.data.get(i).imgUrl);
                            map.put("date", bean.data.get(i).time);
                            newsList.add(map);
                            imgList.add(bean.data.get(i).imgUrl);
                        }
                        // handle
                        Message msg=new Message();
                        msg.what=1;
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Object reasonOBJ) {
                Log.e(TAG,reasonOBJ.toString());
            }
        });


        banner.setDatas(imgList);
        Log.i(TAG,imgList.toString()+"==============================");
        banner.setAdapter(new BannerImageAdapter<String>(imgList) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                //  设置圆形指示点，设置循环时间
                Glide.with(holder.itemView)
                        .load(data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        }).setIndicator(new CircleIndicator(context)).setLoopTime(1500);

    }

    public void setRC(RecyclerView rc){
        Log.i(TAG,newsList.toString()+"map");
        rc.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        News_adapter recy=new News_adapter(newsList,context);
        //设置布局显示格式
        rc.setLayoutManager(new LinearLayoutManager(context));
        rc.setAdapter(recy);
    }

}

