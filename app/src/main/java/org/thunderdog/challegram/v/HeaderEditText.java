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
import android.view.inputmethod.EditorInfo;
import org.thunderdog.challegram.R;

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
    Views.setCursorDrawable(view, R.drawable.ic_caret);
    return view;
  }
}
