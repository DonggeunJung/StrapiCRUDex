package com.example.strapicrudex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.strapicrudex.model.Place;
import com.example.strapicrudex.model.RestApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText etX;
    EditText etY;
    ListView lvPlaces;
    List<Place> placesList;
    ArrayList<String> placesStringList = new ArrayList<String>();
    long selId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etX = findViewById(R.id.etX);
        etY = findViewById(R.id.etY);
        lvPlaces = findViewById(R.id.lvPlaces);
        lvPlaces.setOnItemClickListener(mItemClickListener);

        getPlacesList();
    }

    private void getPlacesList() {
        Call<List<Place>> call = RestApi.api.getPlacesList();

        call.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                placesList = response.body();
                if(placesList != null) {
                    placesStringList.clear();

                    for (int i = 0; i < placesList.size(); i++) {
                        Place place = placesList.get(i);
                        String line = place.getId() + " - x : " + place.getX() + " / y : " + place.getY();
                        placesStringList.add(line);
                    }
                    lvPlaces.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, placesStringList));
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    AdapterView.OnItemClickListener mItemClickListener =
            new AdapterView.OnItemClickListener() {
        @SuppressWarnings("unchecked")
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Place place = placesList.get(position);
            selId = place.getId();
            etX.setText("" + place.getX());
            etY.setText("" + place.getY());
        }
    };

    public void onBnAdd(View v) {
        Float x = Float.parseFloat( etX.getText().toString() );
        Float y = Float.parseFloat( etY.getText().toString() );
        Place place = new Place(x, y);
        Call<Place> call = RestApi.api.addPlace(place);

        call.enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                getPlacesList();
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBnModify(View v) {
        if(selId < 1) { return; }
        Float x = Float.parseFloat( etX.getText().toString() );
        Float y = Float.parseFloat( etY.getText().toString() );
        Place place = new Place(selId, x, y);
        Call<Place> call = RestApi.api.modifyPlace(selId, place);

        call.enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                getPlacesList();
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBnDel(View v) {
        if(selId < 1) { return; }
        Call<Place> call = RestApi.api.delPlace(selId);

        call.enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                getPlacesList();
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
