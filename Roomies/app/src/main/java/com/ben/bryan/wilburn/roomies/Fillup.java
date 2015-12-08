package com.ben.bryan.wilburn.roomies;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Ben on 12/7/2015.
 */
public class Fillup {

    public Fillup(){}

    public boolean sendNewFillup(double newGallons, double newPricePerGallon, ParseUser user) {
        ParseObject fillup = new ParseObject("Car");
        fillup.put("User", user.getString("displayname"));
        fillup.put("Gallons", newGallons);
        fillup.put("Price", newPricePerGallon);
        fillup.put("Apartment", user.get("Apartment"));
        //update  FInancial
        ParseObject financial =  new ParseObject("Financial");
        financial.put("payment", newGallons * newPricePerGallon);
        financial.put("Apartment", user.get("Apartment"));
        financial.put("User", user.getString("displayname"));


        try {

            //update apartment with query
            ParseQuery<ParseObject> apartmentQuery = ParseQuery.getQuery("ApartmentID");
            apartmentQuery.whereEqualTo("Apartment", user.getString("Apartment"));
            ParseObject apartmentID = apartmentQuery.find().get(0);
            apartmentID.put("ApartmentBalance", apartmentID.getDouble("ApartementBalance") + (newGallons * newPricePerGallon));
            //update user with query
            user.put("UserBalance", user.getDouble("UserBalance") + (newGallons * newPricePerGallon));
            user.save();
            apartmentID.save();
            fillup.save();
            financial.save();
            return true;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] getFillup(ParseUser user) {

        String[] returnList;
        ParseQuery<ParseObject> FillupQuery = ParseQuery.getQuery("Fillup");
        FillupQuery.whereEqualTo("Apartment", user.get("Apartment"));
        try {
            List<ParseObject> fillupList =  FillupQuery.find();
            returnList = new String[fillupList.size()];
            int i = 0;
            for (ParseObject p: fillupList) {
                returnList[i] = p.getString("Car") + " " + String.format("%.1f",p.getDouble("Miles"));
                i++;
            }
        } catch (ParseException e) {
            returnList = null;
            e.printStackTrace();
        }
        return returnList;
    }
}
