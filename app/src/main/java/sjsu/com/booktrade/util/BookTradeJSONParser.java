package sjsu.com.booktrade.util;

import android.util.Log;

import org.json.JSONObject;

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
}
