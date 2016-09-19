package com.battleshippark.examplervscroll;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Extra2Activity extends AppCompatActivity {
    @BindViews({R.id.recyclerView1, R.id.recyclerView2})
    List<RecyclerView> recyclerViews;

    private MainAdapter adapter;
    private List<LinearLayoutManager> layoutManagers = new ArrayList<>();
    private List<Integer> items;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra2);
        ButterKnife.bind(this);

        layoutManagers.add(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        layoutManagers.add(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add(i);
        }
        adapter = new MainAdapter(this);
        adapter.setItems(items);

        for (int i = 0; i < 2; i++) {
            recyclerViews.get(i).setLayoutManager(layoutManagers.get(i));
            recyclerViews.get(i).setAdapter(adapter);
        }
    }

    @OnClick(R.id.checkbox1)
    void onClickCheck1(CheckBox checkBox) {
        for (int i = 0; i < 2; i++) {
            layoutManagers.get(i).setStackFromEnd(checkBox.isChecked());
        }
    }

    @OnClick(R.id.btn1)
    void onClickBtn1() {
        position = (position + 6) % 20;
        for (int i = 0; i < 2; i++) {
            layoutManagers.get(i).scrollToPositionWithOffset(position, 20);
        }
    }

    @OnClick(R.id.btn2)
    void onClickBtn2() {
        items.add(items.size());
        for (int i = 0; i < 2; i++) {
            recyclerViews.get(i).setAdapter(adapter);
        }
    }

    private class MainViewHolder extends RecyclerView.ViewHolder {
        private final TextView item;
        private final ImageView image;

        public MainViewHolder(View itemView) {
            super(itemView);

            item = (TextView) itemView.findViewById(R.id.item);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        public void bind(int position) {
            item.setText(position + "");
            image.setImageResource(R.drawable.item_bg);
        }
    }

    private class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
        private Context context;
        private List<Integer> items;

        public MainAdapter(Context context) {
            this.context = context;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        public void setItems(List<Integer> items) {
            this.items = items;
        }
    }
}
