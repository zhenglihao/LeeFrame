package com.example.lee.leeframe.mvp.contract;

import android.app.Activity;
import com.example.lee.leeframe.api.http.BaseResponse;
import com.example.lee.leeframe.base.BaseView;
import com.example.lee.leeframe.bean.UploadPhotoBean;
import com.example.lee.leeframe.bean.requestbean.UpdateImageRequestBean;
import com.lzy.imagepicker.bean.ImageItem;
import java.util.ArrayList;
import okhttp3.MultipartBody;
import rx.Observable;


public class PersonalCenterContract {

    public interface View extends BaseView {
        void onUploadImageSuccess(ArrayList<ImageItem> images, UploadPhotoBean uploadPhotoBean);
        void updateImageSuccess(Object object);
    }

    public interface Presenter{
        void getImageString(ArrayList<ImageItem> images, Activity activity);
        void updateImage(String token, UpdateImageRequestBean updateImageBean);
    }


    public interface Model{

        Observable<BaseResponse<UploadPhotoBean>> getImageString(MultipartBody.Part photo, int width, int height, float percent, Activity activity);
        Observable<BaseResponse<Object>> updateImage(String token, UpdateImageRequestBean updateImageBean);
    }


}
