package com.nick.mpv.io.consumer;

import static com.nick.mpv.io.exception.CodeExp.ServerCode.SUCCESS;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nick.mpv.io.M3Params;
import com.nick.mpv.io.request.Request;
import com.nick.mpv.io.response.DataSet;
import com.nick.mpv.io.response.IResponse;
import com.nick.mpv.io.util.Util;

import java.io.IOException;
import java.lang.reflect.Type;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;


/**
 * 返回数据對象解析
 */
public class ConvertResponseFunction implements Function<ResponseBody, DataSet> {
    private Request request;
    private IResponse response;

    public ConvertResponseFunction(Request request, IResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 备注. 不能返回null， 会触发网络超时异常。
     *
     * @param responseBody
     * @return
     */
    @Override
    public DataSet apply(ResponseBody responseBody) {
        return responseParseString(responseBody);
    }

    private DataSet responseParseString(ResponseBody responseBody) {
        DataSet dataSet = new DataSet();
        String responseText = null;
        try {
            responseText = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(responseText)) {
            return dataSet;
        }

        JsonObject jsonObject = JsonParser.parseString(responseText).getAsJsonObject();
        dataSet.errorCode = jsonObject.get(M3Params.Response.RESULT_ID).getAsInt();
        dataSet.errorMsg = jsonObject.get(M3Params.Response.RESULT_MSG).getAsString();
        dataSet.data = jsonObject.get(M3Params.Response.RESULT_DATA).toString();

        // 数据返回成功时，反序列化数据
        if (dataSet.errorCode == SUCCESS) {
            Type type = Util.getType(response, 0);
            if (null != type && !TextUtils.isEmpty(dataSet.data)) {
                dataSet.t = Util.getGson().fromJson(dataSet.data, type);
            }
        }
        return dataSet;
    }

}