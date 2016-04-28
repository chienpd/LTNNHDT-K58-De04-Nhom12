package chienphamk58.ailatrieuphu.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by pdc on 26/04/2016.
 */
public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    public List<String> list1, list2, list3, list4, list5, list6;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null)
            instance = new DatabaseAccess(context);
        return instance;
    }

    public void open(){
        this.database = openHelper.getWritableDatabase();
    }

    public void close(){
        if(database != null)
            this.database.close();
    }

    public void getData(){
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Question",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            list1.add(cursor.getString(0));
            list2.add(cursor.getString(3));
            list3.add(cursor.getString(4));
            list4.add(cursor.getString(5));
            list5.add(cursor.getString(6));
            list6.add(cursor.getString(7));
            cursor.moveToNext();
        }
        cursor.close();
    }
}
