package sjsu.com.booktrade;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sjsu.com.booktrade.beans.BooksTO;
import sjsu.com.booktrade.beans.UserTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;


/**
 * Created by nkotasth on 4/23/16.
 */
public class Home extends Fragment {


    View myView;
    ArrayList<BooksTO> bookList;
    BooksAdapter adapter;
    Activity activity;
    ListView listview;
    TextView tView;
    Context context;

    private Location presentLocation;

    private static final int INITIAL_REQUEST=1337;
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = inflater.inflate(R.layout.list_view_books, container, false);

        listview = (ListView)myView.findViewById(R.id.display_booksList);
        bookList = new ArrayList<>();



        Intent intent = getActivity().getIntent();
        UserTO userInfo=(UserTO) intent.getSerializableExtra("UserInfo");
        Log.d("Email",userInfo.getEmailId());
        int userId = userInfo.getUserId();
//        TextView user=(TextView) myView.findViewById(R.id.dummy);
//        Log.d("Email", String.valueOf(userId));
//        email.setText(userInfo.getEmailId());
        //String lName = intent.getStringExtra("lastName");

//        Toolbar myToolbar = (Toolbar) getView().findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);
//        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
//        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
//        ab.setDisplayHomeAsUpEnabled(false);
//        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
//        ab.setDisplayShowTitleEnabled(false); // disable the default



//        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        DrawerLayout drawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) getView().findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        View header=navigationView.getHeaderView(0);
//        //View myView = findViewById(R.id.nav_view);
//        TextView email=(TextView)header.findViewById(R.id.display_email);
//        email.setText(userInfo.getEmailId());

