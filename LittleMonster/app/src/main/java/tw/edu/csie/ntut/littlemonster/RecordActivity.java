package tw.edu.csie.ntut.littlemonster;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static tw.edu.csie.ntut.littlemonster.SplashActivity.bookKeeping;
import static tw.edu.csie.ntut.littlemonster.SplashActivity.type;

public class RecordActivity extends AppCompatActivity {

    private ImageView background;
    private ListView mList;
    private TextView monthTxt;
    private int year, month;
    private Time now = new Time();
    private ImageButton lastBtn, nextBtn;
    private Button detailBtn, performanceBtn;
    private Boolean isDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        isDetail = true;
//        background= (ImageView)findViewById(R.id.background);
//        background.setImageResource(R.drawable.background);
//        Glide.with(this).load(R.drawable.background).into(background);
        now.setToNow();
        lastBtn = (ImageButton) findViewById(R.id.lastButton);
        lastBtn.setOnClickListener(btnLastOnClick);
        nextBtn = (ImageButton) findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(btnNextOnClick);
        monthTxt = (TextView) findViewById(R.id.monthText);
        detailBtn = (Button) findViewById(R.id.detailButton);
        detailBtn.setOnClickListener(btnDetailOnClick);
        detailBtn.setEnabled(false);
        performanceBtn = (Button) findViewById(R.id.perfomanceButton);
        performanceBtn.setOnClickListener(btnPerpomanceOnClick);
        mList = (ListView)findViewById(R.id.listData);
        SetMonth(now.year, now.month + 1);
        SetDetailList();
        //setImg();
    }

    private void SetMonth(int setYear, int setMonth) {
        year = setYear;
        month = setMonth;
        monthTxt.setText(year + "\n" + (month) + "月");
    }

    private void SetDetailList() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("日期                      寵物           花費");
        for (int i = 0; i < bookKeeping.GetData().size(); i++) {
            if (bookKeeping.GetData().get(i).get(0) == year && bookKeeping.GetData().get(i).get(1) == month) {
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
                data){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(getResources().getColor(R.color.boardColor));

                return view;
            }
        };;

        mList.setAdapter(adapter);
    }

    private void SetPerfomanceList() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("寵物               總計");
        data.add(type[0] + "              $" + bookKeeping.GetTypeBalance(year, month, 0));
        for (int i = 1; i < 7; i++) {
            data.add(type[i] + "                  $" + bookKeeping.GetTypeBalance(year, month, i));
        }
        data.add("");
        data.add("收入 - 其他    $" + bookKeeping.GetBalance(year, month));
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(getResources().getColor(R.color.boardColor));

                return view;
            }
        };
        ;
        mList.setAdapter(adapter);
    }

    private View.OnClickListener btnLastOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (month == 1)
                SetMonth(year - 1, 12);
            else
                SetMonth(year, month - 1);
            if (isDetail)
                SetDetailList();
            else
                SetPerfomanceList();
        }
    };

    private View.OnClickListener btnNextOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (month == 12)
                SetMonth(year + 1, 1);
            else
                SetMonth(year, month + 1);
            if (isDetail)
                SetDetailList();
            else
                SetPerfomanceList();
        }
    };

    private View.OnClickListener btnDetailOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SetIsDetail(true);
        }
    };

    private View.OnClickListener btnPerpomanceOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SetIsDetail(false);
        }
    };

    private void SetIsDetail(boolean isD) {
        isDetail = isD;
        if (isD)
            SetDetailList();
        else
            SetPerfomanceList();
        detailBtn.setEnabled(!isDetail);
        performanceBtn.setEnabled(isDetail);
    }


//    private void setImg(){
//        background= (ImageView)findViewById(R.id.background);
//        background.setImageResource(R.drawable.background);
//        Glide.with(this).load(R.drawable.background).into(background);}
}
