package com.jafir.qingning.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;
import com.jafir.qingning.R;
import com.jafir.qingning.app.activity.rent.ChooseBikeActivity;
import com.jafir.qingning.app.fragment.EventFragment;
import com.jafir.qingning.model.bean.Event;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.widget.RoundImageView;

/**
 * Created by jafir on 16/5/10.
 */
public class EventRecyclerAdapter extends BaseRecyclerAdapter<Event> {

    private boolean hasHeader = true;

    public boolean isHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1 && hasHeader) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_lunbo, parent, false);
            return new LunboHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_event_recycler, parent, false);
            return new ImageViewHolder(view);
        }
    }


    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder myholder, int position) {
        if (mDatas.isEmpty()) {
            return;
        }
        if (myholder instanceof LunboHolder) {
            LunboHolder holder = (LunboHolder) myholder;
            holder.mViewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return ChooseBikeActivity.imgs.length;
                }

                @Override
                public boolean isViewFromObject(View view, Object o) {
                    return view == o;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {

                    ImageView imageView = new ImageView(container.getContext());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(container.getContext()).load(EventFragment.imgs[position % EventFragment.imgs.length])
                            .into(imageView);
                    container.addView(imageView);
                    return imageView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });
            holder.mIndicator.setViewPager(holder.mViewpager, ChooseBikeActivity.imgs.length);


        } else if (myholder instanceof ImageViewHolder) {
            Event event = mDatas.get(position);
            ImageViewHolder holder = (ImageViewHolder) myholder;
            holder.mName.setText(event.getName());
            holder.mTime.setText(event.getTime());
            holder.mPeople.setText(event.getPeople());
            holder.mBussiness.setText(event.getBussiness());
            Glide.with(mContext).load(event.getImg()).into(holder.mImageView);
//            Glide.with(mContext).load(event.getPortrait()).into(holder.mPortrait);
            new KJBitmap.Builder().imageUrl(event.getPortrait()).view(holder.mPortrait).display();

        }


    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0 && hasHeader) {
            return 1;
        } else {
            return 2;
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private RoundImageView mPortrait;
        private TextView mName;
        private TextView mTime;
        private TextView mPeople;
        private TextView mBussiness;





        public ImageViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null && mDatas.size() != 0) {
                        mOnItemClickListener.onItemClick(v, getAdapterPosition() % mDatas.size());
                    }
                }
            });
            mImageView = (ImageView) itemView.findViewById(R.id.item_event_img);
            mName = (TextView) itemView.findViewById(R.id.item_event_name);
            mPeople = (TextView) itemView.findViewById(R.id.item_event_people);
            mPortrait = (RoundImageView) itemView.findViewById(R.id.item_event_portrait);
            mBussiness = (TextView) itemView.findViewById(R.id.item_event_business);
            mTime = (TextView) itemView.findViewById(R.id.item_event_time);
        }
    }

    private class LunboHolder extends RecyclerView.ViewHolder {
        private ViewPager mViewpager;
        private FlycoPageIndicaor mIndicator;

        public LunboHolder(View itemView) {
            super(itemView);
            mViewpager = (ViewPager) itemView.findViewById(R.id.view_pager);
            mIndicator = (FlycoPageIndicaor) itemView.findViewById(R.id.page_indicator);
        }
    }
}
