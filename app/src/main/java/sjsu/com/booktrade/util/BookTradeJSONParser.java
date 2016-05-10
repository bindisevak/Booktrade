package sjsu.com.booktrade.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sjsu.com.booktrade.beans.AddressTO;
import sjsu.com.booktrade.beans.AddressType;
import sjsu.com.booktrade.beans.BooksTO;
import sjsu.com.booktrade.beans.SchedulesTO;
import sjsu.com.booktrade.beans.UserTO;

/**
 * Created by hetalashar on 4/10/16.
 */
public class BookTradeJSONParser {

    public UserTO parseUserInfo(String data) {
        UserTO uInfo = null;
        JSONObject jsonObject = null;
        try {
            uInfo = new UserTO();
            jsonObject = new JSONObject(data);

            uInfo.setFirstName((String) jsonObject.get("firstName"));
            uInfo.setLastName((String) jsonObject.get("lastName"));
            uInfo.setPassword((String) jsonObject.get("password"));
            uInfo.setUserType((String) jsonObject.get("userType"));
            uInfo.setContactNumber((String) jsonObject.get("contactNumber"));
            uInfo.setEmailId((String) jsonObject.get("emailId"));
            uInfo.setUserId((int) jsonObject.get("userId"));
            uInfo.setCredits(jsonObject.getDouble("credits"));
        } catch (Exception ex) {
            Log.d("JSON Parser Exception", ex.getStackTrace().toString());
            ex.printStackTrace();
            uInfo = null;

        }

        return uInfo;

    }

    public List<BooksTO> parseBooksInfo(String data) {
        BooksTO bInfo = null;
        JSONObject jsonObject = null;
        List<BooksTO> bookList = new ArrayList<BooksTO>();
        try {
            bInfo = new BooksTO();
            Log.d("Data", data);
            data = "{Data:[" + data.substring(data.indexOf("[") + 1, data.length());
            data = data.substring(0, data.lastIndexOf("]")) + "]}";
            Log.d("Data", data);
            jsonObject = new JSONObject(data);
            JSONArray bookListObj = jsonObject.getJSONArray("Data");

            bookList = new ArrayList<BooksTO>();

            for (int i = 0; i < bookListObj.length(); i++) {

                jsonObject = (JSONObject) bookListObj.get(i);
                bInfo = new BooksTO();
                String user = null;
                String address = null;
                String schedules = null;
                if (jsonObject.getString("user") != null && jsonObject.getString("user").length() > 0) {
                    user = jsonObject.getString("user");
                }
                if (jsonObject.getString("address") != null && jsonObject.getString("address").length() > 0) {
                    address = jsonObject.getString("address");
                }
                if (jsonObject.getString("schedules") != null && jsonObject.getString("schedules").length() > 0) {
                    schedules = jsonObject.getString("schedules");
                }

                if (jsonObject.has("bookId") && jsonObject.get("bookId") != null)
                    bInfo.setBookId((Integer) jsonObject.get("bookId"));
                if (jsonObject.has("bookName") && jsonObject.get("bookName") != null)
                    bInfo.setBookName((String) jsonObject.get("bookName"));
                if (jsonObject.has("author") && jsonObject.get("author") != null)
                    bInfo.setAuthor((String) jsonObject.get("author"));
                if (jsonObject.has("edition") && jsonObject.get("edition") != null)
                    bInfo.setEdition((Integer) jsonObject.get("edition"));
                if (jsonObject.has("pickUpOrShip") && jsonObject.get("pickUpOrShip") != null) {
                    bInfo.setPickUpOrShip((String) jsonObject.getString("pickUpOrShip"));
                }
                if (jsonObject.has("price") && jsonObject.get("price") != null)
                    bInfo.setPrice((Double) jsonObject.get("price"));
                if (user != null && user.length() > 0 && user != "null")
                    bInfo.setUser(parseUserInfo(user));
                if (address != null && address.length() > 0 && address != "null")
                    bInfo.setAddress(parseAddressInfo(address));
                // if (jsonObject.has("imageURLSmall") && jsonObject.getString("imageURLSmall") != "null")
                bInfo.setImageURLSmall((String) jsonObject.getString("imageURLSmall"));
                //  if (jsonObject.has("imageURLLarge") && jsonObject.getString("imageURLLarge") != "null")
                bInfo.setImageURLLarge((String) jsonObject.getString("imageURLLarge"));
                if (schedules != null && schedules.length() > 0 && schedules != "null")
                    bInfo.setSchedules(parseSchedulesInfo(schedules));
                if (jsonObject.has("transactionComplete") && jsonObject.get("transactionComplete") != null)
                    bInfo.setTransactionComplete((boolean) jsonObject.get("transactionComplete"));
                if (jsonObject.has("category") && jsonObject.get("category") != null && jsonObject.getString("category") != "null") {
                    bInfo.setCategory((String) jsonObject.get("category"));
                }
                bookList.add(bInfo);
                Log.d("Hi", String.valueOf(bookList));

            }


        } catch (Exception ex) {
            Log.d("JSON Parser Exception", ex.getStackTrace().toString());
            ex.printStackTrace();
            bInfo = null;

        }

        return bookList;


    }

