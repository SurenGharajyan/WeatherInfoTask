package pixomaric.com.pixomaticweather;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by USER on 05.02.2018.
 */

public class DialogManager {
    private static DialogManager _instance;

    private DialogManager() {
    }

    public static DialogManager getInstance() {
        if (_instance == null) {
            _instance = new DialogManager();
        }
        return _instance;
    }


    public AlertDialog alertCountryChoose(final Context cont, final ICountryChange changeCountry, final ProgressDialog progDialog) {
        final AlertDialog alertDialog = new AlertDialog.Builder(cont).create();
        View view = LayoutInflater.from(cont).inflate(R.layout.dialog_country_choose,null);
        alertDialog.setView(view);
        Button btnChoose = view.findViewById(R.id.btnChoose);
        final ListView listCountry = view.findViewById(R.id.listView);
        String[] countries = cont.getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(cont, android.R.layout.simple_list_item_single_choice, countries);
        listCountry.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listCountry.setAdapter(arrayAdapter);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCountry.onChangeCountry(listCountry.getCheckedItemPosition());

                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }

}
