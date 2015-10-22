package com.thewalkinggame.app.gestionJeu.QuizHandler;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thewalkinggame.app.R;

/**
 * Created by kevin on 27/03/2014.
 */
public class QuizFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quizactivity,container,false);
    }
}
