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

public class VaultBridge {

  // فحص ما إذا كان يجب منع حذف الرسالة في هذه المحادثة
  public static boolean shouldBlockDelete(Context context, long chatId) {
    return AntiDeleteHelper.shouldPreventDeletion(context, chatId);
  }

  // حفظ الوسائط المؤقتة تلقائياً قبل أن تختفي
  public static void saveTempMediaIfNeeded(Context context, String fileUrl, String fileName) {
    if (context != null && fileUrl != null && !fileUrl.isEmpty()) {
      MediaVaultHelper.saveSelfDestructMedia(context, fileUrl, fileName);
    }
  }
}
