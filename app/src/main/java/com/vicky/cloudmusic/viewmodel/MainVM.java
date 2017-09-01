package com.vicky.cloudmusic.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.vicky.cloudmusic.view.activity.MainActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.cloudmusic.view.fragment.MusicListFragment;

import java.util.ArrayList;
import java.util.List;


/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class MainVM extends AbstractViewModel<MainActivity> {

    List<Fragment> fragmentList = new ArrayList<>();
    @Override
    public void onBindView(@NonNull MainActivity view) {
        super.onBindView(view);
        init();
    }

    private void init(){
        fragmentList.add(new MusicListFragment());

        if (getView() != null)
            getView().setFragments(fragmentList);
    }

}
