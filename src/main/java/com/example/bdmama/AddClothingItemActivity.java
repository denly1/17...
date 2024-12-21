package com.example.bdmama;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.paperdb.Paper;

public class AddClothingItemActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private EditText nameEditText, descriptionEditText;
    private ImageView imageView;
    private String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothing_item);


        Paper.init(this);

        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        imageView = findViewById(R.id.imageView);
        Button addButton = findViewById(R.id.addButton);
        Button selectImageButton = findViewById(R.id.selectImageButton);


        selectImageButton.setOnClickListener(v -> openGallery());


        addButton.setOnClickListener(v -> addClothingItem());
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            imageUri = selectedImage.toString();
        }
    }


    private void addClothingItem() {
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String id = UUID.randomUUID().toString();

        if (name.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }


        ClothingItem item = new ClothingItem(id , name, description, imageUri);


        List<ClothingItem> existingItems = Paper.book().read("clothing_items", new ArrayList<ClothingItem>());
        if (existingItems == null) {
            existingItems = new ArrayList<>(); // Инициализация списка, если он пуст
        }
        existingItems.add(item); // Добавление нового элемента
        Paper.book().write("clothing_items", existingItems); // Сохранение обновленного списка

        Toast.makeText(this, "Вещь добавлена", Toast.LENGTH_SHORT).show();
        finish(); // Закрытие активности и возврат к MainActivity
    }
}
