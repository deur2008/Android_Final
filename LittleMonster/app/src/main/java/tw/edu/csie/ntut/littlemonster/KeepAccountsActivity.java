package tw.edu.csie.ntut.littlemonster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static tw.edu.csie.ntut.littlemonster.MainActivity.bookKeeping;
import static tw.edu.csie.ntut.littlemonster.MainActivity.type;


public class KeepAccountsActivity extends AppCompatActivity {

    private ImageButton foodBtn, clothesBtn, houseBtn, moveBtn, educationBtn, amusementBtn, incomeBtn;
    private ImageView background;
    private TextView feedTxt;
    private Spinner yearSpn, monthSpn, daySpn;
    private EditText amountEdit;
    private Button confirmBtn;
    private int feedType;
//    private BookKeeping bookKeeping;
    private Time now = new Time();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keepaccounts);
        now.setToNow();

//        bookKeeping = new BookKeeping();
        foodBtn = (ImageButton) findViewById(R.id.foodButton);
        foodBtn.setOnClickListener(btnFoodOnClick);
        clothesBtn = (ImageButton) findViewById(R.id.clothesButton);
        clothesBtn.setOnClickListener(btnClothesOnClick);
        houseBtn = (ImageButton) findViewById(R.id.houseButton);
        houseBtn.setOnClickListener(btnHouseOnClick);
        moveBtn = (ImageButton) findViewById(R.id.moveButton);
        moveBtn.setOnClickListener(btnMoveOnClick);
        educationBtn = (ImageButton) findViewById(R.id.educationButton);
        educationBtn.setOnClickListener(btnEducationOnClick);
        amusementBtn = (ImageButton) findViewById(R.id.amusementButton);
        amusementBtn.setOnClickListener(btnAmusementOnClick);
        incomeBtn = (ImageButton) findViewById(R.id.incomeButton);
        incomeBtn.setOnClickListener(btnIncomeOnClick);
        feedTxt = (TextView) findViewById(R.id.feedTextView);
        yearSpn = (Spinner) findViewById(R.id.yearSpinner);
        monthSpn = (Spinner) findViewById(R.id.monthSpinner);
        daySpn = (Spinner) findViewById(R.id.dateSpinner);
        amountEdit = (EditText) findViewById(R.id.amountEditText);
        confirmBtn = (Button) findViewById(R.id.confirmButton);
        confirmBtn.setOnClickListener(btnConfirmOnClick);
        setImg();
        setDates(31);
        String[] years = new String[30];
        for (int i = 0; i < 30; i++) {
            years[i] = Integer.toString(2018 - i);
        }
        final String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            months[i] = Integer.toString(i + 1);
        }
        ArrayAdapter<String> aaYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, years);
        yearSpn.setAdapter(aaYear);
        ArrayAdapter<String> aaMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, months);
        monthSpn.setAdapter(aaMonth);
        yearSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (monthSpn.getSelectedItem() == "2") {
                    if (Integer.parseInt(yearSpn.getSelectedItem().toString()) % 4 == 0) {
                        setDates(29);
                    }
                    else {
                        setDates(28);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int m = Integer.parseInt(monthSpn.getSelectedItem().toString());
                if ((m <= 7 && m % 2 == 1) || (m >= 8 && m % 2 == 0)) {
                    setDates(31);
                }
                else {
                    if (m != 2) {
                        setDates(30);
                    }
                    else {
                        if (Integer.parseInt(yearSpn.getSelectedItem().toString()) % 4 == 0) {
                            setDates(29);
                        }
                        else {
                            setDates(28);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        yearSpn.setSelection(2018 - now.year);
        monthSpn.setSelection(now.month);
    }

    private View.OnClickListener btnFoodOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            SetType(1);
        }
    };

    private View.OnClickListener btnClothesOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            SetType(2);
        }
    };

    private View.OnClickListener btnHouseOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            SetType(3);
        }
    };

    private View.OnClickListener btnMoveOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            SetType(4);
        }
    };

    private View.OnClickListener btnEducationOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            SetType(5);
        }
    };

    private View.OnClickListener btnAmusementOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            SetType(6);
        }
    };

    private View.OnClickListener btnIncomeOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            SetType(0);
        }
    };

    private void SetType(int setType) {
        feedType = setType;
        feedTxt.setText("餵食寵物: " + type[setType]);
    }

    private View.OnClickListener btnConfirmOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            int year = Integer.parseInt(yearSpn.getSelectedItem().toString());
            int month = Integer.parseInt(monthSpn.getSelectedItem().toString());
            int day = Integer.parseInt(daySpn.getSelectedItem().toString());
            int amount = Integer.parseInt(amountEdit.getText().toString());
            if (amount <= 0) {
                Toast.makeText(KeepAccountsActivity.this, "金額輸入錯誤!!", Toast.LENGTH_LONG).show();
            }
            else {
                bookKeeping.AddData(year, month, day, feedType, amount);
                amountEdit.setText("");
            }
        }
    };

    private void setDates(int max) {
        String[] dates = new String[max];
        for (int i = 0; i < max; i++) {
            dates[i] = Integer.toString(i + 1);
        }
        ArrayAdapter<String> aaDate = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dates);
        daySpn.setAdapter(aaDate);
        if (max >= now.monthDay) {
            daySpn.setSelection(now.monthDay - 1);
        }
    }

    private void setImg(){
        background= (ImageView)findViewById(R.id.background);
        background.setImageResource(R.drawable.bg);
        Glide.with(this).load(R.drawable.bg).into(background);

        foodBtn.setImageResource(R.drawable.slime01);
        Glide.with(this).load(R.drawable.slime01).into(foodBtn);

        clothesBtn.setImageResource(R.drawable.slime02);
        Glide.with(this).load(R.drawable.slime02).into(clothesBtn);

        houseBtn.setImageResource(R.drawable.slime03);
        Glide.with(this).load(R.drawable.slime03).into(houseBtn);

        moveBtn.setImageResource(R.drawable.slime04);
        Glide.with(this).load(R.drawable.slime04).into(moveBtn);

        educationBtn.setImageResource(R.drawable.slime05);
        Glide.with(this).load(R.drawable.slime05).into(educationBtn);

        amusementBtn.setImageResource(R.drawable.slime06);
        Glide.with(this).load(R.drawable.slime06).into(amusementBtn);

        incomeBtn.setImageResource(R.drawable.slime07);
        Glide.with(this).load(R.drawable.slime07).into(incomeBtn);
    }

}
