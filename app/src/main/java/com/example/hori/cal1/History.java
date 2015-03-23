package com.example.hori.cal1;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class History extends Activity {

    private static final int CONTEXT_MENU_ID_1 = 0 ;
    private SQLiteDatabase historyDB;

    static ArrayAdapter<String> adapter;

    private ListView listView;
    static List<Task> taskList = new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SQLiteOpenHelper helper = new HistorySQLiteOpenHelper(getApplicationContext());
        historyDB = helper.getReadableDatabase();

        String[] from = new String[]{"formula"};
        int[] to = new int[]{R.id.formula};

        final Cursor cursor = historyDB.query(
                HistorySQLiteOpenHelper.HISTORY_TABLE,
                new String[]{"_id","formula"}, null, null, null, null, "_id DESC");

        SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.row_history,
                        cursor,
                        from,
                        to,
                        0
                );

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(cursorAdapter);

        //コンテキストメニュー使用
        registerForContextMenu(listView);

        loadTask();

    }

    //taskListにDB情報を格納
    protected void loadTask(){
        taskList.clear();

        Cursor c = historyDB.query(
                HistorySQLiteOpenHelper.HISTORY_TABLE,
                new String[]{"_id","formula"}, null, null, null, null, "_id DESC"
        );

        startManagingCursor(c);

        if(c.moveToFirst()){
            do {
                Task task = new Task(
                        c.getInt(c.getColumnIndex("_id")),
                        c.getString(c.getColumnIndex("formula"))
                );
                taskList.add(task);
            } while (c.moveToNext());
        }

        stopManagingCursor(c);

        /*
        Adapterを使っていないためAdapter関連のメソッドは使用せず
         */
    }


    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderTitle("コンテキストメニュー");

        menu.add(0, CONTEXT_MENU_ID_1, 0, "削除");

        loadTask();

    }

    //コンテキストメニューの操作
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        Task task = taskList.get(info.position);

        int taskId = task.getId();


        String str = "_id=" + Integer.toString(taskId);
        //削除押したらDBから消したい
        switch (item.getItemId()) {
            case CONTEXT_MENU_ID_1:
                //選ばれた場所を指定して削除
                historyDB.delete("history", str, null);
                loadTask();


                String[] from = new String[]{"formula"};
                int[] to = new int[]{R.id.formula};

                Cursor cursor = historyDB.query(
                        HistorySQLiteOpenHelper.HISTORY_TABLE,
                        new String[]{"_id","formula"}, null, null, null, null, "_id DESC");

                SimpleCursorAdapter cursorAdapter =
                        new SimpleCursorAdapter(
                                this,
                                R.layout.row_history,
                                cursor,
                                from,
                                to,
                                0
                        );

                ListView listView = (ListView)findViewById(R.id.listView);
                listView.setAdapter(cursorAdapter);

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickBackButton(View view) {

        switch (view.getId()) {
            case R.id.button_back:
                Intent intent = new Intent(this, MyActivity.class);
                finish();
                break;
        }
    }

    public void onClickDeleteButton(View view){

        SQLiteOpenHelper helper = new HistorySQLiteOpenHelper(getApplicationContext());
        historyDB = helper.getWritableDatabase();

        historyDB.delete("history", null, null);

        String[] from = new String[]{"formula"};
        int[] to = new int[]{R.id.formula};

        Cursor cursor = historyDB.query(
                HistorySQLiteOpenHelper.HISTORY_TABLE,
                new String[]{"_id","formula"}, null, null, null, null, "_id DESC");

        SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.row_history,
                        cursor,
                        from,
                        to,
                        0
                );

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(cursorAdapter);

    }

}
