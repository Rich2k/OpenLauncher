package be.geecko.openlauncher.UI;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import be.geecko.openlauncher.CustomContent;
import be.geecko.openlauncher.R;
import be.geecko.openlauncher.net.SuggestionsTask;

/**
 * This file is part of OpenLauncher for Android
 * Created by geecko on 3/07/14.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class SearchBar extends EditText implements TextView.OnEditorActionListener, TextWatcher {
    public SearchBar(Context context) {
        super(context);
        setOnEditorActionListener(this);
        addTextChangedListener(this);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnEditorActionListener(this);
        addTextChangedListener(this);
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnEditorActionListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return CustomContent.search(
                textView.getText().toString(),
                getContext());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String searchTerms = editable.toString();
        LinearLayout container = (LinearLayout) getRootView().findViewById(R.id.cards_container);
        if (searchTerms.length() > 0) {
            SuggestionsTask st = new SuggestionsTask(container);
            st.execute(searchTerms);
            setClearButton(false);
        } else {
            if (container.getChildAt(0) != null &&
                    "suggestion card".equals(container.getChildAt(0).getContentDescription()))
                container.removeViewAt(0);
            setClearButton(true);
        }
    }

    private void setClearButton(boolean flag) {
        RelativeLayout searchBox = (RelativeLayout) this.getParent();
        ImageButton clearButton = (ImageButton) searchBox.getChildAt(1);
        clearButton.setVisibility(!flag ? View.VISIBLE : View.INVISIBLE);
        this.setCompoundDrawablesWithIntrinsicBounds(0, 0, !flag ? 0 : R.drawable.ic_search, 0);
    }
}