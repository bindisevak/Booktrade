package sjsu.com.booktrade;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import sjsu.com.booktrade.beans.UserTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;

/**
 * Created by nkotasth on 5/4/16.
 */


public class BuyBook extends AppCompatActivity {

    EditText date;
    EditText time;
    LinearLayout pickupLayout;
    LinearLayout delivery;
    Button btn_checkout_view;
    EditText addr1;
    EditText addr2;
    EditText city;
    EditText state;
    EditText postalCode;

    UserTO userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buybook);

        date = (EditText) findViewById(R.id.input_date);
        time = (EditText) findViewById(R.id.input_time);
        pickupLayout = (LinearLayout) findViewById(R.id.pickup);
        delivery = (LinearLayout) findViewById(R.id.delivery);
        addr1 = (EditText)findViewById(R.id.input_add1) ;
        addr2 = (EditText)findViewById(R.id.input_add2) ;
        city = (EditText)findViewById(R.id.input_city) ;
        state = (EditText)findViewById(R.id.input_state) ;
        postalCode = (EditText)findViewById(R.id.input_zip) ;
        btn_checkout_view = (Button)findViewById(R.id.btn_checkout) ;

        userInfo=(UserTO) getIntent().getSerializableExtra("UserInfo");

        Intent in =getIntent();
        final Boolean pickup = in.getBooleanExtra("pickup",true);
        final String sellerId = in.getStringExtra("sellerIdFromBookDetails");
        final String bookId = in.getStringExtra("bookIdFromBookDetails");
        final String price = in.getStringExtra("priceFromBookDetails");
        final String userId = in.getStringExtra("userId");
        if(pickup == true) {
            pickupLayout.setVisibility(View.VISIBLE);
            delivery.setVisibility(View.GONE);

        }

        else {
            pickupLayout.setVisibility(View.GONE);
            pickupLayout.setVisibility(View.GONE);
        }
        btn_checkout_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               buyBook(pickup, sellerId, bookId, price, userId);
            }
        });

    }

    String message = null;
    String messageForSeller = null;
    private void buyBook(Boolean pickup, String sellerId, String bookId, String price, String userId) {

        String pickupdate = null;
        String pickUptime = null;
        String addressLine1 = null;
        String addressLine2 = null;
        String cityStr = null;
        String stateStr = null;
        String postalCodeStr = null;
        String pickUpOrShipping = "Shipping";
        if(pickup){
            pickUpOrShipping = "pickUp";
            pickupdate = date.getText().toString();
            pickUptime = time.getText().toString();
        }else{
            addressLine1 = addr1.getText().toString();
            addressLine2 = addr2.getText().toString();
            cityStr = city.getText().toString();
            stateStr = state.getText().toString();
            postalCodeStr = postalCode.getText().toString();
        }
        BuyBookAction bookAction = new BuyBookAction(this);
        bookAction.execute(pickUpOrShipping, sellerId, bookId, price, userId, pickupdate, pickUptime, addressLine1, addressLine2, cityStr, stateStr, postalCodeStr);

    }

    private class BuyBookAction extends AsyncTask<String,String,String> {
        String bookId = null;
        Context context;

        private BuyBookAction(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            BookTradeHttpConnection conn = new BookTradeHttpConnection();
            bookId = conn.buyBook(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8],params[9],params[10],params[11]);
            StringBuilder addressBuilder = new StringBuilder();
            addressBuilder.append(params[7]).append(" ").append(params[8]).append(" ").append(params[9]).append(" ").append(params[10]).append(" ").append(params[11]);
            if(params[0].equalsIgnoreCase("pickUp")){
                message = " You need to Pick up the book on "+params[6] +" at "+params[7]+" "+params[3]+" credits have been deducted from your account.For any changes contact seller on "+getIntent().getStringExtra("contactNumber");
                messageForSeller = "Bindi has placed order for the ad posted by you. User will be coming and picking up on "+params[5]+"at "+params[6];
            }else{
                message = "Book will be shipped and delivered within a week at the given address!!";
                messageForSeller = "Book needs to be Shipped at Following address: "+addressBuilder.toString();
            }
            Log.d("BookId ",bookId);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("User","-----------------------------------------"+userInfo.getEmailId());
            if(bookId!=null) {

               String contactNumber = "4089170987";

                SmsManager sms = SmsManager.getDefault();
                // this is the function that does all the magic
                sms.sendTextMessage(contactNumber, null, messageForSeller, null, null);
                Toast.makeText(getApplicationContext(), "Sucessfully placed the order!! "+message, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Error encountered while placing an order", Toast.LENGTH_LONG).show();
            }

        }


    }

}
