package com.ben.bryan.wilburn.roomies;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 12/7/2015.
 */
public class Claim {

    public Claim(){}
    /**
     *
     * @param newCar string of car
     * @param newMiles double of miles
     * @param user current parse user
     * @return returns true if it worked
     */
    public boolean sendNewClaim(String newCar, double newMiles, ParseUser user) {
        ParseObject claim = new ParseObject("Claim");
        claim.put("Car", newCar);
        claim.put("Miles", newMiles);
        claim.put("Payed", false);
        claim.put("Apartment", user.get("Apartment"));
        try {
            ArrayList<String> returnList = new ArrayList<String>();
            ParseQuery<ParseObject> carQuery = ParseQuery.getQuery("Car");
            carQuery.whereEqualTo("Apartment", user.get("Apartment"));
            for(ParseObject p : carQuery.find()) {
                if (p.get("Name").equals(newCar)) {
                    claim.save();
                    return true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     *
     * @param user current parse user
     * @return String[] of cars and miles formated to one line each
     */
    public String[] getClaim(ParseUser user) {

        String[] returnList;
        ParseQuery<ParseObject> drivesQuery = ParseQuery.getQuery("BulletinBoard");
        drivesQuery.whereEqualTo("Apartment", user.get("Apartment"));
        try {
            List<ParseObject> drivesList =  drivesQuery.find();
            returnList = new String[drivesList.size()];
            int i = 0;
            for (ParseObject p: drivesList) {
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
