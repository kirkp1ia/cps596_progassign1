package edu.cmich.kirkp1ia.cps596.progassign1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    private HashMap<String, Double> tip_percents_map = new HashMap<String, Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] pcnts = getResources().getStringArray(R.array.tip_percents);
        for (int i = 0; i < pcnts.length; i ++) {
            Double actual_val = Double.valueOf(pcnts[i].substring(0, pcnts[i].indexOf('%'))) / 100;
            this.tip_percents_map.put(pcnts[i], actual_val);
        }

        setContentView(R.layout.activity_main);
        this.newForm();
    }

    private void newForm() {
        ((EditText) this.findViewById(R.id.in__amt_bill)).setText("");
        ((EditText) this.findViewById(R.id.in__num_mems)).setText("");
        ((EditText) this.findViewById(R.id.out__tip_amt)).setText("");
        ((EditText) this.findViewById(R.id.out__tip_pmember)).setText("");
    }

    /**
     * First element is the total tip
     * Second element is the tip per member
     * @return
     */
    private double[] calculateTip() {
        double totalPrice = Double.valueOf(((EditText) this.findViewById(R.id.in__amt_bill)).getText().toString());
        int numMems = Integer.valueOf(((EditText) this.findViewById(R.id.in__num_mems)).getText().toString());

        double tipPercent = this.tip_percents_map.get(((Spinner) this.findViewById(R.id.in__tip_prcnt)).getSelectedItem().toString());

        double tipRaw = totalPrice * tipPercent;
        double tipPerMem = tipRaw/numMems;

        return new double[]{tipRaw, tipPerMem};
    }

    private void displayTip() {
        double[] tipVals = this.calculateTip();
        double rawTip = tipVals[0];
        double tipPerMember = tipVals[1];

        Log.d(TAG, "Tip: " + Arrays.toString(tipVals));
        DecimalFormat currencyFormat = new DecimalFormat(".##");
        String tipTotalDisplay = currencyFormat.format(rawTip);
        String tipPerMemberDisplay = currencyFormat.format(tipPerMember);

        ((EditText) this.findViewById(R.id.out__tip_amt)).setText("Tip Total: $" + tipTotalDisplay);
        ((EditText) this.findViewById(R.id.out__tip_pmember)).setText("Tip per Person: $" + tipPerMemberDisplay);
    }

    public void requestTipCalculation(View v) {
        this.displayTip();
    }
}
