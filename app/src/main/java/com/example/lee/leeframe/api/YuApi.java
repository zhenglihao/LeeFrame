package com.example.lee.leeframe.api;

import java.util.List;

import com.example.lee.leeframe.api.http.BaseResponse;
import com.example.lee.leeframe.api.http.ListResult;
import com.example.lee.leeframe.bean.ImmigrationTagBean;
import com.example.lee.leeframe.bean.LoginBean;
import com.example.lee.leeframe.bean.UploadPhotoBean;
import com.example.lee.leeframe.bean.requestbean.PublishImmigrationRequestBean;
import com.example.lee.leeframe.bean.requestbean.ThirdLoginRequestBean;
import com.example.lee.leeframe.bean.requestbean.UpdateImageRequestBean;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 网络请求接口url
 */

public interface YuApi {

    /**
     * 方式1: @POST -- @Field
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("customer-login")
    Observable<BaseResponse<LoginBean>> login(@Field("username") String username,
                                              @Field("password") String password);

    /**
     * 方式2: @GET -- @Query
     *
     * @param contactPhone 邮箱/手机号
     * @return
     */
    @GET("pushSms")
    Observable<BaseResponse<Object>> pushSms(@Query("contactPhone") String contactPhone, @Query("appid") String appId);


    /**
     * 方式3 :@POST + @Headers -- @Body (注: post 传递是实体bean,不再是键值对; 区别方式1)
     *
     * @param requestBean
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("wechat-login")
    Observable<BaseResponse<LoginBean>> thirdLogin(@Body ThirdLoginRequestBean requestBean);


    /**
     * 方式4 :@POST + @Headers + url包含id -- @Body (注: 需要token, post 传递是实体bean,不再是键值对; @Path 传递id, 区别方式1)
     *
     * @param bean
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("consultant/{id}/auth")
    Observable<BaseResponse<String>> modifyIdentifyInfo(@Path("id") String id, @Body ThirdLoginRequestBean bean, @Header("Authorization") String token);


    /**
     * 方式5： 获取数据有做分页处理的（例如; 列表数据）
     *
     * @param orderStatus
     * @param offset
     * @param pageSize
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("customer/orders")
    Observable<BaseResponse<ListResult<LoginBean>>> getOrderList(@Header("Authorization") String token,
                                                                 @Query("orderStatus") String orderStatus,
                                                                 @Query("page") String offset,
                                                                 @Query("pageSize") String pageSize);

    /**
     * 方式6： 多图片（文件）上传 @Part()
     *
     * @param parts
     * @return
     */
    @Multipart
    @POST("common/upload")
    Observable<BaseResponse<UploadPhotoBean>> getImageString(@Part() List<MultipartBody.Part> parts, @Header("Authorization") String token);


    /**
     * 3.6.	更改头像006或背景图片014（raw）
     *
     * @param updateImageBean
     * @return
     */
    @POST("customer/addOrUpdateImage")
    Observable<BaseResponse<Object>> updateImage(@Header("Authorization") String token, @Body UpdateImageRequestBean updateImageBean);


    /**
     * 发布移民圈
     *
     * @param token
     * @param publishImmigrationRawBean
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("user/circles")
    Observable<BaseResponse<Integer>> publishImmigration(@Header("Authorization") String token, @Body PublishImmigrationRequestBean publishImmigrationRawBean);

    /**
     * 获取移民圈的tags
     *
     * @return
     */
    @GET("commons/immigrantCircleTag")
    Observable<BaseResponse<List<ImmigrationTagBean>>> getImmigrationTag();


    /**
     * 用户注册环信
     *
     * @param token
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("user/registerImUsers")
    Observable<BaseResponse<Object>> registerImUsers(@Header("Authorization") String token);

}
