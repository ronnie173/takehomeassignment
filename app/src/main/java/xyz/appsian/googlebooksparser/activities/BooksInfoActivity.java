package xyz.appsian.googlebooksparser.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import xyz.appsian.googlebooksparser.R;


/**
 * The type Books info activity.
 */
public class BooksInfoActivity extends AppCompatActivity {
    /**
     * The Title.
     */
    String title;
    /**
     * The Sub title.
     */
    String subTitle;
    /**
     * The Image link.
     */
    String imageLink;
    /**
     * The Info link.
     */
    String infoLink;
    /**
     * The Description.
     */
    String description;
    /**
     * The Publisher.
     */
    String publisher;
    /**
     * The Language.
     */
    String language;
    /**
     * The Category.
     */
    String category;
    /**
     * The More info.
     */
    String moreInfo = "Full Book Info";
    /**
     * The Main image view.
     */
    ImageView mainImageView;
    /**
     * The Title tv.
     */
    TextView titleTv;
    /**
     * The Author tv.
     */
    TextView authorTv;
    /**
     * The Description tv.
     */
    TextView descriptionTv;
    /**
     * The Language tv.
     */
    TextView languageTv;
    /**
     * The Publisher tv.
     */
    TextView publisherTv;
    /**
     * The Category tv.
     */
    TextView categoryTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_info);
        description = getIntent().getExtras().getString("description");
        title = getIntent().getExtras().getString("title");
        subTitle = getIntent().getExtras().getString("subTitle");
        imageLink = getIntent().getExtras().getString("imageLink");
        infoLink = getIntent().getExtras().getString("infoLink");
        publisher = getIntent().getExtras().getString("publisher");
        language = getIntent().getExtras().getString("language");
        category = getIntent().getExtras().getString("category");
        setupViews();
        Log.d("", "onCreate: ");
    }

    /**
     * Sets views.
     */
    protected void setupViews() {
        mainImageView = findViewById(R.id.mainImageView);
        titleTv = findViewById(R.id.titleTv);
        authorTv = findViewById(R.id.authorTv);
        descriptionTv = findViewById(R.id.descriptionTv);
        languageTv = findViewById(R.id.languageTv);
        publisherTv = findViewById(R.id.publisherTv);
        categoryTv = findViewById(R.id.categoryTv);
        Picasso.with(this).load(imageLink).into(mainImageView);
        descriptionTv.setText(description);
        descriptionTv.setMovementMethod(new ScrollingMovementMethod());
        titleTv.setText(title);
        authorTv.setText("Publisher: " + publisher);
        languageTv.setText(language);
        publisherTv.setText(Html.fromHtml("<a href=\"" + infoLink + "\">" + moreInfo + "</a>"));
        publisherTv.setMovementMethod(LinkMovementMethod.getInstance());
        categoryTv.setText(category);

    }


}
