package Database;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create the retrofit API server
 * connect with the backend NodeJs server
 * Base_UR:: http://10.0.2.2:8070/
 */
public class RetrofitClient {
    private static Retrofit retrofit;
    private static String BASE_URL="http://10.0.2.2:8070/";

    //Create the API to connect with the backend server
    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
