package nanodegree.damian.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RemoteViews;

import nanodegree.damian.bakingapp.IngredientsWidgetConfigure;
import nanodegree.damian.bakingapp.MainActivity;
import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.data.database.AppDatabase;

/**
 * Created by robert_damian on 06.06.2018.
 */

public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static void updateIngredientsWidget(final Context context, AppWidgetManager appWidgetManager,
                                               int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        Intent intent = new Intent(context, IngredientsWidgetService.class);

        intent.putExtra(IngredientsWidgetService.WIDGET_ID, appWidgetId);
        remoteViews.setRemoteAdapter(R.id.lv_ingredients, intent);

        Intent launchAppIntent = new Intent(context, MainActivity.class);
        PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context, 0,
                launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.lv_ingredients, launchAppPendingIntent);

        AsyncTask.execute(() -> {
            int preferredRecipeId = IngredientsWidgetConfigure
                    .loadPreferredRecipe(context, appWidgetId);
            remoteViews.setTextViewText(R.id.tv_recipe_name, AppDatabase.getInstance(context)
                    .recipeDao().loadRecipe(preferredRecipeId).getName());
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        });
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateIngredientsWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        updateIngredientsWidget(context, appWidgetManager, appWidgetId);
    }
}
