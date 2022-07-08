package com.fc.wensi;

public interface IGetDataListener<T> {
     void onSuccess(T dataobj);
     void onFailure(Object reasonOBJ);
}
