package com.example.lee.leeframe.mvp.model;

import android.app.Activity;

import com.example.lee.leeframe.api.http.BaseResponse;
import com.example.lee.leeframe.api.http.ObjectLoader;
import com.example.lee.leeframe.app.App;
import com.example.lee.leeframe.app.AppSp;
import com.example.lee.leeframe.bean.UploadPhotoBean;
import com.example.lee.leeframe.bean.requestbean.UpdateImageRequestBean;
import com.example.lee.leeframe.mvp.contract.PersonalCenterContract;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;


public class PersonalCenterModel extends ObjectLoader implements PersonalCenterContract.Model {

    @Override
    public Observable<BaseResponse<UploadPhotoBean>> getImageString(MultipartBody.Part photo, int width, int height,
                                                                    float percent, Activity activity) {

        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(photo);
        parts.add(MultipartBody.Part.createFormData("bizType", "006"));
        parts.add(MultipartBody.Part.createFormData("w", String.valueOf(width)));
        parts.add(MultipartBody.Part.createFormData("h", String.valueOf(height)));
        parts.add(MultipartBody.Part.createFormData("q", String.valueOf(percent)));
        return observe(App.getInstance().getApi().getImageString(parts, AppSp.getMember().getToken()));

    }

    @Override
    public Observable<BaseResponse<Object>> updateImage(String token, UpdateImageRequestBean updateImageBean) {
        return observe(App.getInstance().getApi().updateImage(token, updateImageBean));
    }
}
