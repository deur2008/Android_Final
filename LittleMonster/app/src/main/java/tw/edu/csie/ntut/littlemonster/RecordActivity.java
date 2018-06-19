package tw.edu.csie.ntut.littlemonster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static tw.edu.csie.ntut.littlemonster.MainActivity.bookKeeping;
import static tw.edu.csie.ntut.littlemonster.MainActivity.type;

public class RecordActivity extends AppCompatActivity {

    private ListView mList;
    private TextView monthTxt;
    private int year, month;
    private Time now = new Time();
    private ImageButton lastBtn, nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        now.setToNow();
        lastBtn = (ImageButton) findViewById(R.id.lastButton);
        lastBtn.setOnClickListener(btnLastOnClick);
        nextBtn = (ImageButton) findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(btnNextOnClick);
        monthTxt = (TextView) findViewById(R.id.monthText);
        mList = (ListView)findViewById(R.id.listData);
        SetMonth(now.year, now.month);
        SetList();
    }

    private void SetMonth(int setYear, int setMonth) {
        year = setYear;
        month = setMonth;
        monthTxt.setText(year + "\n" + (month + 1) + "月");
    }

    private void SetList() {
        ArrayList<String> data = new ArrayList<String>();
        for (int i = 0; i < bookKeeping.GetData().size(); i++) {
            if (bookKeeping.GetData().get(i).get(0) == year && bookKeeping.GetData().get(i).get(1) == month + 1) {
                String date = bookKeeping.GetData().get(i).get(0).toString();
                if (bookKeeping.GetData().get(i).get(1) < 10) {
                    date += "/0" + bookKeeping.GetData().get(i).get(1);
                } else {
                    date += "/" + bookKeeping.GetData().get(i).get(1);
                }
                if (bookKeeping.GetData().get(i).get(2) < 10) {
                    date += "/0" + bookKeeping.GetData().get(i).get(2);
                } else {
                    date += "/" + bookKeeping.GetData().get(i).get(2);
                }
                if (bookKeeping.GetData().get(i).get(3) == 0)
                    data.add(date + "          " + type[bookKeeping.GetData().get(i).get(3)] + "          $" + bookKeeping.GetData().get(i).get(4));
                else
                    data.add(date + "          " + type[bookKeeping.GetData().get(i).get(3)] + "             $" + bookKeeping.GetData().get(i).get(4));
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data);
        mList.setAdapter(adapter);
    }

    private View.OnClickListener btnLastOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            month--;
            if (month <= 0) {
                month = 12;
                year--;
            }
            monthTxt.setText(year + "\n" + (month + 1) + "月");
            SetList();
        }
    };

    private View.OnClickListener btnNextOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            month++;
            if (month >= 13) {
                month = 1;
                year++;
            }
            monthTxt.setText(year + "\n" + (month + 1) + "月");
            SetList();
        }
    };
}
