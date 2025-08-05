package com.mahditaz.newshub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mahditaz.newshub.R;
import com.mahditaz.newshub.databinding.ItemLoadStateBinding;

public class ArticlesLoadStateAdapter extends LoadStateAdapter<ArticlesLoadStateAdapter.VH> {
    private View.OnClickListener mRetryCallback;

    public ArticlesLoadStateAdapter(View.OnClickListener mRetryCallback) {
        this.mRetryCallback = mRetryCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesLoadStateAdapter.VH vh, @NonNull LoadState loadState) {
        vh.bind(loadState);
    }

    @NonNull
    @Override
    public ArticlesLoadStateAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new ArticlesLoadStateAdapter.VH(viewGroup, mRetryCallback);
    }

    public class VH extends RecyclerView.ViewHolder{
        private ProgressBar mProgressBar;
        private TextView mErrorMsg, mErrorMsg2, mErrorMsg3;
        private Button mRetry;
        public VH(@NonNull ViewGroup parent, @NonNull View.OnClickListener retryCallback) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_state, parent, false));
            ItemLoadStateBinding binding = ItemLoadStateBinding.bind(itemView); // ITEMVIEW ACTUALLY REFERS TO A SINGLE ITEM IN A RECYCLER VIEW
            mProgressBar = binding.progBar;
            mErrorMsg = binding.errorMsg;
            mErrorMsg2 = binding.errorMsg2;
            mErrorMsg3 = binding.errorMsg3;
            mRetry = binding.retryBtn;
            mRetry.setOnClickListener(retryCallback);
        }

        public void bind(LoadState loadState){
            if (loadState instanceof LoadState.Error){
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                mErrorMsg.setText(loadStateError.getError().getLocalizedMessage());
            }
            mProgressBar.setVisibility(loadState instanceof LoadState.Loading ? View.VISIBLE : View.GONE);
            mRetry.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
            mErrorMsg.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
            mErrorMsg2.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
            mErrorMsg3.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
        }
    }
}
