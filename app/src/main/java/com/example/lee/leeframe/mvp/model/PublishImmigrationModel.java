package com.example.lee.leeframe.mvp.model;

import com.example.lee.leeframe.api.http.BaseResponse;
import com.example.lee.leeframe.api.http.ObjectLoader;
import com.example.lee.leeframe.app.App;
import com.example.lee.leeframe.bean.ImmigrationTagBean;
import com.example.lee.leeframe.bean.UploadPhotoBean;
import com.example.lee.leeframe.bean.requestbean.PublishImmigrationRequestBean;
import com.example.lee.leeframe.mvp.contract.PublishImmigrationContract;

import java.util.ArrayList;
import java.util.List;
import okhttp3.MultipartBody;
import rx.Observable;

public class PublishImmigrationModel extends ObjectLoader implements PublishImmigrationContract.Model {


    @Override
    public Observable<BaseResponse<Integer>> publishImmigration(String token, PublishImmigrationRequestBean publishImmigrationRawBean) {
        return observe(App.getInstance().getApi().publishImmigration(token, publishImmigrationRawBean));
    }

    @Override
    public Observable<BaseResponse<List<ImmigrationTagBean>>> getImmigrationTag() {
        return observe(App.getInstance().getApi().getImmigrationTag());
    }

    @Override
    public Observable<BaseResponse<UploadPhotoBean>> getImageString(String token, MultipartBody.Part photo, int width, int height, float percent) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(photo);
        parts.add(MultipartBody.Part.createFormData("bizType", "008"));
        parts.add(MultipartBody.Part.createFormData("w", String.valueOf(width)));
        parts.add(MultipartBody.Part.createFormData("h", String.valueOf(height)));
        parts.add(MultipartBody.Part.createFormData("q", String.valueOf(percent)));
        return observe(App.getInstance().getApi().getImageString(parts,token));
    }
}
