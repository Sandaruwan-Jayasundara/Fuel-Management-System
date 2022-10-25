package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import Models.RegisterUserModel;

public class DBHelper extends SQLiteOpenHelper {


    //Create database name
    private static final String DB_NAME = "UserManagement";

    //Assign database version
    private static final int DB_VERSION = 1;

    //Create table name
    private static final String TABLE_NAME = "Auth";

    //Create column 1 name
    private static final String ID_COL = "id";

    //Create column 2 name
    private static final String NAME_COL = "name";

    //Create column 3 name
    private static final String PHONE_COL = "phone";

    //Create column 4 name
    private static final String UTYPE_COL = "utype";

    //Create column 5 name
    private static final String PASSWORD_COL = "password";

    //Create column 6 name
    private static final String TYPE_COL = "type";

    //assign data through constructor
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

   //Create the database
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
            Create the data base
            ID_COL is the primary key of the table
         */
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " String,"
                + PHONE_COL + " String,"
                + UTYPE_COL + " String,"
                + TYPE_COL + " String,"
                + PASSWORD_COL + " String)";

        //Execute the query
        db.execSQL(query);
    }

    /**
     * Add user to the database
     * @param phone
     * @param name
     * @param utype
     * @param password
     * @param type
     */
    public void registerUser(String phone, String name, String utype, String password, String type) {

        //create a database writer
        SQLiteDatabase db = this.getWritableDatabase();

        //Declare contectvalues
        ContentValues values = new ContentValues();

        //put values to the content
        values.put(NAME_COL, name);
        values.put(PHONE_COL, phone);
        values.put(UTYPE_COL, utype);
        values.put(TYPE_COL, type);
        values.put(PASSWORD_COL, password);

        //Execute the query
        db.insert(TABLE_NAME, null, values);

        //colse the database connection
        db.close();
    }

    /**
     * Get user data
     * @param phoneN
     * @return arrayList
     */
    public ArrayList getinfo(String phoneN) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor myCursor = db.rawQuery("SELECT * FROM Auth where phone=" + phoneN + "", null);

        ArrayList arrayList = new ArrayList();

        //assign values to the arraylist
        if (myCursor.getCount() >= 1) {
            while (myCursor.moveToNext()) {

                arrayList.add(myCursor.getInt(0));
                arrayList.add(myCursor.getString(1));
                arrayList.add(myCursor.getString(2));
                arrayList.add(myCursor.getString(3));
                arrayList.add(myCursor.getString(4));
                arrayList.add(myCursor.getString(5));
            }
            //colse the database connection
            myCursor.close();
        }
        return arrayList;
    }

    /**
     * Get all user data from the database
     * @return data
     */
    public List<RegisterUserModel> getAll(){

        //User level
        String type = "1";

        List<RegisterUserModel> data = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();

        //Execute the query
        Cursor myCursor = db.rawQuery("SELECT * FROM Auth where type=" + type + "", null);

        //Assign values to Register User Model
        if(myCursor.moveToFirst()){
            do {
                RegisterUserModel registerUserModel = new RegisterUserModel();
                registerUserModel.setId(myCursor.getInt(0));
                registerUserModel.setName(myCursor.getString(1));
                registerUserModel.setEmail(myCursor.getString(2));
                registerUserModel.setUtype(myCursor.getString(3) + " " + "Shed");
                registerUserModel.setType(myCursor.getString(4));
                data.add(registerUserModel);

            }while (myCursor.moveToNext());
        }
        return data;
    }

    /**
     * on update to update database varsion and the column values
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop the exiting table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //call to recreate a table
        onCreate(db);
    }
}