package com.music.xiangdamuxic;


import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.jaeger.library.StatusBarUtil;
import com.music.xiangdamuxic.utils.Constant;
import com.music.xiangdamuxic.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends SuperActivity implements View.OnClickListener {

    private EditText mEtPassword;
    private EditText mEtPassword1;
    private EditText mEtEmail;


    /**
     * 账户输入框
     */
    private EditText mEtMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //ActionBar隐藏，透明化任务栏
        StatusBarUtil.setTransparent(this);

        //设置状态栏字体颜色为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //初始化用户名输入框
        initUserNameEditText();

        //初始化密码输入框
        initPasswordEditText();

        //初始化图片点击（清除用户名，密码，显示隐藏密码）
        initImageButton();

        //初始化登录按钮
        initRegistButton();

        //初始化邮箱输入框
        initUserEmailEditText();
    }

    /**
     * 初始化注册界面
     */
    private void initRegistButton() {
        //获取
        final ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        final Button Regist = (Button) findViewById(R.id.btn_regist);

        //设置点击（mProgressBar和button配合）
        Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtMobile.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "用户名为空", Toast.LENGTH_LONG);
                    //参数1：当前的上下文环境。可用getApplicationContext()或this
                    //参数2：要显示的字符串。
                    //参数3：显示的时间长短。Toast默认的有两个LENGTH_LONG(长)和LENGTH_SHORT(短)
                    toast.setGravity(Gravity.CENTER, 0, 1080);//设置提示框显示的位置

                    toast.show();//显示消息
                } else if (TextUtils.isEmpty(mEtEmail.getText()) || !isEmail(mEtEmail.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "请输入正确的邮箱", Toast.LENGTH_LONG);
                    //参数1：当前的上下文环境。可用getApplicationContext()或this
                    //参数2：要显示的字符串。
                    //参数3：显示的时间长短。Toast默认的有两个LENGTH_LONG(长)和LENGTH_SHORT(短)
                    toast.setGravity(Gravity.CENTER, 0, 1080);//设置提示框显示的位置

                    toast.show();//显示消息
                } else if (TextUtils.isEmpty(mEtPassword.getText()) || TextUtils.isEmpty(mEtPassword1.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "请输入正确的密码", Toast.LENGTH_LONG);
                    //参数1：当前的上下文环境。可用getApplicationContext()或this
                    //参数2：要显示的字符串。
                    //参数3：显示的时间长短。Toast默认的有两个LENGTH_LONG(长)和LENGTH_SHORT(短)
                    toast.setGravity(Gravity.CENTER, 0, 1080);//设置提示框显示的位置

                    toast.show();//显示消息
                } else if (!mEtPassword.getText().toString().trim().equals(mEtPassword1.getText().toString().trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "两次输入的密码不一致", Toast.LENGTH_LONG);
                    //参数1：当前的上下文环境。可用getApplicationContext()或this
                    //参数2：要显示的字符串。
                    //参数3：显示的时间长短。Toast默认的有两个LENGTH_LONG(长)和LENGTH_SHORT(短)
                    toast.setGravity(Gravity.CENTER, 0, 1080);//设置提示框显示的位置

                    toast.show();//显示消息
                } else {
                    // 设置用户名
                    Utils.putString(RegisterActivity.this, Constant.userNameSPKey, mEtMobile.getText().toString());
                    // 设置密码
                    Utils.putString(RegisterActivity.this, Constant.userPasswordSPKey, mEtPassword.getText().toString());
                    //设置邮箱
                    Utils.putString(RegisterActivity.this, Constant.userEmail, mEtEmail.getText().toString());


                    // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("user_name", mEtMobile.getText().toString().trim());
                    setResult(RESULT_OK, intent);

                    RegisterActivity.this.finish();
                }
            }
        });


    }

    /**
     * 初始化图片点击（清除用户名，密码，显示隐藏密码）
     */
    private void initImageButton() {
        findViewById(R.id.iv_clean_phone).setOnClickListener(this);
        findViewById(R.id.clean_password).setOnClickListener(this);
        findViewById(R.id.iv_show_pwd).setOnClickListener(this);

        findViewById(R.id.cleanr_password).setOnClickListener(this);
        findViewById(R.id.ri_show_pwd).setOnClickListener(this);
        findViewById(R.id.clean_email).setOnClickListener(this);
    }

    /**
     * 初始化密码输入框
     */
    private void initPasswordEditText() {
        mEtPassword = findViewById(R.id.et_password);
        mEtPassword1 = findViewById(R.id.right_password);
        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


            /**
             * 输入完检测
             * @param s
             */
            @Override
            public void afterTextChanged(Editable s) {
                ImageView mCleanPassword = findViewById(R.id.clean_password);
                if (!TextUtils.isEmpty(s) && mCleanPassword.getVisibility() == View.GONE) {
                    mCleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mCleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty()) {
                    return;
                }
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(RegisterActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    //取消当前的输入
                    mEtPassword.setSelection(s.length());
                }
            }
        });


        mEtPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


            /**
             * 输入完检测
             * @param s
             */
            @Override
            public void afterTextChanged(Editable s) {
                ImageView mCleanPassword = findViewById(R.id.cleanr_password);
                if (!TextUtils.isEmpty(s) && mCleanPassword.getVisibility() == View.GONE) {
                    mCleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mCleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty()) {
                    return;
                }
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(RegisterActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    //取消当前的输入
                    mEtPassword1.setSelection(s.length());
                }
            }
        });
    }

    /**
     * 初始化用户名输入框
     */
    private void initUserNameEditText() {
        mEtMobile = findViewById(R.id.et_mobile);
        mEtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ImageView mIvCleanPhone = findViewById(R.id.iv_clean_phone);
                if (!TextUtils.isEmpty(s) && mIvCleanPhone.getVisibility() == View.GONE) {
                    mIvCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mIvCleanPhone.setVisibility(View.GONE);
                }
            }
        });


    }

    //初始化邮箱输入框
    private void initUserEmailEditText() {
        mEtEmail = findViewById(R.id.user_email);
        mEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ImageView mIvCleanEmail = findViewById(R.id.clean_email);
                if (!TextUtils.isEmpty(s) && mIvCleanEmail.getVisibility() == View.GONE) {
                    mIvCleanEmail.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mIvCleanEmail.setVisibility(View.GONE);
                }
            }
        });


    }

    //判断邮箱是否合乎格式
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clean_phone:
                mEtMobile.setText("");
                break;
            case R.id.clean_password:
                mEtPassword.setText("");
                break;
            case R.id.cleanr_password:
                mEtPassword1.setText("");
                break;
            case R.id.clean_email:
                mEtEmail.setText("");
                break;
            case R.id.iv_show_pwd:
                if (mEtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ImageView mIvShowPwd = findViewById(R.id.iv_show_pwd);
                    mIvShowPwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ImageView mIvShowPwd = findViewById(R.id.iv_show_pwd);
                    mIvShowPwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd = mEtPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    mEtPassword.setSelection(pwd.length());
                break;

            case R.id.ri_show_pwd:
                if (mEtPassword1.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mEtPassword1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ImageView mIvShowPwd = findViewById(R.id.ri_show_pwd);
                    mIvShowPwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    mEtPassword1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ImageView mIvShowPwd = findViewById(R.id.ri_show_pwd);
                    mIvShowPwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd1 = mEtPassword1.getText().toString();
                if (!TextUtils.isEmpty(pwd1))
                    mEtPassword1.setSelection(pwd1.length());
                break;
        }
    }
}
