package munshig.in.kiranamark3;


import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class UserModel {


    public String uID;
    public String name;
    public String phoneNumber;
    public List<Integer> secondaryNumber = new ArrayList<>();
    public String longitude;
    public String latitude;
    public String shopName;
    public String shopAddress;
    public String aadharNumber;
    public String registrationNumber;
    public boolean approved;



    public UserModel(){

    }

    public UserModel(String uID, String name, String phoneNumber, List<Integer> secondaryNumber, String longitude, String latitude, String shopName, String shopAddress, String aadharNumber, String registrationNumber, boolean approved) {
        this.uID = uID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.secondaryNumber = secondaryNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.aadharNumber = aadharNumber;
        this.registrationNumber = registrationNumber;
        this.approved = approved;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uID", uID);
        result.put("name", name);
        result.put("phoneNumber", phoneNumber);
        result.put("secondaryNumber", secondaryNumber);
        result.put("longitude", longitude);
        result.put("latitude",latitude);
        result.put("shopName",shopName);
        result.put("shopAddress",shopAddress);
        result.put("aadharNumber",aadharNumber);
        result.put("registrationNumber",registrationNumber);
        result.put("approved",approved);
        return result;
    }
}
