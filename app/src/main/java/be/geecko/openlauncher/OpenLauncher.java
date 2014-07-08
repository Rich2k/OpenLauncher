package be.geecko.openlauncher;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.launcher3.HolographicLinearLayout;
import com.android.launcher3.Launcher;

import be.geecko.openlauncher.UI.SearchBar;

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
public class OpenLauncher extends Launcher {

    @Override
    protected boolean hasCustomContentToLeft() {
        return true; //todo: as a setting
    }

    @Override
    protected void addCustomContentToLeft() {
        CustomContent customContent = (CustomContent) getLayoutInflater().inflate(
                R.layout.custom_content, null, false);
        this.addToCustomContentPage(customContent, customContent, "Custom Content");
    }

    @Override
    protected boolean updateGlobalSearchIcon() {
        String searchEngine = PreferenceManager
                .getDefaultSharedPreferences(this).getString("search_engine", "DuckDuckGo");
        switch (searchEngine) {
            case "Google":
                return super.updateGlobalSearchIcon();
            default:
                final View searchButtonContainer =
                        findViewById(com.android.launcher3.R.id.search_button_container);
                final ImageView searchButton =
                        (ImageView) findViewById(com.android.launcher3.R.id.search_button);

                if (searchButtonContainer != null) {
                    searchButtonContainer.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    searchButton.setImageResource(R.drawable.ic_home_search_normal_holo);
                }
                return true;
        }
    }


    @Override
    protected boolean updateVoiceSearchIcon(boolean searchVisible) {
        String searchEngine = PreferenceManager
                .getDefaultSharedPreferences(this).getString("search_engine", "DuckDuckGo");
        switch (searchEngine) {
            case "Google":
                return super.updateVoiceSearchIcon(searchVisible);
            default:
                final HolographicLinearLayout voiceButtonContainer = (HolographicLinearLayout)
                        findViewById(com.android.launcher3.R.id.voice_button_container);
                final ImageView voiceButton =
                        (ImageView) findViewById(com.android.launcher3.R.id.voice_button);
                if (searchVisible) {
                    if (voiceButtonContainer != null) {
                        voiceButtonContainer.setVisibility(View.VISIBLE);
                    }
                    if (voiceButton != null) {
                        voiceButton.setVisibility(View.VISIBLE);
                        voiceButton.setImageResource(
                                com.android.launcher3.R.drawable.ic_home_voice_search_holo
                        );
                        voiceButton.invalidate();
                    }
                    updateVoiceButtonProxyVisible(false);
                } else {
                    if (voiceButtonContainer != null) voiceButtonContainer.setVisibility(View.GONE);
                    if (voiceButton != null) voiceButton.setVisibility(View.GONE);
                    updateVoiceButtonProxyVisible(true);
                }
                return searchVisible;
        }
    }

    @Override
    public boolean onSearchRequested() {
        moveToCustomContentScreen(true);
        final View searchBar = findViewById(R.id.search_bar);
        searchBar.post(new Runnable() {
            public void run() {
                searchBar.requestFocusFromTouch();
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(searchBar, 0);
            }
        });
        return true;
    }

    @Override
    public void startVoice() {
        String searchEngine = PreferenceManager
                .getDefaultSharedPreferences(this).getString("search_engine", "DuckDuckGo");
        switch (searchEngine) {
            case "Google":
                super.startVoice();
                break;
            default:
                Toast.makeText(this, "voice", Toast.LENGTH_SHORT).show();
                //todo
                break;
        }
    }

    @Override
    protected void startSettings() {
        startActivity(new Intent(this, LauncherSettings.class));
    }

    public void clearSearchBar(View view) {
        ((SearchBar) findViewById(R.id.search_bar)).setText("");
    }
}