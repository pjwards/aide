package com.pjwards.aide.domain.builder;

import com.pjwards.aide.domain.Assets;
import org.springframework.test.util.ReflectionTestUtils;

public class AssetsBuilder {
    private Assets model;

    public AssetsBuilder(){
        model = new Assets();
    }

    public AssetsBuilder id(Long id){
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public AssetsBuilder fileName(String fileName){
        model.update(fileName, model.getRealPath(), model.getFileSize(), model.getDownloadCount());
        return this;
    }

    public AssetsBuilder realPath(String realPath){
        model.update(model.getFileName(), realPath, model.getFileSize(), model.getDownloadCount());
        return this;
    }

    public AssetsBuilder fileSize(Long fileSize){
        model.update(model.getFileName(), model.getRealPath(), fileSize, model.getDownloadCount());
        return this;
    }

    public AssetsBuilder downloadCount(Integer downloadCount){
        model.update(model.getFileName(), model.getRealPath(), model.getFileSize(), downloadCount);
        return this;
    }

    public Assets build(){
        return model;
    }
}
