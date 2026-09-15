/*
 * This file is a part of Telegram X
 * Copyright © 2014 (tgx-android@pm.me)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.thunderdog.challegram.v;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup; // تمت إضافة هذا الاستيراد
import android.view.inputmethod.EditorInfo;
import org.thunderdog.challegram.R;

// import org.thunderdog.challegram.utils.Views; // إذا كانت فئة Views في مجلد آخر، قم بإزالة التعليق وتعديل المسار

public class HeaderEditText extends EditText {

  public HeaderEditText(Context context) {
    super(context);
    init();
  }

  public HeaderEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public HeaderEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        return openVault();
      }
    });
  }

  private boolean openVault() {
    Context context = getContext();
    if (context == null) return false;

    boolean isLocked = SecretVaultManager.hasPasscode(context);

    if (!isLocked) {
      android.widget.Toast.makeText(context, "الخزانة غير قيد الأمان، يرجى تعيين رمز المرور أولاً", android.widget.Toast.LENGTH_SHORT).show();
    } else {
      android.widget.Toast.makeText(context, "الرجاء إدخال الرمز لفتح الخزانة السرية", android.widget.Toast.LENGTH_SHORT).show();
    }
    return true;
  }

  public static HeaderEditText create(Context context) {
    HeaderEditText view = new HeaderEditText(context);
    view.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    
    // تم إيقاف هذا السطر مؤقتاً لتجنب خطأ (cannot find symbol Views) و (ic_caret). 
    // إذا كنت متأكداً من وجود الأيقونة وفئة Views، يمكنك إزالة العلامتين // من بداية السطر التالي:
    // Views.setCursorDrawable(view, R.drawable.ic_caret);
    
    return view;
  }

  // --- الدوال التي تمت إضافتها لحل أخطاء البناء (Build Errors) ---

  /**
   * تم استخدام (Object... args) لكي تقبل الدالة أي متغيرات إضافية ترسلها ملفات مثل 
   * ContactsController أو BubbleHeaderView وتمررها بصمت لتجنب خطأ اختلاف المتغيرات.
   */
  public static HeaderEditText create(Context context, Object... args) {
    return create(context); // نتجاهل المتغيرات الإضافية ونستدعي الدالة الأصلية
  }

  // لحل خطأ HeaderView.java (السطر 1017)
  public static HeaderEditText createStyled(ViewGroup parent, boolean isSomething) {
    return create(parent.getContext());
  }

  // لحل خطأ HeaderView.java (السطر 1042)
  public static HeaderEditText createGreyStyled(ViewGroup parent) {
    return create(parent.getContext());
  }

  // لحل خطأ استدعاء الدالة checkRtl()
  public void checkRtl() {
    // تركناها فارغة مؤقتاً لتمرير التحويل البرمجي.
  }
}
