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

import sjsu.com.booktrade.beans.BooksTO;
import sjsu.com.booktrade.beans.UserTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;

/**
 * Created by Bindi on 5/9/2016.
 */
public class SoldHistory extends Fragment {

    View myView;
    ArrayList<BooksTO> bbooksList;
    ArrayList<BooksTO> sbooksList;
    SoldHistoryAdapter adapter;
    ListView sbooks;
    Button sButton;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sold_history, container, false);

        //bbooks = (ListView) myView.findViewById(R.id.boughtBooks);
        sbooks = (ListView) myView.findViewById(R.id.sellBooks);
        bbooksList = new ArrayList<>();
        sbooksList = new ArrayList<>();

        Intent intent = getActivity().getIntent();
        UserTO userInfo = (UserTO) intent.getSerializableExtra("UserInfo");
        int userId = userInfo.getUserId();

        HistoryAction hAction = new HistoryAction(this);
        hAction.execute(String.valueOf(userId));

        sButton = (Button) myView.findViewById(R.id.cancel_sButton);
        sButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           cancelPressed(v);

                                       }
                                   }
        );


        return myView;
    }

    public void cancelPressed(View pressed) {
        History fragment = new History();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private class HistoryAction extends AsyncTask<String, Double, String> {
        SoldHistory context;
        List<BooksTO> sList = new ArrayList<BooksTO>();
        List<BooksTO> bList = new ArrayList<BooksTO>();


        private HistoryAction(SoldHistory context) {
            this.context = context;
        }


        @Override
        protected String doInBackground(String... params) {


            BookTradeHttpConnection conn = new BookTradeHttpConnection();

            sList = conn.getSoldTransaction(params[0]);
            // bList =  conn.getBoughtTransaction();


            Log.d("MSG", "I am before for");

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Adding Items to ListView
            //if(result != null){
            //List result = new ArrayList<BooksTO>();
            Log.d("TAG", "size>>>" + sList.size());
            adapter = new SoldHistoryAdapter(getActivity(), R.layout.display_soldhistory, sList);
            // listview=new ListView(LandingPage.this);
            sbooks.setAdapter(adapter);

        }
    }


}
