package munshig.in.kiranamark3;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by ALANKRITA on 02-02-2018.
 */
public class BarcodeItemModel {
   // public String firebaseid;
    public String itemname;
    public float itemprice;
    public String barcode;

    public BarcodeItemModel(){

    }

    public BarcodeItemModel(String itemname,float itemprice, String barcode){
      //  this.firebaseid=firebaseid;
        this.itemname=itemname;
        this.itemprice=itemprice;
        this.barcode=barcode;
    }

//    public String getfirebaseid()
//    {
//        return firebaseid;
//    }

    public String getBarcode()
    {
        return barcode;
    }

    public String getname()
    {
        return itemname;
    }
    public float getprice()
    {
        return itemprice;
    }



//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("firebaseid", firebaseid);
//        result.put("name", name);
//        result.put("price", price);
//        result.put("barcode", barcode);
//        return result;
//    }




}
