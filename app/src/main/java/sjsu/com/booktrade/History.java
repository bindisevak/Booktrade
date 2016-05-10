package sjsu.com.booktrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import sjsu.com.booktrade.beans.BooksTO;
import sjsu.com.booktrade.beans.UserTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;

/**
 * Created by Bindi on 4/19/2016.
 */
public class History extends Fragment {

    View myView;
    Button btnSold;
    Button btnBought;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate( R.layout.history, container, false );

        btnSold = (Button) myView.findViewById(R.id.sold_button);
        btnBought = (Button) myView.findViewById(R.id.bought_button);
        btnSold.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onSoldButton(v);


            }


        });

        btnBought.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBoughtButton(v);


            }


        });

        return myView;
    }


    public void onSoldButton(View pressed) {
        SoldHistory fragment = new SoldHistory();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onBoughtButton(View v) {

        BoughtHistory fragment = new BoughtHistory();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
