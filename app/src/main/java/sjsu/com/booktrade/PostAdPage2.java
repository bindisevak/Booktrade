package sjsu.com.booktrade;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

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

            }
        });


        return myView;

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
