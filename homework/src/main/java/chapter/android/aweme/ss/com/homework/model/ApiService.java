package chapter.android.aweme.ss.com.homework.model;

import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("api/invoke/video/invoke/video")
    Call<JsonArray> getR();

}
