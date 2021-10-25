package com.nick.mpv.io.request;

import com.nick.mpv.io.util.Util;
import com.trello.rxlifecycle3.LifecycleProvider;

import java.io.File;
import java.util.ArrayList;

/**
 * zhengz
 */
public class MultipartRequest extends Request {


    private ArrayList<FileType> files;


    public ArrayList<FileType> getFileParams() {
        return files;
    }


    @Override
    public String generateKey() {
        StringBuilder builder = new StringBuilder(getUrl());
        builder.append(getParams().toString());
        if (null != files && files.size() > 0) {
            builder.append(files.toString());
        }
        return Util.MD5.gen(builder.toString());
    }

    public static class Builder extends Request.Builder {
        private ArrayList<FileType> files = new ArrayList();

        public Builder(LifecycleProvider provider, String url) {
            super(provider, url);
        }

        public Builder putImage(File f) {
            return putImage(f.getName(), f);
        }

        public Builder putImage(String fName, File f) {
            files.add(new ImageType(fName, f));
            return this;
        }

        public Builder putVideo(File f) {
            return putVideo(f.getName(), f);
        }

        public Builder putVideo(String fName, File f) {
            files.add(new VideoType(fName, f));
            return this;
        }

        public Builder putAudio(File f) {
            return putAudio(f.getName(), f);
        }

        public Builder putAudio(String fName, File f) {
            files.add(new AudioType(fName, f));
            return this;
        }

        public MultipartRequest build() {
           MultipartRequest request = new MultipartRequest();
            copy(request);
            request.files = files;
            return request;
        }
    }

    public static abstract class FileType {
        private File file;
        private String fileName;

        private FileType(File file) {
            this(file.getName(), file);
        }

        private FileType(String fileName, File file) {
            this.file = file;
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }

        public File getFile() {
            return file;
        }
    }


    /**
     * 图片格式
     */
    public static class ImageType extends FileType {

        public ImageType(File file) {
            super(file);
        }

        public ImageType(String fileName, File file) {
            super(fileName, file);
        }
    }

    /**
     * 视频格式
     */
    public static class VideoType extends FileType {

        public VideoType(File file) {
            super(file);
        }

        public VideoType(String fileName, File file) {
            super(fileName, file);
        }
    }

    /**
     * 音频格式
     */
    public static class AudioType extends FileType {

        public AudioType(File file) {
            super(file);
        }

        public AudioType(String fileName, File file) {
            super(fileName, file);
        }
    }
}
