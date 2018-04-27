package demo.test.com.codingtestdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import demo.test.com.codingtestdemo.model.TestBean;

/**
 * Created by felix on 2018/4/26.
 */
public class DataStore {

    private static final String TAG = "DataStore";

    private Context mContext;

    private CustomAsyncTask customAsyncTask;

    private OnDataChangedListener onDataChangedListener;

    public DataStore() {

    }

    public DataStore(Context context, OnDataChangedListener onDataChangedListener) {
        this.mContext = context;
        this.onDataChangedListener = onDataChangedListener;
    }

    /*
    * fetch all city guide data
    * */
    public void fetchCityGuide() {

        Log.v(TAG, "fetchCityGuide");

        customAsyncTask = new CustomAsyncTask();

        customAsyncTask.execute("test.json");
    }

    /*
    * custom async task for fetching data from web service
    * */
    class CustomAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.v(TAG, "onPreExecute");
        }

        @Override
        protected String doInBackground(String... params) {

            Log.v(TAG, "doInBackground" + params[0]);

            return getJson(mContext, params[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            Log.v(TAG, "onPostExecute");

            if (json != null) {
                ArrayList<TestBean> testBeen = analyseJsonData(json);

                if(onDataChangedListener != null){
                    onDataChangedListener.onDataChanged(testBeen);
                }
            }
        }
    }


    /*
    * Use third-party library GSON to analyse json data
    * */
    protected ArrayList<TestBean> analyseJsonData(String strByJson){

        JsonObject jsonObject = new JsonParser().parse(strByJson).getAsJsonObject();

        JsonArray jsonArray = jsonObject.getAsJsonArray("data");

        Gson gson = new Gson();
        ArrayList<TestBean> testBeanList = new ArrayList<>();

        for (JsonElement user : jsonArray) {
            TestBean testBean = gson.fromJson(user, new TypeToken<TestBean>() {}.getType());
            testBeanList.add(testBean);
        }

        return testBeanList;
    }


    /*
    * Get json data from local assets
    * */
    private String getJson(Context context, String fileName){

        Log.v(TAG, "getJson");

        StringBuilder stringBuilder = new StringBuilder();

        //Get assets resource manager
        AssetManager assetManager = context.getAssets();

        //Use IO stream read json file
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "---" + stringBuilder.toString());

        return stringBuilder.toString();
    }
}
