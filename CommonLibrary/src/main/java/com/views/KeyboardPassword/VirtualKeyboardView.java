package com.views.KeyboardPassword;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gun0912.tedpicker.R;
import com.views.KeyboardPassword.adapter.KeyBoardAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 虚拟键盘
 */
public class VirtualKeyboardView extends RelativeLayout implements View.OnClickListener{

    private final View view;
    private TextView tv_clean_up;
    private TextView tv_backspace;
    private TextView tv_confirm;
    Context context;

    //因为就6个输入框不会变了，用数组内存申请固定空间，比List省空间（自己认为）
    private GridView gridView;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能

    private ArrayList<Map<String, String>> valueList;    //有人可能有疑问，为何这里不用数组了？
    //因为要用Adapter中适配，用数组不能往adapter中填充


    /** 外部传入的文本编辑器帮助显示输入结果 */
    private EditText textAmount;
    private InputCallback inputCallback;
    private ConfirmCallback confirmCallback;

    private AdapterView.OnItemClickListener onItemClickListener;

    private boolean isPassWord = false;
    private KeyBoardAdapter keyBoardAdapter;
    private boolean isEnabled = true;

    public VirtualKeyboardView(Context context) {
        this(context, null);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isEnabled = true;
        this.context = context;
        view = View.inflate(context, R.layout.layout_virtual_keyboard, null);

        valueList = new ArrayList<>();


        gridView = (GridView) view.findViewById(R.id.gv_keybord);

        tv_confirm = (TextView)view.findViewById(R.id.tv_confirm);
        tv_clean_up = (TextView)view.findViewById(R.id.tv_clean_up);
        tv_backspace = (TextView)view.findViewById(R.id.tv_backspace);

        initValueList();

        setupView();
        removeAllViews();
        addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);      //必须要，不然不显示控件
    }


    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }

    private void initValueList() {

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 12) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 10) {
                map.put("name", ".");
            }
            valueList.add(map);
        }
    }

    public GridView getGridView() {
        return gridView;
    }

    private void setupView() {
        keyBoardAdapter = new KeyBoardAdapter(context, valueList, gridView);
        gridView.setAdapter(keyBoardAdapter);
        onItemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if(!isEnabled){
                    return;
                }

                if(textAmount != null){
                    if (position < 11 && position != 9) {    //点击0~9按钮

                        String amount = textAmount.getText().toString().trim();
                        amount = amount + valueList.get(position).get("name");

                        textAmount.setText(amount);

                        Editable ea = textAmount.getText();
                        textAmount.setSelection(ea.length());
                    } else {

                        if (position == 9) {      //点击 . 键
                            String amount = textAmount.getText().toString().trim();
                            if (!amount.contains(".")) {
                                amount = amount + valueList.get(position).get("name");
                                textAmount.setText(amount);

                                Editable ea = textAmount.getText();
                                textAmount.setSelection(ea.length());
                            }
                        }

                        if (position == 11) {      //点击退格键
                            String amount = textAmount.getText().toString().trim();
                            if (amount.length() > 0) {
                                amount = amount.substring(0, amount.length() - 1);
                                textAmount.setText(amount);

                                Editable ea = textAmount.getText();
                                textAmount.setSelection(ea.length());
                            }
                        }
                    }
                }else{
                    if(inputCallback != null){
                        String amount = "";
                        if (position < 11 && position != 9) {    //点击0~9按钮
                            amount = amount + valueList.get(position).get("name");
                        } else {
                            if (position == 9) {      //点击 . 键
                                if (!amount.contains(".")) {
                                    amount = amount + valueList.get(position).get("name");
                                }
                            }
                            if (position == 11) {      //点击退格键
                                amount = "d";
                            }
                        }
                        inputCallback.callback(amount);
                    }
                }
            }
        };

        gridView.setOnItemClickListener(onItemClickListener);
        tv_backspace.setOnClickListener(this);
        tv_clean_up.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }





    @Override
    public void onClick(View v) {

        if(!isEnabled){
            return;
        }

        int i = v.getId();
        if (i == R.id.tv_confirm) {
            onPublicKeyClick("ok");

        } else if (i == R.id.tv_clean_up) {
            onPublicKeyClick("c");

        } else if (i == R.id.tv_backspace) {
            onPublicKeyClick("d");

        } else {
        }
    }

    private void onPublicKeyClick(String s) {
        if (textAmount != null) {
            String amount = textAmount.getText().toString().trim();
            switch (s) {
                case "ok" ://确认按钮
                    if(confirmCallback!=null){
                        confirmCallback.onConfirmCallback();
                    }
                    break;
                case "c" ://清除按钮
                    if (amount.length() > 0) {
                        amount = "";
                        textAmount.setText(amount);
                        Editable ea = textAmount.getText();
                        if(ea != null){
                            textAmount.setSelection(ea.length());
                        }
                    }
                    break;
                case "d" ://退格(删除)按钮
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        textAmount.setText(amount);
                        Editable ea = textAmount.getText();
                        textAmount.setSelection(ea.length());
                    }
                    break;

                default:
                    break;
            }


        } else {
            if (inputCallback != null) {
                if("ok".equals(s)){
                    if(confirmCallback != null){
                        confirmCallback.onConfirmCallback();
                    }
                }else{
                    inputCallback.callback(s);
                }
            }
        }

    }

    /**
     * 输入回调,如果没有传EditText就传入这个回调,把出入内容传出去
     */
    public interface InputCallback{

        /**
         * 回调
         * @param s 当参数为d:退格删除、c:清除
         */
        void callback(String s);
    }

    /**
     * 确认键回调
     */
    public interface ConfirmCallback{
        void onConfirmCallback();
    }

    /**
     * 初始化控件,参数isPassWord 为false的情况 inputCallback 传入 null 需传editText   或者相反
     * @param isPassWord
     * @param editText
     * @param inputCallback
     * @param confirmCallback
     */
    public void init(boolean isPassWord, String confirmBtnTips, EditText editText, InputCallback inputCallback, ConfirmCallback confirmCallback){
        setPassWord(isPassWord, confirmBtnTips);
        textAmount = editText;
        this.inputCallback = inputCallback;
        //if(this.confirmCallback == null && this.confirmCallback!= confirmCallback){
            this.confirmCallback = confirmCallback;
        //}
    }

    public ConfirmCallback getConfirmCallback() {
        return confirmCallback;
    }

    public void setConfirmCallback(ConfirmCallback confirmCallback) {
        this.confirmCallback = confirmCallback;
    }

    public EditText getTextAmount() {
        return textAmount;
    }

    public void setTextAmount(EditText textAmount) {
        this.textAmount = textAmount;
    }

    public InputCallback getInputCallback() {
        return inputCallback;
    }

    public void setInputCallback(InputCallback inputCallback) {
        this.inputCallback = inputCallback;
    }




    public boolean isPassWord() {
        return isPassWord;
    }

    public void setPassWord(boolean passWord, String confirmBtnTips) {
        isPassWord = passWord;
//        if(isPassWord){
//            tv_confirm.setText("低分抵扣：");
//        }else{
//            tv_confirm.setText("使用积分抵扣");
//        }
        tv_confirm.setText(confirmBtnTips);
        keyBoardAdapter.setPassWord(isPassWord);
    }

    /**
     * 确认按钮样式选中修改
     * @param isSelect
     */
    public void setConfirmButtonSelect(boolean isSelect){
        tv_confirm.setSelected(isSelect);
    }


    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
