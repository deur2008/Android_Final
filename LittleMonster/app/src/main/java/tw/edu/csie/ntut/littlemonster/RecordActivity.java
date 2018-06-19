package tw.edu.csie.ntut.littlemonster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static tw.edu.csie.ntut.littlemonster.MainActivity.bookKeeping;
import static tw.edu.csie.ntut.littlemonster.MainActivity.type;

public class RecordActivity extends AppCompatActivity {

    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        mList = (ListView)findViewById(R.id.listData);
        ArrayList<String> data = new ArrayList<String>();
        for (int i = 0; i < bookKeeping.GetData().size(); i++) {
            data.add(bookKeeping.GetData().get(i).get(0) + "/" + bookKeeping.GetData().get(i).get(1)  + "/" + bookKeeping.GetData().get(i).get(2) + "          " + type[bookKeeping.GetData().get(i).get(3)] + "          " + bookKeeping.GetData().get(i).get(4));
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data);
        mList.setAdapter(adapter);
    }
}
