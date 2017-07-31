package xyz.appsian.googlebooksparser.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyz.appsian.googlebooksparser.application.App;
import xyz.appsian.googlebooksparser.R;
import xyz.appsian.googlebooksparser.adapters.GoogleBooksApiAdapter;
import xyz.appsian.googlebooksparser.api.GoogleBooksApiResponse;
import xyz.appsian.googlebooksparser.api.Item;
import xyz.appsian.googlebooksparser.di.GoogleBooksAPI;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {


    /**
     * The Google books api.
     */
    @Inject
    GoogleBooksAPI googleBooksAPI;

    private ImageButton searchBtn;
    private Button loadMoreBtn;
    private static EditText searchEditText;
    private static String searchEditTextCached;
    private static int startIndex = 0;
    private List<Item> items = Collections.emptyList();
    static  List<Item> lastSearchedItems;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CircularProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getComponent().inject(this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        searchBtn = findViewById(R.id.searchBtn);
        searchEditText = findViewById(R.id.searchEditText);
        loadMoreBtn = findViewById(R.id.loadMoreBtn);
        progressView=findViewById(R.id.progress_view);
        setupListeners();

    }

    /**
     * Lazy way to handle caching when use returns to the main view after a search
     * Could have used an Interceptor here
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (lastSearchedItems != null) {
            mRecyclerView = findViewById(R.id.recyclerview);
            mAdapter = new GoogleBooksApiAdapter(lastSearchedItems);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }

    }
    private void queryService() {
        progressView.startAnimation();
        progressView.setVisibility(View.VISIBLE);
        Log.d("Jerome", "queryService: " + startIndex);
        Observable<GoogleBooksApiResponse> observable = googleBooksAPI.getAllBooksReactive(searchEditText.getText().toString(), 40,startIndex);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GoogleBooksApiResponse>() {
                    @Override
                    public void onCompleted() {
                        mRecyclerView = findViewById(R.id.recyclerview);
                        mAdapter = new GoogleBooksApiAdapter(items);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                        progressView.stopAnimation();
                        progressView.setVisibility(View.GONE);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, " error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GoogleBooksApiResponse googleBooksApiResponse) {
                        items = googleBooksApiResponse.getItems();
                        lastSearchedItems = googleBooksApiResponse.getItems();
                        searchEditTextCached = searchEditText.getText().toString();

                    }

                });

        startIndex +=40;


    }
    /**
     * Setup all of my listeners
     */
    private void setupListeners() {
        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startIndex<40){

                }else{
                    searchEditText.setText(searchEditTextCached);
                    queryService();
                }

            }
        });



        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                progressView = findViewById(R.id.progress_view);
                progressView.startAnimation();
                queryService();
            }


        });


    }


}
