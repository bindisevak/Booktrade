package sjsu.com.booktrade.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import java.util.ArrayList;
import java.util.List;

import sjsu.com.booktrade.beans.BooksTO;
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

    public UserTO registerUser(String fName, String passwd, String lName, String uName, String contact)
    {

        HttpClient conn = new DefaultHttpClient();
        HttpPost postData = new HttpPost(BookTradeConstants.BASE_REF_URL+"/user/register");
        HttpResponse response =  null;
        postData.setHeader("content-type", "application/json; charset=UTF-8");
        JSONObject data = new JSONObject();
        InputStream myInfo = null;
       String uInfo = null;
        //UserTO user = null;
        try {

            data.put("firstname",fName);
            data.put("lastname",lName);
            data.put("emailId",uName);
            data.put("password",passwd);
            data.put("contactNumber",contact);

            Log.d("Hello this is  me",data.toString());
            postData.setEntity(new StringEntity(data.toString()));

            response = conn.execute(postData);

//            myInfo= (response.getEntity().getContent());
//            if(myInfo!=null)
//            {
//                uInfo = decodeMyData(myInfo);
//                user = new BookTradeJSONParser().parseUserInfo(uInfo);
//            }
//            else
//            {
//                uInfo = "No Data";
//            }


//            System.out.print("********************"+uInfo);
//            Log.d("Hello Data", uInfo);

        }
        catch(Exception ex)
        {
            Log.d("Login Error",ex.getStackTrace().toString());
            ex.printStackTrace();
        }

        //return  user;

        return null;
    }

    public List<BooksTO> getBooksTO()
    {
        List<BooksTO> bookList = new ArrayList<BooksTO>();
        HttpClient conn = new DefaultHttpClient();
        HttpPost getData = new HttpPost(BookTradeConstants.BASE_REF_URL+"/books/fetchAllAvailableBooks");
        HttpResponse response =  null;
        getData.setHeader("content-type", "application/json; charset=UTF-8");
        InputStream myInfo = null;
        String bInfo = null;
        BooksTO books = null;

        try {
            response = conn.execute(getData);
            myInfo = (response.getEntity().getContent());
            Log.d("String", String.valueOf(myInfo));
            if(myInfo!=null)
            {
                bInfo = decodeMyData(myInfo);
                Log.d("data",bInfo);
                bookList = new BookTradeJSONParser().parseBooksInfo(bInfo);

            }
            else
            {
                //bInfo = "No Data";
            }


            System.out.print("********************"+bInfo);
            //Log.d("Hello Data",bInfo);

        }
        catch(Exception ex)
        {
            Log.d("book Info Error",ex.getStackTrace().toString());
            ex.printStackTrace();
        }
        return (List<BooksTO>) bookList;



    }

    public List<BooksTO> getBooksWithinFiftyMiles(String userId, String latitude, String longitude) {
        List<BooksTO> bookListGPS = new ArrayList<BooksTO>();
        HttpClient conn = new DefaultHttpClient();
        HttpPost getData = new HttpPost(BookTradeConstants.BASE_REF_URL + "/books/fetchBooksWithinFiftyMiles");
        HttpResponse response = null;
        getData.setHeader("content-type", "application/json; charset=UTF-8");
        InputStream myInfo = null;
        String bInfo = null;
        BooksTO books = null;
        JSONObject data = new JSONObject();

        try {
            data.put("userId", Integer.parseInt(userId));
            data.put("latitude", Double.parseDouble(latitude));
            data.put("longitude", Double.parseDouble(longitude));
            getData.setEntity(new StringEntity(data.toString()));

            response = conn.execute(getData);

            myInfo = (response.getEntity().getContent());
            Log.d("String", String.valueOf(myInfo));
            if (myInfo != null) {
                bInfo = decodeMyData(myInfo);
                Log.d("data", bInfo);
                bookListGPS = new BookTradeJSONParser().parseBooksInfo(bInfo);

            } else {
                //bInfo = "No Data";
            }


            //System.out.print("********************" + bInfo);
            Log.d("Hello Data", bInfo);

        } catch (Exception ex) {
            Log.d("book Info Error", ex.getStackTrace().toString());
            ex.printStackTrace();
        }
        return (List<BooksTO>) bookListGPS;
    }

