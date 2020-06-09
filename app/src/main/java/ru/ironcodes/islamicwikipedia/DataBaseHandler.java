package ru.ironcodes.islamicwikipedia;

/**
 *All Copy Rights Reserved by IronCodes 15/11/2018
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import static ru.ironcodes.islamicwikipedia.MyApplication.getInstance;
public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version

    static Context myContext;
    private static DataBaseHandler instance ;


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;

    }

    private static final int DATABASE_VERSION = 5;

    // Database Name

    public   static   String DATABASE_NAME =  getInstance().getResources().getString(R.string.db_source) ;
    public static final String DB_PATH_SUFFIX = "/databases/";
    // Azkars table name
    private static final String TABLE_AZKAR = "azkartable";

    // Azkars Table Columns names
    private static final String KEY_ID = "_id";





    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = getDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(myContext.getApplicationInfo().dataDir
                + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public static String getDatabasePath() {


        return myContext.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public void openDataBase() throws SQLException {
        File dbFile = myContext.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

    }





    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {


    }






    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DataBaseHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        try {
            CopyDataBaseFromAsset();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.w(DataBaseHandler.class.getName(), "Data base is upgraded  ");

    }

    // Getting single Azkar
    public Azkar getAzkar (int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db
                .rawQuery(
                        "SELECT azkartable._id, azkartable.subcat_name, azkartable.azkarfield, azkartable.category_name, subcategory.file_name,fav FROM azkartable,subcategory WHERE subcategory.name = azkartable.subcat_name AND "
                                + KEY_ID + "= " + id, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {

                Azkar azkar = new Azkar(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),cursor.getString(5));
                return azkar;
            }
        } finally {
            cursor.close();
            db.close();
        }
        // return Azkar
        return null;

    }

    // Getting All SubCat
    public List<Azkar> getAllSubCat(CharSequence value) {
        List<Azkar> subcatList = new ArrayList<Azkar>();
        // Select All Query
        String selectQuery = "SELECT name,file_name, COUNT(subcat_name ) AS count FROM subcategory LEFT JOIN azkartable ON name = subcat_name WHERE name LIKE '%"
                + value + "%'  GROUP BY name ORDER BY  name ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Azkar subcat = new Azkar();

                    subcat.setName(cursor.getString(0));

                    subcat.setFileName(cursor.getString(1));
                    subcat.setCount(cursor.getString(2));
                    subcatList.add(subcat);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        // return azkar list
        return subcatList;

    }

    // Getting All Categories
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT name, file_name, COUNT(subcat_name ) AS count FROM category LEFT JOIN azkartable ON name = category_name  GROUP BY name ORDER BY  name ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category();
                    category.setName(cursor.getString(0));
                    category.setFileName(cursor.getString(1));
                    category.setCount(cursor.getString(2));
                    categoryList.add(category);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        return categoryList;

    }

    // Getting All Azkar
    public List<Azkar> getAllAzkar(String limit) {
        List<Azkar> azkarList = new ArrayList<Azkar>();
        // Select All Query
        String selectQuery = "SELECT azkartable._id, azkartable.subcat_name, azkartable.azkarfield, azkartable.category_name,fav, subcategory.file_name  FROM azkartable,subcategory where subcategory.name = azkartable.subcat_name ORDER BY azkartable.azkarfield "+limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    Azkar azkar = new Azkar();

                    azkar.setID(Integer.parseInt(cursor.getString(0)));
                    azkar.setName(cursor.getString(1));
                    azkar.setAzkar(cursor.getString(2));
                    azkar.setCategory(cursor.getString(3));
                    // azkar.setImage(cursor.getBlob(4));
                    azkar.setFav(cursor.getString(4));
                    azkar.setFileName(cursor.getString(5));
                    azkarList.add(azkar);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        // return azkar list
        return azkarList;

    }



    // Getting Favorites
    public List<Azkar> getFavorites() {
        List<Azkar> azkarList = new ArrayList<Azkar>();
        // Select All Query
        String selectQuery = "SELECT azkartable._id, azkartable.subcat_name, azkartable.azkarfield, azkartable.category_name,fav, subcategory.file_name  FROM azkartable,subcategory where subcategory.name = azkartable.subcat_name AND fav ='1'  ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    Azkar azkar = new Azkar();
                    azkar.setID(Integer.parseInt(cursor.getString(0)));
                    azkar.setName(cursor.getString(1));
                    azkar.setAzkar(cursor.getString(2));
                    // azkar.setImage(cursor.getBlob(4));
                    azkar.setCategory(cursor.getString(3));
                    azkar.setFav(cursor.getString(4));
                    azkar.setFileName(cursor.getString(5));
                    azkarList.add(azkar);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        // close inserting data from database

        return azkarList;

    }

    // Getting Azkars By Category
    public List<Azkar> getAzkarByCategory(String value) {
        List<Azkar> azkarList = new ArrayList<Azkar>();
        // Select All Query
        String selectQuery = "SELECT azkartable._id, azkartable.subcat_name, azkartable.azkarfield, azkartable.category_name,fav, subcategory.file_name  FROM azkartable,subcategory where subcategory.name = azkartable.subcat_name AND category_name = '"
                + value + "' ORDER BY azkartable.azkarfield ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    Azkar azkar = new Azkar();
                    azkar.setID(Integer.parseInt(cursor.getString(0)));
                    azkar.setName(cursor.getString(1));
                    azkar.setAzkar(cursor.getString(2));
                    // azkar.setImage(cursor.getBlob(4));
                    azkar.setCategory(cursor.getString(3));
                    azkar.setFav(cursor.getString(4));
                    azkar.setFileName(cursor.getString(5));
                    azkarList.add(azkar);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        // close inserting data from database

        return azkarList;

    }

    // Getting Azkar By SubCat
    public List<Azkar> getAzkarBySubcat(String value) {
        List<Azkar> azkarList = new ArrayList<Azkar>();
        // Select All Query
        String selectQuery = "SELECT azkartable._id, azkartable.subcat_name, azkartable.azkarfield, azkartable.category_name,fav, subcategory.file_name  FROM azkartable,subcategory where subcategory.name = azkartable.subcat_name AND name= '"
                + value + "' ORDER BY subcat_name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    Azkar azkar = new Azkar();
                    azkar.setID(Integer.parseInt(cursor.getString(0)));
                    azkar.setName(cursor.getString(1));
                    azkar.setAzkar(cursor.getString(2));
                    // azkar.setImage(cursor.getBlob(4));
                    azkar.setCategory(cursor.getString(3));
                    azkar.setFav(cursor.getString(4));
                    azkar.setFileName(cursor.getString(5));
                    azkarList.add(azkar);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        // close inserting data from database

        return azkarList;

    }

    // Updating single azkar

    public int updateAzkar(Azkar azkar) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("_id", azkar.getID());
        values.put("subcat_name", azkar.getName());
        values.put("azkarfield", azkar.getAzkar());
        values.put("category_name", azkar.getCategory());
        values.put("fav", azkar.getFav());
        // updating row
        return db.update(TABLE_AZKAR, values, KEY_ID + " = ?",
                new String[] { String.valueOf(azkar.getID()) });


    }



    public void  refresh (){

        DataBaseHandler newdb = new DataBaseHandler(myContext);
        newdb.openDataBase();
    }

    public void recreate () {
        this.onCreate(null);
        instance = new DataBaseHandler(myContext);
        instance.openDataBase() ;
        instance.refresh();
    }









    public int getLastInsertId() {
        SQLiteDatabase db = this.getReadableDatabase();
        int index = 0;
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_AZKAR, null);

        if(cursor.moveToLast()){
            //name = cursor.getString(column_index);//to get other values
            index = cursor.getInt(0);//to get id, 0 is the column index
        }
//        if (cursor.moveToFirst()) {
//            index = cursor.getInt(cursor.getColumnIndex("_id"));
//        }
        cursor.close();
        return index;
    }





}
