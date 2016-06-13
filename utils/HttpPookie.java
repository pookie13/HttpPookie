package prime.com.myapplication.webtask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class is helper class to hit web services.
 * class contains Request,Response and Perams inner class(classes helps for further execution as name suggested).
 * class have an interface.
 * Created by piyush on 10/6/16.
 */
public class HttpPookie {

    public static final String POST = "POST"; ///// Denotes request is type of POST
    public static final String GET = "GET";///// Denotes request is type of GET
    public static final String CHAR_SET = "Accept-Charset";///// defination of char set
    public static final String LOG_ = "pookieResponse";///// defination of char set


    ArrayList<Perams> peramsList = new ArrayList<>(); //contains all parameters for requesting an request to server
    private Request request;  //private member for having request.


    NetworkResponsesHandler responseHandler;  //interface instance which used to callbacks to class from where requesting an service.

    public interface NetworkResponsesHandler {
        void onNoInternetConnection();

        void onUnauthorizedConnection();

        void onResponseData(JSONObject jsonObject, Object tag);

        void onError();

        void onException(Exception e);
    }

    /**
     * this function creates new Request.
     *
     * @return HttpPookie : instance of HttpPookie class for call more methods in that class
     */
    public HttpPookie newRequest() {
        request = new Request();
        return this;
    }

    /**
     * this function sets URL for requesting which will return data from server.
     *
     * @param url : url of webservice(full url).
     * @return HttpPookie: fro more calling methods
     */
    public HttpPookie setUrl(String url) {
        request.url = url;
        return this;
    }

    /**
     * this function sets the type of request
     *
     * @param type : may be 'GET' or 'POST'
     * @return
     */
    public HttpPookie setType(String type) {
        request.type = type;
        return this;
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public HttpPookie setPerams(String key, Object values) {
        Perams perams = new Perams();
        perams.setKey(key);
        perams.setValue(values);
        peramsList.add(perams);
        return this;
    }

    // TODO: 13/6/16 add an method for setting list of perams
    // TODO: 13/6/16 add an method that will accepet object of class and before calling execute() add permas from class.
    // TODO: 13/6/16 add an method which helps to set tag with request and sending response along with tag.

    /**
     * this function sets tag id for request.
     *
     * @param tag : which helps to distinguish different different requests.
     * @return
     */
    public HttpPookie setTag(Object tag) {
        request.tag = tag;
        return this;
    }

    /**
     * sets read time out for request.
     *
     * @param readTimeOut value for time out provide only int byDefault its value is 10000 .
     * @return
     */
    public HttpPookie setReadTimeOut(int readTimeOut) {
        request.readTimeOut = readTimeOut;
        return this;
    }

    /**
     * sets read time out for request.
     *
     * @param connectionTimeOut value for time out provide only int byDefault its value is 15000 .
     * @return
     */
    public HttpPookie setConnectionTimeOut(int connectionTimeOut) {
        request.connectionTimeOut = connectionTimeOut;
        return this;
    }

    /**
     * this function will build a HTTP request
     *
     * @return
     */
    public Request build() {
        request.peramses = peramsList;
//        peramsList = null;
        return request;
    }

    public HttpPookie newCall(Request request) {
        this.request = request;
        return this;
    }

    /**
     * this function execute request which sends in newCall()'s perams.
     *
     * @param responseHandler
     */
    public void execute(NetworkResponsesHandler responseHandler) {
        this.responseHandler = responseHandler;
        new AsynReq(request).execute();
    }

    /**
     * Class which contains meta values (coarse values) of request.
     */
    public class Request {
        String url;  //url for request
        String type;// type of request
        Object tag; // tag of request
        ArrayList<Perams> peramses; //perams of request
        int readTimeOut;   //read Time Out of request
        int connectionTimeOut;   //connection Time Out of request

        public Object getTag() {
            if (tag == null)
                return 0;
            return tag;
        }

        public int getReadTimeOut() {
            if (readTimeOut == 0)
                return 10000;
            return readTimeOut;
        }

        public int getConnectionTimeOut() {
            if (connectionTimeOut == 0)
                return 15000;
            return connectionTimeOut;
        }
    }

    /**
     * sets
     */
    class Perams {
        String key;
        Object value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    class Response {
        String response;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

    /**
     * a simple AsyncTask which helps to execute Http request.
     */
    class AsynReq extends AsyncTask<String, String, JSONObject> {
        Request request;
        JSONParser jsonParser;
        HashMap<String, String> params = new HashMap<>();


        public AsynReq(Request request) {
            this.request = request;
            jsonParser = new JSONParser();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            try {
                for (Perams args : request.peramses) {
                    params.put(args.getKey(), args.getValue().toString());
                }
                JSONObject json = jsonParser.makeHttpRequest(request, params);
                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            responseHandler.onResponseData(jsonObject, request.getTag());
        }

    }
}