    public BooksTO parseBookInfo(String bInfo) {
        BooksTO book = null;
        JSONObject jsonObject = null;
        try {
            book = new BooksTO();
            jsonObject = new JSONObject(bInfo);

            String user = null;
            String address = null;
            String schedules = null;

            if (jsonObject.getString("user") != null && jsonObject.getString("user").length() > 0) {
                user = jsonObject.getString("user");
            }
            if (jsonObject.getString("address") != null && jsonObject.getString("address").length() > 0) {
                address = jsonObject.getString("address");
            }
            if (jsonObject.getString("schedules") != null && jsonObject.getString("schedules").length() > 0) {
                schedules = jsonObject.getString("schedules");
            }

            if (jsonObject.has("bookId") && jsonObject.get("bookId") != null)
                book.setBookId((Integer) jsonObject.get("bookId"));
            if (jsonObject.has("bookName") && jsonObject.get("bookName") != null)
                book.setBookName((String) jsonObject.get("bookName"));
            if (jsonObject.has("author") && jsonObject.get("author") != null)
                book.setAuthor((String) jsonObject.get("author"));
            if (jsonObject.has("edition") && jsonObject.get("edition") != null)
                book.setEdition((Integer) jsonObject.get("edition"));
            if (jsonObject.has("pickUpOrShip") && jsonObject.get("pickUpOrShip") != null) {
                book.setPickUpOrShip((String) jsonObject.getString("pickUpOrShip"));
            }
            if (jsonObject.has("price") && jsonObject.get("price") != null)
                book.setPrice((Double) jsonObject.get("price"));
            if (jsonObject.has("category") && jsonObject.get("category") != null)
                book.setCategory((String) jsonObject.get("category"));
            if (user != null && user.length() > 0 && user != "null")
                book.setUser(parseUserInfo(user));
            if (address != null && address.length() > 0 && address != "null")
                book.setAddress(parseAddressInfo(address));
            if (jsonObject.has("imageURLSmall") && jsonObject.get("imageURLSmall") != null)
                book.setImageURLSmall((String) jsonObject.get("imageURLSmall"));
            if (jsonObject.has("imageURLLarge") && jsonObject.get("imageURLLarge") != null)
                book.setImageURLLarge((String) jsonObject.get("imageURLLarge"));
            if (schedules != null && schedules.length() > 0 && schedules != "null")
                book.setSchedules(parseSchedulesInfo(schedules));
            if (jsonObject.has("transactionComplete") && jsonObject.get("transactionComplete") != null)
                book.setTransactionComplete((boolean) jsonObject.get("transactionComplete"));

        } catch (Exception ex) {
            Log.d("JSON Parser Exception", ex.getStackTrace().toString());
            ex.printStackTrace();
            //bInfo = null;
        }

        return book;
    }

    private AddressTO parseAddressInfo(String addressStr) {
        AddressTO address = null;
        JSONObject jsonObject = null;
        try {
            address = new AddressTO();
            jsonObject = new JSONObject(addressStr);

            address.setAddressId((int) jsonObject.get("addressId"));
            if (jsonObject.get("addressline1") != null && jsonObject.getString("addressline1") != "null")
                address.setAddressline1((String) jsonObject.get("addressline1"));
            if (jsonObject.get("addressline2") != null && jsonObject.getString("addressline2") != "null")
                address.setAddressline2((String) jsonObject.get("addressline2"));
            if (jsonObject.getString("addresstype") != "null" && ((String) jsonObject.get("addresstype")).equalsIgnoreCase("pickUp")) {
                address.setAddresstype(AddressType.PICKUP);
            } else {
                address.setAddresstype(AddressType.SHIPPING);
            }
            if (jsonObject.get("city") != null && jsonObject.getString("city") != "null")
                address.setCity((String) jsonObject.get("city"));
            if (jsonObject.get("pincode") != null && jsonObject.getString("pincode") != "null")
                address.setPincode((String) jsonObject.get("pincode"));
            if (jsonObject.get("state") != null && jsonObject.getString("state") != "null")
                address.setState((String) jsonObject.get("state"));
            address.setUserId((int) jsonObject.get("userId"));
            if (jsonObject.get("latitude") != null && jsonObject.getString("latitude") != "null")
                address.setLatitude(jsonObject.getDouble("latitude"));
            if (jsonObject.get("longitude") != null && jsonObject.getString("longitude") != "null")
                address.setLongitude(jsonObject.getDouble("longitude"));
            address.setBookId((int) jsonObject.get("bookId"));


        } catch (Exception ex) {
            Log.d("JSON Parser Exception", ex.getStackTrace().toString());
            ex.printStackTrace();
            // address = null;

        }
        return address;
    }


