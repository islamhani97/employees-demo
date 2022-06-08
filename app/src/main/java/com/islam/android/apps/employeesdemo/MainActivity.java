package com.islam.android.apps.employeesdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.room.rxjava3.EmptyResultSetException;

import com.datalogic.decode.BarcodeManager;
import com.datalogic.decode.DecodeResult;
import com.datalogic.decode.ReadListener;
import com.islam.android.apps.employeesdemo.databinding.ActivityMainBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding viewBinding;
    private BarcodeManager barcodeManager;
    private ReadListener readListener;
    private String employeeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        barcodeManager = new BarcodeManager();
        readListener = new ReadListener() {
            @Override
            public void onRead(DecodeResult decodeResult) {
                employeeData = decodeResult.getText();
                getEmployee();
            }
        };

        viewBinding.insert.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddEmployeeActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeManager.addReadListener(readListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeManager.removeReadListener(readListener);

    }



    private void getEmployee() {
        Database.getInstance(MainActivity.this).dao().getEmployee(employeeData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Employee>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Employee employee) {

                        viewBinding.employee.setText("Name: " + employee.getName() + "\n\n" +
                                "Title: " + employee.getJobTitle() + "\n\n" +
                                "Working hours: " + employee.getWorkingHours());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof EmptyResultSetException) {
                            viewBinding.employee.setText("This employee not found");
                        } else {
                            Toast.makeText(MainActivity.this, "Error " + e.getClass().getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}