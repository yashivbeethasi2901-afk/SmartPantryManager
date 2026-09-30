# Smart Pantry Manager

A Java Android application that helps reduce food waste by tracking the ingredients a user already has at home and suggesting recipes they can cook using those ingredients.

**Module:** Mobile App Development 700 – Practical Assignment
**Student:** Yashiv Beethasi (402313265)

---

## Description

Smart Pantry Manager lets the user keep a digital pantry of their ingredients, including quantity, unit and an optional expiry date. The app compares the pantry against a built-in collection of recipes and shows the recipes the user can make with what they already have, so there is no need for an extra shopping trip.

### Features

- **Pantry management:** add, edit and delete ingredients (name, quantity, unit and optional expiry date), with input validation on the form.
- **Pantry list:** all ingredients are shown in a RecyclerView with a custom adapter, loaded from the database.
- **Expiry warnings:** ingredients that are expired or expiring within 3 days are flagged (can be switched off in Settings).
- **Recipe collection:** 15 recipes are pre-loaded into the database on first run.
- **Suggested Recipes (strict matching):** a recipe is only suggested if every one of its ingredients is in the pantry in at least the required quantity, so a recipe with even one missing ingredient is excluded. Matching ignores case and spacing, treats singular and plural names as the same (for example tomato and tomatoes), normalises unit spellings and converts between grams and kg and between ml and litres, and adds up quantities when the same ingredient is stored more than once. When no recipe qualifies, a "No recipes match your pantry yet" message is shown instead of a blank screen.
- **Recipe detail:** shows the full ingredient list and method for a selected recipe.
- **Settings:** toggle expiry reminders and switch between light and dark mode.
- **Modern UI:** a green pantry-themed palette using Material 3, with a matching light and dark theme.

### Screens

| Screen | Activity |
| --- | --- |
| Pantry list | `PantryActivity` |
| Add / Edit ingredient | `AddIngredientActivity` |
| Suggested recipes | `SuggestedRecipesActivity` |
| Recipe detail | `RecipeDetailActivity` |
| Settings | `SettingsActivity` |

Screens are connected with Intents, which also pass the selected ingredient or recipe between activities.

---

## Database choice: SQLite

The app uses **SQLite** through `SQLiteOpenHelper` (the `DatabaseHelper` class), stored locally on the device.

**Why SQLite:**

- The pantry and recipe data belongs to a single user on a single device, so there is no need for cloud sync or a separate backend.
- It works offline, which suits a kitchen or pantry where connectivity may be poor.
- It needs no accounts, hosting or extra services, which keeps setup simple for anyone running the project.
- It is covered in the module's persistent data chapter, and it fully supports Create, Read, Update and Delete, with data that persists after the app is closed and reopened.

### Data model

**ingredients**

| Column | Type | Notes |
| --- | --- | --- |
| id | INTEGER | Primary key, auto-increment |
| name | TEXT | Required |
| quantity | REAL | Required |
| unit | TEXT | Required |
| expiryDate | TEXT | Optional |

**recipes**

| Column | Type | Notes |
| --- | --- | --- |
| id | INTEGER | Primary key, auto-increment |
| name | TEXT | Required |
| description | TEXT | |
| ingredients | TEXT | Required. Stored as `name:quantity:unit` entries separated by commas, for example `egg:2:items,cheese:30:grams` |
| instructions | TEXT | Preparation steps |

The default recipes are inserted when the database is first created. If the database version is increased, the tables are recreated and the default recipes are inserted again.

---

## Setup and run instructions

### Requirements

- Android Studio (a recent version)
- JDK 11 or newer (the one bundled with Android Studio is fine)
- An Android emulator or a physical Android device with USB debugging enabled

### Steps

1. **Clone the repository**
   ```
   git clone https://github.com/yashivbeethasi2901-afk/SmartPantryManager.git
   ```
2. **Open the project:** in Android Studio choose **File → Open** and select the cloned `SmartPantryManager` folder.
3. **Sync Gradle:** wait for the automatic sync to finish. If it does not start, choose **File → Sync Project with Gradle Files**. The first sync may take a few minutes while dependencies download.
4. **Choose a device:** create an emulator in **Device Manager**, or connect a physical device.
5. **Run the app:** press the green **Run** button or `Shift+F10`.

### First run

- The recipe collection is created automatically the first time the app runs.
- If you have installed an older version of the app and the recipes look out of date, uninstall the app from the device and run it again. This recreates the database. Note that this also clears any saved pantry items.

---

## Technologies

- Java
- Android Studio
- SQLite (`SQLiteOpenHelper`)
- RecyclerView with a custom adapter
- Material 3 theme with light and dark mode

## Scope

The app deals only with the user's own pantry and recipe matching. It does not use Google Maps, any mapping SDK, or location services.
