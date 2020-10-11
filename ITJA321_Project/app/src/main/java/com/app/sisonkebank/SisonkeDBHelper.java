// Author: Lomar Ungerer Conradie | DQ61ZP3G5 | ITJA321 | Project
package com.app.sisonkebank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

/**
 * This is the main implementation of the SisonkeDBHelper class and all its related functions. It extends as
 * a class of type SQLiteOpenHelper as specified in the project questions pdf.
 * It is important to note that almost all queries have been used with the Cursor implementation to allow
 * query result exposure on the SQLiteDatabase. This requirement has been mentioned in the project pdf as well.
 */
public class SisonkeDBHelper extends SQLiteOpenHelper {
    //These are the global variables used in the SisinkeDBHelper class and they are instantiated here
    public static final String USERS_TABLE = "users";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_SURNAME = "USER_SURNAME";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_USER_MOBILE = "USER_MOBILE";
    public static final String COLUMN_USER_GENDER = "USER_GENDER";
    public static final String COLUMN_USER_BALANCE = "USER_BALANCE";
    public static final String COLUMN_USER_SAVINGS = "USER_SAVINGS";

    public SisonkeDBHelper(@Nullable Context context) {
        super(context, "SisonkeDB", null, 1);
    }

    /**
     * This is the onCreate method that runs once the program starts. The code below instantiates a new table
     * called USERS_TABLE. All variables above are used to define the names of each column, the amount of
     * columns in the table and the type of data for each column. The query to create the table is contained
     * as a String called createTableSTMT.
     * @param DB - This is an instance of the SQLite database and is used as variable SQLiteDatabase to run
     *           the query that creates the table.
     */
    @Override
    public void onCreate(SQLiteDatabase DB) {
        String createTableSTMT = "CREATE TABLE " + USERS_TABLE + "" +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_SURNAME + " TEXT, "
                + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT, " + COLUMN_USER_MOBILE + " INT, " + COLUMN_USER_GENDER +
                " TEXT, " + COLUMN_USER_BALANCE + " INT, " + COLUMN_USER_SAVINGS + " INT)";
        DB.execSQL(createTableSTMT);
    }

    /**
     * This method called onUpgrade determines what the database should do in the event of it upgrading to a newer version.
     * @param DB - This is an instance variable of the SQLite database
     * @param oldVersion - This is an integer that defines the old database version
     * @param newVersion - This is an integer that defines the new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(DB);
    }

    /**
     * addUser is the first method in the SQLite Database. It returns a boolean value that determines whether the addition
     * and registration of a new user was successful. In this method an insert query is run to add the new user details to
     * the database table.
     * @param bankUser - bankUser is a variable of type BankUser and references the BankUser class to obtain the data from
     *                 the registration class.
     * @return - the data returned is a true or false boolean and the registration activity calls this method to check if the
     * registration of the new user was successful.
     */
    public boolean addUser(BankUser bankUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //get values for database from BankUser class getters
        cv.put(COLUMN_USER_NAME, bankUser.getName());
        cv.put(COLUMN_USER_SURNAME, bankUser.getSurname());
        cv.put(COLUMN_USER_EMAIL, bankUser.getEmail());
        cv.put(COLUMN_USER_PASSWORD, bankUser.getPassword());
        cv.put(COLUMN_USER_MOBILE, bankUser.getMobile());
        cv.put(COLUMN_USER_GENDER, bankUser.getGender());
        cv.put(COLUMN_USER_BALANCE, bankUser.getBalance());
        cv.put(COLUMN_USER_SAVINGS, bankUser.getSavings());
        //adding values to the database
        long insert = db.insert(USERS_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else {
            return true;
        }
    }

    /**
     * This is the checkEmail method that is used by the Registration activity to determine if the
     * email entered by the new user does not already exist in the database. It returns a boolean
     * variable called isTrue that allows the program to determine if the email already exists or not.
     * @param return_email - This is a variable of type string that obtains the email inserted by the
     *                     user in the Registration activity and allows the method to search for that
     *                     value.
     * @return - The return value is called isTrue and allows the program to identify if the inserted email
     * already exists.
     */
    public boolean checkEmail(String return_email){
        SQLiteDatabase db = getReadableDatabase();
        String selectEmail = "SELECT * FROM " + USERS_TABLE + " WHERE " + COLUMN_USER_EMAIL + " =?";
        Cursor cursor = db.rawQuery(selectEmail, new String[]{return_email});
        boolean isTrue = false;
        if(cursor.moveToFirst()){
            isTrue = true;
        }
        cursor.close();
        db.close();
        return isTrue;
    }

    /**
     * This method, authUserLogin, allows the database to determine if the Login credentials inserted
     * by the user are correct. It uses a select query with a where clause to determine if the email
     * and password match the database user data.
     * @param email - This string is obtained from the Login activity and allows the method to check
     *              the variable and see if the email is correct.
     * @param password - This string is obtained from the Login activity and allows the method to check
     *      *       the variable and see if the password is correct.
     * @return - This method returns a boolean called loginCheck and if it is true, then the user
     * is allow access due to their login credentials being correct. If false, then the user is denied
     * access because the login credentials provided are incorrect.
     */
    public boolean authUserLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String loginQuery = "SELECT * FROM " + USERS_TABLE + " WHERE " + COLUMN_USER_EMAIL + " = ? and " + COLUMN_USER_PASSWORD + " =?";
        Cursor cursor = db.rawQuery(loginQuery,new String[]{email, password});
        boolean loginCheck;
        if(cursor.getCount()>0){
            loginCheck = true;
        }else{
            loginCheck = false;
        }
        db.close();
        cursor.close();
        return loginCheck;
    }

    /**
     * This method is called getUserDetails and is used by the Home, ViewAccount and Transfer activities. The details of the user
     * that is currently logging in is checked by the database and the details are then put in a String[] of type BankUser.
     * @param email - The email of the user that has successfully logged in is stored as a String and used to determine
     *              which users' details need to be obtained and stored.
     * @return - This method returns a variable String[] of type BankUser which is then used to obtain the user data, such
     * as the users' name, surname and balance details.
     */
    public BankUser getUserDetails(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(USERS_TABLE,
                new String[] { COLUMN_ID, COLUMN_USER_NAME, COLUMN_USER_SURNAME, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD,
                        COLUMN_USER_MOBILE, COLUMN_USER_GENDER, COLUMN_USER_BALANCE, COLUMN_USER_SAVINGS },
                COLUMN_USER_EMAIL + "=?",
                 new String[] { String.valueOf(email) },
                null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        BankUser bankUser = new BankUser(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5),
                cursor.getString(6),
                cursor.getInt(7),
                cursor.getInt(8));
        cursor.close();
        db.close();
        return bankUser;
    }

    /**
     * The final method is called the updateBalance method. The transfer activity uses this method to update the details
     * of the user once a successful transaction takes place.
     * @param current - The current integer is used to determine the new current balance of the user
     * @param savings - The savings integer is used to determine the new savings balance of the user
     * @param email_return - The email_return String is used to determine the where clause for the update query
     * @return - This method returns a boolean which will allow the program to check if the update was successful.
     */
    public boolean updateBalance(int current, int savings, String email_return){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + USERS_TABLE + " SET " + COLUMN_USER_BALANCE + " = '" + current + "', " + COLUMN_USER_SAVINGS + " = '"
                + savings + "' WHERE " + COLUMN_USER_EMAIL + " = '" + email_return + "'");
        return true;
    }
}
