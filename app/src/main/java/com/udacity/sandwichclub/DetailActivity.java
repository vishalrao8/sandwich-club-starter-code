package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView ingredientsIv;
    private TextView alsoKnownAsTv;
    private TextView placeOfOriginTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initializing required variables
        ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // Getting JSON String from resources
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json, this);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // Function to populate whole UI
        populateUI(sandwich);

        // Populating image from URL into ImageView
        Picasso.get()
                .load(sandwich.getImage())
                .error(R.drawable.ic_error)
                .into(ingredientsIv, new Callback() {
                    @Override
                    public void onSuccess() {

                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError(Exception e) {

                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);

                    }
                });

        // Updating title with main name
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Populating UI with collected information
        alsoKnownAsTv.setText(sandwich.getAlsoKnownAs().get(0));
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());

        for (int i = 0; i < sandwich.getIngredients().size(); i++) {

            if (i != sandwich.getIngredients().size()-1)
                ingredientsTv.append(sandwich.getIngredients().get(i) + ", ");

            else
                ingredientsTv.append(sandwich.getIngredients().get(i) + ".");

        }

    }
}
