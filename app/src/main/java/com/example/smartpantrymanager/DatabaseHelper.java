package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create ingredients table
        String createIngredientsTable = "CREATE TABLE ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiryDate TEXT)";

        db.execSQL(createIngredientsTable);


        // Create recipes table
        String createRecipesTable = "CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "description TEXT, " +
                "ingredients TEXT NOT NULL, " +
                "instructions TEXT)";

        db.execSQL(createRecipesTable);


        // Add the default recipes
        insertDefaultRecipes(db);
    }

    private void insertDefaultRecipes(SQLiteDatabase db) {

        // 1. Spanish Tortilla
        insertRecipe(
                db,
                "Spanish Tortilla",
                "A thick, golden potato and onion omelette served warm or cold.",
                "egg:4:items,potato:2:items,onion:1:items,olive oil:40:ml",
                "Thinly slice the potatoes and onion and cook slowly in olive oil until soft. Beat the eggs, mix in the potato and onion, then pour back into the pan. Cook on low heat until set, flip carefully, and cook the other side until golden."
        );

        // 2. Honey Garlic Chicken
        insertRecipe(
                db,
                "Honey Garlic Chicken",
                "Sticky, sweet and savoury chicken pieces glazed in honey and soy.",
                "chicken:200:grams,garlic:3:items,honey:30:ml,soy sauce:20:ml,olive oil:10:ml",
                "Cut the chicken into bite-sized pieces and brown in olive oil. Add minced garlic and cook for one minute. Stir in honey and soy sauce and simmer until the sauce thickens and coats the chicken."
        );

        // 3. Creamy Mushroom Pasta
        insertRecipe(
                db,
                "Creamy Mushroom Pasta",
                "Pasta tossed in a rich garlic mushroom cream sauce.",
                "pasta:200:grams,mushroom:150:grams,garlic:2:items,cream:100:ml,cheese:30:grams,butter:10:grams",
                "Cook the pasta. Fry sliced mushrooms and garlic in butter until browned. Pour in the cream and simmer for a few minutes, stir in the cheese, then toss with the drained pasta."
        );

        // 4. Veggie Stir-Fry Noodles
        insertRecipe(
                db,
                "Veggie Stir-Fry Noodles",
                "Quick noodles with crunchy vegetables and a soy garlic glaze.",
                "noodles:200:grams,carrot:1:items,onion:1:items,garlic:2:items,soy sauce:20:ml,olive oil:15:ml",
                "Boil the noodles and drain. Stir-fry sliced carrot, onion and garlic in hot oil for a few minutes. Add the noodles and soy sauce and toss until everything is evenly coated and hot."
        );

        // 5. Avocado Egg Toast
        insertRecipe(
                db,
                "Avocado Egg Toast",
                "Crunchy toast topped with smashed avocado, lemon and a fried egg.",
                "bread:2:items,avocado:1:items,egg:2:items,lemon:1:items,olive oil:5:ml",
                "Toast the bread. Mash the avocado with a squeeze of lemon juice and spread it on the toast. Fry the eggs in olive oil and place one on each slice."
        );

        // 6. Tuna Rice Bowl
        insertRecipe(
                db,
                "Tuna Rice Bowl",
                "A fresh rice bowl with creamy tuna, cucumber and soy.",
                "rice:200:grams,tuna:1:items,cucumber:1:items,soy sauce:15:ml,mayonnaise:15:ml",
                "Cook the rice and let it cool slightly. Mix the tuna with mayonnaise. Slice the cucumber, then arrange the rice, tuna and cucumber in a bowl and drizzle with soy sauce."
        );

        // 7. Cheesy Potato Bake
        insertRecipe(
                db,
                "Cheesy Potato Bake",
                "Layers of sliced potato baked in garlic milk with a golden cheese top.",
                "potato:4:items,cheese:80:grams,milk:100:ml,butter:15:grams,garlic:1:items",
                "Slice the potatoes thinly and layer them in a buttered dish. Pour over milk mixed with crushed garlic and cover with cheese. Bake at 180C for about 45 minutes until tender and golden."
        );

        // 8. Lemon Butter Chicken and Rice
        insertRecipe(
                db,
                "Lemon Butter Chicken and Rice",
                "Juicy chicken in a bright lemon butter sauce served over rice.",
                "chicken:150:grams,rice:200:grams,lemon:1:items,butter:20:grams,garlic:2:items",
                "Cook the rice. Pan-fry the chicken in half the butter until cooked through. Add garlic, the remaining butter and the lemon juice, simmer briefly, and serve over the rice."
        );

        // 9. Banana Oat Bowl
        insertRecipe(
                db,
                "Banana Oat Bowl",
                "Warm, creamy oats topped with banana and a drizzle of honey.",
                "oats:80:grams,milk:200:ml,banana:1:items,honey:15:ml",
                "Heat the oats and milk in a pot, stirring, until thick and creamy. Pour into a bowl, top with sliced banana and drizzle with honey."
        );

        // 10. Roasted Tomato Soup
        insertRecipe(
                db,
                "Roasted Tomato Soup",
                "Smooth, comforting soup made from roasted tomatoes, garlic and basil.",
                "tomato:5:items,onion:1:items,garlic:2:items,basil:5:grams,cream:50:ml,olive oil:15:ml",
                "Roast the tomatoes, onion and garlic with olive oil at 200C for 25 minutes. Blend with basil until smooth, warm in a pot, and stir in the cream before serving."
        );

        // 11. Chicken Yogurt Wrap
        insertRecipe(
                db,
                "Chicken Yogurt Wrap",
                "A tortilla wrap with chicken, cool cucumber and tangy yogurt.",
                "tortilla:2:items,chicken:100:grams,cucumber:1:items,yogurt:80:grams,tomato:1:items",
                "Cook and slice the chicken. Slice the cucumber and tomato. Spread yogurt over the tortillas, add the chicken and vegetables, then roll tightly."
        );

        // 12. Cinnamon Apple Pancakes
        insertRecipe(
                db,
                "Cinnamon Apple Pancakes",
                "Fluffy pancakes with grated apple and a warm hint of cinnamon.",
                "apple:1:items,flour:100:grams,egg:1:items,milk:120:ml,cinnamon:2:grams,butter:10:grams",
                "Grate the apple and mix with flour, egg, milk and cinnamon into a thick batter. Melt butter in a pan and cook spoonfuls of batter until golden on both sides."
        );

        // 13. Shakshuka
        insertRecipe(
                db,
                "Shakshuka",
                "Eggs poached in a spiced tomato and onion sauce.",
                "egg:3:items,tomato:4:items,onion:1:items,garlic:2:items,paprika:3:grams,olive oil:20:ml",
                "Soften chopped onion and garlic in olive oil. Add chopped tomatoes and paprika and simmer until thick. Make small wells in the sauce, crack in the eggs, cover, and cook until the whites are set."
        );

        // 14. Cheeseburger
        insertRecipe(
                db,
                "Cheeseburger",
                "A classic homemade beef burger with melted cheese and fresh toppings.",
                "beef:200:grams,bun:2:items,cheese:40:grams,tomato:1:items,onion:1:items",
                "Shape the beef into two patties and fry for about 4 minutes per side. Add cheese at the end to melt. Slice the tomato and onion, and assemble everything in the buns."
        );

        // 15. Garlic Butter Shrimp Pasta
        insertRecipe(
                db,
                "Garlic Butter Shrimp Pasta",
                "Pasta with juicy shrimp in a zesty garlic butter sauce.",
                "pasta:200:grams,shrimp:150:grams,garlic:3:items,butter:20:grams,lemon:1:items",
                "Cook the pasta. Melt butter in a pan, add garlic and cook briefly, then add the shrimp until pink. Squeeze in the lemon juice, add the pasta, and toss to coat."
        );
    }

    private void insertRecipe(
            SQLiteDatabase db,
            String name,
            String description,
            String ingredients,
            String instructions
    ) {

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("description", description);
        values.put("ingredients", ingredients);
        values.put("instructions", instructions);

        db.insert("recipes", null, values);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        db.execSQL("DROP TABLE IF EXISTS ingredients");
        db.execSQL("DROP TABLE IF EXISTS recipes");

        onCreate(db);
    }
}