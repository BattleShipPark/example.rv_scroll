package com.battleshippark.examplervscroll;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MainAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Integer> items;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add(i);
        }
        adapter = new MainAdapter(this);
        adapter.setItems(items);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.btn1)
    void onClickBtn1() {
        position = (position + 6) % 20;
        recyclerView.scrollToPosition(position);
    }

    @OnClick(R.id.btn2)
    void onClickBtn2() {
        position = (position + 6) % 20;
        layoutManager.scrollToPositionWithOffset(position, 20);
    }

    @OnClick(R.id.btn3)
    void onClickBtn3() {
        startActivity(new Intent(this, Extra1Activity.class));
    }

    @OnClick(R.id.btn4)
    void onClickBtn4() {
        startActivity(new Intent(this, Extra2Activity.class));
    }

    @OnClick(R.id.btn5)
    void onClickBtn5() {
        position = (position + 6) % 20;
        RecyclerView.SmoothScroller smoothScroller = new CustomSmoothScroller(recyclerView.getContext(), layoutManager, 30);
        smoothScroller.setTargetPosition(position);
        layoutManager.startSmoothScroll(smoothScroller);
    }

    @OnClick(R.id.btn6)
    void onClickBtn6() {
        items.add(1, items.size());
        adapter.notifyDataSetChanged();
        layoutManager.scrollToPosition(1);
    }

    private static class CustomSmoothScroller extends LinearSmoothScroller {
        private LinearLayoutManager layoutManager;
        private int centerOffset;

        public CustomSmoothScroller(Context context, LinearLayoutManager layoutManager, int centerOffset) {
            super(context);
            this.layoutManager = layoutManager;
            this.centerOffset = centerOffset;
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return layoutManager.computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return super.calculateSpeedPerPixel(displayMetrics) * 3;
        }

        @Override
        public int calculateDxToMakeVisible(View view, int snapPreference) {
            return super.calculateDxToMakeVisible(view, SNAP_TO_START) + centerOffset;
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

        public void setItems(List<Integer> items) {
            this.items = items;
        }
    }
}
