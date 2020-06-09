package chapter.android.aweme.ss.com.homework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

import chapter.android.aweme.ss.com.homework.model.ApiService;
import chapter.android.aweme.ss.com.homework.model.VideoBean;
import chapter.android.aweme.ss.com.homework.widget.MyAdapter;


//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MyAdapter.ListItemClickListener {
    private static final String TAG = "peggie";

    private static final int NUM_LIST_ITEMS = 30;

    private MyAdapter mAdapter;
    private RecyclerView mNumbersListView;
    private VideoView videoView;

    private Toast mToast;
    List<VideoBean> mDataset;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolist);
        getSupportActionBar().hide();//去除标题栏
        mNumbersListView = findViewById(R.id.rv_list);
        videoView = findViewById(R.id.videoView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataset = new ArrayList<>();
        mNumbersListView.setLayoutManager(layoutManager);
        mNumbersListView.setHasFixedSize(true);

        getData(this);

        mNumbersListView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // 最后一个完全可见项的位置
            private int lastCompletelyVisibleItemPosition;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                        Toast.makeText(MainActivity.this, "已滑动到底部!,触发loadMore", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                }
                Log.d(TAG, "onScrolled: lastVisiblePosition=" + lastCompletelyVisibleItemPosition);
            }
        });



    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d(TAG, "onListItemClick: ");
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + (clickedItemIndex+1) + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        Intent intent1 = new Intent();
        intent1.setClass(this,VideoActivity.class);
        String str1 = String.valueOf(clickedItemIndex);
        String str2 = mDataset.get(clickedItemIndex%10).getFeedurl();
        intent1.putExtra("item",str1);
        intent1.putExtra("video",str2);
        startActivity(intent1);

    }


    /***
     * 2020/5/25
     * retrofit解析
     */

    private void getData(MyAdapter.ListItemClickListener listener){

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://wanandroid.com/")
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getR().enqueue(new Callback<JsonArray>() {
            @Override
            //想要VideoResponse类型的对象，但是传入的是Array
            /**
             * @Date 2020/5/23
             * 拿到了JsonArray
             *
             */
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("Vretrofit", String.valueOf(response.body().isJsonArray()));
                Log.d("Vretrofit", response.body().toString());

                String str=response.body().toString();
                Type type=new TypeToken<List<VideoBean>>(){}.getType();

                Gson gson=new Gson();
                mDataset=gson.fromJson(str,type);
                Log.d("sss",mDataset.toString());
                for(VideoBean v:mDataset){
                    Log.d("sss","------------------------------");
                    //Log.d("vvv",v.getId());
                    Log.d("sss","nickname:"+v.getNickname());
                    Log.d("sss","description:"+v.getDescription());
                    Log.d("sss","avatar:"+v.getAvatar());
                    Log.d("sss","feedurl:"+v.getFeedurl());
                    Log.d("sss","likecount:"+String.valueOf(v.getLikecount()));

                }
                Log.d("sss",mDataset.toString());
                mAdapter = new MyAdapter(NUM_LIST_ITEMS, listener,mDataset);
                mAdapter.notifyDataSetChanged();
                mNumbersListView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("Eretrofit", t.getMessage());
            }
        });




        }

    }
