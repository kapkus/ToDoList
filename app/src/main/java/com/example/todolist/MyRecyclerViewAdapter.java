package com.example.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnClickListener {
        void onClick(int position);
    }

    private final List<TaskData> itemList;
    private final LayoutInflater mInflater;
    private final ArrayList<String> urls;
    Context context;

    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    MyRecyclerViewAdapter(Context context, List<TaskData> itemList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.itemList = itemList;
        this.urls = populateUrls();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = mInflater.inflate(R.layout.recyclerview_row1, parent, false);
                return new ViewHolder0(view);
            case 2:
                view = mInflater.inflate(R.layout.recyclerview_row2, parent, false);
                return new ViewHolder2(view);
            default:
                Log.e("Error: ", "Unknown view");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        boolean hide = itemList.get(position).getDeleted();
        switch (holder.getItemViewType()) {
            case 0:
                if(hide){
                    holder.itemView.setVisibility(View.GONE);
                    ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                    params.height = 0;
                    params.width = 0;
                    holder.itemView.setLayoutParams(params);
                }
                ViewHolder0 viewHolder0 = (ViewHolder0) holder;
                String task = itemList.get(position).text;
                viewHolder0.taskText.setText(task);
                break;
            case 2:
                if(hide){
                    holder.itemView.setVisibility(View.GONE);
                    ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                    params.height = 0;
                    params.width = 0;
                    holder.itemView.setLayoutParams(params);
                }
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                viewHolder2.webView.loadUrl(urls.get(position % 3));
                break;
        }
    }

    public class ViewHolder0 extends RecyclerView.ViewHolder {

        TextView taskText;
        ImageView deleteIcon;
        boolean hide;

        public ViewHolder0(View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            deleteIcon.setOnClickListener(view -> {
                ((MainActivity) context).onClick(getAdapterPosition());
            });
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        WebView webView;
        ImageView deleteIcon;

        public ViewHolder2(View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.webView);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            deleteIcon.setOnClickListener(view -> {
                ((MainActivity) context).onClick(getAdapterPosition());
            });
        }
    }

    public void addItem(TaskData task){
        itemList.add(task);
        notifyItemInserted(getItemCount() - 1);
    }

    public void hideItem(int position){
        itemList.get(position).setDeleted(true);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private ArrayList<String> populateUrls() {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://www.google.com/");
        urls.add("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png");
        urls.add("https://scontent.fktw4-1.fna.fbcdn.net/v/t39.8562-6/109960336_274477960450922_1306319190754819753_n.png?_nc_cat=107&ccb=1-7&_nc_sid=6825c5&_nc_ohc=vxJ2qQt8cTcAX9aE1go&_nc_ht=scontent.fktw4-1.fna&oh=00_AfD2Z_5gR8snYznVeqMzMWLmj2xJu3w30tg0cwyIEDzXyA&oe=63BE2670");
        return urls;
    }

}