        if (!canAccessLocation()) {
          requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
            BooksAction bAction = new BooksAction(this);
            bAction.execute();
        }
        else{
            GPSTracker gps = new GPSTracker(getContext());
            presentLocation = gps.getLocation();
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                Log.d("Lat",latitude+"");
                Log.d("Lang",longitude+"");
                Toast.makeText(getContext(), "Your location is " + latitude + "Longitude is " + longitude, Toast.LENGTH_LONG).show();
                BooksActionGPS bActionGPS = new BooksActionGPS(this);
                bActionGPS.execute(String.valueOf(userId), String.valueOf(latitude), String.valueOf(longitude));
            } else {
                //gps.showSettingsAlert();
                BooksAction bAction = new BooksAction(this);
                bAction.execute();
            }

        }

        EditText filterEditText = (EditText) myView.findViewById(R.id.filterText);

        // Add Text Change Listener to EditText
        filterEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return myView;
    }


    BooksTO bInfo;

    public void bindView(){
        BooksAction bAction = new BooksAction(this);
        bAction.execute();

    }




    private class BooksAction extends AsyncTask<String, Double, String> {
        Home context;
        List<BooksTO> bList = new ArrayList<BooksTO>();

        private BooksAction(Home context) {
            this.context = context;
        }


        @Override
        protected String  doInBackground(String... params) {


            BookTradeHttpConnection conn = new BookTradeHttpConnection();

            bList = conn.getBooksTO();


            Log.d("MSG", "I am before for");

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Adding Items to ListView
            //if(result != null){
            //List result = new ArrayList<BooksTO>();
            Log.d("TAG","size>>>"+bList.size());
            adapter = new BooksAdapter(getActivity(), R.layout.display_books, bList);

            // listview=new ListView(LandingPage.this);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //BooksTO books = (BooksTO)parent.getItemAtPosition(position);
                    String bookId = ((((TextView)view.findViewById(R.id.book_id))).getText().toString());
                    ImageView image = (ImageView)view.findViewById(R.id.book_image);
                    String name = (((TextView)view.findViewById(R.id.book_name))).getText().toString();
                    String author = (((TextView)view.findViewById(R.id.book_author))).getText().toString();
                    String price = ((((TextView)view.findViewById(R.id.book_price))).getText().toString());
                    String pickShip = ((((TextView)view.findViewById(R.id.book_pickUpShip))).getText().toString());
                    String edition = ((((TextView)view.findViewById(R.id.book_edition))).getText().toString());
                    String category = ((((TextView)view.findViewById(R.id.book_category))).getText().toString());
                    String sellerId = ((TextView)view.findViewById(R.id.book_userId)).getText().toString();
                    String contactNumber = ((TextView)view.findViewById(R.id.book_userContact)).getText().toString();

                    Intent in = new Intent(getContext(), BookDetails.class );
                    in.putExtra("id", bookId);
                    // in.putExtra("image", (Serializable) image);
                    in.putExtra("name", name);
                    in.putExtra("author",  author);
                    in.putExtra("price", price);
                    in.putExtra("pickShip", pickShip);
                    in.putExtra("edition", edition);
                    in.putExtra("category", category);
                    in.putExtra("sellerId", sellerId);
                    in.putExtra("contactNumber", contactNumber);

                    Intent intent = getActivity().getIntent();
                    UserTO userInfo=(UserTO) intent.getSerializableExtra("UserInfo");
                    int userId = userInfo.getUserId();
                    in.putExtra("userId", userId);
                    in.putExtra("UserInfo", userInfo);
                    image.buildDrawingCache();
                    Bitmap image1= image.getDrawingCache();

                    Bundle extras = new Bundle();
                    extras.putParcelable("imagebitmap", image1);
                    in.putExtras(extras);

                    startActivity(in);
                    //Toast.makeText(getApplicationContext(), textView.getText(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private class BooksActionGPS extends AsyncTask<String, Double, String> {
        Home context;
        List<BooksTO> bListGPS = new ArrayList<BooksTO>();

        private BooksActionGPS(Home context) {
            this.context = context;
        }


        @Override
        protected String doInBackground(String... params) {


            BookTradeHttpConnection conn = new BookTradeHttpConnection();

            bListGPS = conn.getBooksWithinFiftyMiles(params[0], params[1], params[2]);


            Log.d("MSG", "I am before for");

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Adding Items to ListView
            //if(result != null){
            //List result = new ArrayList<BooksTO>();
            Log.d("TAG", "size>>>" + bListGPS.size());
            adapter = new BooksAdapter(getActivity(), R.layout.display_books, bListGPS);
            // listview=new ListView(LandingPage.this);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //BooksTO books = (BooksTO)parent.getItemAtPosition(position);
                    String bookId = ((((TextView) view.findViewById(R.id.book_id))).getText().toString());
                    ImageView image = (ImageView) view.findViewById(R.id.book_image);
                    String name = (((TextView) view.findViewById(R.id.book_name))).getText().toString();
                    String author = (((TextView) view.findViewById(R.id.book_author))).getText().toString();
                    String price = ((((TextView) view.findViewById(R.id.book_price))).getText().toString());
                    String pickShip = ((((TextView) view.findViewById(R.id.book_pickUpShip))).getText().toString());
                    String edition = ((((TextView) view.findViewById(R.id.book_edition))).getText().toString());
                    String category = ((((TextView) view.findViewById(R.id.book_category))).getText().toString());
                    String sellerId = ((TextView)view.findViewById(R.id.book_userId)).getText().toString();
                    String contactNumber = ((TextView)view.findViewById(R.id.book_userContact)).getText().toString();

                    Intent in = new Intent(getContext(), BookDetails.class);
                    in.putExtra("id", bookId);
                    // in.putExtra("image", (Serializable) image);
                    in.putExtra("name", name);
                    in.putExtra("author", author);
                    in.putExtra("price", price);
                    in.putExtra("pickShip", pickShip);
                    in.putExtra("edition", edition);
                    in.putExtra("category", category);
                    in.putExtra("sellerId", sellerId);
                    in.putExtra("contactNumber", contactNumber);
                    Intent intent = getActivity().getIntent();
                    UserTO userInfo=(UserTO) intent.getSerializableExtra("UserInfo");
                    String userId = String.valueOf(userInfo.getUserId());
                    in.putExtra("userId", userId);

                    startActivity(in);

                    //Toast.makeText(getApplicationContext(), textView.getText(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED== ContextCompat.checkSelfPermission(getContext(),perm));
    }


}