    private SchedulesTO parseSchedulesInfo(String schedules) {
        SchedulesTO schedulesTO = new SchedulesTO();
        JSONObject jsonObject = null;
        try {
            schedulesTO = new SchedulesTO();
            jsonObject = new JSONObject(schedules);

            schedulesTO.setScheduleId((int) jsonObject.get("scheduleId"));
            schedulesTO.setBookId((int) jsonObject.get("bookId"));
            schedulesTO.setBuyerId((int) jsonObject.get("buyerId"));
            schedulesTO.setSellerId((int) jsonObject.get("sellerId"));
            if (jsonObject.getString("dayFrom") != null && jsonObject.getString("dayFrom") != "null")
                schedulesTO.setDayFrom((String) jsonObject.get("dayFrom"));
            if (jsonObject.getString("dayTo") != null && jsonObject.getString("dayTo") != "null")
                schedulesTO.setDayTo((String) jsonObject.get("dayTo"));
            if (jsonObject.getString("timeFrom") != null && jsonObject.getString("timeFrom") != "null")
                schedulesTO.setTimeFrom((String) jsonObject.get("timeFrom"));
            if (jsonObject.getString("timeTo") != null && jsonObject.getString("timeTo") != "null")
                schedulesTO.setTimeTo((String) jsonObject.get("timeTo"));


        } catch (Exception ex) {
            Log.d("JSON Parser Exception", ex.getStackTrace().toString());
            ex.printStackTrace();
            //schedules = null;

        }
        return schedulesTO;
    }

    public List<BooksTO> parseSoldHistoryInfo(String data) {
        BooksTO bInfo = null;
        JSONObject jsonObject = null;
        List<BooksTO> sHistory = null;
        try {
            bInfo = new BooksTO();
            Log.d("Data", data);
            data = "{Data:[" + data.substring(data.indexOf("[") + 1, data.length());
            data = data.substring(0, data.lastIndexOf("]")) + "]}";
            Log.d("Data", data);
            jsonObject = new JSONObject(data);
            JSONArray bookListObj = jsonObject.getJSONArray("Data");

            sHistory = new ArrayList<BooksTO>();

            for (int i = 0; i < bookListObj.length(); i++) {

                jsonObject = (JSONObject) bookListObj.get(i);
                bInfo = new BooksTO();
                String user = null;
                String schedules = null;
                if (jsonObject.getString("user") != null && jsonObject.getString("user").length() > 0) {
                    user = jsonObject.getString("user");
                }
                if (jsonObject.getString("schedules") != null && jsonObject.getString("schedules").length() > 0) {
                    schedules = jsonObject.getString("schedules");
                }

                if (jsonObject.has("bookId") && jsonObject.get("bookId") != null)
                    bInfo.setBookId((Integer) jsonObject.get("bookId"));
                if (jsonObject.has("bookName") && jsonObject.get("bookName") != null)
                    bInfo.setBookName((String) jsonObject.get("bookName"));
                if (jsonObject.has("author") && jsonObject.get("author") != null)
                    bInfo.setAuthor((String) jsonObject.get("author"));
                if (jsonObject.has("edition") && jsonObject.get("edition") != null)
                    bInfo.setEdition((Integer) jsonObject.get("edition"));
                if (jsonObject.has("pickUpOrShip") && jsonObject.get("pickUpOrShip") != null) {
                    bInfo.setPickUpOrShip((String) jsonObject.getString("pickUpOrShip"));
                }
                if (jsonObject.has("price") && jsonObject.get("price") != null)
                    bInfo.setPrice((Double) jsonObject.get("price"));
                if (user != null && user.length() > 0 && user != "null")
                    bInfo.setUser(parseUserInfo(user));

                // if (jsonObject.has("imageURLSmall") && jsonObject.getString("imageURLSmall") != "null")
                bInfo.setImageURLSmall((String) jsonObject.getString("imageURLSmall"));
                //  if (jsonObject.has("imageURLLarge") && jsonObject.getString("imageURLLarge") != "null")
                bInfo.setImageURLLarge((String) jsonObject.getString("imageURLLarge"));
                if (schedules != null && schedules.length() > 0 && schedules != "null")
                    bInfo.setSchedules(parseSchedulesInfo(schedules));
//                if (jsonObject.has("transactionComplete") && jsonObject.get("transactionComplete") != null)
//                    bInfo.setTransactionComplete((boolean) jsonObject.get("transactionComplete"));
                if (jsonObject.has("category") && jsonObject.get("category") != null && jsonObject.getString("category") != "null") {
                    bInfo.setCategory((String) jsonObject.get("category"));
                }
                sHistory.add(bInfo);
                Log.d("Hi", String.valueOf(sHistory));

            }


        } catch (Exception ex) {
            Log.d("JSON Parser Exception", ex.getStackTrace().toString());
            ex.printStackTrace();
            bInfo = null;

        }

        return sHistory;


    }

