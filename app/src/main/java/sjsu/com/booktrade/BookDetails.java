package sjsu.com.booktrade;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import sjsu.com.booktrade.beans.UserTO;

public class BookDetails extends AppCompatActivity {

Button btn_buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_buy = (Button) findViewById(R.id.btn_buy);

//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);



                TextView idDetails = (TextView) findViewById(R.id.book_idDetails);
                ImageView imageDetails = (ImageView) findViewById(R.id.book_imageDetails);
                TextView nameDetails = (TextView) findViewById(R.id.book_nameDetails);
                TextView authorDetails = (TextView) findViewById(R.id.book_authorDetails);
                TextView priceDetails = (TextView) findViewById(R.id.book_priceDetails);
                TextView editionDetails = (TextView) findViewById(R.id.book_editionDetails);
                TextView categoryDetails = (TextView) findViewById(R.id.book_categoryDetails);
                TextView pickShipDetails = (TextView) findViewById(R.id.book_pickUpShipDetails);
                TextView bookUserId = (TextView) findViewById(R.id.book_userIdMain);
                TextView contactNumber = (TextView)findViewById(R.id.book_userContactMain);

                Intent in = getIntent();
                String name = in.getStringExtra("name");
                final String bookId = in.getStringExtra("id");
                String author = in.getStringExtra("author");
                final String price = in.getStringExtra("price");
                String edition = in.getStringExtra("edition");
                String category = in.getStringExtra("category");
                final String pickShip = in.getStringExtra("pickShip");
                final String sellerId = in.getStringExtra("sellerId");
                final String userId = in.getStringExtra("userId");
                final String contactNumberStr = in.getStringExtra("contactNumber");


                Bundle extras = getIntent().getExtras();
                Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");

                imageDetails.setImageBitmap(bmp );
                idDetails.setText(bookId);
                nameDetails.setText(name);
                authorDetails.setText(author);
                priceDetails.setText(price);
                editionDetails.setText(edition);
                categoryDetails.setText(category);
                pickShipDetails.setText(pickShip);
        Log.d("Seller Id::::::: ",sellerId);
                bookUserId.setText(sellerId);
                contactNumber.setText(contactNumberStr);

        btn_buy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent buybook = new Intent(getApplicationContext(), BuyBook.class);
                if (pickShip.equals("pickup"))
                    buybook.putExtra("pickup",true);
                else buybook.putExtra("pickup", false);
                UserTO userInfo=(UserTO) getIntent().getSerializableExtra("UserInfo");
                buybook.putExtra("bookIdFromBookDetails",bookId);
                buybook.putExtra("priceFromBookDetails", price);
                buybook.putExtra("sellerIdFromBookDetails", sellerId);
                buybook.putExtra("userId", userId);
                buybook.putExtra("contactNumber", contactNumberStr);
                Log.d("USERINFO--------",userInfo.getEmailId());
                buybook.putExtra("UserInfo", userInfo);
                Log.d("UserId:: ",">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
                startActivity(buybook);

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), Home.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



