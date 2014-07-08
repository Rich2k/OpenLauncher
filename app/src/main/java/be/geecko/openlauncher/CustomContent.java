package be.geecko.openlauncher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.launcher3.Launcher;
import com.android.launcher3.SearchDropTargetBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import be.geecko.openlauncher.cards.SuggestionsCard;

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

    //TODO rework cards interface
    //TODO voice

    //TODO Search Phone
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

    public void pushCard(View content, Class cardType) {
        final LinearLayout cardsContainer = (LinearLayout) findViewById(R.id.cards_container);
        CardView card;
        if (cardsContainer.getChildAt(0) != null && cardType.isInstance(cardsContainer.getChildAt(0))) {
            card = (CardView) cardsContainer.getChildAt(0);
            card.removeAllViewsInLayout();
        } else {
            card = (CardView) LayoutInflater.from(getContext()).inflate(
                    R.layout.card, cardsContainer, false);
            cardsContainer.addView(card);
        }
        card.addView(content);
    }

    @Override
    public void onShow() {
        SearchDropTargetBar searchShortcut = ((OpenLauncher) getContext()).getSearchBar();
        searchShortcut.setVisibility(View.GONE);
    }

    @Override
    public void onHide() { // Search bar loses focus and keyboard is dismissed
        final View searchBar = findViewById(R.id.search_bar);
        searchBar.post(new Runnable() {
            public void run() {
                searchBar.clearFocus();
                InputMethodManager inputManager = (InputMethodManager)
                        searchBar.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(CustomContent.this.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void onScrollProgressChanged(float progress) {
        //Custom screen and search bar are fading in or out
        this.setAlpha(progress);

        SearchDropTargetBar searchShortcut = ((OpenLauncher) getContext()).getSearchBar();
        if (searchShortcut != null) {
            if (progress > 0.5f)
                searchShortcut.hideSearchBar(true);
            else {
                searchShortcut.showSearchBar(true);
                searchShortcut.setVisibility(View.VISIBLE);
            }
        }
    }
}