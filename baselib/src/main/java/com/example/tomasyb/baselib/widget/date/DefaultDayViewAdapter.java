package com.example.tomasyb.baselib.widget.date;

import android.view.ContextThemeWrapper;
import android.widget.TextView;

import com.example.tomasyb.baselib.R;
import com.example.tomasyb.baselib.util.SizeUtils;


public class DefaultDayViewAdapter implements DayViewAdapter {
  @Override
  public void makeCellView(CalendarCellView parent) {
      TextView textView = new TextView(
              new ContextThemeWrapper(parent.getContext(), R.style.CalendarCell_CalendarDate));
      textView.setDuplicateParentStateEnabled(true);
      parent.addView(textView);
      textView.setText("入住");
      textView.setTextSize(SizeUtils.px2dp(30));
      parent.setDayOfMonthTextView(textView);
  }


}
