package com.example.tiketku.presenter.signup;

//todo 3(Signup) settings interface
public interface SignupView {
    void onSuccess(String message);
    void onError(String message);
    void inputKosong();
    void hideProgress();
    void hideLoading();
    void emailAda();
}
