package com.example.strapicrudex.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    // Make Retrofit object
    public static final RestApi api = new Retrofit.Builder()
            .baseUrl(RestApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
            .build().create(RestApi.class);

    String BASE_URL = "http://110.234.25.90:1337/";

    @GET("places")
    Call<List<Place>> getPlacesList();

    @POST("places")
    Call<Place> addPlace(@Body Place place);

    @PUT("places/{id}")
    Call<Place> modifyPlace(@Path("id") long id, @Body Place place);

    @DELETE("places/{id}")
    Call<Place> delPlace(@Path("id") long id);


    /*
@ Delete item
DELETE http://110.234.25.90:1337/places/2
 = Response : { "id": 4, "x": 40, "y": 41, "created_by": null, "updated_by": null,
"created_at": "2020-09-08T00:53:20.946Z", "updated_at": "2020-09-08T00:57:36.651Z"}
    * */
}
