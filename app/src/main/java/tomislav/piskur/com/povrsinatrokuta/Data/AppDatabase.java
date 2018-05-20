package tomislav.piskur.com.povrsinatrokuta.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by srs on 20.05.2018.
 */

public class AppDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static AppDatabase instance = null;



    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + TasksContract.TABLE_NAME + " ("
                + TasksContract.Columns._ID + "  INTEGER PRIMARY KEY NOT NULL, "
                + TasksContract.Columns.TASKS_A + " TEXT NOT NULL, "
                + TasksContract.Columns.TASKS_B + " TEXT NOT NULL,"
                + TasksContract.Columns.TASKS_C + " TEXT NOT NULL, "
                + TasksContract.Columns.TASKS_POVRSINA + " TEXT NOT NULL, "
                + TasksContract.Columns.TASKS_DATUM + " TEXT NOT NULL);"

                + TasksContract.Columns.TASKS_ORDER + " INTEGER"
                + " ); ";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                // upgrade logic 1
                break;
            case 2:
                // upgrade logic 2
                break;
            default:
                throw new IllegalStateException("Problems with db on upgrade");
        }
    }

}