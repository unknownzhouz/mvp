package com.nick.mpv.io.consumer;


import static com.nick.mpv.io.exception.CodeExp.ServerCode.SUCCESS;
import static com.nick.mpv.io.exception.CodeExp.ServerCode.DEFAULT;

import androidx.annotation.Nullable;


import com.nick.mpv.io.response.DataSet;
import com.nick.mpv.io.response.IResponse;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Http请求结果处理类
 */
public class ConsumerRequestNext implements Consumer<DataSet> {

    private IResponse iResponse;

    public ConsumerRequestNext(@Nullable IResponse response) {
        this.iResponse = response;
    }

    @Override
    public void accept(@NonNull DataSet dataSet) {
        if (null == iResponse) {
            return;
        }
        // 成功
        if (SUCCESS == dataSet.errorCode) {
            if (null != dataSet.t) {
                iResponse.onSuccess(SUCCESS, dataSet.errorMsg, dataSet.t);
            } else {
                iResponse.onSuccess(SUCCESS, dataSet.errorMsg, dataSet.data);
            }
            return;
        }

        // 失败
        if (DEFAULT == dataSet.errorCode) {
            iResponse.onFailure(new Exception(), dataSet.errorCode, "未知错误");
        } else {
            iResponse.onFailure(new Exception(), dataSet.errorCode, dataSet.errorMsg);
        }
    }


}
