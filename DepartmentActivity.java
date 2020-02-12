package com.example.androidphpmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DepartmentActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextdept_id,editTextdept;
    ProgressDialog progressDialog2;
    Button buttonaddDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        editTextdept_id = (EditText) findViewById(R.id.editTextDept_id);
        editTextdept = (EditText) findViewById(R.id.editTextDepartment);
        buttonaddDept = (Button) findViewById(R.id.buttonAddDept);
        buttonaddDept.setOnClickListener(this);
    }
    private void addDepartment() {
        /*final int id = Integer.parseInt(dept_id.getText().toString().trim()); */
        final String id = editTextdept_id.getText().toString().trim();
        final String dept_name= editTextdept.getText().toString().trim();
        progressDialog2.setMessage("Adding Department..");
        progressDialog2.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADDDEPT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog2.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog2.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("dept_name",dept_name);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onClick(View v) {
        if(v==buttonaddDept)
            addDepartment();
    }
}
