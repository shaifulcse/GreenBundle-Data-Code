package com.example.shaiful.eventbundling.presenter;

import com.example.shaiful.eventbundling.contract.Contract.Presenter;

/**
 * Created by shaiful on 09/05/18.
 */

public class PresenterFactory {

    public static Presenter createPresenter(String type) {

        if(type.equals("noBundling")){
            return NoBundlingPresenter.getInstance();

        } else if(type.equals("bundling")) {
            return BundlingSendAllPresenter.getInstance();

        } else{
            return DroppingPresenter.getInstance();
        }

    }
}
