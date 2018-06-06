package nanodegree.damian.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.util.ArrayList;

import nanodegree.damian.bakingapp.MainActivity;
import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.data.Ingredient;
import nanodegree.damian.bakingapp.visuals.IngredientArrayAdapter;

/**
 * Created by robert_damian on 06.06.2018.
 */

public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static void updatePlantWidget(Context context, AppWidgetManager appWidgetManager,
                                         int appWidgetId) {
        Intent launchAppIntent = new Intent(context, MainActivity.class);
        PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context, 0,
                launchAppIntent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}
