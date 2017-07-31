package xyz.appsian.googlebooksparser.di.modules;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.appsian.googlebooksparser.di.GoogleBooksAPI;

/**
 * Created by jeromeraymond on 7/29/17.
 */
@Module
public class NetworkModule {

    /**
     * The Base url.
     */
    public final String BASE_URL = "https://www.googleapis.com/books/v1/";

    /**
     * Provide client ok http client.
     *
     * @return the ok http client
     */
    @Provides
    public OkHttpClient provideClient() {
        return new OkHttpClient.Builder().build();
    }

    /**
     * Provide a retrofit instance.
     *
     * @param baseUrl the base url
     * @param client  the client
     * @return the retrofit
     */
    @Provides
    public Retrofit provideRetrofit(String baseUrl,OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * Provide google books api google books api.
     *
     * @return the google books api
     */
    @Provides
    public GoogleBooksAPI provideGoogleBooksAPI()
    {
        return provideRetrofit(BASE_URL,provideClient()).create(GoogleBooksAPI.class);
    }
}
