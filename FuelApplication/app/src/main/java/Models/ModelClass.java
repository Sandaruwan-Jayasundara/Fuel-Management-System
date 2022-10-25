package Models;

/**
 * Model Class uses for the recycle view
 * Store shed details
 */
public class ModelClass {

    private int imageview;
    private String textview1;
    private String textview2;
    private String textview3;
    private String phone;

    public ModelClass(int imageview, String textview1, String textview2, String textview3, String phone)
    {
        this.imageview=imageview;
        this.textview1=textview1;
        this.textview2=textview2;
        this.textview3=textview3;
        this.phone=phone;
    }
    public int getImageview() {
        return imageview;
    }
    public String getTextview1() {
        return textview1;
    }
    public String getPhone()
    {
        return phone;
    }
    public String getTextview2() {
        return textview2;
    }
    public String getTextview3() {
        return textview3;
    }
}