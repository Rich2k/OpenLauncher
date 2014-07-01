package be.geecko.openlauncher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.launcher3.Launcher;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * This file is part of OpenLauncher for Android
 * Created by geecko on 1/07/14.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class CustomContent extends FrameLayout implements Launcher.CustomContentCallbacks {
    public CustomContent(Context context) {
        super(context);
        this.setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        this.addView(new TextView(context),0);
        ((TextView) this.getChildAt(0)).setText("Text");
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() {

    }

    @Override
    public void onScrollProgressChanged(float progress) {

    }
}
