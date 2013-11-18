package ca.brocku.cosc.flock.data.api;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import ca.brocku.cosc.flock.data.api.json.models.GenericErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.JsonModelBase;

/**
 * Handles making requests to and from the Flock API Server.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/17/2013
 */
public class FlockAPIConnection {
    private static final String DEBUG_TAG = "FLOCK_API_CONNECTION";
    private static final String SERVER_ADDRESS = "http://216.121.182.110:9999"; // Flock API Server

    public static void send(String path, JsonModelBase request, Class<? extends JsonModelBase> responseClass, IFlockAPIResponse responseHandler) {
        // Append slash to front if it is not there
        if(path.length() > 0 && path.charAt(0) != '/') {
            path = "/" + path;
        }

        (new FlockApiNetworkThread(path, request, responseHandler, responseClass)).execute(); // Execute request
    }

    /**
     * Run the request in a network thread.
     */
    private static class FlockApiNetworkThread extends AsyncTask<Void, Void, String> {
        private static final ObjectMapper mapper = new ObjectMapper();

        private JsonModelBase request;
        private String path;
        private IFlockAPIResponse responseHandler;
        private Class<? extends JsonModelBase> responseClass;

        public FlockApiNetworkThread(String path, JsonModelBase request, IFlockAPIResponse responseHandler, Class<? extends JsonModelBase> responseClass) {
            this.request = request;
            this.path = path;
            this.responseHandler = responseHandler;
            this.responseClass = responseClass;
        }

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

        @Override
        protected void onPostExecute(String result) {
            try {
                responseHandler.onResponse(mapper.readValue(result, responseClass));
            } catch(Exception ex) {
                try {
                    GenericErrorModel error = mapper.readValue(result, GenericErrorModel.class);
                    if(error.status == JsonModelBase.STATUS_ERROR) {
                        responseHandler.onError(error);
                        return;
                    }
                } catch (Exception ex2) {
                    throw new RuntimeException(ex2);
                }
            }
        }
    }
}
