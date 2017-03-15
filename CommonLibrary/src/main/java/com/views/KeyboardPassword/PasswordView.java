package com.views.KeyboardPassword;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gun0912.tedpicker.R;


/**
 * Created by LiesLee on 16/10/9.
 */
public class PasswordView extends RelativeLayout {

    private LinearLayout view;
    Context context;

    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？

    private ImageView[] imgList;      //用数组保存6个TextView，为什么用数组？

    private int currentIndex = -1;    //用于记录当前输入密码格位置

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = (LinearLayout) inflater.inflate(R.layout.password_view, null);
        LinearLayoutCompat.LayoutParams layoutParams1 = new LinearLayoutCompat.LayoutParams(context, attrs);
        layoutParams1.height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
        layoutParams1.width = LinearLayoutCompat.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(layoutParams1);
        initView(view);
        addView(view);
    }

    private void initView(View view) {


        tvList = new TextView[6];

        imgList = new ImageView[6];

        tvList[0] = (TextView) view.findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) view.findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) view.findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) view.findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) view.findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) view.findViewById(R.id.tv_pass6);


        imgList[0] = (ImageView) view.findViewById(R.id.img_pass1);
        imgList[1] = (ImageView) view.findViewById(R.id.img_pass2);
        imgList[2] = (ImageView) view.findViewById(R.id.img_pass3);
        imgList[3] = (ImageView) view.findViewById(R.id.img_pass4);
        imgList[4] = (ImageView) view.findViewById(R.id.img_pass5);
        imgList[5] = (ImageView) view.findViewById(R.id.img_pass6);

    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {


        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().length() == 1) {
//                    pass.inputFinish(getPassword());    //接口中要实现的方法，完成密码输入完成后的响应逻辑
//                    removePassword();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    pass.inputFinish(getPassword());    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                }
            }
        });
    }

    /**
     * 获取密码
     * @return
     */
    public String getPassword(){
        String strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
        for (int i = 0; i < 6; i++) {
            strPassword += tvList[i].getText().toString().trim();
        }
        return strPassword.trim();
    }

    /**
     * 删除所有输入的密码
     */
    public void removePassword(){
        if(currentIndex == -1) return;
        for (int i = 0; i < 6; i++) {
            tvList[i].setText("");
            tvList[i].setVisibility(View.VISIBLE);
            imgList[i].setVisibility(View.INVISIBLE);
        }
        currentIndex = -1;
    }

    /**
     * 按退格删除一位密码
     */
    public void backspacePassword(){
        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界

            tvList[currentIndex].setText("");

            tvList[currentIndex].setVisibility(View.VISIBLE);
            imgList[currentIndex].setVisibility(View.INVISIBLE);

            currentIndex--;
        }
    }

    /**
     * 输入一位密码
     * @param s
     */
    public void addPassword(String s){
        if(!TextUtils.isEmpty(s)){
            if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界

                ++currentIndex;
                tvList[currentIndex].setText(s);

                tvList[currentIndex].setVisibility(View.INVISIBLE);
                imgList[currentIndex].setVisibility(View.VISIBLE);
            }
        }

    }
}
