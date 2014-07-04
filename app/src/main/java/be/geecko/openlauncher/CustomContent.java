package be.geecko.openlauncher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.launcher3.Launcher;
import com.android.launcher3.SearchDropTargetBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    //TODO voice

    //TODO rework cards interface
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

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() { /*
        OpenLauncher openLauncher = (OpenLauncher) getContext();
        SearchDropTargetBar searchBar = (SearchDropTargetBar) openLauncher.findViewById(
                com.android.launcher3.R.id.search_drop_target_bar);
        final View searchButtonContainer = openLauncher.findViewById(R.id.search_button_container);
        if (searchButtonContainer != null) searchButtonContainer.setVisibility(View.VISIBLE);
        searchBar.showSearchBar(true); */
    }

    @Override
    public void onScrollProgressChanged(float progress) {
        boolean hide = progress > 0.5f;
        OpenLauncher openLauncher = (OpenLauncher) getContext();
        SearchDropTargetBar searchBar = (SearchDropTargetBar) openLauncher.findViewById(
                com.android.launcher3.R.id.search_drop_target_bar);
        if (hide)
            searchBar.hideSearchBar(false);
        else
            searchBar.showSearchBar(true);
        final View searchButtonContainer = openLauncher.findViewById(R.id.search_button_container);
        if (searchButtonContainer != null) searchButtonContainer.setVisibility(
                hide ? View.GONE : View.VISIBLE
        );
    }
}
