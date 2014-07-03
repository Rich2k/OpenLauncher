package be.geecko.openlauncher;

import android.view.LayoutInflater;
import android.view.View;

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

    //TODO add searchbar at the top
    //TODO red bar should not move when selection action bar is called

    @Override
    protected boolean hasCustomContentToLeft() {
        return true;
    }

    @Override
    protected void populateCustomContentContainer() {
        CustomContent customContent = (CustomContent)
                LayoutInflater.from(this).inflate(R.layout.custom_content, null);
        this.addToCustomContentPage(customContent, customContent, "Custom Content");
    }

    public void clearSearchBar(View view) {
        ((SearchBar) findViewById(R.id.search_bar)).setText("");
    }

}
