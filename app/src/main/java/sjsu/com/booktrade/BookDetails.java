package sjsu.com.booktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Bindi on 5/2/2016.
 */
public class BookDetails extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.book_details);

        TextView idDetails = (TextView) findViewById(R.id.book_idDetails);
        ImageView imageDetails = (ImageView) findViewById(R.id.book_imageDetails);
        TextView nameDetails = (TextView) findViewById(R.id.book_nameDetails);
        TextView authorDetails = (TextView) findViewById(R.id.book_authorDetails);
        TextView priceDetails = (TextView) findViewById(R.id.book_priceDetails);
        TextView editionDetails = (TextView) findViewById(R.id.book_editionDetails);
        TextView categoryDetails = (TextView) findViewById(R.id.book_categoryDetails);
        TextView pickShipDetails = (TextView) findViewById(R.id.book_pickUpShipDetails);

        Intent in = getIntent();
        String name = in.getStringExtra("name");
        String bookId = in.getStringExtra("id");
        String author = in.getStringExtra("author");
        String price = in.getStringExtra("price");
        String edition = in.getStringExtra("edition");
        String category = in.getStringExtra("category");
        String pickShip = in.getStringExtra("pickShip");

        idDetails.setText(bookId);
        nameDetails.setText(name);
        authorDetails.setText(author);
        priceDetails.setText(price);
        editionDetails.setText(edition);
        categoryDetails.setText(category);
        pickShipDetails.setText(pickShip);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
