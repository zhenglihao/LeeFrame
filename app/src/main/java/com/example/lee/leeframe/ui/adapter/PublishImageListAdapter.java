package com.example.lee.leeframe.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lee.leeframe.R;
import com.example.lee.leeframe.base.recyclerView.BaseRecyclerViewAdapter;
import com.example.lee.leeframe.base.recyclerView.BaseRecyclerViewHolder;
import com.example.lee.leeframe.databinding.RvItemPublishImageBinding;

import java.io.File;


public class PublishImageListAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;

    public PublishImageListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PublishImageListAdapter.PublishImageViewHolder(parent, R.layout.rv_item_publish_image);
    }


    class PublishImageViewHolder extends BaseRecyclerViewHolder<String, RvItemPublishImageBinding> {

        public PublishImageViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(String object, int position) {
            Glide.with(mContext).load(new File(object)).into(binding.ivPublish);
        }
    }
}
