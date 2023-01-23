import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;


public class PopulateData extends DBConnection
{
    Connection dbConnection = null;
    JSONParser parser = new JSONParser();
    boolean CONNECTION_ESTABLISHED = false;

    public PopulateData()
    {
        try
        {
            dbConnection = DBConnection.getDBConnection();
            CONNECTION_ESTABLISHED = true;
            System.out.println("Established DB connection");
        }
        catch (Exception e)
        {
            System.out.println("Establishing db connection failed : "+e.getMessage());
        }
    }

    //Populate yelp users table
    private void insertUsers() throws IOException, ParseException {
        PreparedStatement userStatement = null;
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();

            //BufferedReader br = new BufferedReader(new FileReader("/Users/himabindu/Desktop/MSCS/DBMS/Assignments/HW3/YelpDataset/yelp_user.json"));
            BufferedReader br = new BufferedReader(new FileReader("../../YelpDataset/yelp_user.json"));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    jsonObject = (JSONObject) jsonParser.parse(line);

                    try
                    {
                        if (userStatement == null)
                        {
                            try {
                                String sql = "INSERT INTO yelpdbase.yelp_users(USER_ID, USER_NAME, YELPING_SINCE, REVIEW_COUNT, USEFUL_VOTES, FUNNY_VOTES, COOL_VOTES, NO_OF_VOTES, NO_OF_FRIENDS, AVERAGE_STARS)"
                                        + " VALUES (?,?,?,?,?,?,?,?,?,?)";
                                userStatement = dbConnection.prepareStatement(sql);
                            } catch (SQLException e1) {
                                System.out.println(e1.getMessage());
                                e1.printStackTrace();
                            }
                        }

                        assert userStatement != null;
                        userStatement.setString(1, jsonObject.get("user_id").toString());
                        userStatement.setString(2, jsonObject.get("name").toString());
                        userStatement.setString(3, jsonObject.get("yelping_since").toString());
                        userStatement.setInt(4, Integer.parseInt(jsonObject.get("review_count").toString()));
                        JSONObject votes = (JSONObject) jsonObject.get("votes");
                        int funny = Integer.parseInt(votes.get("funny").toString());
                        userStatement.setInt(5, funny);
                        int useful = Integer.parseInt(votes.get("useful").toString());
                        userStatement.setInt(6, useful);
                        int cool = Integer.parseInt(votes.get("cool").toString());
                        userStatement.setInt(7, cool);
                        int noOfVotes = funny + useful + cool;
                        userStatement.setInt(8, noOfVotes);
                        JSONArray friends = (JSONArray) jsonObject.get("friends");
                        userStatement.setInt(9, friends.size());
                        String avgStars_s = jsonObject.get("average_stars").toString();
                        double avgStars_d = Double.parseDouble(avgStars_s.substring(0,Math.min(5, avgStars_s.length())));
                        double avgStars_r = Double.parseDouble(Long.toString(Math.round(avgStars_d*10)))/10;
                        userStatement.setDouble(10, avgStars_r);

                        System.out.println(jsonObject.get("user_id").toString());

                        try
                        {
                            userStatement.executeUpdate();
                        }
                        catch (SQLException e)
                        {

                        }

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if (userStatement != null) {
                            try {
                                userStatement.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            userStatement = null;
                        }

                    }
                }
            }
    }

    //Populate businesses table
    private void insertBusiness() throws IOException, ParseException {
        PreparedStatement businessStatement = null;
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();

        //BufferedReader br = new BufferedReader(new FileReader("/Users/himabindu/Desktop/MSCS/DBMS/Assignments/HW3/YelpDataset/yelp_business.json"));
        BufferedReader br = new BufferedReader(new FileReader("../../YelpDataset/yelp_business.json"));
        String line = null;
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                jsonObject = (JSONObject) jsonParser.parse(line);

                try
                {
                    if (businessStatement == null)
                    {
                        try
                        {
                            String sql = "INSERT INTO yelpdbase.business(BUSINESS_ID, BUSINESS_NAME, OPEN, CITY, STATE, REVIEW_COUNT, STARS)"
                                    + " VALUES (?,?,?,?,?,?,?)";
                            businessStatement = dbConnection.prepareStatement(sql);
                        }
                        catch (SQLException e1)
                        {
                            System.out.println(e1.getMessage());
                            e1.printStackTrace();
                        }
                    }
                    assert businessStatement != null;
                    businessStatement.setString(1, jsonObject.get("business_id").toString());
                    businessStatement.setString(2, jsonObject.get("name").toString());
                    businessStatement.setString(3, jsonObject.get("open").toString());
                    businessStatement.setString(4, jsonObject.get("city").toString());
                    businessStatement.setString(5, jsonObject.get("state").toString());
                    businessStatement.setInt(6, Integer.parseInt(jsonObject.get("review_count").toString()));

                    //Round off to half-star
                    businessStatement.setDouble(7, rounfOffToHalfStar(Double.parseDouble(jsonObject.get("stars").toString())));

                    try
                    {
                        businessStatement.executeUpdate();
                        System.out.println("business ID = " + jsonObject.get("business_id").toString());
                    }
                    catch (SQLIntegrityConstraintViolationException  e1)
                    {

                    }
                    catch (SQLException e)
                    {

                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (businessStatement != null) {
                        try {
                            businessStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        businessStatement = null;
                    }

                }
            }
        }
    }

    //Populate sub categories table
    private void insertBusinessCategory() throws IOException, ParseException {
        PreparedStatement businessStatement = null;
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();

        //BufferedReader br = new BufferedReader(new FileReader("/Users/himabindu/Desktop/MSCS/DBMS/Assignments/HW3/YelpDataset/yelp_business.json"));
        BufferedReader br = new BufferedReader(new FileReader("../../YelpDataset/yelp_business.json"));
        String line = null;
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                jsonObject = (JSONObject) jsonParser.parse(line);

                try
                {
                    if (businessStatement == null)
                    {
                        try
                        {
                            String sql = "INSERT INTO yelpdbase.business_category(BUSINESS_ID, BUSINESS_CATEGORY_NAME)"
                                    + " VALUES (?,?)";
                            businessStatement = dbConnection.prepareStatement(sql);
                        }
                        catch (SQLException e1)
                        {
                            System.out.println(e1.getMessage());
                            e1.printStackTrace();
                        }
                    }
                    assert businessStatement != null;

                    businessStatement.setString(1, jsonObject.get("business_id").toString());
                    JSONArray subCategoriesArray = (JSONArray) jsonObject.get("categories");

                    for (String subCategory : (Iterable<String>) subCategoriesArray)
                    {
                        businessStatement.setString(2, subCategory);

                        try
                        {
                            businessStatement.executeUpdate();
                            System.out.println("business ID = " + jsonObject.get("business_id").toString() );
                        }
                        catch (SQLException e)
                        {

                        }
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (businessStatement != null) {
                        try {
                            businessStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        businessStatement = null;
                    }

                }
            }
        }
    }

    //Populate attributes table
    private void insertBusinessAttributes() throws IOException, ParseException {
        PreparedStatement businessStatement = null;
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();

        //BufferedReader br = new BufferedReader(new FileReader("/Users/himabindu/Desktop/MSCS/DBMS/Assignments/HW3/YelpDataset/yelp_business.json"));
        BufferedReader br = new BufferedReader(new FileReader("../../YelpDataset/yelp_business.json"));
        String line = null;
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                jsonObject = (JSONObject) jsonParser.parse(line);

                try
                {
                    if (businessStatement == null)
                    {
                        try
                        {
                            String sql = "INSERT INTO yelpdbase.business_attributes(BUSINESS_ID, B_ATTRIBUTE)"
                                    + " VALUES (?,?)";
                            businessStatement = dbConnection.prepareStatement(sql);
                        }
                        catch (SQLException e1)
                        {
                            System.out.println(e1.getMessage());
                            e1.printStackTrace();
                        }
                    }
                    assert businessStatement != null;

                    businessStatement.setString(1, jsonObject.get("business_id").toString());
                    JSONObject attrObj = (JSONObject) jsonObject.get("attributes");

                    for(Object key: attrObj.keySet())
                    {
                        boolean datacopied = false;
                        String attributesStrToCpy ="";
                        String[] attrObjsStr = isJSONValid(attrObj.get(key).toString());
                        if(attrObjsStr !=null)
                            if(attrObjsStr.length > 0)
                        {

                            for(int i = 0; i < attrObjsStr.length ; i++)
                            {
                                attributesStrToCpy = key.toString() +
                                        attrObj.get(key).toString().replaceAll("\\{.*?\\}", "")+ " "
                                        +attrObjsStr[i];
                                businessStatement.setString(2, attributesStrToCpy);

                                //Populate here
                                try
                                {
                                    businessStatement.executeUpdate();
                                    System.out.println("business ID = " + jsonObject.get("business_id").toString() + " -- "+ attributesStrToCpy);
                                }
                                catch (SQLException e)
                                {

                                }
                            }
                            datacopied = true;
                        }
                        if(!datacopied)
                        {
                            attributesStrToCpy = key.toString() +" " +attrObj.get(key).toString();
                            businessStatement.setString(2, attributesStrToCpy);

                            //Populate here
                            try
                            {
                                businessStatement.executeUpdate();
                                System.out.println("business ID = " + jsonObject.get("business_id").toString() + " -- "+ attributesStrToCpy);
                            }
                            catch (SQLException e)
                            {

                            }
                        }

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (businessStatement != null) {
                        try {
                            businessStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        businessStatement = null;
                    }

                }
            }
        }
    }

    //Populate business hours table
    private void insertBusinessHours() {

    }

    //Populate reviews table
    private void insertReviews() throws IOException, ParseException  {

        PreparedStatement reviewStatement = null;
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();

        //BufferedReader br = new BufferedReader(new FileReader("/Users/himabindu/Desktop/MSCS/DBMS/Assignments/HW3/YelpDataset/yelp_review.json"));
        BufferedReader br = new BufferedReader(new FileReader("../../YelpDataset/yelp_review.json"));
        String line = null;
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                jsonObject = (JSONObject) jsonParser.parse(line);

                try
                {
                    if (reviewStatement == null)
                    {
                        try {
                            String sql = "INSERT INTO yelpdbase.reviews(USER_ID, REVIEW_ID, STARS, DATEE, REVIEW_TEXT, BUSINESS_ID, USEFUL_VOTES, FUNNY_VOTES, COOL_VOTES, TOTAL_VOTES)"
                                    + " VALUES (?,?,?,?,?,?,?,?,?,?)";
                            reviewStatement = dbConnection.prepareStatement(sql);
                        } catch (SQLException e1) {
                            System.out.println(e1.getMessage());
                            e1.printStackTrace();
                        }
                    }

                    assert reviewStatement != null;
                    reviewStatement.setString(1, jsonObject.get("user_id").toString());
                    reviewStatement.setString(2, jsonObject.get("review_id").toString());
                    reviewStatement.setInt(3, Integer.parseInt(jsonObject.get("stars").toString()));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = format.parse(jsonObject.get("date").toString());
                    reviewStatement.setDate(4, new Date(parsedDate.getTime()));
                    reviewStatement.setString(5, jsonObject.get("text").toString());
                    reviewStatement.setString(6, jsonObject.get("business_id").toString());

                    JSONObject votes = (JSONObject) jsonObject.get("votes");
                    int useful = Integer.parseInt(votes.get("useful").toString());
                    reviewStatement.setInt(7, useful);
                    int funny = Integer.parseInt(votes.get("funny").toString());
                    reviewStatement.setInt(8, funny);
                    int cool = Integer.parseInt(votes.get("cool").toString());
                    reviewStatement.setInt(9, cool);
                    reviewStatement.setInt(10, useful+funny+cool);

                    try
                    {
                        reviewStatement.executeUpdate();
                        System.out.println(jsonObject.get("review_id").toString());
                    }
                    catch (SQLException e)
                    {

                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (reviewStatement != null)
                    {
                        try
                        {
                            reviewStatement.close();
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                        reviewStatement = null;
                    }

                }
            }
        }
    }

    //Populate business main categories table
    private void insertBusinessMainCategories() {

        PreparedStatement businessStatement = null;
        ArrayList<String> mainCategoriesList = new ArrayList<>();

        mainCategoriesList.add("Active Life"); //1
        mainCategoriesList.add("Arts & Entertainment"); //2
        mainCategoriesList.add("Automotive"); //3
        mainCategoriesList.add("Car Rental"); //4
        mainCategoriesList.add("Cafes"); //5
        mainCategoriesList.add("Beauty & Spas"); //6
        mainCategoriesList.add("Convenience Store"); //7
        mainCategoriesList.add("Dentists"); //8
        mainCategoriesList.add("Doctors"); //9
        mainCategoriesList.add("Drugstores"); //10
        mainCategoriesList.add("Department Stores"); //11
        mainCategoriesList.add("Education"); //12
        mainCategoriesList.add("Event Planning & Services"); //13
        mainCategoriesList.add("Flowers & Gifts"); //14
        mainCategoriesList.add("Food"); //15
        mainCategoriesList.add("Health & Medical"); //16
        mainCategoriesList.add("Home Services"); //17
        mainCategoriesList.add("Home & Garden"); //18
        mainCategoriesList.add("Hospitals"); //19
        mainCategoriesList.add("Hotels & Travel"); //20
        mainCategoriesList.add("Hardware Stores"); //21
        mainCategoriesList.add("Grocery"); //22
        mainCategoriesList.add("Medical Centers"); //23
        mainCategoriesList.add("Nurseries & Gardening"); //24
        mainCategoriesList.add("Nightlife"); //25
        mainCategoriesList.add("Restaurants"); //26
        mainCategoriesList.add("Shopping"); //27
        mainCategoriesList.add("Transportation"); //28

        try
        {
            if (businessStatement == null)
            {
                try {
                    String sql = "INSERT INTO yelpdbase.business_main_categories(CATEGORY_NAME)"
                            + " VALUES (?)";
                    businessStatement = dbConnection.prepareStatement(sql);
                } catch (SQLException e1) {
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                }
            }

            for(int i  = 0; i < mainCategoriesList.size(); i++)
            {
                assert businessStatement != null;
                businessStatement.setString(1, mainCategoriesList.get(i));

                try
                {
                    businessStatement.executeUpdate();
                    System.out.println(mainCategoriesList.get(i));
                }
                catch (SQLException e)
                {

                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (businessStatement != null)
            {
                try
                {
                    businessStatement.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                businessStatement = null;
            }

        }
    }

    double rounfOffToHalfStar(double original_value)
    {
        double diff = original_value - ((int) original_value);
        double result = 0.0;
        if(diff >= 0 && diff < 0.3)
        {
            result = Math.floor(original_value);
        }
        else if(diff >= 0.3 && diff <= 0.7)
        {
            result = (int)original_value + 0.5;
        }
        else
        {
            result = Math.ceil(original_value);
        }

        return result;
    }

    public String[] isJSONValid(String test)
    {
        String appendedStr = "";
        try
        {
            JSONParser jsonParser = new JSONParser();
            JSONObject obj3 = (JSONObject) jsonParser.parse(test);

            for(Object key: obj3.keySet())
            {
                appendedStr += key + " "+(obj3.get(key))+ ",";
            }
        }
        catch (Exception ex)
        {
            return null;
        }
        return appendedStr.split(",");
    }


    public static void main(String[] args) throws IOException, ParseException {

        System.out.println("Populating database with businesses, users and reviews data");
        PopulateData populateData = new PopulateData();

        if(populateData.CONNECTION_ESTABLISHED)
        {

            populateData.insertBusiness();
            System.out.println("Inserted business");

            populateData.insertBusinessMainCategories();
            System.out.println("Inserted business main categories");

            populateData.insertBusinessCategory();
            System.out.println("Inserted business category");

            populateData.insertBusinessAttributes();
            System.out.println("Inserted business attributes");

            //populateData.insertBusinessHours();
            System.out.println("Inserted business hours");

            populateData.insertUsers();
            System.out.println("Inserted yelp users");

            populateData.insertReviews();
            System.out.println("Inserted reviews");

        }

    }
}
