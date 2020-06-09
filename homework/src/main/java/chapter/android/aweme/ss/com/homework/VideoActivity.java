package chapter.android.aweme.ss.com.homework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.yqw.hotheart.HeartFrameLayout;


public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;
    private static final String TAG = "mVideo";
    private static RelativeLayout.LayoutParams originLayoutParams;
    private int flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setTitle("视频播放");
        getSupportActionBar().hide();//去除标题栏
        HeartFrameLayout heartFrameLayout = findViewById(R.id.heart);
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        String feedurl =bundle.getString("video");//getString()返回指定key的值

        videoView = findViewById(R.id.videoView);
        //进度条
       /* mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.show(10000);*/

        videoView.setVideoURI(Uri.parse(feedurl));

        heartFrameLayout.setOnTouchListener(new MyClickListener
                (new MyClickListener.MyClickCallBack() {

                    @Override
                    public void onSimpleClick() {
                        //showToast("单击了");
                        if (videoView.isPlaying()==true){
                            videoView.pause();
                            Log.d("flag",String.valueOf(videoView.isPlaying()));

                        }
                        else
                        {
                            videoView.start();
                            //Log.d("flag","start");
                            Log.d("flag",String.valueOf(videoView.isPlaying()));
                            //flag=0;
                        }

                    }

                    @Override
                    public void onDoubleClick() {
                        //showToast("双击了");
                   }
                }));


        videoView.start();
        flag=0;

    }


    public boolean onTouchEvent(MotionEvent event){

        Log.d("flag","-------------------");
        flag=flag+1;
        Log.d("flag",String.valueOf(videoView.isPlaying())+String.valueOf(flag));

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (videoView.isPlaying()==true){
                    videoView.pause();
                    Log.d("flag",String.valueOf(videoView.isPlaying()));

                }

                else
                {
                    videoView.start();
                    //Log.d("flag","start");
                    Log.d("flag",String.valueOf(videoView.isPlaying()));
                    //flag=0;
                }
                break;
        }



        return super.onTouchEvent(event);

    }

    private void showToast(String content){
        Toast.makeText(this, content,Toast.LENGTH_SHORT).show();
    }

}

