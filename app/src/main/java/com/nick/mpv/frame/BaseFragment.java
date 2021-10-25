package com.nick.mpv.frame;

import com.nick.mpv.frame.base.AndBaseFragment;
import com.nick.mpv.frame.base.BasePresenter;
import com.nick.mpv.frame.base.BaseView;


public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends AndBaseFragment<V, P> {
    public BaseFragment() {
    }

    public void showDialog() {
        this.showDialog("");
    }

    public void showDialog(String msg) {
        if (this.getActivity() instanceof BaseActivity) {
            ((BaseActivity) this.getActivity()).showDialog(msg);
        }
    }

    public void dismissDialog() {
        if (this.getActivity() instanceof BaseActivity) {
            ((BaseActivity) this.getActivity()).dismissDialog();
        }
    }

    public void onDestroyView() {
        this.dismissDialog();
        super.onDestroyView();
    }
}
