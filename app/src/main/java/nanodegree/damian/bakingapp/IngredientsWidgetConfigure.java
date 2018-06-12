package nanodegree.damian.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.helpers.DatabaseRecipesLoader;
import nanodegree.damian.bakingapp.widget.IngredientsWidgetProvider;

/**
 * Inspired by: https://android.googlesource.com/platform/development/+/master/samples/ApiDemos/src/com/example/android/apis/appwidget/ExampleAppWidgetConfigure.java
 */
public class IngredientsWidgetConfigure extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Recipe>>{

    public static final String SHARED_PREFERENCES_PREFIX = "RECIPE_FOR_WIDGET_";
    public static final int DEFAULT_RECIPE_ID = 1;
    public static final int RECIPE_NAMES_LOADER_ID = 113;


    @BindView(R.id.lv_recipes)
    ListView recipeListView;

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_widget_configure);
        ButterKnife.bind(this);       // Find the widget id from the intent.

        // handle widget id
        mAppWidgetId = getIntent().getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        getSupportLoaderManager().initLoader(RECIPE_NAMES_LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new DatabaseRecipesLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        List<String> recipeNameList = new ArrayList<>();

        for (Recipe recipe : data) {
            recipeNameList.add(recipe.getName());
        }

        ArrayAdapter<String> simpleArrayAdapter = new ArrayAdapter<>(this,
                R.layout.simple_string_item, recipeNameList);
        recipeListView.setAdapter(simpleArrayAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    @OnItemClick(R.id.lv_recipes)
    public void selectRecipe(int position) {
        storePreferredRecipe(this, mAppWidgetId, position + 1);

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        widgetManager.notifyAppWidgetViewDataChanged(mAppWidgetId, R.id.lv_ingredients);
        IngredientsWidgetProvider.updateIngredientsWidget(this, widgetManager,
                mAppWidgetId);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);

        finish();
    }

    public static int loadPreferredRecipe(Context context, int mAppWidgetId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getInt(SHARED_PREFERENCES_PREFIX + mAppWidgetId,
                DEFAULT_RECIPE_ID);
    }

    public static void storePreferredRecipe(Context context, int mAppWidgetId, int selectedRecipeId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREFERENCES_PREFIX + mAppWidgetId, selectedRecipeId);
        editor.apply();
    }
}
