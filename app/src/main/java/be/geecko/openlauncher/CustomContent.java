package be.geecko.openlauncher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.launcher3.Launcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import be.geecko.openlauncher.net.SuggestionsTask;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * This file is part of OpenLauncher for Android
 * Created by geecko on 1/07/14.
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
public class CustomContent extends FrameLayout implements Launcher.CustomContentCallbacks {

    //TODO auto-complete
    //TODO voice

    //TODO cards
    //TODO Pull to refresh
    //TODO Scroll tricks

    public CustomContent(Context context) {
        super(context);
    }

    public CustomContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomContent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static boolean search(String search, Context context) {
        Intent browserIntent;
        try {
            browserIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://duckduckgo.com/?q=" +
                            URLEncoder.encode(search, "utf-8"))
            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        context.startActivity(browserIntent);
        return true;
    }

    private void setupSearchBar() {
        EditText searchBar = (EditText) findViewById(R.id.search_bar);

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return CustomContent.search(
                        textView.getText().toString(),
                        CustomContent.this.getContext());
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchTerms = editable.toString();
                LinearLayout container = (LinearLayout) findViewById(R.id.cards_container);
                if (searchTerms.length() > 2) {
                    SuggestionsTask st = new SuggestionsTask(container);
                    st.execute(searchTerms);
                } else if (container.getChildAt(0) != null &&
                        "suggestion card".equals(container.getChildAt(0).getContentDescription()))
                    container.removeViewAt(0);
            }
        });
        //TODO to class? W/ voice & cancel button
    }

    @Override
    public void onShow() {
        setupSearchBar();
    }

    @Override
    public void onHide() {

    }

    @Override
    public void onScrollProgressChanged(float progress) {
    }
}
