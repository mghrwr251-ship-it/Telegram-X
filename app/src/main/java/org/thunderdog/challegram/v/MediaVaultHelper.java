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
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MediaVaultHelper {

  // دالة حفظ الوسائط ذاتية التدمير أو المؤقتة تلقائياً
  public static void saveSelfDestructMedia(Context context, String fileUrl, String fileName) {
    new Thread(() -> {
      try {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        
        InputStream input = connection.getInputStream();
        
        // إنشاء مجلد خاص بالخزنة في ذاكرة الهاتف
        File vaultDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "TelegramX_SecretVault");
        if (!vaultDir.exists()) {
          vaultDir.mkdirs();
        }
        
        File outputFile = new File(vaultDir, fileName != null ? fileName : "vault_media_" + System.currentTimeMillis() + ".jpg");
        FileOutputStream output = new FileOutputStream(outputFile);
        
        byte[] data = new byte[4096];
        int count;
        while ((count = input.read(data)) != -1) {
          output.write(data, 0, count);
        }
        
        output.flush();
        output.close();
        input.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }
}
