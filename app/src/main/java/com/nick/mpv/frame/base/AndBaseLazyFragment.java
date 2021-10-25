package com.nick.mpv.frame.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * zhengz Fragment 基类
 * 懒加载数据
 */
public abstract class AndBaseLazyFragment<V extends BaseView, P extends BasePresenter<V>> extends AndBaseFragment<V, P> {

    /**
     * 懒加载前，初始化控件
     * @param root
     * @param savedState
     */
    public abstract void onInitView(View root, Bundle savedState);

    /**
     * 懒加载数据（备注View初始化不在这边）
     */
    public abstract void onLazyData();

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onInitView(getView(), savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            onLazyData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }




}
