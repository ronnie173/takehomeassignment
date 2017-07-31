package xyz.appsian.googlebooksparser.di;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import xyz.appsian.googlebooksparser.api.GoogleBooksApiResponse;


//https://www.googleapis.com/books/v1/volumes?q=quilting

/**
 * The interface Google books api.
 */


public interface GoogleBooksAPI {

    /**
     * Gets all books.
     *I just keep this one in here just incase I wanted to do it the retrofit way
     * @param value the value
     * @return the all books
     */
    @GET("volumes")
    Call<GoogleBooksApiResponse> getAllBooks(@Query("q") String value);


    /**
     * Gets all books reactive.
     * remember 40 is the max results that you can pull at any given time
     * @param value      the value
     * @param maxResults the max results
     * @return the all books reactive
     */
    @GET("volumes")
    Observable<GoogleBooksApiResponse> getAllBooksReactive(@Query("q") String value,
                                                           @Query("maxResults") int maxResults,
                                                           @Query("startIndex") int startIndex);
}