//    public List<BooksTO> getBooksList(String query) {
//
//        List<BooksTO> bookList = new ArrayList<BooksTO>();
//        HttpClient conn = new DefaultHttpClient();
//        HttpPost getData = new HttpPost(BookTradeConstants.BASE_REF_URL + "/books/");
//        HttpResponse response = null;
//        getData.setHeader("content-type", "application/json; charset=UTF-8");
//        InputStream myInfo = null;
//        String bInfo = null;
//        BooksTO books = null;
//        JSONObject data = new JSONObject();
//
//        try {
//            data.put("Name", query);
//
//            getData.setEntity(new StringEntity(data.toString()));
//
//            response = conn.execute(getData);
//
//            myInfo = (response.getEntity().getContent());
//            Log.d("String", String.valueOf(myInfo));
//            if (myInfo != null) {
//                bInfo = decodeMyData(myInfo);
//                Log.d("data", bInfo);
//                bookList = new BookTradeJSONParser().parseBooksInfo(bInfo);
//
//            } else {
//                //bInfo = "No Data";
//            }
//
//
//            System.out.print("********************" + bInfo);
//            //Log.d("Hello Data",bInfo);
//
//        } catch (Exception ex) {
//            Log.d("book Info Error", ex.getStackTrace().toString());
//            ex.printStackTrace();
//        }
//        return (List<BooksTO>) bookList;
//    }

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

    public boolean postAd(String name, String author, String category, String imageURLLarge, String imageURLSmall, String pickUporShipOption, String editionVal,
                          String creditsVal, String userId, String notesStr, String address1,
                          String address2, String city, String postalCode, String state, String dayFrom, String dayTo, String timeFrom, String timeTo) {
        HttpClient conn = new DefaultHttpClient();
        HttpPost postData = new HttpPost(BookTradeConstants.BASE_REF_URL+"/books/tradeabook");
        HttpResponse response =  null;
        postData.setHeader("content-type", "application/json; charset=UTF-8");
        JSONObject data = new JSONObject();
        try {
            data.put("bookname",name);
            data.put("author",author);
            data.put("edition",editionVal);
            data.put("pickUpOrShip",pickUporShipOption);
            data.put("price",creditsVal);
            data.put("userId",userId);
            data.put("category",category);
            data.put("small_image_url",imageURLSmall);
            data.put("large_image_url",imageURLLarge);
            data.put("addressLine1",address1);
            data.put("addressLine2",address2);
            data.put("city",city);
            data.put("state",state);
            data.put("postalCode",postalCode);
            data.put("day_from",dayFrom);
            data.put("day_to",dayTo);
            data.put("time_from",timeFrom);
            data.put("time_to",timeTo);
            data.put("notes",notesStr);

            Log.d("Hello this is  me",data.toString());
            postData.setEntity(new StringEntity(data.toString()));

            response = conn.execute(postData);
            decodeMyData(response.getEntity().getContent());

        }
        catch(Exception ex) {
            Log.d("Login Error", ex.getStackTrace().toString());
            ex.printStackTrace();
        }
        return true;
    }

    public BooksTO getBookDetailsFromISBN(String isbn) {
        BooksTO books = null;
        HttpClient conn = new DefaultHttpClient();
        HttpPost postData = new HttpPost(BookTradeConstants.BASE_REF_URL+"/books/getBooksFromISBN");
        HttpResponse response =  null;
        postData.setHeader("content-type", "application/json; charset=UTF-8");
        JSONObject data = new JSONObject();
        InputStream myInfo = null;
        String bInfo = null;
        try {

            data.put("isbn",isbn);
            postData.setEntity(new StringEntity(data.toString()));

            response = conn.execute(postData);
            myInfo= (response.getEntity().getContent());
            if(myInfo!=null)
            {
                bInfo = decodeMyData(myInfo);
                books = new BookTradeJSONParser().parseBookInfo(bInfo);
                if(books.getBookName() == null){
                    return null;
                }
            }
            else
            {
                bInfo = "No Data";
                return null;
            }
            System.out.print("********************"+bInfo);
            Log.d("Hello Data", bInfo);
        }
        catch(Exception ex)
        {
            Log.d("Login Error",ex.getStackTrace().toString());
            ex.printStackTrace();
        }

        return books;
    }

    public String buyBook(String pickup, String sellerId, String bookId, String price, String userId, String pickupDate, String pickupTime, String addressLine1,
                          String addressLine2, String city, String state, String postalCode) {
            int books = 0;
            HttpClient conn = new DefaultHttpClient();
            HttpPost postData = new HttpPost(BookTradeConstants.BASE_REF_URL+"/buyer/placeOrder");
            HttpResponse response =  null;
            postData.setHeader("content-type", "application/json; charset=UTF-8");
            JSONObject data = new JSONObject();
            InputStream myInfo = null;
            String bInfo = null;
            try {

                data.put("sellerId",sellerId);
                data.put("bookId",bookId);
                data.put("price",price);
                data.put("userId",userId);
                data.put("pickUpOrShip", pickup);
                data.put("pickupDate",pickupDate);
                data.put("pickUpTime",pickupTime);
                data.put("addressLine1",addressLine1);
                data.put("addressLine2",addressLine2);
                data.put("city",city);
                data.put("state",state);
                data.put("postalCode",postalCode);
                Log.d("Data getting passed:: ",data.toString());
                postData.setEntity(new StringEntity(data.toString()));

                response = conn.execute(postData);
                myInfo= (response.getEntity().getContent());

                if(myInfo!=null)
                {
                    bInfo = decodeMyData(myInfo);
                    Log.d("books:::",bInfo);
                    if(bInfo != null)
                        books = Integer.parseInt(bInfo);
                    if(books == 0){
                        return null;
                    }
                }
                else
                {
                    bInfo = "No Data";
                    return null;
                }
                System.out.print("********************"+bInfo);
                Log.d("Hello Data", bInfo);
            }
            catch(Exception ex)
            {
                Log.d("Login Error",ex.getStackTrace().toString());
                ex.printStackTrace();
            }

            return String.valueOf(bInfo);
        }
}
