package com.nick.mpv.io.consumer;


import android.graphics.Bitmap;


import com.nick.mpv.io.M3SDK;
import com.nick.mpv.io.request.GetRequest;
import com.nick.mpv.io.request.MultipartRequest;
import com.nick.mpv.io.request.Request;
import com.nick.mpv.io.util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 发起Http的请求类
 */
public class ConvertRequestFunction implements Function<Request, ObservableSource<ResponseBody>> {

    @Override
    public ObservableSource<ResponseBody> apply(@NonNull Request request) {
        if (request instanceof MultipartRequest) {
            return sendMultipartBody((MultipartRequest) request);
        } else if (request instanceof GetRequest) {
            return sendGetBody((GetRequest) request);
        } else {
            return sendRequestBody(request);
        }
    }

    /**
     * Post方式
     *
     * @param request
     */
    public static Observable<ResponseBody> sendRequestBody(Request request) {
        String content = Util.getGson().toJson(request.getParams());
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), content);
        return M3SDK.INSTANCE.call20.post(request.getUrl(), body, request.getHeaders());
    }

    /**
     * Get方式
     *
     * @param request
     * @return
     */
    private static Observable<ResponseBody> sendGetBody(GetRequest request) {
        return M3SDK.INSTANCE.call20.get(request.getUrl(), request.getParams(), request.getHeaders());
    }

    /**
     * 文件+文本 表单方式传输
     * <p>
     * 图片格式
     * image/gif ：   gif图片格式 .gif
     * image/jpeg ：  jpg图片格式 .jpg
     * image/png：    png图片格式 .png
     * <p>
     * 视频格式
     * video/mpeg4 :   视频mp4格式 .mp4
     * video/x-mpg :   视频mpa格式 .mpg
     * video/x-mpeg :  视频mpeg格式 .mpeg
     * video/mpg :     视频mpg格式 .mpg
     * <p>
     * 音频格式
     * audio/mp3 :     音频mp3格式 .mp3
     * audio/rn-mpeg : 音频mpga格式 .mpga
     *
     * @param request
     */
    private static Observable<ResponseBody> sendMultipartBody(MultipartRequest request) {
        ArrayList<MultipartBody.Part> partArray = new ArrayList<>();

        ArrayList<MultipartRequest.FileType> fileTypes = request.getFileParams();
        int size = null == fileTypes ? 0 : fileTypes.size();
        for (int i = 0; i < size; i++) {
            MultipartRequest.FileType fileType = fileTypes.get(i);
            String key = null;
            MediaType mediaType = null;

            if (fileType instanceof MultipartRequest.ImageType) {
                key = "image-" + System.currentTimeMillis();
                if (Util.parseFileFormat(fileType.getFile().getAbsolutePath()) == Bitmap.CompressFormat.PNG) {
                    mediaType = MediaType.parse("image/png");
                } else {
                    mediaType = MediaType.parse("image/jpeg");
                }
            } else if (fileType instanceof MultipartRequest.AudioType) {
                key = "audio-" + System.currentTimeMillis();
                mediaType = MediaType.parse("audio/*");

            } else if (fileType instanceof MultipartRequest.VideoType) {
                key = "video-" + System.currentTimeMillis();
                mediaType = MediaType.parse("video/*");
            }

            if (null == key || null == mediaType) {
                continue;
            }

            RequestBody body = RequestBody.create(mediaType, fileType.getFile());
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, fileType.getFileName(), body);
            partArray.add(part);

        }

        return M3SDK.INSTANCE.call20.post(request.getUrl(), partArray, request.getHeaders());
    }
}

