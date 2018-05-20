package tomislav.piskur.com.povrsinatrokuta.Data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by srs on 20.05.2018.
 */

public class AppProvider extends ContentProvider{
    private AppDatabase openHelper;


    static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "."
            + TasksContract.CONTENT_AUTHORITY + "."
            + TasksContract.TABLE_NAME;


    static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "."
            + TasksContract.CONTENT_AUTHORITY + "."
            + TasksContract.TABLE_NAME;

    private static final int TASKS = 100;
    private static final int TASK_ID = 101;



    private static UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TasksContract.CONTENT_AUTHORITY, TasksContract.TABLE_NAME, TASKS);


        uriMatcher.addURI(TasksContract.CONTENT_AUTHORITY, TasksContract.TABLE_NAME + "/#", TASK_ID);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        openHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {


        final int match = uriMatcher.match(uri);

        switch (match) {
            case TASKS:
                return CONTENT_TYPE;
            case TASK_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final int match = uriMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case TASKS:
                queryBuilder.setTables(TasksContract.TABLE_NAME);
                break;
            case TASK_ID:
                queryBuilder.setTables(TasksContract.TABLE_NAME);
                long taskId = TasksContract.getTaskId(uri);
                queryBuilder.appendWhere(TasksContract.Columns._ID + " = " + taskId);
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }

        SQLiteDatabase database = openHelper.getReadableDatabase();

        return queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = uriMatcher.match(uri);
        final SQLiteDatabase database;
        Uri returningUri;
        long recordId;

        switch (match) {
            case TASKS:
                database = openHelper.getWritableDatabase();
                recordId = database.insert(TasksContract.TABLE_NAME, null, contentValues);         // vraća broj uspješno upisanih rekorda
                if (recordId > 0) {
                    returningUri = TasksContract.buildTaskUri(recordId);
                } else {
                    throw new SQLException();
                }
                break;

            default:
                throw new IllegalArgumentException("Wrong URI" + uri);
        }
        return returningUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = uriMatcher.match(uri);
        final SQLiteDatabase database;
        int count;
        String selectionCriteria;

        switch (match) {
            case TASKS:
                database = openHelper.getWritableDatabase();
                count = database.delete(TasksContract.TABLE_NAME, selection, selectionArgs);
                break;
            case TASK_ID:
                database = openHelper.getWritableDatabase();
                long id = TasksContract.getTaskId(uri);
                selectionCriteria = TasksContract.Columns._ID + " = " + id;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = database.delete(TasksContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI" + uri);
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = uriMatcher.match(uri);
        final SQLiteDatabase database;
        int count;
        String selectionCriteria;

        switch (match) {
            case TASKS:
                database = openHelper.getWritableDatabase();
                count = database.update(TasksContract.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case TASK_ID:
                database = openHelper.getWritableDatabase();
                long id = TasksContract.getTaskId(uri);
                selectionCriteria = TasksContract.Columns._ID + " = " + id;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = database.update(TasksContract.TABLE_NAME, contentValues, selectionCriteria, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Wrong Uri" + uri);
        }
        return count;
    }
}