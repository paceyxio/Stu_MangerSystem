package com.example.pacey.stu_mangersystem;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import utils.Constant;
import utils.DbManger;
import utils.MySqliteHelper;

public class MainActivity extends AppCompatActivity {
    private MySqliteHelper helper;

    //ListView listView = (ListView) findViewById(R.id.listView);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = DbManger.getIntance(this);

    }

    /*
    * 点击按钮创建数据库
    * */
    public void createDb(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
    }

    public void onClick(View view) {
//        EditText idEditText = (EditText) findViewById(R.id.idEditText);
//        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
//        EditText addressEditText = (EditText) findViewById(R.id.addressEditText);
//        EditText telephoneEditText = (EditText) findViewById(R.id.telephoneEditText);
        switch (view.getId()) {
            case R.id.insertButton:
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Constant._ID,1);
                values.put(Constant.NAME, "'张三'");
                values.put(Constant.ADDRESS, "'南昌'");
                values.put(Constant.TELEPHONE, "'123456'");
                long result = db.insert(Constant.TABLE_NAME, null, values);
                if (result > 0) {
                    Toast.makeText(MainActivity.this, "插入数据成功！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,"插入数据失败！",Toast.LENGTH_LONG).show();
                }
                db.close();
                break;
        }
    }
}
