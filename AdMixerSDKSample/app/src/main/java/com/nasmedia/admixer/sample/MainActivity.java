package com.nasmedia.admixer.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        menuAdapter = new MenuAdapter();

        menuAdapter.addItem("일반배너1");
        menuAdapter.addItem("일반배너2");
        menuAdapter.addItem("전면배너");
        menuAdapter.addItem("네이티브");
        menuAdapter.addItem("동영상");
        menuAdapter.addItem("전면동영상");
        menuAdapter.addItem("전면리워드동영상");

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(menuAdapter);
    }

    private class MenuAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<String> menuList = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_menu, parent, false);

            return new ViewHolder(context, view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TextView tvMenu = holder.tvMenu;

            tvMenu.setText(menuList.get(position));
        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }

        public void addItem(String item) {
            menuList.add(item);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public Context context;
        public TextView tvMenu;

        ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            tvMenu = itemView.findViewById(R.id.tv_menu);

            tvMenu.setOnClickListener(v -> {
                        switch (getAdapterPosition()) {
                            case 0: {
                                context.startActivity(new Intent(context, BannerActivity.class));
                                break;
                            }
                            case 1: {
                                context.startActivity(new Intent(context, Banner2Activity.class));
                                break;
                            }
                            case 2: {
                                context.startActivity(new Intent(context, InterstitialActivity.class));
                                break;
                            }
                            case 3: {
                                context.startActivity(new Intent(context, NativeActivity.class));
                                break;
                            }
                            case 4: {
                                context.startActivity(new Intent(context, VideoActivity.class));
                                break;
                            }
                            case 5: {
                                context.startActivity(new Intent(context, InterstitialVideoActivity.class));
                                break;
                            }
                            case 6: {
                                context.startActivity(new Intent(context, RewardInterstitialVideoActivity.class));
                                break;
                            }
                        }
                    }
            );
        }
    }

}
