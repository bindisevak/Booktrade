package sjsu.com.booktrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
    ArrayList<BooksTO> bbooksList;
    ArrayList<BooksTO> sbooksList;
    BooksAdapter adapter;
    Activity activity;
    ListView bbooks;
    ListView sbooks;
    TextView tv1;
    TextView tv2;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate( R.layout.history, container, false );
        bbooks = (ListView) myView.findViewById(R.id.boughtBooks);
        sbooks = (ListView) myView.findViewById(R.id.sellBooks);
        bbooksList = new ArrayList<>();
        sbooksList = new ArrayList<>();

//        BooksAction bAction = new BooksAction(this);
//        bAction.execute();
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>();
//
//        bbooks.setAdapter(arrayAdapter);
//
//        Intent intent = getActivity().getIntent();
//        UserTO userInfo=(UserTO) intent.getSerializableExtra("UserInfo");
//        Log.d("Email",userInfo.getEmailId());
//
        return myView;
    }
//
////    @Override
////    public void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////
////        new AsyncTask<Void, Void, Void>() {
////
////            @Override
////            protected Void doInBackground(Void... params) {
////                try {
////                    Thread.sleep(2000);
////                } catch (InterruptedException ex) {
////                }
////                return null;
////            }
////
////            @Override
////            protected void onPostExecute(Void result){
////                getResources().getString(R.string.app_name);
////            }
////
////        }.execute();
////    }
//
//
//    BooksTO bInfo;
//
//    public void bindView(){
//        BooksAction bAction = new BooksAction(this);
//        bAction.execute();
//
//    }
//
//
//
//
//    private class BooksAction extends AsyncTask<String, Double, String> {
//        Home context;
//        List<BooksTO> bList = new ArrayList<BooksTO>();
//
//        private BooksAction(Home context) {
//            this.context = context;
//        }
//
//
//        @Override
//        protected String  doInBackground(String... params) {
//
//
//            BookTradeHttpConnection conn = new BookTradeHttpConnection();
//
//            bList = conn.getBooksTO();
//
//
//            Log.d("MSG", "I am before for");
//
//            return "";
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            // Adding Items to ListView
//            //if(result != null){
//            //List result = new ArrayList<BooksTO>();
//            Log.d("TAG","size>>>"+bList.size());
//            adapter = new BooksAdapter(getActivity(), R.layout.display_books, bList);
//            // listview=new ListView(LandingPage.this);
//            listview.setAdapter(adapter);
//            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    //BooksTO books = (BooksTO)parent.getItemAtPosition(position);
//                    String bookId = ((((TextView)view.findViewById(R.id.book_id))).getText().toString());
//                    ImageView image = (ImageView)view.findViewById(R.id.book_image);
//                    String name = (((TextView)view.findViewById(R.id.book_name))).getText().toString();
//                    String author = (((TextView)view.findViewById(R.id.book_author))).getText().toString();
//                    String price = ((((TextView)view.findViewById(R.id.book_price))).getText().toString());
//                    String pickShip = ((((TextView)view.findViewById(R.id.book_pickUpShip))).getText().toString());
//                    String edition = ((((TextView)view.findViewById(R.id.book_edition))).getText().toString());
//                    String category = ((((TextView)view.findViewById(R.id.book_category))).getText().toString());
//
//                    Intent in = new Intent(getContext(), BookDetails.class );
//                    in.putExtra("id", bookId);
//                    //in.putExtra("image", (Serializable) image);
//                    in.putExtra("name", name);
//                    in.putExtra("author",  author);
//                    in.putExtra("price", price);
//                    in.putExtra("pickShip", pickShip);
//                    in.putExtra("edition", edition);
//                    in.putExtra("category", category);
//
//
//                    startActivity(in);
//                    //Toast.makeText(getApplicationContext(), textView.getText(), Toast.LENGTH_LONG).show();
//
//                }
//            });
//        }
//    }

}
