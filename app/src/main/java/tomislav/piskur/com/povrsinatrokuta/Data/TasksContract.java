package tomislav.piskur.com.povrsinatrokuta.Data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by srs on 20.05.2018.
 */

public class TasksContract {
    static final String TABLE_NAME = "Tasks";

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String TASKS_A = "StranicaA";
        public static final String TASKS_B = "StranicaB";
        public static final String TASKS_C = "StranicaC";
        public static final String TASKS_POVRSINA = "Povrsina";
        public static final String TASKS_DATUM = "Datum";
        public static final String TASKS_ORDER = "Order";

        private Columns () {}

    }

    static final String CONTENT_AUTHORITY = "tomislav.piskur.com.probacontent";

    static private final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI,TABLE_NAME);

    static Uri buildTaskUri(long taskId) {                          // id je long a ne int
        return ContentUris.withAppendedId(CONTENT_URI,taskId);
    }

    static long getTaskId(Uri taskUri) {
        return ContentUris.parseId(taskUri);
    }

}