package com.example.owner.testtwo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Owner on 7/30/2017.
 */

public class CustomDialog extends Dialog {


    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.custom_dialog);
        setTitle("Black Market");
        setCanceledOnTouchOutside(true);
        ListView modeList;
        modeList = (ListView) findViewById(R.id.customListView);
        String[] stringArray = new String[] {
                "M16", "AK47", "M1GRAND", "AA12", "M&P", "9mm", "45mm", "12 Gage", "50 Cal", "More", "More", "More", "More"
        };
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_2, android.R.id.text1, stringArray);
        modeList.setAdapter(modeAdapter);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
