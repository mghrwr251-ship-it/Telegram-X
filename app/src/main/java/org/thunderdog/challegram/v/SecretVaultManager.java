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
import java.util.HashSet;
import java.util.Set;

public class SecretVaultManager {
  private static final String PREF_NAME = "secret_vault_prefs";
  private static final String KEY_PASSCODE = "vault_passcode";
  private static final String KEY_LOCK_TYPE = "vault_lock_type"; // 0: PIN, 1: Emoji, 2: Pattern, 3: Biometric
  private static final String KEY_HIDDEN_CHATS = "hidden_chats_ids";

  // حفظ نوع وقفل الأمان
  public static void setVaultLock(Context context, String passcode, int lockType) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    prefs.edit()
      .putString(KEY_PASSCODE, passcode)
      .putInt(KEY_LOCK_TYPE, lockType)
      .apply();
  }

  // التحقق من صحة الرمز المدخل
  public static boolean verifyPasscode(Context context, String input) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    String saved = prefs.getString(KEY_PASSCODE, "");
    return saved.equals(input);
  }

  // فحص هل تم تعيين قفل مسبقاً
  public static boolean hasPasscode(Context context) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    return prefs.contains(KEY_PASSCODE);
  }

  // جلب نوع القفل المستخدم
  public static int getLockType(Context context) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    return prefs.getInt(KEY_LOCK_TYPE, 0);
  }

  // إضافة محادثة للقائمة المخفية
  public static void hideChat(Context context, long chatId) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    Set<String> hidden = new HashSet<>(prefs.getStringSet(KEY_HIDDEN_CHATS, new HashSet<>()));
    hidden.add(String.valueOf(chatId));
    prefs.edit().putStringSet(KEY_HIDDEN_CHATS, hidden).apply();
  }

  // إزالة محادثة من القائمة المخفية
  public static void unhideChat(Context context, long chatId) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    Set<String> hidden = new HashSet<>(prefs.getStringSet(KEY_HIDDEN_CHATS, new HashSet<>()));
    hidden.remove(String.valueOf(chatId));
    prefs.edit().putStringSet(KEY_HIDDEN_CHATS, hidden).apply();
  }

  // التحقق هل المحادثة مخفية أم لا
  public static boolean isChatHidden(Context context, long chatId) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    Set<String> hidden = prefs.getStringSet(KEY_HIDDEN_CHATS, new HashSet<>());
    return hidden.contains(String.valueOf(chatId));
  }

  // دالة لحذف الخزانة وإعادة تعيين الرمز في حال نسيانه
  public static void resetVault(Context context) {
    Context c = context.getApplicationContext();
    if (c != null) {
      c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply();
    }
  }
}

