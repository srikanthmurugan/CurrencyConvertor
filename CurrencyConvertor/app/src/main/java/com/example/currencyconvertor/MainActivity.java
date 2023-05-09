package com.example.currencyconvertor;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import android.media.MediaPlayer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    TextView convertFromDropdownTextView, convertToDropdownTextView, conversionRateText,convertedValueText;
    EditText amountToConvert;
    ArrayList<String> arrayList;
    Dialog toDialog;
    Dialog fromDialog;
    Button convertButton;
    String convertFromValue, convertToValue;
    String[] country = {  "USD",
            "AED",
            "AFN",
            "ALL",
            "AMD",
            "ANG",
            "AOA",
            "ARS",
            "AUD",
            "AWG",
            "AZN",
            "BAM",
            "BBD",
            "BDT",
            "BGN",
            "BHD",
            "BIF",
            "BMD",
            "BND",
            "BOB",
            "BRL",
            "BSD",
            "BTN",
            "BWP",
            "BYN",
            "BZD",
            "CAD",
            "CDF",
            "CHF",
            "CLP",
            "CNY",
            "COP",
            "CRC",
            "CUP",
            "CVE",
            "CZK",
            "DJF",
            "DKK",
            "DOP",
            "DZD",
            "EGP",
            "ERN",
            "ETB",
            "EUR",
            "FJD",
            "FKP",
            "FOK",
            "GBP",
            "GEL",
            "GGP",
            "GHS",
            "GIP",
            "GMD",
            "GNF",
            "GTQ",
            "GYD",
            "HKD",
            "HNL",
            "HRK",
            "HTG",
            "HUF",
            "IDR",
            "ILS",
            "IMP",
            "INR",
            "IQD",
            "IRR",
            "ISK",
            "JEP",
            "JMD",
            "JOD",
            "JPY",
            "KES",
            "KGS",
            "KHR",
            "KID",
            "KMF",
            "KRW",
            "KWD",
            "KYD",
            "KZT",
            "LAK",
            "LBP",
            "LKR",
            "LRD",
            "LSL",
            "LYD",
            "MAD",
            "MDL",
            "MGA",
            "MKD",
            "MMK",
            "MNT",
            "MOP",
            "MRU",
            "MUR",
            "MVR",
            "MWK",
            "MXN",
            "MYR",
            "MZN",
            "NAD",
            "NGN",
            "NIO",
            "NOK",
            "NPR",
            "NZD",
            "OMR",
            "PAB",
            "PEN",
            "PGK",
            "PHP",
            "PKR",
            "PLN",
            "PYG",
            "QAR",
            "RON",
            "RSD",
            "RUB",
            "RWF",
            "SAR",
            "SBD",
            "SCR",
            "SDG",
            "SEK",
            "SGD",
            "SHP",
            "SLE",
            "SLL",
            "SOS",
            "SRD",
            "SSP",
            "STN",
            "SYP",
            "SZL",
            "THB",
            "TJS",
            "TMT",
            "TND",
            "TOP",
            "TRY",
            "TTD",
            "TVD",
            "TWD",
            "TZS",
            "UAH",
            "UGX",
            "UYU",
            "UZS",
            "VES",
            "VND",
            "VUV",
            "WST",
            "XAF",
            "XCD",
            "XDR",
            "XOF",
            "XPF",
            "YER",
            "ZAR",
            "ZMW",
            "ZWL"};




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        convertFromDropdownTextView = findViewById(R.id.convert_from_dropdown_menu);
        convertToDropdownTextView = findViewById(R.id.convert_to_dropdown_menu);
        convertButton = findViewById(R.id.conversionButton);
        conversionRateText = findViewById(R.id.conversionRateResultText);
        amountToConvert = findViewById(R.id.amountToConvertValueEditText);
        convertedValueText = findViewById(R.id.convertedValue);

        arrayList = new ArrayList<>();
        for(String i : country ){
            arrayList.add(i);
        }

        convertFromDropdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(MainActivity.this);
                fromDialog.setContentView(R.layout.from_spinner);
                fromDialog.getWindow().setLayout(650, 800);
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.edit_text);
                ListView listView = fromDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        convertFromDropdownTextView.setText(adapter.getItem(i));
                        fromDialog.dismiss();
                        convertFromValue = adapter.getItem(i);
                    }
                });

                convertToDropdownTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toDialog = new Dialog(MainActivity.this);
                        toDialog.setContentView(R.layout.to_spinner);
                        toDialog.getWindow().setLayout(650, 800);
                        toDialog.show();

                        EditText editText = toDialog.findViewById(R.id.edit_text);
                        ListView listView = toDialog.findViewById(R.id.list_view);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                        listView.setAdapter(adapter);

                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                adapter.getFilter().filter(charSequence);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                convertToDropdownTextView.setText(adapter.getItem(i));
                                toDialog.dismiss();
                                convertToValue = adapter.getItem(i);
                            }
                        });
                    }
                });

                convertButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Double amountToConvert = Double.valueOf(MainActivity.this.amountToConvert.getText().toString());
                            getConversionRate(convertFromValue, convertToValue, amountToConvert);
                        } catch (Exception e) {

                        }
                    }
                });
            }




            public void getConversionRate(String convertFrom, String convertTo, Double amountToConvert) {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="https://v6.exchangerate-api.com/v6/958b4bf7afc0b777518bf4c9/latest/"+convertFrom;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()  {


                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject rates = response.getJSONObject("conversion_rates");

                            double conversionToValue = round(((Double) rates.getDouble(convertTo)), 2);
                            double convertedValue = round((conversionToValue * amountToConvert), 2);
                            String conversionValue = "" + convertedValue;
                            playMusic();
                            convertedValueText.setText(convertTo+" : "+conversionValue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                queue.add(request);
            }

            public double round(double value, int places) {
                if (places < 0) throw new IllegalArgumentException();
                BigDecimal bd = BigDecimal.valueOf(value);
                bd = bd.setScale(places, RoundingMode.HALF_UP);
                return bd.doubleValue();
            }

        });







        }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.soundcc);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }



    }