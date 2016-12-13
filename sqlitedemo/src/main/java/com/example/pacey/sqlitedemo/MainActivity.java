package com.example.pacey.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = openOrCreateDatabase("user.db", MODE_PRIVATE, null);
        db.execSQL("create table if not exists usertb (_id integer primary key autoincrement,name text not null,address text,telephone text)");
//        db.execSQL("insert into usertb(name,address,telephone) values ('张三','南昌','1234567')");
//        db.execSQL("insert into usertb(name,address,telephone) values ('李四','宜春','12345678')");
//        db.execSQL("insert into usertb(name,address,telephone) values ('王五','九江','123456789')");
        db.close();
    }

    public void listDb(View view) {
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arr_adapter;
        SimpleAdapter simp_adapter;
        List<Map<String, Object>> datalist;
        datalist = new ArrayList<Map<String, Object>>();
        String[] arr_data = new String[100];
        db = openOrCreateDatabase("user.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select * from usertb", null);
        if (cursor != null) {
            int i = 0;
            while (cursor.moveToNext()) {
                Log.i("info", "_id:" + cursor.getInt(cursor.getColumnIndex("_id")));
                Log.i("info", "name:" + cursor.getString(cursor.getColumnIndex("name")));
                Log.i("info", "address:" + cursor.getString(cursor.getColumnIndex("address")));
                Log.i("info", "telephone:" + cursor.getString(cursor.getColumnIndex("telephone")));
//                arr_data[i++] = cursor.getInt(cursor.getColumnIndex("_id"))+" "+cursor.getString(cursor.getColumnIndex("name"))+" "+cursor.getString(cursor.getColumnIndex("address"))+" "+cursor.getString(cursor.getColumnIndex("telephone"));
                String info = cursor.getInt(cursor.getColumnIndex("_id")) + " " + cursor.getString(cursor.getColumnIndex("name")) + " " + cursor.getString(cursor.getColumnIndex("address")) + " " + cursor.getString(cursor.getColumnIndex("telephone"));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("string", info);
                datalist.add(map);
            }
            cursor.close();
        }
        simp_adapter = new SimpleAdapter(this, datalist, R.layout.item, new String[]{"string"}, new int[]{R.id.textView});
//        String[]arr_data2 = {"1","2","3","4","5","6","7","8"};
//        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_data);
//        listView.setAdapter(arr_adapter);
        listView.setAdapter(simp_adapter);
        db.close();
    }

    public void onClick(View view) {
        ContentValues contentValues = new ContentValues();
        EditText idEditText = (EditText) findViewById(R.id.idEditText);
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText addressEditText = (EditText) findViewById(R.id.addressEditText);
        EditText telephoneEditText = (EditText) findViewById(R.id.telephoneEditText);
        switch (view.getId()) {
            case R.id.insertButton:
                db = openOrCreateDatabase("user.db", MODE_PRIVATE, null);
                contentValues.clear();
//                db.execSQL("insert into usertb(name,address,telephone) values ('" + nameEditText.getText().toString() + "','" + addressEditText.getText().toString() + "','" + telephoneEditText.getText().toString() + "')");
                //db.execSQL("insert into usertb(name,address,telephone) values ('赵六','九江','123456789')");
                contentValues.put("_id",idEditText.getText().toString());
                contentValues.put("name", nameEditText.getText().toString());
                contentValues.put("address", addressEditText.getText().toString());
                contentValues.put("telephone", telephoneEditText.getText().toString());
                long result = db.insert("usertb", null, contentValues);
                if (result > 0) {
                    Toast.makeText(MainActivity.this, "插入数据成功！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "插入数据失败！", Toast.LENGTH_LONG).show();
                }
                db.close();
                break;
            case R.id.changeButton:
                db = openOrCreateDatabase("user.db", MODE_PRIVATE, null);
                contentValues.clear();
//                db.execSQL("update usertb set name='" + nameEditText.getText().toString() + "',address='" + addressEditText.getText().toString() + "',telephone='" + telephoneEditText.getText().toString() + "' where _id=" + idEditText.getText().toString());
//                db.execSQL("update usertb set name='pacey',address='nc',telephone='321' where _id = 1");
                contentValues.put("name", nameEditText.getText().toString());
                contentValues.put("address", addressEditText.getText().toString());
                contentValues.put("telephone", telephoneEditText.getText().toString());
                int count = db.update("usertb", contentValues, "_id = ?", new String[]{idEditText.getText().toString()});
                if (count > 0) {
                    Toast.makeText(MainActivity.this, "修改数据成功！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "修改数据失败！", Toast.LENGTH_LONG).show();
                }
                db.close();
                break;
            case R.id.deleteButton:
                db = openOrCreateDatabase("user.db", MODE_PRIVATE, null);
                int count2 = db.delete("usertb", "_id = " + idEditText.getText().toString(), null);
                if (count2 > 0) {
                    Toast.makeText(MainActivity.this, "删除数据成功！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "删除数据失败！", Toast.LENGTH_LONG).show();
                }
                db.close();
                break;
            default:

        }
    }
}
