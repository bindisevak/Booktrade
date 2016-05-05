package sjsu.com.booktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by nkotasth on 5/4/16.
 */


public class BuyBook extends AppCompatActivity {

    EditText date;
    EditText time;
    LinearLayout pickupLayout;
    LinearLayout delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buybook);

        date = (EditText) findViewById(R.id.input_date);
        time = (EditText) findViewById(R.id.input_time);
        pickupLayout = (LinearLayout) findViewById(R.id.pickup);
        delivery = (LinearLayout) findViewById(R.id.delivery);

        Intent in =getIntent();
        Boolean pickup = in.getBooleanExtra("pickup",true);
        
        if(pickup == true) {
            pickupLayout.setVisibility(View.VISIBLE);
            delivery.setVisibility(View.GONE);

        }

        else {
            pickupLayout.setVisibility(View.GONE);
            pickupLayout.setVisibility(View.GONE);
        }


    }

}
