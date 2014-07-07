package be.geecko.openlauncher.net;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.json.*;

import be.geecko.openlauncher.CustomContent;
import be.geecko.openlauncher.R;
import be.geecko.openlauncher.cards.SuggestionsCard;

/**
 * This file is part of OpenLauncher for Android
 * Created by geecko on 2/07/14.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class SuggestionsTask extends AsyncTask<String, Integer, JSONArray> {
    private final CustomContent customContent;

    public SuggestionsTask(CustomContent customContent) {
        this.customContent = customContent;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        String searchTerms = strings[0];
        try {
            searchTerms = URLEncoder.encode(searchTerms, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(
                "https://clients1.google.com/complete/search?output=toolbar&client=firefox&hl=%s&q=%s",
                "en", // fixme
                searchTerms);
        JSONArray result = null;
        try {
            JSONArray suggestionArray = new JSONArray(readUrl(url));
            result = suggestionArray.getJSONArray(1);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONArray jsonResult) {
        super.onPostExecute(jsonResult);
        if (jsonResult == null)
            return;

        final int limit = jsonResult.length() > 3 ? 3 : jsonResult.length();
        final String[] results = new String[limit];
        try {
            for (int i = 0; i < limit; i++)
                results[i] = jsonResult.getString(i);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        ListView resultList = (ListView) LayoutInflater.from(customContent.getContext())
                .inflate(R.layout.suggestion_listview, null);
        resultList.setAdapter(new ArrayAdapter<CharSequence>(customContent.getContext(),
                R.layout.card_list_adapter, results));
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomContent.search(results[i], view.getContext());
            }
        });

        if (((EditText) customContent.findViewById(R.id.search_bar)).getText().length() > 0)
            customContent.pushCard(resultList, SuggestionsCard.class);
    }

    private static String readUrl(String http) throws IOException {
        URL url = new URL(http);
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        String encoding = connection.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        return IOUtils.toString(in, encoding);
    }

}