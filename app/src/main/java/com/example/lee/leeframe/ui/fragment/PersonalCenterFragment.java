package com.example.lee.leeframe.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lee.leeframe.R;
import com.example.lee.leeframe.app.AppManager;
import com.example.lee.leeframe.app.AppSp;
import com.example.lee.leeframe.base.BaseFragment;
import com.example.lee.leeframe.bean.UploadPhotoBean;
import com.example.lee.leeframe.bean.requestbean.UpdateImageRequestBean;
import com.example.lee.leeframe.databinding.FragmentPersonalcenterBinding;
import com.example.lee.leeframe.mvp.contract.PersonalCenterContract;
import com.example.lee.leeframe.mvp.presenter.PersonalCenterPresenter;
import com.example.lee.leeframe.ui.activity.LoginActivity;
import com.example.lee.leeframe.utils.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;

public class PersonalCenterFragment extends BaseFragment<FragmentPersonalcenterBinding>
        implements PersonalCenterContract.View {

    private PersonalCenterPresenter personalCenterPresenter;
    private Dialog dialog;
    private final int IMAGE_PICK = 10001;

    @Override
    public void showError(String msg) {
        ToastUtils.showShortToast(getActivity(), msg);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personalcenter;
    }

    @Override
    protected void initView() {

        dialog = new Dialog(getActivity(), R.style.SetHeaderDialog);

        // 游客模式不给看
        if (TextUtils.isEmpty(AppSp.getAccessToken02())) {
            AppManager.getAppManager().goToAndFinishAll(getActivity(), LoginActivity.class);
            return;
        }
    }


    @Override
    protected void initEventAndData() {

        personalCenterPresenter = new PersonalCenterPresenter(this);
        mBinding.rlHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        mBinding.btnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSp.clearUser();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                AppManager.getAppManager().AppExit(getActivity(), true);
            }
        });

    }

    private void showDialog() {

        // 1.填充Dialog布局
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.dialog_setheader, null);
        dialog.setContentView(dialogView);

        // 2. 设置Dialog宽高
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = (int) (getActivity().getWindowManager().getDefaultDisplay().getWidth() * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // 3. 把属性添加到Dialog
        window.setAttributes(lp);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

        dialogView.findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeCamera();
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeSelect();
                dialog.dismiss();
            }
        });


    }

    private void takeCamera() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(1);
        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, IMAGE_PICK);
    }

    private void takeSelect() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setShowCamera(false);
        imagePicker.setSelectLimit(1);
        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICK) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mLoadingDialog.show();
                // 上传照片（拿到图片的url 或者 FileId  请求参见 drawable-xhdpi -- interface_photo_upload.png ）
                personalCenterPresenter.getImageString(images, getActivity());
            }
        }
    }

    @Override
    public void onUploadImageSuccess(ArrayList<ImageItem> images, UploadPhotoBean uploadPhotoBean) {
        Glide.with(this).load(images.get(0).path).into(mBinding.civHeader);
        // 更换图片
        personalCenterPresenter.updateImage(AppSp.getAccessToken(),
                new UpdateImageRequestBean(uploadPhotoBean.getFileId() + "", "006"));
    }

    @Override
    public void updateImageSuccess(Object object) {

        if (mLoadingDialog != null) {
            mLoadingDialog.dissmiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if( personalCenterPresenter != null){
            personalCenterPresenter.unSubscribe();
        }
    }
}
