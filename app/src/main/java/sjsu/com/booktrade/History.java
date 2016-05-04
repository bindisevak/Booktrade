package sjsu.com.booktrade;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.InjectView;

/**
 * Created by Bindi on 4/19/2016.
 */
public class History extends Fragment {

    View myView;
    //@InjectView(R.id.image_small) ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate( R.layout.book_details, container, false );
        //int SDK_INT = android.os.Build.VERSION.SDK_INT;
//        try {
////            //ImageView i = (ImageView) myView.findViewById(R.);
////            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464").getContent());
////            i.setImageBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JSONObject obj = new JSONObject();
//
//
//            try {
//                obj.put( "name", "Cracking the Coding Interview" );
//                obj.put( "author", "Gayle" );
//                obj.put( "ISBN", "012345678901" );
//                obj.put( "year", new Integer( 2015 ) );
//                obj.put( "credits", new Integer( 2 ) );
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        myView = inflater.inflate(R.layout.book_details_nn, container, false);
        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result){
                getResources().getString(R.string.app_name);
            }

        }.execute();
    }

}
