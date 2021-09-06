package com.daqsoft.commonnanning.ui.service.complaint;

/**
 * 过滤表情
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-10-20.13:50
 * @since JDK 1.8
 */
import android.text.InputFilter;
import android.text.Spanned;

import java.util.HashSet;
import java.util.Set;

public class EmojiFilter implements InputFilter{
    private static Set<String> filterSet = null;
    private static void addUnicodeRangeToSet(Set<String> set, int start, int end) {
        if (set == null) {
            return;
        }
        if (start > end) {
            return;
        }
        for (int i = start; i <= end; i++) {
            filterSet.add(new String(new int[]{
                    i
            }, 0, 1));
        }
    }
    static {
        filterSet = new HashSet<String>();
        addUnicodeRangeToSet(filterSet, 0x1F601, 0X1F64F);
        addUnicodeRangeToSet(filterSet, 0x2702, 0X27B0);
        addUnicodeRangeToSet(filterSet, 0X1F680, 0X1F6C0);
        addUnicodeRangeToSet(filterSet, 0X24C2, 0X1F251);
        addUnicodeRangeToSet(filterSet, 0X1F600, 0X1F636);
        addUnicodeRangeToSet(filterSet, 0X1F681, 0X1F6C5);
        addUnicodeRangeToSet(filterSet, 0X1F30D, 0X1F567);

    }
    public EmojiFilter() {
        super();
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                               int dend) {
        if (filterSet.contains(source.toString())) {
            return "";
        }
        return source;
    }


}
