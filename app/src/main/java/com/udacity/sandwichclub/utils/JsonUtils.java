package com.udacity.sandwichclub.utils;

import android.util.TypedValue;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

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
                JSONObject sandwichNameInfo = sandwichJsonData.getJSONObject("name");
                JSONArray alsoKnownAsArray = sandwichNameInfo.getJSONArray("alsoKnownAs");
                if (alsoKnownAsArray.length() > 0) {

                    alsoKnownAs = Arrays.asList(alsoKnownAsArray.getString(0));

                } else
                    alsoKnownAs.add("N/A");

                /* Fetching out mainName */
                mainName = sandwichNameInfo.getString("mainName");

                /* Fetching out placeOfOrigin */
                placeOfOrigin = sandwichJsonData.getString("placeOfOrigin");
                if (placeOfOrigin.equals(""))
                    placeOfOrigin = "N/A";

                /* Fetching out description */
                description = sandwichJsonData.getString("description");

                /* Fetching out image's URL */
                image = sandwichJsonData.getString("image");

                /* Fetching out ingredients */
                JSONArray ingredientsArray = sandwichJsonData.getJSONArray("ingredients");
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

