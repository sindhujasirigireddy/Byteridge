/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.mindorks.framework.mvvm.ui.main;

import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;
import androidx.databinding.ObservableField;
import com.androidnetworking.widget.ANImageView;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.data.model.db.Option;
import com.mindorks.framework.mvvm.data.model.others.QuestionCardData;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import java.util.HashMap;

/**
 * Created by amitshekhar on 08/07/17.
 */

@NonReusable
@Layout(R.layout.card_layout)
public class QuestionCard {

    @View(R.id.btn_option_1)
    private Button mOption1Button;

    @View(R.id.btn_option_2)
    private Button mOption2Button;

    @View(R.id.btn_option_3)
    private Button mOption3Button;

    @View(R.id.btn_option_4)
    private Button mOption4Button;

    @View(R.id.iv_pic)
    private ANImageView mPicImageView;

    private QuestionCardData mQuestionCardData;

    private ObservableField<HashMap<Integer, Boolean>> mAttemptedQuestions;

    @View(R.id.tv_question_txt)
    private TextView mQuestionTextView;

    public QuestionCard(QuestionCardData questionCardData, ObservableField<HashMap<Integer, Boolean>> attemptedQuestions) {
        mQuestionCardData = questionCardData;
        mAttemptedQuestions = attemptedQuestions;
    }

    @Click(R.id.btn_option_1)
    public void onOption1Click() {
        setResult(0);
        showCorrectOptions();
    }

    @Click(R.id.btn_option_2)
    public void onOption2Click() {
        setResult(1);
        showCorrectOptions();
    }

    @Click(R.id.btn_option_3)
    public void onOption3Click() {
        setResult(2);
        showCorrectOptions();
    }

    @Click(R.id.btn_option_4)
    public void onOption4Click() {
        setResult(3);
        showCorrectOptions();
    }

    private void setResult(int questionIndex) {
        if (!mQuestionCardData.mShowCorrectOptions) {
            setAttemptedTestResultsData(Integer.parseInt(mQuestionCardData.question.id.toString()), mQuestionCardData.options.get(questionIndex).isCorrect);
        }
    }

    public void setAttemptedTestResultsData(int question, boolean isCorrect) {
        HashMap<Integer, Boolean> data = mAttemptedQuestions.get();
        data.put(question, isCorrect);
        mAttemptedQuestions.set(data);
        mAttemptedQuestions.notifyChange();
    }

    @Resolve
    private void onResolved() {
        mQuestionTextView.setText(mQuestionCardData.question.questionText);
        if (mQuestionCardData.mShowCorrectOptions) {
            showCorrectOptions();
        }

        int optionCount = mQuestionCardData.options.size();
        if (optionCount > 3) {
            mOption4Button.setVisibility(android.view.View.VISIBLE);
        } else {
            mOption4Button.setVisibility(android.view.View.GONE);
        }

        for (int i = 0; i < optionCount; i++) {
            Button button = null;
            switch (i) {
                case 0:
                    button = mOption1Button;
                    break;
                case 1:
                    button = mOption2Button;
                    break;
                case 2:
                    button = mOption3Button;
                    break;
                case 3:
                    button = mOption4Button;
            }

            if (button != null) {
                button.setText(mQuestionCardData.options.get(i).optionText);
            }

            if (mQuestionCardData.question.imgUrl != null) {
                mPicImageView.setImageUrl(mQuestionCardData.question.imgUrl);
            }
        }
    }

    private void showCorrectOptions() {
        mQuestionCardData.mShowCorrectOptions = true;
        int optionCount = mQuestionCardData.options.size();
        for (int i = 0; i < optionCount; i++) {
            Option option = mQuestionCardData.options.get(i);
            Button button = null;
            switch (i) {
                case 0:
                    button = mOption1Button;
                    break;
                case 1:
                    button = mOption2Button;
                    break;
                case 2:
                    button = mOption3Button;
                    break;
                case 3:
                    button = mOption4Button;
                    break;
            }
            if (button != null) {
                if (option.isCorrect) {
                    button.setBackgroundColor(Color.GREEN);
                } else {
                    button.setBackgroundColor(Color.RED);
                }
            }
        }
    }
}
