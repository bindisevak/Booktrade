package sjsu.com.booktrade;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sjsu.com.booktrade.beans.BooksTO;
import sjsu.com.booktrade.beans.UserTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;

public class LandingPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Location presentLocation;


    //GPS RELATED VARS
    private static final int INITIAL_REQUEST=1337;
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    ArrayList<BooksTO> bookList;
    BooksAdapter adapter;
    Activity activity;
    ListView listview;
    TextView tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        listview = (ListView)findViewById(R.id.display_booksList);
        bookList = new ArrayList<>();
        tView=(TextView)findViewById(R.id.mallik) ;


        Intent intent = getIntent();

        UserTO userInfo=(UserTO) intent.getSerializableExtra("UserInfo");
        Log.d("Email",userInfo.getEmailId());
        //String lName = intent.getStringExtra("lastName");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        //View myView = findViewById(R.id.nav_view);
        TextView email=(TextView)header.findViewById(R.id.display_email);
        email.setText(userInfo.getEmailId());

        if (!canAccessLocation()) {


            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }
        else{
            GPSTracker gps = new GPSTracker(LandingPage.this);
            presentLocation = gps.getLocation();
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                Log.d("Lat",latitude+"");
                Log.d("Lang",longitude+"");
                Toast.makeText(getApplicationContext(), "Your location is " + latitude + "Longitude is " + longitude, Toast.LENGTH_LONG).show();
            } else {
                gps.showSettingsAlert();
            }

        }

        BooksAction bAction = new BooksAction(this);
        bAction.execute();
    }

    BooksTO bInfo;


    private class BooksAction extends AsyncTask<String, Double, String> {
        Context context;
        List<BooksTO> bList = new ArrayList<BooksTO>();

        private BooksAction(Context context) {
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
            adapter = new BooksAdapter(LandingPage.this, R.layout.display_books, bList);
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

                    Intent in = new Intent(getApplicationContext(), BookDetails.class );
                    in.putExtra("id", bookId);
                    // in.putExtra("image", (Serializable) image);
                    in.putExtra("name", name);
                    in.putExtra("author",  author);
                    in.putExtra("price", price);
                    in.putExtra("pickShip", pickShip);
                    in.putExtra("edition", edition);
                    in.putExtra("category", category);


                    startActivity(in);
                    //Toast.makeText(getApplicationContext(), textView.getText(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_postAdd) {
            PostAd fragment = new PostAd();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            return true;
        }

        else if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Home fragment = new Home();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();


            // Handle the camera action
        } else if (id == R.id.nav_payment) {
            Payment fragment = new Payment();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();


        } else if (id == R.id.nav_history) {
            History fragment = new History();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();


        } else if (id == R.id.nav_about) {
            About fragment = new About();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();


        } else if (id == R.id.nav_setting) {
            Home fragment = new Home();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();


        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }
}
