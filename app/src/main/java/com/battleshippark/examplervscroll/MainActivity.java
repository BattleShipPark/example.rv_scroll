package com.battleshippark.examplervscroll;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Integer> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add(i);
        }
        adapter = new MainAdapter(this);
        adapter.setItems(items);
        recyclerView.setAdapter(adapter);

        Button button1 = (Button) findViewById(R.id.test1);
        button1.setOnClickListener(new View.OnClickListener() {
            private int position = 0;

            @Override
            public void onClick(View v) {
                position = (position + 6) % 20;
                recyclerView.scrollToPosition(position);
            }
        });

        Button button2 = (Button) findViewById(R.id.test2);
        button2.setOnClickListener(new View.OnClickListener() {
            private int position = 0;

            @Override
            public void onClick(View v) {
                position = (position + 6) % 20;
                recyclerView.smoothScrollToPosition(position);
            }
        });
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
            holder.bind(position);
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
