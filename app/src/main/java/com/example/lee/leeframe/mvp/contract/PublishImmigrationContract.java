package com.example.lee.leeframe.mvp.contract;

import com.example.lee.leeframe.api.http.BaseResponse;
import com.example.lee.leeframe.base.BaseView;
import com.example.lee.leeframe.bean.ImmigrationTagBean;
import com.example.lee.leeframe.bean.UploadPhotoBean;
import com.example.lee.leeframe.bean.requestbean.PublishImmigrationRequestBean;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

public class PublishImmigrationContract {

    public interface View extends BaseView {

        void publishImmigrationResult(int i);
        void getTagResult(List<ImmigrationTagBean> list);
        void getImageStringResult(UploadPhotoBean uploadPhotoBean);
    }

    public interface Presenter {

        void publishImmigration(String token, PublishImmigrationRequestBean publishImmigrationRawBean);
        void getImmigrationTag();
        void getImageString(String token, ImageItem imageItem);
    }

    public interface Model {

        Observable<BaseResponse<Integer>> publishImmigration(String token, PublishImmigrationRequestBean publishImmigrationRawBean);
        Observable<BaseResponse<List<ImmigrationTagBean>>> getImmigrationTag();
        Observable<BaseResponse<UploadPhotoBean>> getImageString(String token, MultipartBody.Part photo, int width, int height, float percent);
    }

}
