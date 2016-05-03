package sjsu.com.booktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nkotasth on 5/2/16.
 */
public class PostAd extends Fragment {
    View myView;
    @InjectView(R.id.isbn) EditText isbn;
    @InjectView(R.id.btn_isbnLookup) Button btnIsbnLookup;
    @InjectView(R.id.or) TextView or;
    @InjectView(R.id.btn_scan) Button btnScan;
    @InjectView(R.id.note) TextView note;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = inflater.inflate(R.layout.postad, container, false);
        ButterKnife.inject(this,myView);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Post Ad");

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBarcodeScanner();
            }
        });
        btnIsbnLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startnew activity
            }
        });
        return myView;

    }

    private void startBarcodeScanner(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

}
