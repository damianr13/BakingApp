package nanodegree.damian.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import nanodegree.damian.bakingapp.IngredientsWidgetConfigure;
import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.data.Ingredient;
import nanodegree.damian.bakingapp.data.database.AppDatabase;

/**
 * Created by robert_damian on 06.06.2018.
 */

public class IngredientsWidgetService extends RemoteViewsService {

    public static final String WIDGET_ID = "WIDGET_ID";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int preferredRecipeId = intent.getIntExtra(WIDGET_ID,
                IngredientsWidgetConfigure.DEFAULT_RECIPE_ID);
        return new IngredientsRemoteViewsFactory(this.getApplicationContext(), preferredRecipeId);
    }
}

class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredient> mIngredientList;
    private int mAppWidgetId;

    IngredientsRemoteViewsFactory(Context context, int widgetId) {
        this.mAppWidgetId = widgetId;
        this.mContext = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        loadIngredientsFromDatabase();
    }

    private void loadIngredientsFromDatabase() {
        int preferredRecipe = IngredientsWidgetConfigure.loadPreferredRecipe(mContext, mAppWidgetId);
        mIngredientList = AppDatabase.getInstance(mContext).ingredientDao()
                .getIngredientsByRecipe(preferredRecipe);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredientList == null) {
            return 0;
        }

        return mIngredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Ingredient selectedIngredient = mIngredientList.get(i);

        RemoteViews result = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);
        result.setTextViewText(R.id.tv_ingredient_name, selectedIngredient.getName());
        result.setTextViewText(R.id.tv_measure, selectedIngredient.getMeasure());
        result.setTextViewText(R.id.tv_quantity,
                String.valueOf(selectedIngredient.getQuantity()));

        result.setOnClickFillInIntent(R.id.tv_ingredient_name, new Intent());

        return result;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
