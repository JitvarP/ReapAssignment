package com.example.jitvar.reapassignment;

/**
 * Created by jitvar on 6/3/16.
 */
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.jitvar.reapassignment.Controller.BaseController;
import com.example.jitvar.reapassignment.Core.UCProduct;
import com.example.jitvar.reapassignment.Database.DataCursorLoader;
import com.example.jitvar.reapassignment.Database.UCDatabaseConstants;
import com.example.jitvar.reapassignment.WebEntities.WeData;
import com.example.jitvar.reapassignment.WebEntities.WeProduct;
import com.example.jitvar.reapassignment.WebEntities.WeRawData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProductListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String TAG = ProductListFragment.class.getSimpleName();
    private static final int PRODUCT_LOADER = 1;
    private int fragNum;
    private List<UCProduct> productList ;
    private SimpleCursorAdapter productListAdapter;
    private ListView productListView;
    private BaseController baseController;

    private static final String[] PRODUCT_FROM_FIELDS = {
            UCProduct.COLUMN_SCHEME,
            UCProduct.COLUMN_BRAND_NAME,
            UCProduct.COLUMN_MANUFACTURE,
            UCProduct.COLUMN_NAME,
            UCProduct.COLUMN_END_DATE
    };

    private static final int[] PRODUCT_TO_FIELDS = {
            R.id.tv_date,
            R.id.tv_name,
            R.id.tv_company_name,
            R.id.tv_scheme
    };




    static ProductListFragment init(int val) {
        ProductListFragment productListFragment = new ProductListFragment();

        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        productListFragment.setArguments(args);

        return productListFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
        baseController = new BaseController();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_product_list,container, false);
        productListView = (ListView) layoutView.findViewById(R.id.lv_product);

        return layoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new FetchDataFromServer().execute();
        Log.i(TAG, "onActivityCreated Called");
        if (getLoaderManager().getLoader(PRODUCT_LOADER) == null) {
            getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
        }
    }

    private void persistDataToDB(WeData data){

        for(int i = 0 ; i<20;i++){
            WeProduct weProduct = data.getProduct(i);
            if(weProduct!=null){
                UCProduct ucProduct;
                ucProduct = (UCProduct) baseController.getEntityByResourceID(UCProduct.class,weProduct.getDrugId());
                if(ucProduct == null)
                    ucProduct = new UCProduct();

                ucProduct.setBrandName(weProduct.getBrandName());
                ucProduct.setDrugID(weProduct.getDrugId());
                ucProduct.setResourceId(weProduct.getDrugId());
                ucProduct.setEndDate(weProduct.getEndDate());
                if(weProduct.getIsFav().equals("false"))
                    ucProduct.setIsFav(false);
                else
                    ucProduct.setIsFav(true);
                ucProduct.setManufacturer(weProduct.getManufacturer());
                ucProduct.setName(weProduct.getName());
                ucProduct.setScheme(weProduct.getScheme());

                if(ucProduct.getId() == 0)
                    baseController.persistEntity(ucProduct);
                else
                    baseController.updateEntity(ucProduct);
            }
        }
        getLoaderManager().restartLoader(PRODUCT_LOADER, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection;

        switch (id) {
            case PRODUCT_LOADER:
                Log.i(TAG,"Inside onCreateLoader");
                selection = "SELECT product." + UCProduct.COLUMN_ID
                        + " , product." + UCProduct.COLUMN_SCHEME
                        + " , product." + UCProduct.COLUMN_BRAND_NAME
                        + " , product." + UCProduct.COLUMN_MANUFACTURE
                        + " , product." + UCProduct.COLUMN_NAME
                        + " , product." + UCProduct.COLUMN_END_DATE
                        + " FROM "
                        + UCDatabaseConstants.TABLE_PRODUCT + " AS product "
                        +" ORDER BY product."
                        + UCProduct.COLUMN_END_DATE
                        + " COLLATE NOCASE asc";

                return new DataCursorLoader(getActivity(), selection, null);
        }
        return null;
    }

    private String formatDate (int n){
        if(n<10)
            return "0"+n;
        else
            return ""+n;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Log.i(TAG,"Size of cursor = "+data.getCount());
        switch (loader.getId()) {
            case PRODUCT_LOADER:
            if(productListAdapter == null){

                productListAdapter = new SimpleCursorAdapter(
                        getContext(), // Current context
                        R.layout.item_lst_product, // Layout for a single row
                        data, // Cursor received after load finished
                        PRODUCT_FROM_FIELDS, // Cursor columns to use
                        PRODUCT_TO_FIELDS, // Layout fields to use
                        0 //No flags
                );
            }else{
                productListAdapter.changeCursor(data);
            }
            productListView.setAdapter(productListAdapter);

            productListAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

                    switch (view.getId()){
                        case R.id.tv_scheme:
                            TextView txtScheme = (TextView) view;
                            String scheme = cursor.getString(cursor.getColumnIndex(UCProduct.COLUMN_SCHEME));
                            txtScheme.setText(scheme);
                            break;
                        case R.id.tv_company_name:
                            TextView txtBrandName = (TextView) view;
                            String brandName = cursor.getString(cursor.getColumnIndex(UCProduct.COLUMN_BRAND_NAME));
                            txtBrandName.setText(brandName);
                            break;
                        case R.id.tv_name:
                            TextView txtName = (TextView) view;
                            String name = cursor.getString(cursor.getColumnIndex(UCProduct.COLUMN_NAME));
                            txtName.setText(name);
                            break;
                        case R.id.tv_date:
                            TextView txtDate = (TextView) view;
                            String date = cursor.getString(cursor.getColumnIndex(UCProduct.COLUMN_END_DATE));
                            DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                            DateTime dateTime = f.parseDateTime(date);
                            date = formatDate(dateTime.getDayOfMonth())+"/"+formatDate(dateTime.getMonthOfYear())+"/"+dateTime.getYear();
                            txtDate.setText(date);
                            break;
                    }
                    return true;
                }
            });

        }

    }




    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case PRODUCT_LOADER:
                if (productListAdapter != null) {
                    productListAdapter.changeCursor(null);
                }
                break;
            default:
                break;
        }

    }


    private class FetchDataFromServer extends AsyncTask<Void,Void,WeRawData> {
        private final String TAG = FetchDataFromServer.class.getSimpleName();
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        WeRawData responce ;

        @Override
        protected WeRawData doInBackground(Void... params) {
            try {

                // Url of the server use builder to built url
                URL url = new URL(" http://mobile-dev.letsreap.com/mobile-api/v1/3eba480b13642409957cdde42e315afbde4b312ba259717aa0bdc09944deee3c0799061800b7854a6252b41895a26a0128e24c0b8efd56c571cc6f8bf7c0f5d1/schemes?store-pincode=411045");

                // Create request
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                Reader reader = new InputStreamReader(inputStream);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                responce = gson.fromJson(reader,WeRawData.class);

                Log.i(TAG,"responce status ="+responce.getStatus());
                inputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return responce;
        }

        @Override
        protected void onPostExecute(WeRawData weRawData) {
            WeData weData = weRawData.getData();
            persistDataToDB(weData);
        }
    }
}