package chienphamk58.ailatrieuphu.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by pdc on 26/04/2016.
 */
public class DatabaseOpenHelper extends SQLiteAssetHelper{

    private static final String DATABASE_NAME = "question";
    private static final int DATABASE_VERSION = 1;
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, null, DATABASE_VERSION);
    }
}
