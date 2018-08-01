package com.example.lee.leeframe.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.lee.leeframe.R;
import com.example.lee.leeframe.app.AppSp;
import com.example.lee.leeframe.base.BaseActivity;
import com.example.lee.leeframe.base.recyclerView.OnItemClickListener;
import com.example.lee.leeframe.bean.ImmigrationTagBean;
import com.example.lee.leeframe.bean.UploadPhotoBean;
import com.example.lee.leeframe.bean.requestbean.PublishImmigrationRequestBean;
import com.example.lee.leeframe.databinding.ActivityToPublishImmigrationBinding;
import com.example.lee.leeframe.mvp.contract.PublishImmigrationContract;
import com.example.lee.leeframe.mvp.presenter.PublishImmigrationPresenter;
import com.example.lee.leeframe.ui.adapter.PublishImageListAdapter;
import com.example.lee.leeframe.utils.ToastUtils;
import com.example.lee.leeframe.utils.UiUtil;
import com.example.lee.leeframe.widget.dialog.CameraDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import java.util.ArrayList;
import java.util.List;

public class ToPublishImmigrationActivity extends BaseActivity<ActivityToPublishImmigrationBinding>
        implements PublishImmigrationContract.View, View.OnClickListener {

    private final int IMAGE_PICK = 10001;
    private final int MY_COLLECTION = 10000;
    private List<String> imagePaths;
    private ArrayList<ImageItem> images;
    private PublishImageListAdapter mAdapter;
    private PublishImmigrationPresenter publishImmigrationPresenter;
    private List<ImmigrationTagBean> immigrationTagBeans = new ArrayList<>();
    private int uploadImageNum = 0;
    private int id = -1;//项目或者活动的id
    private int type = 0; //1是项目，2是活动（选择项目活动后返回的） 0: 表示 无关联（即 没有选择 项目/活动）
    private List<Integer> tagId = new ArrayList<>();
    private List<Integer> fileIds = new ArrayList<>();

    @Override
    public void showError(String msg) {
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    protected ActivityToPublishImmigrationBinding createDataBinding(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_to_publish_immigration);
    }

    @Override
    protected void initView() {
        initActionBar();
        imagePaths = new ArrayList<>();
        mAdapter = new PublishImageListAdapter(this);
        mAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(String s, int position) {
                if (position == mAdapter.getItemCount() - 1 && TextUtils.isEmpty(s)) {
                    showPublishDialog();
                }
            }
        });
        mAdapter.addAll(imagePaths);
        mBinding.rv.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        mBinding.rv.setAdapter(mAdapter);
    }

    private void initActionBar() {
        ((TextView) findViewById(R.id.top_title1)).setText("发布移民圈");
        ((TextView) findViewById(R.id.top_right1)).setText("发布");
        findViewById(R.id.iv_back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.top_right1).setOnClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        publishImmigrationPresenter = new PublishImmigrationPresenter(this);
        mBinding.setOnClickListener(this);

        mBinding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBinding.tvCharacterNum.setText(s.length() + "/200");
            }
        });

        //点击界面最上面的tag监听
        mBinding.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (checkedId == group.getChildAt(i).getId()) {
                        tagId.set(0, immigrationTagBeans.get(i).getId());
                        mBinding.rg.getChildAt(i).setBackground(ActivityCompat.getDrawable(ToPublishImmigrationActivity.this, R.drawable.shape_publish_immigration_tag_bg));
                        ((RadioButton) mBinding.rg.getChildAt(i)).setTextColor(ActivityCompat.getColor(ToPublishImmigrationActivity.this, R.color.colorPrimaryDark));
                    } else {
                        mBinding.rg.getChildAt(i).setBackground(ActivityCompat.getDrawable(ToPublishImmigrationActivity.this, R.drawable.shape_publish_immigration_tag_gray_bg));
                        ((RadioButton) mBinding.rg.getChildAt(i)).setTextColor(ActivityCompat.getColor(ToPublishImmigrationActivity.this, R.color.default_text_color));
                    }
                }
            }
        });

        //获取tag
        publishImmigrationPresenter.getImmigrationTag();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_photo:
                showPublishDialog();
                break;
            case R.id.btn_camera://对话框——》拍摄
                takeCamera();
                break;
            case R.id.btn_select://对话框——》从相册选择
                takeSelect();
                break;
            //发布
            case R.id.top_right1:
                toPublish();
                break;
            default:
                break;

        }
    }


    //发布
    public void toPublish() {
        if (TextUtils.isEmpty(mBinding.etContent.getText().toString())) {
            ToastUtils.showShortToast(this, "请输入发布的内容");
            return;
        }

        if (mBinding.etContent.getText().toString().length() < 10) {
            ToastUtils.showShortToast(this, "内容不能少于10个字");
            return;
        }

        mLoadingDialog.show();
        if (images != null && images.size() > 0) {
            mLoadingDialog.show();
            for (int i = 0; i < images.size(); i++) {
                publishImmigrationPresenter.getImageString(AppSp.getAccessToken(), images.get(i));
            }
        } else {
            // 没有删上传图片
            publishImmigrationPresenter.publishImmigration(AppSp.getAccessToken(),
                    new PublishImmigrationRequestBean(id, type, tagId, mBinding.etContent.getText().toString(), fileIds));
        }
    }


    //获取tag结果
    @Override
    public void getTagResult(List<ImmigrationTagBean> list) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTagName().equals("精选")) {
                list.remove(i);
            }
        }
        immigrationTagBeans = list;
        tagId.add(list.get(0).getId()); // 默认是选中第一个tag
        if (list.size() > 0) {
            RadioButton radioButton;
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    radioButton = getRadioButton(true);
                    radioButton.setText(list.get(i).getTagName());
                } else {
                    radioButton = getRadioButton(false);
                    radioButton.setText(list.get(i).getTagName());
                }
                switch (i) {
                    case 0:
                        radioButton.setId(R.id.rb0);
                        break;
                    case 1:
                        radioButton.setId(R.id.rb1);
                        break;
                    case 2:
                        radioButton.setId(R.id.rb2);
                        break;
                    case 3:
                        radioButton.setId(R.id.rb3);
                        break;
                    case 4:
                        radioButton.setId(R.id.rb4);
                        break;
                    case 5:
                        radioButton.setId(R.id.rb5);
                        break;
                    case 6:
                        radioButton.setId(R.id.rb6);
                        break;
                    case 7:
                        radioButton.setId(R.id.rb7);
                        break;
                    case 8:
                        radioButton.setId(R.id.rb8);
                        break;
                    case 9:
                        radioButton.setId(R.id.rb9);
                        break;
                    case 10:
                        radioButton.setId(R.id.rb10);
                        break;
                    default:
                        break;
                }
                radioButton.setButtonDrawable(null);
                mBinding.rg.addView(radioButton);
            }
        }


    }

    //上传图片结果

    @Override
    public void getImageStringResult(UploadPhotoBean uploadPhotoBean) {
        uploadImageNum++;
        fileIds.add(Integer.valueOf(uploadPhotoBean.getFileId()));
        if (uploadImageNum == images.size()) {
            publishImmigrationPresenter.publishImmigration(AppSp.getAccessToken(), new PublishImmigrationRequestBean(id, type, tagId, mBinding.etContent.getText().toString(), fileIds));
        }
    }

    // 设置radioButton
    private RadioButton getRadioButton(boolean selected) {
        RadioButton radioButton = new RadioButton(this);
        if (selected) {
            radioButton.setTextColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));
            radioButton.setBackground(ActivityCompat.getDrawable(this, R.drawable.shape_publish_immigration_tag_bg));
        } else {
            radioButton.setTextColor(ActivityCompat.getColor(this, R.color.default_text_color));
            radioButton.setBackground(ActivityCompat.getDrawable(this, R.drawable.shape_publish_immigration_tag_gray_bg));
        }

        int dp7 = (int) UiUtil.getDp(this, 7);
        int dp10 = ((int) UiUtil.getDp(this, 10));
        radioButton.setPadding(dp10, dp7, dp10, dp7);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 25, 0);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    @Override
    public void publishImmigrationResult(int i) {
        if (mLoadingDialog != null) {
            mLoadingDialog.dissmiss();
        }
        ToastUtils.showShortToast(this, "发布成功!");
        finish();
    }

    private void showPublishDialog() {
        new CameraDialog.Buidler(this).clickListener(this).create().show();
    }

    private void takeCamera() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(1);
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, IMAGE_PICK);
    }

    private void takeSelect() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setShowCamera(false);
        imagePicker.setSelectLimit(3);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICK) {
                mBinding.rv.setVisibility(View.VISIBLE);
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (ImageItem image : images) {
                    imagePaths.add(imagePaths.size(), image.path);
                }

                if (imagePaths.size() > 3) {
                    imagePaths = imagePaths.subList(0, 3);
                }
                mAdapter.clear();
                mAdapter.addAll(imagePaths);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
