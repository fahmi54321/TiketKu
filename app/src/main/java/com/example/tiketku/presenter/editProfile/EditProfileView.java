package com.example.tiketku.presenter.editProfile;

// todo 3 (Edit Profile) Kerangka respon dari edit profile
public interface EditProfileView {
    void onSuccess(String message);
    void onError(String message);
    void inputKosong();
    void hideProgress();
    void hideLoading();
}
