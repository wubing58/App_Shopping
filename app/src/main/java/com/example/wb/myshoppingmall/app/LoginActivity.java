package com.example.wb.myshoppingmall.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wb.myshoppingmall.R;

public class LoginActivity extends Activity implements View.OnClickListener {
    private ImageButton ibLoginBack;
    private EditText etLoginPhone;
    private EditText etLoginPwd;
    private ImageButton ibLoginVisible;
    private Button btnLogin;
    private TextView tvLoginRegister;
    private TextView tvLoginForgetPwd;
    private ImageButton ib_weibo;
    private ImageButton ib_qq;
    private ImageButton ib_wechat;

    private int count;
    private String screen_name;
    private String profile_image_url;


    private void findViews() {
        ibLoginBack = (ImageButton) findViewById(R.id.ib_login_back);
        etLoginPhone = (EditText) findViewById(R.id.et_login_phone);
        etLoginPwd = (EditText) findViewById(R.id.et_login_pwd);
        ibLoginVisible = (ImageButton) findViewById(R.id.ib_login_visible);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvLoginRegister = (TextView) findViewById(R.id.tv_login_register);
        tvLoginForgetPwd = (TextView) findViewById(R.id.tv_login_forget_pwd);
        ib_weibo = (ImageButton) findViewById(R.id.ib_weibo);
        ib_qq = (ImageButton) findViewById(R.id.ib_qq);
        ib_wechat = (ImageButton) findViewById(R.id.ib_wechat);

        ibLoginBack.setOnClickListener(this);
        ibLoginVisible.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ib_weibo.setOnClickListener(this);
        ib_qq.setOnClickListener(this);
        ib_wechat.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == ibLoginBack) {
            finish();
        } else if (v == ibLoginVisible) {
            count++;
            if (count % 2 == 0) {
                //使密码不可见,setBackgroundResource更换图片
                ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_invisible);
                etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                //使密码可见
                ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_visible);
                etLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
        } else if (v == btnLogin) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if (v == ib_weibo) {
            Toast.makeText(this,"微博",Toast.LENGTH_SHORT).show();
        } else if (v == ib_qq) {
            Toast.makeText(this,"QQ",Toast.LENGTH_SHORT).show();
        } else if (v == ib_wechat) {
            Toast.makeText(this,"微信",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
