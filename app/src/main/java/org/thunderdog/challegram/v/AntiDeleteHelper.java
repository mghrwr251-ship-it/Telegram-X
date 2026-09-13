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
import android.content.SharedPreferences;

public class AntiDeleteHelper {
  private static final String PREF_ANTIDELETE = "anti_delete_prefs";

  // التحقق هل ميزة منع الحذف مفعلة لمحادثة أو بشكل عام
  public static boolean isAntiDeleteEnabled(Context context, long chatId) {
    if (context == null) return true; // مفعل افتراضياً للخزانة والمحادثات المستهدفة
    SharedPreferences prefs = context.getSharedPreferences(PREF_ANTIDELETE, Context.MODE_PRIVATE);
    return prefs.getBoolean("anti_delete_" + chatId, true);
  }

  // تفعيل أو تعطيل منع الحذف لمحادثة معينة
  public static void setAntiDelete(Context context, long chatId, boolean enabled) {
    if (context == null) return;
    SharedPreferences prefs = context.getSharedPreferences(PREF_ANTIDELETE, Context.MODE_PRIVATE);
    prefs.edit().putBoolean("anti_delete_" + chatId, enabled).apply();
  }

  // اعتراض حدث حذف الرسائل (يتم استدعاؤها من معالج تحديثات TDLib)
  public static boolean shouldPreventDeletion(Context context, long chatId) {
    // إذا كانت المحادثة داخل الخزانة السرية أو مفعلاً لها المنع، يتم الاحتفاظ بالرسالة
    return SecretVaultManager.isChatHidden(context, chatId) || isAntiDeleteEnabled(context, chatId);
  }
}
