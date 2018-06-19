package tw.edu.csie.ntut.littlemonster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static tw.edu.csie.ntut.littlemonster.MainActivity.bookKeeping;

public class RecordActivity extends AppCompatActivity {

    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        mList = (ListView)findViewById(R.id.listData);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> data = bookKeeping.GetData().get(0);
//        ArrayList<String> data = bundle.getStringArrayList("0");
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data);
        mList.setAdapter(adapter);
    }
}
