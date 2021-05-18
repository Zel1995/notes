package com.example.notes.domain;

public interface MyCallback <T>{

    void onSuccess(T value);
    void onError(Throwable error);
}
