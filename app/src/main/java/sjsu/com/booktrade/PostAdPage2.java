package sjsu.com.booktrade;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sjsu.com.booktrade.util.BookTradeHttpConnection;

/**
 * Created by nkotasth on 5/3/16.
 */
public class PostAdPage2 extends Fragment {

    public View myView;
    public RadioButton rb;
    public RadioGroup radioGroup;
    public LinearLayout hidden;
    public Spinner spinner1,spinner2;
    private TimePicker timePicker1;
    private TextView time;
    private Calendar calendar;
    private String format = "";



    //@InjectView(R.id.bookImage) Image bookImage;
    @InjectView(R.id.bookDetails) TableLayout bookDetails;
    @InjectView(R.id.input_edition) EditText edition;
    @InjectView(R.id.input_credits) EditText credits;
    @InjectView(R.id.btn_postAd) Button btnPostAd;
    @InjectView(R.id.input_notes) EditText notes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myView = inflater.inflate(R.layout.postad2, container, false);

        ImageView imageDetails = (ImageView) myView.findViewById(R.id.bookImage);
        TextView nameDetails = (TextView) myView.findViewById(R.id.book_title);
        TextView authorDetails = (TextView) myView.findViewById(R.id.book_author1);
        TextView categoryDetails = (TextView) myView.findViewById(R.id.book_category);

        String name = getArguments().getString("name");
        String author = getArguments().getString("author");
        String category = getArguments().getString("category");
        String imageURLLarge = getArguments().getString("imageURLLarge");

        try {
            Bitmap bitmap;
            Log.d("POST AD PAGE 2",""+imageURLLarge);
            if(imageURLLarge != null) {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageURLLarge).getContent());
            } else {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464").getContent());
            }
            imageDetails.setImageBitmap( bitmap);;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nameDetails.setText(name);
        authorDetails.setText(author);
        categoryDetails.setText(category);

        ButterKnife.inject(this,myView);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Post Ad");
        RadioButton pickup = (RadioButton) myView.findViewById(R.id.pickup);
        RadioButton delivery = (RadioButton) myView.findViewById(R.id.delivery);
        hidden = (LinearLayout) myView.findViewById(R.id.hidden);
        delivery.setChecked(true);
        hidden.setVisibility(myView.GONE);

        radioGroup = (RadioGroup) myView.findViewById(R.id.rg);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.pickup:
                        hidden.setVisibility(myView.VISIBLE);
                        break;
                    case R.id.delivery:
                        hidden.setVisibility(myView.GONE);
                        break;
                }

            }
        });

        addListenerOnSpinnerItemSelection();

        btnPostAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAd();
            }
        });
        return myView;
    }

    private void postAd() {
        String name = getArguments().getString("name");
        String author = getArguments().getString("author");
        String category = getArguments().getString("category");
        String imageURLLarge = getArguments().getString("imageURLLarge");
        String imageURLSmall = getArguments().getString("imageURLSmall");
        String pickUporShipOption = ((RadioButton)getActivity().findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
        String editionVal = edition.getText().toString();
        String creditsVal = credits.getText().toString();
        String userId = String.valueOf(getArguments().getInt("userId"));
        String notesStr = notes.getText().toString();
        String address1 = null;
        String address2 = null;
        String city = null;
        String postalCode = null;
        String state = null;
        String dayFrom = null;
        String dayTo = null;
        String timeFrom = null;
        String timeTo = null;
        if(pickUporShipOption.equalsIgnoreCase("pickup")){
            address1 = ((TextView)getView().findViewById(R.id.input_add1)).getText().toString();
            address2 = ((TextView)getView().findViewById(R.id.input_add2)).getText().toString();
            city = ((TextView)getView().findViewById(R.id.input_city)).getText().toString();
            state = ((TextView)getView().findViewById(R.id.input_state)).getText().toString();
            postalCode = ((TextView)getView().findViewById(R.id.input_zip)).getText().toString();
            Spinner spinner = (Spinner)getView().findViewById(R.id.fromDaySpinner);
            dayFrom = spinner.getSelectedItem().toString();
            spinner = (Spinner)getView().findViewById(R.id.toDaySpinner);
            dayTo = spinner.getSelectedItem().toString();
            spinner = (Spinner)getView().findViewById(R.id.fromTimeSpinner);
            timeFrom = spinner.getSelectedItem().toString();
            spinner = (Spinner)getView().findViewById(R.id.toTimeSpinner);
            timeTo = spinner.getSelectedItem().toString();

        }
        PostAdAction postAction = new PostAdAction(this);
        postAction.execute(name, author, category, imageURLLarge, imageURLSmall, pickUporShipOption, editionVal, creditsVal, userId, notesStr, address1,
                address2, city, postalCode, state, dayFrom, dayTo, timeFrom, timeTo);


    }

    boolean adPosted;
    private class PostAdAction extends AsyncTask<String,String,String> {
        PostAdPage2 context;

        private PostAdAction(PostAdPage2 context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            BookTradeHttpConnection conn = new BookTradeHttpConnection();
            adPosted = conn.postAd(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8],params[9],params[10],params[11],
                    params[12],params[13],params[14],params[15],params[16],params[17],params[18]);
            Log.d("PostAdClick","After Post Ad completed successfully");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(adPosted) {
                Log.d("Msg::","Ad Posted successfully....");
                Home fragment = new Home();
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                Toast.makeText(getActivity().getApplicationContext(), "Ad Posted Successfully!!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(), "Error in Saving Book Details!! Please try Again", Toast.LENGTH_LONG).show();
            }

        }


    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) myView.findViewById(R.id.fromDaySpinner);
        spinner2 = (Spinner) myView.findViewById(R.id.toDaySpinner);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }


}


class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
//        Toast.makeText(parent.getContext(),
//                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
