package com.example.lee.leeframe.mvp.presenter;

import android.app.Activity;

import com.example.lee.leeframe.api.http.ErrorHandler;
import com.example.lee.leeframe.api.http.PayLoad;
import com.example.lee.leeframe.base.BasePresenter;
import com.example.lee.leeframe.bean.UploadPhotoBean;
import com.example.lee.leeframe.bean.requestbean.UpdateImageRequestBean;
import com.example.lee.leeframe.mvp.contract.PersonalCenterContract;
import com.example.lee.leeframe.mvp.model.PersonalCenterModel;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.functions.Action1;


public class PersonalCenterPresenter extends BasePresenter implements PersonalCenterContract.Presenter {

    private PersonalCenterContract.View view;
    private PersonalCenterModel model;

    public PersonalCenterPresenter(PersonalCenterContract.View view) {
        this.view = view;
        this.model = new PersonalCenterModel();
    }

    @Override
    public void getImageString(final ArrayList<ImageItem> images, final Activity activity) {

        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), new File(images.get(0).path));
        // "file" 为字段名
        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", "file.png", photoRequestBody);
        addSubscription(model.getImageString(photo, images.get(0).width, images.get(0).height, 1.0f, activity)
                .map(new PayLoad<UploadPhotoBean>())
                .subscribe(new Action1<UploadPhotoBean>() {
                    @Override
                    public void call(UploadPhotoBean uploadPhotoBean) {
                        view.onUploadImageSuccess(images, uploadPhotoBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ErrorHandler.e(throwable, view);
                    }
                }));
    }


    @Override
    public void updateImage(String token, UpdateImageRequestBean updateImageBean) {
        addSubscription(model.updateImage(token, updateImageBean)
                .map(new PayLoad<>()).subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        view.updateImageSuccess(o);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ErrorHandler.e(throwable, view);
                    }
                }));
    }
}
