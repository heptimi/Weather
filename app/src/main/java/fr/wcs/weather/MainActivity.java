package fr.wcs.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "119648f18330ca4f98d45cb9021c7c9c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView tvweather = (TextView) findViewById(R.id.tvweather) ;



        // Crée une file d'attente pour les requêtes vers l'API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // TODO : URL de la requête vers l'API
        String url = "https://api.openweathermap.org/data/2.5/weather?q=Bordeaux&appid=" + API_KEY;
        String url2= "https://api.openweathermap.org/data/2.5/forecast?q=Bordeaux&appid=" + API_KEY;


        // Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url2, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO : traiter la réponse

                        try {

                            //                          JSONArray weather = response.getJSONArray("weather");
//                            for (int i = 0; i < weather.length(); i++) {
//                                JSONObject weatherInfos = (JSONObject) weather.get(i);
//                                String description = weatherInfos.getString("description");
//                                tvweather.setText(description);
//                              }

                            int day = 1;
                            String description = "";
                            JSONArray list = response.getJSONArray("list");
                            for (int i = 0; i < list.length(); i+=8) {
                                JSONObject listInfos = (JSONObject) list.getJSONObject(i);
                                JSONArray weather = listInfos.getJSONArray("weather");
                                JSONObject weatherdescription = weather.getJSONObject(0);
                                description = weatherdescription.getString("description");
                                //tvweather.append("Day %d : %s %n", day, description);
                                tvweather.append(day + ":" + description + "\n");
                                day++;
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);
    }
}
