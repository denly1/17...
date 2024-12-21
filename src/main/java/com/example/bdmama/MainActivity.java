package com.example.bdmama;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private RecyclerView recyclerView;
    private ClothingAdapter adapter;
    private List<ClothingItem> clothingItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация PaperDB
        Paper.init(this);

        // Найти элементы интерфейса
        addButton = findViewById(R.id.addButton);
        recyclerView = findViewById(R.id.recyclerView);

        // Загрузка данных из PaperDB
        clothingItems = loadClothingItems();

        // Настройка RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClothingAdapter(clothingItems, this);
        recyclerView.setAdapter(adapter);

        // Переход на экран добавления нового товара
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddClothingItemActivity.class);
            startActivity(intent);
        });
    }

    // Загрузка данных из PaperDB
    private List<ClothingItem> loadClothingItems() {
        return Paper.book().read("clothing_items", new ArrayList<>());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Обновляем список после возврата с экрана добавления
        clothingItems.clear();
        clothingItems.addAll(loadClothingItems());
        adapter.notifyDataSetChanged();
    }
}