    public List<BooksTO> parseBoughtHistoryInfo(String data) {

        BooksTO bInfo = null;
        JSONObject jsonObject = null;
        List<BooksTO> bHistory = null;
        try {
            bInfo = new BooksTO();
            Log.d("Data", data);
            data = "{Data:[" + data.substring(data.indexOf("[") + 1, data.length());
            data = data.substring(0, data.lastIndexOf("]")) + "]}";
            Log.d("Data", data);
            jsonObject = new JSONObject(data);
            JSONArray bookListObj = jsonObject.getJSONArray("Data");

            bHistory = new ArrayList<BooksTO>();

            for (int i = 0; i < bookListObj.length(); i++) {

                jsonObject = (JSONObject) bookListObj.get(i);
                bInfo = new BooksTO();
                String user = null;
                String schedules = null;
                if (jsonObject.getString("user") != null && jsonObject.getString("user").length() > 0) {
                    user = jsonObject.getString("user");
                }
                if (jsonObject.getString("schedules") != null && jsonObject.getString("schedules").length() > 0) {
                    schedules = jsonObject.getString("schedules");
                }

                if (jsonObject.has("bookId") && jsonObject.get("bookId") != null)
                    bInfo.setBookId((Integer) jsonObject.get("bookId"));
                if (jsonObject.has("bookName") && jsonObject.get("bookName") != null)
                    bInfo.setBookName((String) jsonObject.get("bookName"));
                if (jsonObject.has("author") && jsonObject.get("author") != null)
                    bInfo.setAuthor((String) jsonObject.get("author"));
                if (jsonObject.has("edition") && jsonObject.get("edition") != null)
                    bInfo.setEdition((Integer) jsonObject.get("edition"));
                if (jsonObject.has("pickUpOrShip") && jsonObject.get("pickUpOrShip") != null) {
                    bInfo.setPickUpOrShip((String) jsonObject.getString("pickUpOrShip"));
                }
                if (jsonObject.has("price") && jsonObject.get("price") != null)
                    bInfo.setPrice((Double) jsonObject.get("price"));
                if (user != null && user.length() > 0 && user != "null")
                    bInfo.setUser(parseUserInfo(user));

                // if (jsonObject.has("imageURLSmall") && jsonObject.getString("imageURLSmall") != "null")
                bInfo.setImageURLSmall((String) jsonObject.getString("imageURLSmall"));
                //  if (jsonObject.has("imageURLLarge") && jsonObject.getString("imageURLLarge") != "null")
                bInfo.setImageURLLarge((String) jsonObject.getString("imageURLLarge"));
                if (schedules != null && schedules.length() > 0 && schedules != "null")
                    bInfo.setSchedules(parseSchedulesInfo(schedules));
//                if (jsonObject.has("transactionComplete") && jsonObject.get("transactionComplete") != null)
//                    bInfo.setTransactionComplete((boolean) jsonObject.get("transactionComplete"));
                if (jsonObject.has("category") && jsonObject.get("category") != null && jsonObject.getString("category") != "null") {
                    bInfo.setCategory((String) jsonObject.get("category"));
                }
                bHistory.add(bInfo);
                Log.d("Hi", String.valueOf(bHistory));

            }


        } catch (Exception ex) {
            Log.d("JSON Parser Exception", ex.getStackTrace().toString());
            ex.printStackTrace();
            bInfo = null;

        }

        return bHistory;
    }
}
