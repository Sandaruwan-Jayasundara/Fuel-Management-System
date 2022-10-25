package Models;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Declare all APIs
 */
public interface Methods {

    //Display time data
    @FormUrlEncoded
    @POST("api/time/display")
    Call<Time> getTime(@Field("StationNumber") String StationNumber);

    //display patrol details
    @FormUrlEncoded
    @POST("api/patrol/display")
    Call<Model> getPatrol(@Field("StationNumber") String StationNumber);

    //Display Diesel details
    @FormUrlEncoded
    @POST("api/diesel/display")
    Call<Model> getDesel(@Field("StationNumber") String StationNumber);

    //Remove Diesel details
    @FormUrlEncoded
    @POST("api/diesel/remove")
    Call<Model> deleteDesel(@Field("StationNumber") String StationNumber
    );

    //Display patrol details
    @FormUrlEncoded
    @POST("api/patrol/display")
    Call<Model> shedPatrol(@Field("StationNumber") String StationNumber
    );

    //Display Diesel details
    @FormUrlEncoded
    @POST("api/diesel/display")
    Call<Model> shedDesel(@Field("StationNumber") String StationNumber
    );

    //Add patrol details to the database
    @FormUrlEncoded
    @POST("api/patrol")
    Call<Model> addPatrol(@Field("liters") String Liters,
                          @Field("arrival") String Arrival,
                          @Field("departure") String Departure,
                          @Field("queue") String Queue,
                          @Field("StationNumber") String StationNumber,
                          @Field("StationName") String StationName,
                          @Field("StationLocation") String StationLocation
    );

    //Update patrol details
    @FormUrlEncoded
    @POST("api/patrol/update")
    Call<Model> updatePatrol(@Field("liters") String Liters,
                             @Field("arrival") String Arrival,
                             @Field("departure") String Departure,
                             @Field("queue") String Queue,
                             @Field("StationNumber") String StationNumber,
                             @Field("StationName") String StationName,
                             @Field("StationLocation") String StationLocation
    );

    @FormUrlEncoded
    @POST("api/patrol/remove")
    Call<Model> deletePatrol(@Field("StationNumber") String StationNumber
    );

    //Add Diesel details
    @FormUrlEncoded
    @POST("api/diesel")
    Call<Model> addDesel(@Field("liters") String Liters,
                         @Field("arrival") String Arrival,
                          @Field("departure") String Departure,
                         @Field("queue") String Queue,
                         @Field("StationNumber") String StationNumber,
                         @Field("StationName") String StationName,
                         @Field("StationLocation") String StationLocation
    );

    //Update Diesel details
    @FormUrlEncoded
    @POST("api/diesel/update")
    Call<Model> updateDesel(@Field("liters") String Liters,
                            @Field("arrival") String Arrival,
                             @Field("departure") String Departure,
                            @Field("queue") String Queue,
                            @Field("StationNumber") String StationNumber,
                            @Field("StationName") String StationName,
                            @Field("StationLocation") String StationLocation
    );

    //Add time details
    @FormUrlEncoded
    @POST("api/time")
    Call<Model> AddDetail(@Field("arrival") String Arrival,
                          @Field("departure") String Departure,
                          @Field("StationNumber") String StationNumber,
                          @Field("StationName") String StationName,
                          @Field("StationLocation") String StationLocation
    );

    //Remove time details
    @FormUrlEncoded
    @POST("api/time/remove")
    Call<Model> remove(@Field("StationNumber") String StationNumber
    );

    //Add Queue details
    @FormUrlEncoded
    @POST("api/queue")
    Call<Queue> addToQueue(@Field("type") String type,
                           @Field("queue") String queue,
                           @Field("shed") String shed
    );

    //Display Queue details
    @FormUrlEncoded
    @POST("api/queue/display")
    Call<Queue> getQueue(@Field("shed") String shed
    );

    //Decrease Queue count
    @FormUrlEncoded
    @POST("api/queue/decr")
    Call<Queue> decreaseQueue(@Field("shed") String shed,
                              @Field("queue") String queue
    );

    //Display user details
    @FormUrlEncoded
    @POST("api/user/display")
    Call<UserQueue> UserData(@Field("StationNumber") String StationNumber
    );

    //add user details to the database
    @FormUrlEncoded
    @POST("api/user")
    Call<UserQueue> addQueueTime(
            @Field("StationNumber") String StationNumber,
            @Field("Fuel") String Fuel
    );

    //Update user details to the database
    @FormUrlEncoded
    @POST("api/user/update")
    Call<UserQueue> updateQueueTime(
            @Field("StationNumber") String StationNumber,
            @Field("Status") String Status
    );
}

