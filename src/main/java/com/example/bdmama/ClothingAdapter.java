package com.example.bdmama;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.ClothingViewHolder> {

    private List<ClothingItem> clothingItems;
    private Context context;

    public ClothingAdapter(List<ClothingItem> clothingItems, Context context) {
        this.clothingItems = clothingItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ClothingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothing_item_layout, parent, false);
        return new ClothingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingViewHolder holder, int position) {
        ClothingItem item = clothingItems.get(position);
        holder.nameTextView.setText(item.getName());
        holder.descriptionTextView.setText(item.getDescription());

        // Загрузка изображения с помощью Glide
        Glide.with(context).load(item.getImage()).into(holder.imageView);

        // Обработчик нажатия на элемент
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditClothingItemActivity.class);
            intent.putExtra("clothing_item", item);
            intent.putExtra("position", position);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return clothingItems.size();
    }

    static class ClothingViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descriptionTextView;
        ImageView imageView;

        public ClothingViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.clothingName);
            descriptionTextView = itemView.findViewById(R.id.clothingDescription);
            imageView = itemView.findViewById(R.id.clothingImage);
        }
    }
}
