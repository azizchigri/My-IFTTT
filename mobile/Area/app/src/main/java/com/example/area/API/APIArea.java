package com.example.area.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIArea {

    @POST("/login")
    Call<Void> UserLogin(@Body Login login);

    @GET("/users")
    Call<User> getUser(@Query("username") String username);

    @POST("/users/sign-up")
    Call<User> UserSignup(@Body UserSignup user);

    @PUT("/users")
    Call<Void> UserUpdate(@Body User user);

    @GET("/actions")
    Call<List<Action>> ListActions();

    @POST("/actions")
    Call<Void> CreateAction(@Body NewAction newAction);

    @GET("/actions/{actionName}")
    Call<SpecificAction> getSpecificAction(@Path("actionName") String actionName);

    @GET("/reactions")
    Call<List<Reaction>> ListReactions();

    @GET("/reactions/{reactionName}")
    Call<SpecificReaction> getSpecificReaction(@Path("reactionName") String reactionName);

    @GET("/about.json")
    Call<About> getAbout();
}