package sjsu.com.booktrade;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sjsu.com.booktrade.beans.BooksTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;

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
                getBookDetailsFromISBNNProceed();
            }
        });
        return myView;

    }

    private void startBarcodeScanner(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(scanningResult != null){
            gotoPostAdPage(scanningResult.getContents());
        }
    }

    private void getBookDetailsFromISBNNProceed(){
        String isbnText = isbn.getText().toString();
        gotoPostAdPage(isbnText);
    }

    private void gotoPostAdPage(String isbn) {
        BooksAction action = new BooksAction(this);
        action.execute(isbn);
    }

    BooksTO books;
    private class BooksAction extends AsyncTask<String,String,String> {
        PostAd context;

        private BooksAction(PostAd context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            BookTradeHttpConnection conn = new BookTradeHttpConnection();
            Log.d("Fetch books via ISBN ","ISBN:: " + params[0]);
            books = conn.getBookDetailsFromISBN(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(books!=null) {
                PostAdPage2 fragment2 = new PostAdPage2();
                Bundle bundle = new Bundle();
                bundle.putString("name", books.getBookName());
                bundle.putString("author", books.getAuthor());
                bundle.putString("imageURLSmall", books.getImageURLSmall());
                bundle.putString("imageURLLarge", books.getImageURLLarge());
                bundle.putString("category", books.getCategory());
                bundle.putInt("userId", getArguments().getInt("userId"));
                Log.d("UserId Insertion", "-++++++++++++++++++++++++++++"+getArguments().getInt("userId"));
                fragment2.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame,fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }else{
                Toast.makeText(getActivity().getApplicationContext(), "Invalid ISBN!! No Book Details found!!", Toast.LENGTH_LONG).show();
            }

        }


    }
}
