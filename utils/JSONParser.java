package prime.com.myapplication.webtask;

/**
 * Created by piyush on 10/6/16.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class JSONParser {

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    /**
     * function which makes Http Request for execution.
     *
     * @param request will execute this method
     * @param params  permameters to sends with this request.
     * @return response after execution.
     */
    public JSONObject makeHttpRequest(HttpPookie.Request request, HashMap<String, String> params) {
        sbParams = new StringBuilder();   //create new string builder to modify url to sends.
        int i = 0;
        for (String key : params.keySet()) {    ////loop to add perams in URl.
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }


        //////////////////////////////////check type of Request///////////////////////////////////////////////
        switch (request.type) {
            case HttpPookie.POST:
                /////////////////////////this work around for Post type Methods//////////////////
                try {
                    urlObj = new URL(request.url);
                    conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod(HttpPookie.POST);
                    conn.setRequestProperty(HttpPookie.CHAR_SET, charset);
                    conn.setReadTimeout(request.getReadTimeOut());
                    conn.setConnectTimeout(request.getConnectionTimeOut());
                    conn.connect();
                    paramsString = sbParams.toString();
                    wr = new DataOutputStream(conn.getOutputStream());
                    wr.writeBytes(paramsString);
                    wr.flush();
                    wr.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                /////////////////////////////////////////////////////////////////////////////////
                break;

            case HttpPookie.GET:
                /////////////////////////this work around for GET type Methods//////////////////
                String localUrl = request.url;
                if (sbParams.length() != 0) {
                    localUrl += "?" + sbParams.toString();
                }
                try {
                    urlObj = new URL(localUrl);
                    conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setDoOutput(false);
                    conn.setRequestMethod(HttpPookie.GET);
                    conn.setRequestProperty(HttpPookie.CHAR_SET, charset);
                    conn.setConnectTimeout(request.getConnectionTimeOut());
                    conn.connect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                /////////////////////////////////////////////////////////////////////////////////
                break;
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            //////Receive the response from the server////////
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.d(HttpPookie.LOG_, result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();
        // try Parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e(HttpPookie.LOG_, "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jObj;
    }
}
