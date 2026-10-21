package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {

        Ingredient ingredient = ingredientList.get(position);

        // Ingredient name
        holder.tvIngredientName.setText(ingredient.getName());

        // Ingredient colour accent
        int ingredientColor = getIngredientColor(ingredient.getName());
        holder.ingredientColorAccent.setBackgroundColor(ingredientColor);

        // Quantity and unit
        String details = ingredient.getQuantity() + " " + ingredient.getUnit();
        holder.tvIngredientDetails.setText(details);

        // Expiry date
        String expiryDate = ingredient.getExpiryDate();

        if (expiryDate != null && !expiryDate.trim().isEmpty()) {

            holder.tvIngredientExpiry.setText("Expires: " + expiryDate);
            holder.tvIngredientExpiry.setVisibility(View.VISIBLE);

            SharedPreferences preferences = holder.itemView.getContext()
                    .getSharedPreferences(
                            "SmartPantrySettings",
                            Context.MODE_PRIVATE
                    );

            boolean expiryRemindersEnabled = preferences.getBoolean(
                    "expiry_reminders",
                    true
            );

            if (expiryRemindersEnabled) {

                String warning = getExpiryWarning(expiryDate);

                if (warning != null) {

                    holder.tvExpiryWarning.setText(warning);
                    holder.tvExpiryWarning.setVisibility(View.VISIBLE);

                } else {

                    holder.tvExpiryWarning.setText("");
                    holder.tvExpiryWarning.setVisibility(View.GONE);
                }

            } else {

                holder.tvExpiryWarning.setText("");
                holder.tvExpiryWarning.setVisibility(View.GONE);
            }

        } else {

            holder.tvIngredientExpiry.setText("");
            holder.tvIngredientExpiry.setVisibility(View.GONE);

            holder.tvExpiryWarning.setText("");
            holder.tvExpiryWarning.setVisibility(View.GONE);
        }

        // EDIT BUTTON
        holder.btnEditIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    AddIngredientActivity.class
            );

            intent.putExtra("ingredient_id", ingredient.getId());
            intent.putExtra("ingredient_name", ingredient.getName());
            intent.putExtra("ingredient_quantity", ingredient.getQuantity());
            intent.putExtra("ingredient_unit", ingredient.getUnit());
            intent.putExtra("ingredient_expiry", ingredient.getExpiryDate());

            v.getContext().startActivity(intent);
        });

        // DELETE BUTTON
        holder.btnDeleteIngredient.setOnClickListener(v -> {

            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Ingredient")
                    .setMessage(
                            "Are you sure you want to delete "
                                    + ingredient.getName()
                                    + "?"
                    )
                    .setPositiveButton("Delete", (dialog, which) -> {

                        Context context = v.getContext();

                        DatabaseHelper databaseHelper =
                                new DatabaseHelper(context);

                        SQLiteDatabase db =
                                databaseHelper.getWritableDatabase();

                        db.delete(
                                "ingredients",
                                "id = ?",
                                new String[]{
                                        String.valueOf(ingredient.getId())
                                }
                        );

                        db.close();

                        // Remove the ingredient from the list
                        // so it disappears from the screen.
                        int currentPosition = holder.getAdapterPosition();

                        if (currentPosition != RecyclerView.NO_POSITION) {

                            ingredientList.remove(currentPosition);

                            notifyItemRemoved(currentPosition);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    // Choose the accent colour based on the ingredient name
    private int getIngredientColor(String ingredientName) {

        String name = ingredientName.toLowerCase(Locale.ROOT).trim();

        if (name.equals("egg") || name.equals("eggs")) {
            return android.graphics.Color.parseColor("#C99A24");
        }

        if (name.equals("tomato") || name.equals("tomatoes")) {
            return android.graphics.Color.parseColor("#C94C4C");
        }

        if (name.equals("potato") || name.equals("potatoes")) {
            return android.graphics.Color.parseColor("#A67C52");
        }

        if (name.equals("onion") || name.equals("onions")) {
            return android.graphics.Color.parseColor("#8A5A9E");
        }

        if (name.equals("garlic")) {
            return android.graphics.Color.parseColor("#7A9A5A");
        }

        if (name.equals("carrot") || name.equals("carrots")) {
            return android.graphics.Color.parseColor("#4F8A5B");
        }

        if (name.equals("mushroom") || name.equals("mushrooms")) {
            return android.graphics.Color.parseColor("#8C7B6B");
        }

        if (name.equals("avocado") || name.equals("avocados")) {
            return android.graphics.Color.parseColor("#5E8C3A");
        }

        if (name.equals("lemon") || name.equals("lemons")) {
            return android.graphics.Color.parseColor("#D9C220");
        }

        if (name.equals("apple") || name.equals("apples")) {
            return android.graphics.Color.parseColor("#B83A3A");
        }

        if (name.equals("banana") || name.equals("bananas")) {
            return android.graphics.Color.parseColor("#D4A72C");
        }

        if (name.equals("cucumber") || name.equals("cucumbers")) {
            return android.graphics.Color.parseColor("#3F9A5A");
        }

        if (name.equals("basil")) {
            return android.graphics.Color.parseColor("#2E7D4F");
        }

        if (name.equals("chicken")) {
            return android.graphics.Color.parseColor("#C9783A");
        }

        if (name.equals("beef")) {
            return android.graphics.Color.parseColor("#8E3B2F");
        }

        if (name.equals("shrimp") || name.equals("prawns")) {
            return android.graphics.Color.parseColor("#E07A5F");
        }

        if (name.equals("tuna")) {
            return android.graphics.Color.parseColor("#5F7F9E");
        }

        if (name.equals("cheese")) {
            return android.graphics.Color.parseColor("#D18B2C");
        }

        if (name.equals("milk")) {
            return android.graphics.Color.parseColor("#5B8DB8");
        }

        if (name.equals("cream")) {
            return android.graphics.Color.parseColor("#CFC3A5");
        }

        if (name.equals("yogurt") || name.equals("yoghurt")) {
            return android.graphics.Color.parseColor("#9FB8D0");
        }

        if (name.equals("butter")) {
            return android.graphics.Color.parseColor("#E3C75A");
        }

        if (name.equals("bread")) {
            return android.graphics.Color.parseColor("#9A6842");
        }

        if (name.equals("bun") || name.equals("buns")) {
            return android.graphics.Color.parseColor("#B07A45");
        }

        if (name.equals("tortilla") || name.equals("tortillas")) {
            return android.graphics.Color.parseColor("#D8B77A");
        }

        if (name.equals("pasta")) {
            return android.graphics.Color.parseColor("#7A5A9E");
        }

        if (name.equals("noodles")) {
            return android.graphics.Color.parseColor("#C9A45C");
        }

        if (name.equals("rice")) {
            return android.graphics.Color.parseColor("#B89B5E");
        }

        if (name.equals("oats")) {
            return android.graphics.Color.parseColor("#BFA67A");
        }

        if (name.equals("flour")) {
            return android.graphics.Color.parseColor("#C8BFA8");
        }

        if (name.equals("honey")) {
            return android.graphics.Color.parseColor("#E0A526");
        }

        if (name.equals("soy sauce")) {
            return android.graphics.Color.parseColor("#4A2F27");
        }

        if (name.equals("mayonnaise")) {
            return android.graphics.Color.parseColor("#D9D2A0");
        }

        if (name.equals("olive oil")) {
            return android.graphics.Color.parseColor("#8A9A3A");
        }

        if (name.equals("paprika")) {
            return android.graphics.Color.parseColor("#B5391F");
        }

        if (name.equals("cinnamon")) {
            return android.graphics.Color.parseColor("#9C5A32");
        }

        // Default colour for other ingredients
        return android.graphics.Color.parseColor("#6B4FA8");
    }

    private String getExpiryWarning(String expiryDate) {

        String[] dateFormats = {
                "dd/MM/yyyy",
                "yyyy-MM-dd",
                "MM/dd/yyyy"
        };

        Date expiry = null;

        for (String format : dateFormats) {

            try {

                SimpleDateFormat dateFormat =
                        new SimpleDateFormat(
                                format,
                                Locale.getDefault()
                        );

                dateFormat.setLenient(false);

                expiry = dateFormat.parse(expiryDate);

                if (expiry != null) {
                    break;
                }

            } catch (ParseException ignored) {
                // Try the next date format.
            }
        }

        if (expiry == null) {
            return null;
        }

        long currentTime = System.currentTimeMillis();
        long difference = expiry.getTime() - currentTime;

        long daysRemaining =
                difference / (1000L * 60L * 60L * 24L);

        if (daysRemaining < 0) {
            return "Expired!";
        }

        if (daysRemaining <= 3) {
            return "Expires soon!";
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView tvIngredientName;
        TextView tvIngredientDetails;
        TextView tvIngredientExpiry;
        TextView tvExpiryWarning;

        Button btnEditIngredient;
        Button btnDeleteIngredient;

        View ingredientColorAccent;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIngredientName =
                    itemView.findViewById(R.id.tvIngredientName);

            tvIngredientDetails =
                    itemView.findViewById(R.id.tvIngredientDetails);

            tvIngredientExpiry =
                    itemView.findViewById(R.id.tvIngredientExpiry);

            tvExpiryWarning =
                    itemView.findViewById(R.id.tvExpiryWarning);

            btnEditIngredient =
                    itemView.findViewById(R.id.btnEditIngredient);

            btnDeleteIngredient =
                    itemView.findViewById(R.id.btnDeleteIngredient);

            ingredientColorAccent =
                    itemView.findViewById(R.id.ingredientColorAccent);
        }
    }
}