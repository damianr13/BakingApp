package nanodegree.damian.bakingapp.visuals;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.data.Ingredient;

/**
 * Created by robert_damian on 03.06.2018.
 */

public class IngredientArrayAdapter extends ArrayAdapter<Ingredient> {

    @BindView(R.id.tv_ingredient_name)
    TextView mIngredientNameTextView;
    @BindView(R.id.tv_quantity)
    TextView mQuantityTextView;
    @BindView(R.id.tv_measure)
    TextView mMeasureTextView;

    private Context mContext;
    private List<Ingredient> mIngredientList;

    public IngredientArrayAdapter(@NonNull Context context, List<Ingredient> ingredients) {
        super(context, 0, ingredients);

        this.mContext = context;
        this.mIngredientList = ingredients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.ingredient_item, parent, false);
        }

        ButterKnife.bind(this, convertView);
        Ingredient ingredient = mIngredientList.get(position);

        mIngredientNameTextView.setText(ingredient.getName());
        mQuantityTextView.setText(String.valueOf(ingredient.getQuantity()));
        mMeasureTextView.setText(ingredient.getMeasure());
        return convertView;
    }
}
