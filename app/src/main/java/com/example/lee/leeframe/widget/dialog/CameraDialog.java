package com.example.lee.leeframe.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lee.leeframe.R;
import com.example.lee.leeframe.databinding.DialogCameraBinding;
import com.example.lee.leeframe.utils.UiUtil;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 照相机/相册 对话框
 */

public class CameraDialog extends Dialog {

    public CameraDialog(@NonNull Context context) {
        super(context);
    }

    public CameraDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Buidler {

        private Context mContext;
        private View.OnClickListener mClickListener;

        public Buidler(Context context) {
            mContext = context;
        }

        public Buidler clickListener(View.OnClickListener clickListener) {
            mClickListener = clickListener;
            return this;
        }

        public CameraDialog create() {

            final CameraDialog dialog = new CameraDialog(mContext, R.style.Dialog);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

            WindowManager.LayoutParams params = window.getAttributes();
            params.y = (int) UiUtil.getDp(mContext, 224);
            window.setAttributes(params);

            final DialogCameraBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.dialog_camera, null, false);
            binding.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btn_camera:
                            if (mClickListener != null) {
                                mClickListener.onClick(binding.btnCamera);
                            }
                            dialog.dismiss();
                            break;
                        case R.id.btn_select:
                            if (mClickListener != null) {
                                mClickListener.onClick(binding.btnSelect);
                            }
                            dialog.dismiss();
                            break;
                        default:
                            break;
                    }
                }
            });
            View view = binding.getRoot();

            dialog.setContentView(view);

            return dialog;
        }
    }

}
