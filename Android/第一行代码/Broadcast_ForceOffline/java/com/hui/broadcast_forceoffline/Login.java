package com.hui.broadcast_forceoffline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends BaseActivity {
    private EditText etName;
    private EditText etPasswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etName = (EditText) findViewById(R.id.username);
        etPasswd = (EditText) findViewById(R.id.password);
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etName.getText().toString();
                String password = etPasswd.getText().toString();
                if(username.equals("admin") && password.equals("123")) {
                    Intent intent = new Intent(Login.this, Welcome.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "输入信息有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
