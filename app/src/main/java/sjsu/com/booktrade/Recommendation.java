package sjsu.com.booktrade;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sjsu.com.booktrade.beans.BooksTO;
import sjsu.com.booktrade.beans.UserTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;

/**
 * Created by Bindi on 5/10/2016.
 */


public class Recommendation extends Fragment {

    View myView;

    RecommendationAdapter adapter;
    ListView rbooks;
    Context context;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.recommendation, container, false);
        rbooks = (ListView) myView.findViewById(R.id.recommendBooks);


        Intent intent = getActivity().getIntent();
        UserTO userInfo = (UserTO) intent.getSerializableExtra("UserInfo");
        int userId = userInfo.getUserId();

        RecommendAction recommend = new RecommendAction(this);
        recommend.execute(String.valueOf(userId));


        return myView;
    }

    private class RecommendAction extends AsyncTask<String, Double, String> {
        Recommendation context;
        List<BooksTO> rList = new ArrayList<BooksTO>();


        private RecommendAction(Recommendation context) {
            this.context = context;
        }


        @Override
        protected String doInBackground(String... params) {


            BookTradeHttpConnection conn = new BookTradeHttpConnection();

            rList = conn.getRecommendedBooks(params[0]);


            Log.d("MSG", "I am before for");

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Adding Items to ListView
            //if(result != null){
            //List result = new ArrayList<BooksTO>();
            Log.d("TAG", "size>>>" + rList.size());
            adapter = new RecommendationAdapter(getActivity(), R.layout.display_recommendedbooks, rList);
            // listview=new ListView(LandingPage.this);
            rbooks.setAdapter(adapter);

        }
    }
}
