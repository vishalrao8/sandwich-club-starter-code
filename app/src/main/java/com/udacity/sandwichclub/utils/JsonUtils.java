package com.udacity.sandwichclub.utils;

import android.content.Context;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json, Context context) {

        if (json != null) {

            try {

                /* Initializing sandwich object */
                Sandwich sandwich = new Sandwich();

                /* Setting required variables */
                List<String> alsoKnownAs = new ArrayList<>();
                String mainName;
                String placeOfOrigin;
                String description;
                List<String> ingredients = new ArrayList<>();
                String image;

                /* Fetching out alsoKnownAs */
                JSONObject sandwichJsonData = new JSONObject(json);
                JSONObject sandwichNameInfo = sandwichJsonData.getJSONObject(context.getString(R.string.name_tag));
                JSONArray alsoKnownAsArray = sandwichNameInfo.getJSONArray(context.getString(R.string.also_known_as_tag));
                if (alsoKnownAsArray.length() > 0) {

                    alsoKnownAs.add(alsoKnownAsArray.getString(0));

                } else
                    alsoKnownAs.add(context.getString(R.string.not_applicable));

                /* Fetching out mainName */
                mainName = sandwichNameInfo.getString(context.getString(R.string.main_name_tag));

                /* Fetching out placeOfOrigin */
                placeOfOrigin = sandwichJsonData.getString(context.getString(R.string.place_of_origin_tag));
                if (placeOfOrigin.equals(""))
                    placeOfOrigin = context.getString(R.string.not_applicable);

                /* Fetching out description */
                description = sandwichJsonData.getString(context.getString(R.string.description_tag));

                /* Fetching out image's URL */
                image = sandwichJsonData.getString(context.getString(R.string.image_tag));

                /* Fetching out ingredients */
                JSONArray ingredientsArray = sandwichJsonData.getJSONArray(context.getString(R.string.ingredients_tag));
                for (int i = 0; i < ingredientsArray.length(); i++) {

                    ingredients.add(ingredientsArray.getString(i));

                }

                /* Updating sandwich object with collected information */
                sandwich.setAlsoKnownAs(alsoKnownAs);
                sandwich.setMainName(mainName);
                sandwich.setPlaceOfOrigin(placeOfOrigin);
                sandwich.setDescription(description);
                sandwich.setImage(image);
                sandwich.setIngredients(ingredients);

                return sandwich;


            } catch (JSONException e) {

                e.printStackTrace();
                return null;

            }

        } else

            return null;

    }
}

