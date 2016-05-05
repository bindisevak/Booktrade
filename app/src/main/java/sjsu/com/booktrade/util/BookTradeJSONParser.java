package sjsu.com.booktrade.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sjsu.com.booktrade.beans.BooksTO;
import sjsu.com.booktrade.beans.UserTO;

/**
 * Created by hetalashar on 4/10/16.
 */
public class BookTradeJSONParser {

    public UserTO parseUserInfo(String data)
    {
        UserTO uInfo = null;
        JSONObject jsonObject =null;
        try
        {
            uInfo = new UserTO();
            jsonObject = new JSONObject(data);

            uInfo.setFirstName((String) jsonObject.get("firstName"));
            uInfo.setLastName((String) jsonObject.get("lastName"));
            uInfo.setPassword((String) jsonObject.get("password"));
            uInfo.setUserType((String) jsonObject.get("userType"));
            uInfo.setContactNumber((String) jsonObject.get("contactNumber"));
            uInfo.setEmailId((String) jsonObject.get("emailId"));
            uInfo.setUserId((int) jsonObject.get("userId"));


        }catch (Exception ex)
        {
            Log.d("JSON Parser Exception", ex.getStackTrace().toString());
            ex.printStackTrace();
            uInfo = null;

        }

        return  uInfo;

    }

    public List<BooksTO> parseBooksInfo(String data)
    {
        BooksTO bInfo = null;
        JSONObject jsonObject =null;
        List<BooksTO> bookList=new ArrayList<BooksTO>();
        try
        {
            bInfo = new BooksTO();
            Log.d("Data", data);
            data="{Data:["+data.substring(data.indexOf("[")+1,data.length());
            data =data.substring(0,data.lastIndexOf("]"))+"]}";
            Log.d("Data",data);
            jsonObject = new JSONObject(data);
            JSONArray bookListObj = jsonObject.getJSONArray("Data");

            bookList = new ArrayList<BooksTO>();

            for (int i =0; i<bookListObj.length(); i++){

                jsonObject=(JSONObject)bookListObj.get(i);
                bInfo=new BooksTO();
                bInfo.setBookName((String) jsonObject.getString("bookName"));
                bInfo.setAuthor((String) jsonObject.getString("author"));
                bInfo.setBookId((int) jsonObject.getInt("bookId"));
                bInfo.setEdition((int) jsonObject.getInt("edition"));
                bInfo.setPrice((double) jsonObject.getDouble("price"));
                bInfo.setPickUpOrShip((String) jsonObject.getString("pickUpOrShip"));
                bInfo.setCategory((String) jsonObject.getString("category"));
                bInfo.setImageURLSmall((String) jsonObject.getString("imageURLSmall"));


                bookList.add(bInfo);
                Log.d("Hi", String.valueOf(bookList));

            }





        }catch (Exception ex)
        {
            Log.d("JSON Parser Exception", ex.getStackTrace().toString());
            ex.printStackTrace();
            bInfo = null;

        }

        return  bookList;


    }
}
