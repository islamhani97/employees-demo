package com.islam.android.apps.employeesdemo;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.datalogic.decode.BarcodeManager;
import com.datalogic.decode.DecodeResult;
import com.datalogic.decode.ReadListener;
import com.islam.android.apps.employeesdemo.databinding.ActivityAddEmployeeBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddEmployeeActivity extends AppCompatActivity {

    private ActivityAddEmployeeBinding viewBinding;
    BarcodeManager barcodeManager;
    ReadListener readListener;
    private String employeeData;
    //    private AlertDialog alertDialog;
    ProgressDialog dialog;
    private boolean savable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_employee);
        barcodeManager = new BarcodeManager();
//        alertDialog = new MaterialAlertDialogBuilder(this).setMessage("Scan the employee id").create()
        readListener = new ReadListener() {
            @Override
            public void onRead(DecodeResult decodeResult) {
                if (savable) {
                    employeeData = decodeResult.getText();
                    savable = false;
                    insertEmployee();
                }

            }
        };
        barcodeManager.addReadListener(readListener);
        viewBinding.insert.setOnClickListener(v -> {
            if (!viewBinding.name.getText().toString().isEmpty() &&
                    !viewBinding.title.getText().toString().isEmpty() &&
                    !viewBinding.workingHours.getText().toString().isEmpty()
            ) {
//                alertDialog.show();
                dialog = ProgressDialog.show(this, "", "Scan the employee ID");
                savable = true;
            } else {
                Toast.makeText(this, "Complete the employeeData data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertEmployee() {
        Database.getInstance(this).dao().insertEmployee(new Employee(
                        viewBinding.name.getText().toString(),
                        viewBinding.title.getText().toString(),
                        Integer.parseInt(viewBinding.workingHours.getText().toString()),
                        employeeData))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                        Toast.makeText(AddEmployeeActivity.this, "The employee inserted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        if (e instanceof SQLiteConstraintException) {
                            Toast.makeText(AddEmployeeActivity.this, "This employee has been inserted before", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddEmployeeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        barcodeManager.removeReadListener(readListener);
    }
}