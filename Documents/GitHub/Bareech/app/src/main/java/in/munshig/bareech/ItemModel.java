package in.munshig.bareech;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ItemModel implements Serializable{
    // public String firebaseid;
    public String itemname;
    public int category;
    public int unit;
    public String barcode;
    public int one,two,five, six, ten, twelve, fifty, hundred, twofifty, fivehundred, onethousand, twothousand, twothousandfivehundred, fivethousand;

    public ItemModel(){

    }

    public ItemModel(String itemname, int category, int unit, String barcode, int one, int two, int five, int six, int ten, int twelve, int fifty, int hundred, int twofifty, int fivehundred, int onethousand, int twothousand, int twothousandfivehundred, int fivethousand){
        //  this.firebaseid=firebaseid;
        this.itemname=itemname;
        this.category=category;
        this.unit = unit;
        this.barcode=barcode;
        this.one=one;
        this.two=two;
        this.five = five;
        this.six = six;
        this.ten=ten;
        this.twelve = twelve;
        this.fifty=fifty;
        this.hundred=hundred;
        this.twofifty=twofifty;
        this.fivehundred=fivehundred;
        this.onethousand=onethousand;
        this.twothousand=twothousand;
        this.twothousandfivehundred=twothousandfivehundred;
        this.fivethousand=fivethousand;
    }

}
