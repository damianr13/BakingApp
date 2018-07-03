package nanodegree.damian.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.data.RecipeStep;

/**
 * Created by robert_damian on 03.07.2018.
 */

public class StepListFragment extends Fragment{
    public static final String EXTRA_RECIPE = RecipeDetailsFragment.EXTRA_RECIPE;
    public static final String TAG = StepListFragment.class.getName();

    private Recipe mRecipe;
    @BindView(R.id.lv_steps)
    ListView mStepListView;

    private OnStepSelectionListener mStepSelectionListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_list,
                container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() == null) {
            // if there is no parent activity we just return an empty view
            return rootView;
        }

        if (mRecipe == null) {
            if (getActivity().getIntent() == null || !getActivity().getIntent().hasExtra(EXTRA_RECIPE)) {
                // if there is no way to get a recipe we return an empty view early
                return rootView;
            }
            mRecipe = Parcels.unwrap(getActivity().getIntent().getParcelableExtra(EXTRA_RECIPE));
        }

        ArrayAdapter<String> stepsArrayAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_string_item, getStepDescriptions());
        mStepListView.setAdapter(stepsArrayAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mStepSelectionListener = (OnStepSelectionListener) context;
        } catch (ClassCastException ex) {
            Log.e(TAG, "Context must implement OnStepSelectionListener", ex);
        }
    }

    @OnItemClick(R.id.lv_steps)
    void selectStep(int index) {
        mStepSelectionListener.selectStep(index);
    }

    private List<String> getStepDescriptions() {
        List<String> result = new ArrayList<>();
        if (mRecipe == null) {
            // return empty list if we don't have a recipe
            return result;
        }

        for (RecipeStep step : mRecipe.getStepList()) {
            result.add(step.getShortDescription());
        }

        return result;
    }

    public static interface OnStepSelectionListener {
        void selectStep(int index);
    }
}
