package tomislav.piskur.com.povrsinatrokuta.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

import tomislav.piskur.com.povrsinatrokuta.R;
import tomislav.piskur.com.povrsinatrokuta.Data.TasksContract;

@SuppressWarnings("ConstantConditions")
public class PovijestIzracunaFragment extends Fragment  {

    private SwipeRefreshLayout srlSwipe;
    private ListView lvResults;
    private SimpleCursorAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_povijest_izracuna, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidgets(view);
        initList();
        setupListeners();

    }

    private void setupListeners() {


        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        srlSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (adapter == null) {
                    initList();
                }
                srlSwipe.setRefreshing(false);
            }
        });


    }

    public void initWidgets(View view){

        srlSwipe = view.findViewById(R.id.srlSwipe);
        lvResults = view.findViewById(R.id.lvResults);

    }

    protected void displayReceivedData(String message)
    {
        String[] parts = message.split("x");
        String part1 = parts[0];
        String part2 = parts[1];
        String part3 = parts[2];
        String part4 = parts[3];

        ContentValues values = new ContentValues();
        values.put(TasksContract.Columns.TASKS_A, part1);
        values.put(TasksContract.Columns.TASKS_B, part2);
        values.put(TasksContract.Columns.TASKS_C, part3);
        values.put(TasksContract.Columns.TASKS_POVRSINA, part4);
        values.put(TasksContract.Columns.TASKS_DATUM, dateTime());
        getContext().getContentResolver().insert(TasksContract.CONTENT_URI, values);
        if (adapter != null) {
            refreshList();
        }


    }
    private void initList() {

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header, lvResults, false);
        lvResults.addHeaderView(header, null, false);

        String[] fromColumns = {TasksContract.Columns.TASKS_A, TasksContract.Columns.TASKS_B, TasksContract.Columns.TASKS_C, TasksContract.Columns.TASKS_POVRSINA, TasksContract.Columns.TASKS_DATUM};
        int[] toViews = {R.id.tvStranicaA, R.id.tvStranicaB, R.id.tvStranicaC, R.id.tvPovrsina, R.id.tvDatum};

        Cursor results = getActivity().getApplicationContext().getContentResolver().query(TasksContract.CONTENT_URI, null, null, null, null);
        if (results != null) {
            adapter = new SimpleCursorAdapter(getContext(), R.layout.list_view, results, fromColumns, toViews, 0);
            lvResults.setAdapter(adapter);
            results.close();
            refreshList();
        }
    }

    private void refreshList() {
        CursorLoader cursorLoader = new CursorLoader(getContext(), TasksContract.CONTENT_URI, null, null, null, "Povrsina+0 DESC");
        Cursor cursor = cursorLoader.loadInBackground();
        adapter.swapCursor(cursor);
    }
    private String dateTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("d-MM-YYYY HH:mm:ss", java.util.Locale.getDefault());
        Date d = new Date();
        return sdf.format(d);

    }
}
