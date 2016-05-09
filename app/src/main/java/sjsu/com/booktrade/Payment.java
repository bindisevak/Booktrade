package sjsu.com.booktrade;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import butterknife.InjectView;
import fr.ganfra.materialspinner.MaterialSpinner;
import sjsu.com.booktrade.beans.UserTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;


public class Payment extends Fragment {
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX; //Enter the correct environment here;
    private static final String CONFIG_CLIENT_ID = "ASPbvujkDumd8FuqXA_nD905I_lwJ85B4aP7xqDEYQvYYfkq5fgzRIt9dcIsjVJEIqTihNkrHllNQGdH";//you need to register with PayPal and enter your client_ID here;

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    double final_amount;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .acceptCreditCards(true)
            .languageOrLocale("EN")
            .rememberUser(true)
            .merchantName("BookTrade");
    EditText Name,Age;
    Spinner amount;
    Button Donate;
    TextView credits_current;
    double finalCredits;
    String currectCredits;
    String user_id;

    UserTO u;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Left", "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.payment, container, false);
        final Context contextWrapper = new ContextThemeWrapper( getActivity(),R.style.AppTheme_Light );
        //amount = (Spinner) rootView.findViewById(R.id.spinner1);
        String[] ITEMS = {"10", "20", "30", "40", "50", "60"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MaterialSpinner spinner = (MaterialSpinner) rootView.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        amount = (MaterialSpinner) rootView.findViewById( R.id.spinner );
        Donate = (Button) rootView.findViewById(R.id.BuyCredits);
        user_id = getArguments().getString( "userid" );
        credits_current = (TextView) rootView.findViewById( R.id.current_credits );
        //Log.d( "Current Credits", currectCredits.toString() );
        currectCredits = getArguments().getString( "credits" );
        credits_current.setText( currectCredits );
        Donate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBuyPressed(v);
            }

        });

        initPaymentService();
        LayoutInflater localInflater = inflater.cloneInContext(contextWrapper);
        //LayoutInflater localInflater = inflater.cloneInContext(contextWrapper);
        localInflater.inflate(R.layout.payment, container, false);
        return rootView;
    }

    public void initPaymentService() {
        try {
            Intent intent = new Intent(getActivity(), PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

            getActivity().startService(intent);
        } catch (Exception e) {
            Log.i("PayPal Exception", e.getMessage());
        }
    }

    public double onChoiceMade() {
        int pos = amount.getSelectedItemPosition();
        double payment;

        if (pos == 0) {
            payment = 10.00;
        }else if (pos == 1) {
            payment = 20.00;
        }else if (pos == 2) {
            payment = 30.00;
        }else if (pos == 3 ){
            payment = 40.00;
        }else if (pos == 4 ){
        payment = 50.00;
        }else {
            payment = 60.00;
        }
        finalCredits = payment;
        return payment;
    }

    public void onBuyPressed(View pressed) {

//        try{
////            int age = Integer.parseInt(Age.getText().toString());
////            if (age >= 18 && age < 99) {
                PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(onChoiceMade()), "USD", "Buy Book", PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
//            }
//            else  {
//                Toast.makeText(getActivity(), "You must be 18 years of age to Process a payment. Please enter a correct age.", Toast.LENGTH_LONG).show();
//                Age.setText("");
//            }
//        }catch (NumberFormatException e){
//            Toast.makeText(getActivity(), "Age value cannot be empty. \n Please enter a valid age.", Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i("paymentExample", confirm.toJSONObject().toString(4));
                        Log.d( "CREDITS BOUGHT", ""+finalCredits );
                        credits_current.setText(""+ (Double.parseDouble(credits_current.getText().toString()) + finalCredits));
                        PaymentAction rAction = new PaymentAction();
                        rAction.execute(user_id, ""+finalCredits);
                        Toast.makeText(getActivity().getApplicationContext(), "PaymentConfirmation info received from PayPal",
                                Toast.LENGTH_LONG).show();



                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getActivity().getApplicationContext(), "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }



    @Override
    public void onDestroy() {
        // Stop service when done
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }

    private class PaymentAction extends AsyncTask<String, String,String> {
        Context context;

        @Override
        protected String doInBackground(String... params) {
            BookTradeHttpConnection conn = new BookTradeHttpConnection();
            Log.d( "", "MAIN HOON DON" );
            boolean b = conn.addCredits(params[0],params[1]);
            return null;
        }

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progress.setMessage("Loading...");
//            progress.show();
//        }



    }



}