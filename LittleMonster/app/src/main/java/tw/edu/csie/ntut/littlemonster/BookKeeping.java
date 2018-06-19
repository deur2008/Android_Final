package tw.edu.csie.ntut.littlemonster;

import java.util.ArrayList;

public class BookKeeping {
    ArrayList<ArrayList<String>> data;

    public BookKeeping() {
        data = new ArrayList<ArrayList<String>>();
    }

    public void AddData(String date, String type, String amount) {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(date);
        temp.add(type);
        temp.add(amount);
        data.add(temp);
    }

    public int GetBalance() {
        int balance = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).get(1) == "income")
                balance += Integer.parseInt(data.get(i).get(2));
            else
                balance -= Integer.parseInt(data.get(i).get(2));
        }
        return balance;
    }

    public int GetTypeBalance(String type) {
        int balance = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).get(1) == type)
                balance += Integer.parseInt(data.get(i).get(2));
        }
        return balance;
    }

    public ArrayList<ArrayList<String>> GetData() {
        return data;
    }
}
