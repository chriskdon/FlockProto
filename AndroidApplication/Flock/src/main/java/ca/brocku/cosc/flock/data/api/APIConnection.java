package ca.brocku.cosc.flock.data.api;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.JsonModelBase;

/**
 * Handles making requests to and from the Flock API Server.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/17/2013
 */
public class APIConnection {
    private static final String DEBUG_TAG = "FLOCK_API_CONNECTION";
    private static final String SERVER_ADDRESS = "http://216.121.182.110:9999"; // Flock API Server

    /**
     * Send a request to the Flock API Server
     *
     * @param path
     * @param request
     * @param responseClass
     * @param response
     */
    public static void send(String path, JsonModelBase request,
                            Class<? extends JsonModelBase> responseClass,
                            APIResponseHandler response) {

        // Append slash to front if it is not there
        if(path.length() > 0 && path.charAt(0) != '/') {
            path = "/" + path;
        }

        // Execute request
        FlockApiNetworkThread thread = new FlockApiNetworkThread(path, request, response, responseClass);
        thread.execute();
    }

    /**
     * Run the request in a network thread.
     */
    private static class FlockApiNetworkThread extends AsyncTask<Void, Void, String> {
        private static final ObjectMapper mapper = new ObjectMapper();

        private JsonModelBase request;
        private String path;
        private APIResponseHandler responseHandler;
        private Class<? extends JsonModelBase> responseClass;   // Response class type.

        /**
         * Setup Constructor
         *
         * @param path
         * @param request
         * @param responseHandler
         * @param responseClass
         */
        public FlockApiNetworkThread(String path, JsonModelBase request, APIResponseHandler responseHandler, Class<? extends JsonModelBase> responseClass) {
            this.request = request;
            this.path = path;
            this.responseHandler = responseHandler;
            this.responseClass = responseClass;
        }

        /**
         * Make the request to the APIAction Server
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(Void... params) {
            // Make Request
            try {
                HttpPost post = new HttpPost(SERVER_ADDRESS + path);
                post.setEntity(new StringEntity(request.toJsonString()));
                post.setHeader("Accept", "application/json");
                post.setHeader("Content-type", "application/json");

                return EntityUtils.toString((new DefaultHttpClient()).execute(post).getEntity());
            } catch(Exception ex) {
                Log.e(DEBUG_TAG, ex.getMessage());
            }

            return null;
        }

        /**
         * Convert the result JSON to a POJO and call the proper handler.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            try {
                responseHandler.onResponse(mapper.readValue(result, responseClass));
            } catch(Exception ex) {
                try {
                    ErrorModel error = mapper.readValue(result, ErrorModel.class);
                    if(error.status == JsonModelBase.STATUS_ERROR) {
                        responseHandler.onError(error);
                        return;
                    }
                } catch (Exception ex2) {
                    // Do nothing
                }
            }
        }
    }
}
