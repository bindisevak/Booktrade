package sjsu.com.booktrade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sjsu.com.booktrade.apis.Login;
import sjsu.com.booktrade.beans.UserTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;

/**
 * Created by hetal on 4/10/2016.
 */
public class RegistrationActivity extends AppCompatActivity {

//    EditText etFirstName,etLastName,etEmail,etPass,etContactNo;
//    Button btnRegister;

    @InjectView(R.id.fName) EditText etFirstName;
    @InjectView(R.id.lName) EditText etLastName;
    @InjectView(R.id.eMail) EditText etEmail;
    @InjectView(R.id.uPass) EditText etPass;
    @InjectView(R.id.cNumber) EditText etContactNo;
    @InjectView(R.id.btn_signup) Button btnRegister;
    @InjectView(R.id.link_login)
    TextView _loginLink;
    //UserTO uInfo=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        uInfo = (UserTO)intent.getSerializableExtra("UserTO");
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

//        etFirstName = (EditText) findViewById(R.id.et_first_name);
//        etLastName = (EditText) findViewById(R.id.et_last_name);
//        etEmail = (EditText) findViewById(R.id.et_email);
//        etPass = (EditText) findViewById(R.id.et_password);
//        etContactNo = (EditText) findViewById(R.id.et_contact_no);
//        btnRegister = (Button) findViewById(R.id.btn_register);
//        btnRegister.setOnClickListener(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_register:
//
//                startBarcodeScanner();
//// call Register AsyncTask
//                //new RegiserAsyncTask().execute();
//                break;
//        }
//
//    }

    public void register(){
        String mail = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        String contact = etContactNo.getText().toString();
        String firstN = etFirstName.getText().toString();
        String lastN = etLastName.getText().toString();

        RegisterAction rAction = new RegisterAction(this);
        rAction.execute(firstN, pass, lastN, mail, contact);
    }

    UserTO uInfo;
    private class RegisterAction extends AsyncTask<String, String,String> {
        Context context;

        private RegisterAction(Context context){this.context = context;}
//        ProgressDialog progress = new ProgressDialog(RegistrationActivity.this);
//        String result = "";

        @Override
        protected String doInBackground(String... params) {
            BookTradeHttpConnection conn = new BookTradeHttpConnection();
            uInfo = conn.registerUser(params[0], params[1], params[2], params[3], params[4]);
            Log.d("User login ","User login : Uemail" + params[0]);
            return null;
        }

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progress.setMessage("Loading...");
//            progress.show();
//        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            System.out.println("User login : Uemail" + uInfo.getEmailId());
//            System.out.println("User login : Upass" + uInfo.getPassword());
//            if(uInfo!=null) {
                Intent mainAct = new Intent(RegistrationActivity.this, LoginActivity.class);
//                mainAct.putExtra("UserInfo",uInfo);
//                mainAct.putExtra("firstName", uInfo.getFirstName());
//                mainAct.putExtra("lastName", uInfo.getLastName());
                startActivity(mainAct);
                Log.d("String", "I am here");
                //Toast.makeText(getApplicationContext(), " Login Credentials are valid !!", Toast.LENGTH_LONG).show();
//            }
//            else
//            {
//                Toast.makeText(getApplicationContext(), "Cannot Register!!", Toast.LENGTH_LONG).show();
//            }

        }
    }

//    private void startBarcodeScanner(){
//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.initiateScan();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        IntentResult intentScanningResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
//        if(intentScanningResult != null){
//            String scanContent = intentScanningResult.getContents();
//            String scanFormat = intentScanningResult.getFormatName();
//            Toast.makeText(this, "Format: " + scanFormat + "Result: " + scanFormat, Toast.LENGTH_LONG).show();
//        }
//
//    }

    public void finish(){

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }

}
