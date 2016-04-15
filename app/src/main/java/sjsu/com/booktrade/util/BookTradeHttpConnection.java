package sjsu.com.booktrade.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import sjsu.com.booktrade.beans.UserTO;

/**
 * Created by hetalashar on 4/10/16.
 */
public class BookTradeHttpConnection {

    public String getData(String URL)
    {
        HttpURLConnection conn = null;
        String JSONData = "";
        BufferedReader iReader = null;

        try{
            java.net.URL queryURL = new URL(URL);
            conn = (HttpURLConnection) queryURL.openConnection();
            conn.connect();
            if(null!=conn && null!=conn.getInputStream()) {
                iReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer buff = new StringBuffer();
                String info = "";

                while ((info = iReader.readLine()) != null) {
                    buff.append(info);
                }

                JSONData = buff.toString();
                iReader.close();
            }

        }
        catch(Exception ex)
        {

        }
        finally {
        conn.disconnect();
        }
        return JSONData;
        }

    public UserTO loginUser(String uName,String passwd)
    {

        HttpClient conn = new DefaultHttpClient();
        HttpPost postData = new HttpPost(BookTradeConstants.BASE_REF_URL+"/user/login");
        HttpResponse response =  null;
        postData.setHeader("content-type", "application/json; charset=UTF-8");
        JSONObject data = new JSONObject();
        InputStream myInfo = null;
        String uInfo = null;
        UserTO user = null;
        try {

            data.put("username",uName);
            data.put("password",passwd);
            Log.d("Hello this is  me",data.toString());
            postData.setEntity(new StringEntity(data.toString()));

            response = conn.execute(postData);
            myInfo= (response.getEntity().getContent());
            if(myInfo!=null)
            {
                uInfo = decodeMyData(myInfo);
                user = new BookTradeJSONParser().parseUserInfo(uInfo);
            }
            else
            {
                uInfo = "No Data";
            }


            System.out.print("********************"+uInfo);
            Log.d("Hello Data", uInfo);

        }
        catch(Exception ex)
        {
            Log.d("Login Error",ex.getStackTrace().toString());
            ex.printStackTrace();
        }

        return  user;

    }


    private static String decodeMyData(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String data = "";
        while((line = bufferedReader.readLine()) != null) {
            data += line;
            Log.d("Database", line);
        }
        inputStream.close();
        return data;

    }


}
