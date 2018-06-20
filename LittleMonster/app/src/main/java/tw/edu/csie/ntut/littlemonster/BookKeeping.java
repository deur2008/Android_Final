package tw.edu.csie.ntut.littlemonster;

import android.text.format.Time;

import java.util.ArrayList;

public class BookKeeping {
    ArrayList<ArrayList<Integer>> data;
    private Time now = new Time();

    public BookKeeping() {
        data = new ArrayList<ArrayList<Integer>>();
        now.setToNow();
    }

    public void AddData(int year, int month, int day, int type, int amount) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(year);
        temp.add(month);
        temp.add(day);
        temp.add(type);
        temp.add(amount);
        data.add(temp);
    }

    public int GetBalance() {
        return GetBalance(now.year, now.month + 1);
    }

    public int GetBalance(int year, int month) {
        int balance = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).get(0) == year && data.get(i).get(1) == month && data.get(i).get(3) == 0)
                balance += data.get(i).get(4);
            else if (data.get(i).get(0) == year && data.get(i).get(1) == month)
                balance -= data.get(i).get(4);
        }
        return balance;
    }

    public int GetTypeBalance(int type) {
        return GetTypeBalance(now.year, now.month + 1, type);
    }

    public int GetTypeBalance(int year, int month, int type) {
        int balance = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).get(0) == year && data.get(i).get(1) == month && data.get(i).get(3) == type)
                balance += data.get(i).get(4);
        }
        return balance;
    }

    public ArrayList<ArrayList<Integer>> GetData() {
        return data;
    }
}
