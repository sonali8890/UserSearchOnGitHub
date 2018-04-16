package com.sonali.githubusersearchapp.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sonali.githubusersearchapp.R;
import com.sonali.githubusersearchapp.databinding.ResultItemviewBinding;
import com.sonali.githubusersearchapp.domain.model.UserModel;

import java.util.List;

/**
 * Created by Sonali
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private Context mContext;
    private List<UserModel.Item> mList;

    public SearchResultAdapter(Context con, List<UserModel.Item> list) {
        mContext = con;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ResultItemviewBinding view = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.result_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultItemviewBinding binder = holder.getBinder();
        binder.name.setText(mList.get(position).getLogin());
        Glide.with(mContext)
                .load(Uri.parse(mList.get(position).getAvatarUrl()))
                .apply(RequestOptions.circleCropTransform())
                .into(binder.image);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ResultItemviewBinding binder;

        public ViewHolder(ResultItemviewBinding itemView) {
            super(itemView.getRoot());
            binder = itemView;
        }

        public ResultItemviewBinding getBinder() {
            return binder;
        }
    }

}
