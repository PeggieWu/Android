package chapter.android.aweme.ss.com.homework.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import chapter.android.aweme.ss.com.homework.R;
import chapter.android.aweme.ss.com.homework.model.VideoBean;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.NumberViewHolder> {

    private static final String TAG = "GreenAdapter";

    private int mNumberItems;

    private final ListItemClickListener mOnClickListener;

    private static int viewHolderCount;
    /****/
    private List<VideoBean> mDataset;




    public MyAdapter(int numListItems, ListItemClickListener listener,  List<VideoBean> mDataset) {
        mNumberItems = numListItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
        this.mDataset = mDataset;

    }

    /******/
    public void setData(List<VideoBean> myDataset) {
        mDataset = myDataset;
        Log.d("tryA",mDataset.toString());
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.im_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: " + viewHolderCount);
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NumberViewHolder numberViewHolder, int position) {

                Glide.with(numberViewHolder.imageView.getContext())
                        .setDefaultRequestOptions(
                                new RequestOptions()
                                        .frame(1000000)//单位微秒
                                        .centerCrop()
                        )

                        .load(mDataset.get(position%10).getFeedurl())
                        .into(numberViewHolder.imageView);
                numberViewHolder.textAuthor.setTextSize(18);
                numberViewHolder.textDescription.setTextSize(18);
                numberViewHolder.textLikecount.setTextSize(18);
                numberViewHolder.textAuthor.setTextColor(Color.WHITE);
                numberViewHolder.textDescription.setTextColor(Color.WHITE);
                numberViewHolder.textLikecount.setTextColor(Color.WHITE);
                numberViewHolder.textAuthor.setText("@"+mDataset.get(position%10).getNickname());
                numberViewHolder.textDescription.setText("#"+mDataset.get(position%10).getDescription());
                numberViewHolder.textLikecount.setText(String.valueOf(mDataset.get(position%10).getLikecount()));

    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       // private final VideoView videoView;
        private final  ImageView imageView;
        private final TextView textAuthor;
        private final TextView textDescription;
        private final TextView textLikecount;



        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            //videoView = (VideoView)itemView.findViewById(R.id.videoView);
            imageView =(ImageView)itemView.findViewById(R.id.ImageView);
            textAuthor=(TextView)itemView.findViewById(R.id.textView_Author);
            textDescription=(TextView)itemView.findViewById(R.id.textView_des);
            textLikecount=(TextView)itemView.findViewById(R.id.textView_likecount) ;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }

        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


}
