package com.asa.meta.rxhttp.subsciber;

import com.asa.meta.rxhttp.interfaces.ProgressDialog;

public abstract class ProgressDialogSubscriber extends JSONObjectSubscriber {
    private ProgressDialog progressDialog;

    public ProgressDialogSubscriber(String tag, ProgressDialog progressDialog) {
        super(tag);
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (progressDialog != null) {
            progressDialog.showProgressDialog();
        }
    }

    @Override
    public void onFinish() {
        if (progressDialog != null) {
            progressDialog.hideProgressDialog();
        }
    }

}
