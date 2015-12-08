package com.ben.bryan.wilburn.roomies;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 12/7/2015.
 */
public class Car {

    public Car(){}

    /**
     *
     * @param newName name of car for apartment
     * @param newMPG double mpg for the car
     * @param user current parse user
     * @return returns true if no error
     */
    public boolean sendNewCar(String newName, double newMPG, ParseUser user) {
        ParseObject car = new ParseObject("Car");
        car.put("Name", newName);
        car.put("MPG", newMPG);
        car.put("Apartment", user.get("Apartment"));
        try {
            ArrayList<String> returnList = new ArrayList<String>();
            ParseQuery<ParseObject> carQuery = ParseQuery.getQuery("Car");
            carQuery.whereEqualTo("Apartment", user.get("Apartment"));
            if(carQuery.find().contains(car)) {
                car.save();
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public String[] getCars(ParseUser user) {

        String[] returnList;
        ParseQuery<ParseObject> carQuery = ParseQuery.getQuery("Car");
        carQuery.whereEqualTo("Apartment", user.get("Apartment"));
        try {
            List<ParseObject> carList = carQuery.find();
            returnList = new String[carList.size()];
            int i = 0;
            for (ParseObject p : carList) {
                returnList[i] = p.getString("Name");
                i++;
            }
        } catch (ParseException e) {
            returnList = null;
            e.printStackTrace();
        }
        return returnList;
    }

}
