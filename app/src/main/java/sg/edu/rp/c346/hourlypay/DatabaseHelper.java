package sg.edu.rp.c346.hourlypay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users_data";

    private static final String COL1 = "ID";
    private static final String COL2 = "description";
    private static final String COL3 = "date";
    private static final String COL4 = "starttime";
    private static final String COL5 = "endtime";
    private static final String COL6 = "breaks";
    private static final String COL7 = "hour";
    private static final String COL8 = "totalpay";



     public DatabaseHelper(Context context) {
                 super(context, DATABASE_NAME, null, 1);
             }


             @Override
     public void onCreate(SQLiteDatabase db) {
                 String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                 " description TEXT, date TEXT ,starttime TEXT ,endtime TEXT ,breaks TEXT,hour TEXT,totalpay TEXT)";
                 db.execSQL(createTable);
             }


             @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                 db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                 onCreate(db);
             }


             public boolean addData(String tdesc,String tdate , String tstarttime, String tendtime , String tbreaks , String thour, String ttotalpay) {
                 SQLiteDatabase db = this.getWritableDatabase();
                 ContentValues contentValues = new ContentValues();
                 contentValues.put(COL2, tdesc);
                 contentValues.put(COL3, tdate);
                 contentValues.put(COL4, tstarttime);
                 contentValues.put(COL5, tendtime);
                 contentValues.put(COL6, tbreaks);
                 contentValues.put(COL7, thour);
                 contentValues.put(COL8 , ttotalpay);




                 long result = db.insert(TABLE_NAME, null, contentValues);


                 //if date as inserted incorrectly it will return -1
                 if (result == -1) {
                         return false;
                     } else {
                         return true;
                     }
             }


             //query for 1 week repeats
             public Cursor getListContents() {
                 SQLiteDatabase db = this.getWritableDatabase();
                 Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
                 return data;
             }
    public void deleteItem(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = new String[] { name };
        db.delete(TABLE_NAME, "description"+ "=?", whereArgs);
        db.close();
    }
    public void deleteItembyid(long pos)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL1 + " = ?",new String[]{Long.toString(pos)} );
        db.close();
    }
    public void deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +  TABLE_NAME ); //delete all rows in a table
    }

}
