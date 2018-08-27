package com.TestApp.myapplication.Methods;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Unit {
    private static final String VK_API_BASE_URL ="https://api.vk.com/";
    private static final String VK_USER_GET ="/method/users.get";
    private static final String PARAM_USER_TO ="user_ids";
    private static final String PARAM_VERSION = "v";
    private static final String ACCESS_TOCKEN = "access_token";
    public static URL generateURL(String searchIDs){

        Uri builtUri = Uri.parse(VK_API_BASE_URL + VK_USER_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_TO, searchIDs)
                .appendQueryParameter(PARAM_VERSION,"5.8")
                .appendQueryParameter(ACCESS_TOCKEN,"5568bfed5568bfed5568bfed0b550d9c09555685568bfed0e2bf7859116abbccca3eb42")
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scan = new Scanner(in);
            scan.useDelimiter("\\A");
            boolean hasInput = scan.hasNext();
            if (hasInput)
                return scan.next();
            else
                return null;

        }catch(UnknownHostException e){
            return null;
        }finally{
            urlConnection.disconnect();
        }
    }
}
