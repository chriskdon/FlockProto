package ca.brocku.cosc.flock.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Store and Retrieve application specific settings from an XML file.
 *
 * Singleton
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/16/2013
 */
public class ApplicationSettings {
    private static ApplicationSettings instance;

    private static final String FILE_LOCATION = "application_settings.xml";

    private String apiServerAddress;
    private int apiServerPort;

    private ApplicationSettings(Context context) {
        AssetManager am  = context.getAssets();

        try {
            InputStream is = am.open(FILE_LOCATION);
            XmlPullParser parser = Xml.newPullParser();

            parser.setInput(is, null);
            parser.nextTag();

            parser.require(XmlPullParser.START_TAG, null, "ApplicationSettings");
            while(parser.next() != XmlPullParser.END_TAG) {
                if(parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                if(parser.getName().equals("ApiServer")) {
                    for(int i = 0; i < parser.getAttributeCount(); i++) {
                        if(parser.getAttributeName(i).equals("address")) {
                            this.apiServerAddress = parser.getAttributeValue(i);
                        } else if(parser.getAttributeName(i).equals("port")) {
                            this.apiServerPort = Integer.parseInt(parser.getAttributeValue(i));
                        } else {
                            throw new XmlPullParserException("Invalid ApiServer Tag: " + parser.getLineNumber());
                        }
                    }
                }

            }
        } catch(IOException ex) {
            Log.e("APPLICATION_SETTINGS", ex.getMessage());
        } catch(XmlPullParserException ex) {
            Log.e("APPLICATION_SETTINGS", ex.getMessage());
        }
    }

    public static ApplicationSettings getInstance() {
        return instance;
    }

    public static void initialize(Context context) {
        if(instance == null) {
            instance = new ApplicationSettings(context);
        }
    }

    public String getApiServerAddress() { return apiServerAddress; }
    public int getApiServerPort() { return apiServerPort; }
}
