package org.esiea.mehrenberger_lestrille.myapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.DatePicker;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
/**
 * Created by pierremehrenberger on 07/12/2016.
 */

public class PickerDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateSettings dateSettings = new DateSettings(getActivity());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(getActivity(), dateSettings, year,month,day);

        return dialog;
    }
}