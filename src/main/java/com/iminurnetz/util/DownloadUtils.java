/**
 * LICENSING
 * 
 * This software is copyright by sunkid <sunkid@iminurnetz.com> and is
 * distributed under a dual license:
 * 
 * Non-Commercial Use:
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Commercial Use:
 *    Please contact sunkid@iminurnetz.com
 */
package com.iminurnetz.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DownloadUtils {

    // from https://raw.github.com/DiddiZ/LogBlock/master/src/de/diddiz/util/Utils.java
    public static void download(Logger logger, URL url, File file) throws IOException {

        createOrReplaceWithNewFile(file);
        
        final int size = url.openConnection().getContentLength();
        logger.log(Level.INFO, "Downloading " + file.getName() + " (" + size / 1024 + "kb) ...");
        final InputStream in = url.openStream();
        final OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        final byte[] buffer = new byte[1024];
        int len, downloaded = 0, msgs = 0;
        final long start = System.currentTimeMillis();
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
            downloaded += len;
            if ((int) ((System.currentTimeMillis() - start) / 500) > msgs) {
                logger.log(Level.INFO, (int) (downloaded / (double) size * 100d) + "%");
                msgs++;
            }
        }
        in.close();
        out.close();
        logger.log(Level.INFO, "Download finished");
    }

    public static void createOrReplaceWithNewFile(File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        
        if (file.exists()) {
            file.delete();
        }
        
        file.createNewFile();
    }

    // from https://raw.github.com/DiddiZ/LogBlock/master/src/de/diddiz/util/Utils.java
    public static String readURL(URL url) throws IOException {
        final StringBuilder content = new StringBuilder();
        final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            content.append(inputLine);
        in.close();
        return content.toString();
    }

    public static String post(String urlString, HashMap<String, String> values) throws IOException {
        StringBuffer retval = new StringBuffer();
        URL url = new URL(urlString);

        StringBuffer data = new StringBuffer();
        for (String key : values.keySet()) {
            data.append(URLEncoder.encode(key, "UTF-8") + "=");
            data.append(URLEncoder.encode(values.get(key), "UTF-8") + "&");
        }

        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);

        OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
        osw.write(data.substring(0, data.length() - 1));
        osw.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            retval.append(line);
        }
        osw.close();
        rd.close();

        return retval.toString();
    }
}
