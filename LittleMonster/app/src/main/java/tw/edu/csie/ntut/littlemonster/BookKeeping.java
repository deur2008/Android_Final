package tw.edu.csie.ntut.littlemonster;

import java.util.ArrayList;

public class BookKeeping {
    ArrayList<ArrayList<Integer>> data;

    public BookKeeping() {
        data = new ArrayList<ArrayList<Integer>>();
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
        int balance = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).get(3) == 0)
                balance += data.get(i).get(3);
            else
                balance -= data.get(i).get(3);
        }
        return balance;
    }

    public int GetTypeBalance(int type) {
        int balance = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).get(3) == type)
                balance += data.get(i).get(4);
        }
        return balance;
    }

    public ArrayList<ArrayList<Integer>> GetData() {
        return data;
    }
}
