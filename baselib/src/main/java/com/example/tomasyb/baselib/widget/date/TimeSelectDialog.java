package com.example.tomasyb.baselib.widget.date;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.tomasyb.baselib.R;

import java.util.Calendar;
import java.util.Date;

/**
 * 日历
 *
 * @author MouJunFeng
 * @time 2018-3-28.
 */

public class TimeSelectDialog extends Dialog {

    public TimeSelectDialog(@NonNull Context context) {
        super(context);
    }

    public TimeSelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private OnTimeSelectListener listener;

        public Builder(Context context) {
            this.context = context;
        }

        public void setListener(OnTimeSelectListener listener) {
            this.listener = listener;
        }

        public TimeSelectDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final TimeSelectDialog dialog = new TimeSelectDialog(context, R.style.MyDialog);
            View v = inflater.inflate(R.layout.dialog_time_select, null);
            dialog.addContentView(v, new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
            final CalendarPickerView view_time = (CalendarPickerView) v.findViewById(R.id.calendar_view);
            Calendar nextYear = Calendar.getInstance();
            view_time.setSelectStartValue("");
            view_time.setSelectEndValue("");
            nextYear.add(Calendar.YEAR, 1);
            Date today = new Date();
            view_time.init(today, nextYear.getTime()).withSelectedDate(today).inMode(CalendarPickerView.SelectionMode.SINGLE);
            TextView txt_cancle = (TextView) v.findViewById(R.id.choose_date_cancel);
            txt_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            TextView txt_submit = (TextView) v.findViewById(R.id.choose_date_sure);
            txt_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Date date = view_time.getSelectedDate();
                    if (listener != null) {
                        listener.select(date);
                    }
                }
            });
            return dialog;
        }
    }

    public interface OnTimeSelectListener {
        void select(Date date);
    }
}
