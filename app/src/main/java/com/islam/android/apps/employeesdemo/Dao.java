package com.islam.android.apps.employeesdemo;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.islam.android.apps.employeesdemo.Employee;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@androidx.room.Dao
public interface Dao {

    @Query("SELECT * FROM employee")
    Single<List<Employee>> getAllEmployees();

    @Query("SELECT * FROM employee WHERE data = :data")
    Single<Employee> getEmployee(String data);

    @Insert
    Completable insertEmployee(Employee employee);

    @Update
    Completable updateEmployee(Employee employee);

    @Delete
    Completable deleteEmployee(Employee employee);
}
