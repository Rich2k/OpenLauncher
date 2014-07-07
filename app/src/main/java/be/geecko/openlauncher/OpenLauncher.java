package be.geecko.openlauncher;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

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
        return true;
    }

    @Override
    protected void populateCustomContentContainer() {
        CustomContent customContent = (CustomContent) getLayoutInflater().inflate(
                R.layout.custom_content, null);
        this.addToCustomContentPage(customContent, customContent, "Custom Content");
    }

    @Override
    public View getQsbBar() {
        ViewGroup mSearchDropTargetBar = this.getSearchBar();
        View mQsb = findViewById(R.id.search_shortcut);
        if (mQsb == null && mSearchDropTargetBar != null)
            mQsb = getLayoutInflater().inflate(R.layout.search_bar, mSearchDropTargetBar, true);
        return mQsb;
    }

    @Override
    protected boolean updateGlobalSearchIcon() {
        final View searchButtonContainer = findViewById(R.id.search_button_container);
        final ImageView searchButton = (ImageView) findViewById(R.id.search_button);
        if (searchButtonContainer != null) searchButtonContainer.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);
        return true;
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
        Toast.makeText(this, "voice", Toast.LENGTH_SHORT).show();
        //todo
    }

    @Override
    protected boolean hasSettings() {
        return true;
    }

    @Override
    protected void startSettings() {
        //todo
    }

    public void clearSearchBar(View view) {
        ((SearchBar) findViewById(R.id.search_bar)).setText("");
    }

}
