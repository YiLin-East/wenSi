package com.fc.wensi;

public interface IGetDataListener<T> {
    public void onSuccess(T dataobj);
    public void onFailure(Object reasonOBJ);
}
