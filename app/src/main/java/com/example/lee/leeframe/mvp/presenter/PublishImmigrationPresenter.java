package com.example.lee.leeframe.mvp.presenter;

import com.example.lee.leeframe.api.http.ErrorHandler;
import com.example.lee.leeframe.api.http.PayLoad;
import com.example.lee.leeframe.base.BasePresenter;
import com.example.lee.leeframe.bean.ImmigrationTagBean;
import com.example.lee.leeframe.bean.UploadPhotoBean;
import com.example.lee.leeframe.bean.requestbean.PublishImmigrationRequestBean;
import com.example.lee.leeframe.mvp.contract.PublishImmigrationContract;
import com.example.lee.leeframe.mvp.model.PublishImmigrationModel;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.functions.Action1;


public class PublishImmigrationPresenter extends BasePresenter implements
        PublishImmigrationContract.Presenter {

    private PublishImmigrationContract.View view;
    private PublishImmigrationModel model;

    public PublishImmigrationPresenter(PublishImmigrationContract.View view) {
        this.view = view;
        model = new PublishImmigrationModel();
    }

    @Override
    public void publishImmigration(String token, PublishImmigrationRequestBean publishImmigrationRawBean) {
        addSubscription(model.publishImmigration(token, publishImmigrationRawBean)
                .map(new PayLoad<Integer>())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        view.publishImmigrationResult(integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ErrorHandler.e(throwable, view);
                    }
                }));
    }

    @Override
    public void getImmigrationTag() {
        addSubscription(model.getImmigrationTag()
                .map(new PayLoad<List<ImmigrationTagBean>>())
                .subscribe(new Action1<List<ImmigrationTagBean>>() {
                    @Override
                    public void call(List<ImmigrationTagBean> integer) {
                        view.getTagResult(integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ErrorHandler.e(throwable, view);
                    }
                }));
    }


    @Override
    public void getImageString(String token, ImageItem imageItem) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), new File(imageItem.path));
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "file.png", requestBody);
        addSubscription(model.getImageString(token, part, 1, 1, 1.0f)
                .map(new PayLoad<UploadPhotoBean>())
                .subscribe(new Action1<UploadPhotoBean>() {
                    @Override
                    public void call(UploadPhotoBean uploadPhotoBean) {
                        view.getImageStringResult(uploadPhotoBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ErrorHandler.e(throwable, view);
                    }
                }));
    }
}
