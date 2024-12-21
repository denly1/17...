package com.example.bdmama;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class EditClothingItemActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;

    private EditText nameEditText, descriptionEditText;
    private ImageView imageView;
    private Button updateButton, deleteButton;
    private ClothingItem clothingItem;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clothing_item);

        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        imageView = findViewById(R.id.imageView);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        clothingItem = (ClothingItem) getIntent().getSerializableExtra("clothing_item");
        position = getIntent().getIntExtra("position", -1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        } else {
            loadImage();
        }


        updateButton.setOnClickListener(v -> updateClothingItem());


        deleteButton.setOnClickListener(v -> deleteClothingItem());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImage();
            } else {
                Toast.makeText(this, "Разрешение на доступ к хранилищу отклонено", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadImage() {
        Log.d("EditClothingItemActivity", "loadImage called");

        clothingItem = (ClothingItem) getIntent().getSerializableExtra("clothing_item");
        position = getIntent().getIntExtra("position", -1);

        if (clothingItem == null) {
            Log.e("EditClothingItemActivity", "clothingItem is null");
            return;
        }

        // Заполнение полей данными элемента
        nameEditText.setText(clothingItem.getName());
        descriptionEditText.setText(clothingItem.getDescription());
        imageView.setImageURI(Uri.parse(clothingItem.getImage()));
    }


    private void updateClothingItem() {


        if (clothingItem == null) {
            Toast.makeText(this, "Ошибка: элемент одежды не найден", Toast.LENGTH_SHORT).show();
            return; // Не продолжайте, если clothingItem равен null
        }

        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        // Обновление данных элемента
        clothingItem.setName(name);
        clothingItem.setDescription(description);

        // Сохранение обновленного списка
        List<ClothingItem> existingItems = Paper.book().read("clothing_items", new ArrayList<>());
        existingItems.set(position, clothingItem);
        Paper.book().write("clothing_items", existingItems);

        Toast.makeText(this, "Вещь обновлена", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void deleteClothingItem() {
        List<ClothingItem> existingItems = Paper.book().read("clothing_items", new ArrayList<>());
        existingItems.remove(position);
        Paper.book().write("clothing_items", existingItems);

        Toast.makeText(this, "Вещь удалена", Toast.LENGTH_SHORT).show();
        finish();
    }
}
