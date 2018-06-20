package tw.edu.csie.ntut.littlemonster;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BubbleDialog extends Fragment {



    public static BookKeeping bookKeeping;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bookKeeping = new BookKeeping();
        TextView monsterType = (TextView)getView().findViewById(R.id.type);
        TextView balance = (TextView)getView().findViewById(R.id.balance);

        monsterType.setText("史萊姆");
        balance.setText("餘額");
        return inflater.inflate(R.layout.fragment_bubbledialog, container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
