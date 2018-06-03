package nanodegree.damian.bakingapp.visuals;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.data.Recipe;

/**
 * Created by robert_damian on 03.06.2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private List<Recipe> mRecipeList;
    private int mRecipeHoldersCount;

    public RecipeAdapter(List<Recipe> recipeList) {
        this.mRecipeList = recipeList;
        mRecipeHoldersCount = 0;
    }

    public void swapRecipeList(List<Recipe> recipeList) {
        this.mRecipeList = recipeList;
        mRecipeHoldersCount = 0;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieView = inflater.inflate(R.layout.recipe_item, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(movieView);

        recipeViewHolder.bind(mRecipeList.get(mRecipeHoldersCount));
        mRecipeHoldersCount++;
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(mRecipeList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) {
            return 0;
        }
        return mRecipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        @BindView(R.id.tv_recipe_name)
        TextView mShortDescriptionTextView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {

        }

        void bind(Recipe recipe) {
            mShortDescriptionTextView.setText(recipe.getName());
        }
    }
}
