package com.yujin.inColor.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.EmailAuthProvider
import com.yujin.inColor.base.BaseViewModel
import com.yujin.inColor.model.FirebaseService
import com.yujin.inColor.model.vo.UserVO
import com.yujin.inColor.model.SaveSharedPreference


class MemberViewModel(private val firebaseService: FirebaseService) : BaseViewModel(){
    private val _userVO = MutableLiveData<UserVO>()

    fun signUp(name:String, email:String, pw:String, success: () -> Unit, fail: () -> Unit){
        firebaseService.createUser(name, email, pw)
            .addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        firebaseService.setName(name)
                        success()
                    }
                    false -> fail()
                }
            }
    }

    fun singIn(id:String, pw:String, success: () -> Unit, fail: () -> Unit) {
        firebaseService.signIn(id, pw)
            .addOnSuccessListener {
                firebaseService.getCurrentUser()?.run {
                    val curName = displayName ?: ""
                    val curEmail = email ?: ""
                    _userVO.value = UserVO(uid, curName, curEmail)
                }
                success()
            }.addOnFailureListener {
                fail()
            }
    }

    fun sendPasswordResetEmail(email:String, success: () -> Unit, fail: () -> Unit){
        firebaseService.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                when(it.isSuccessful) {
                    true -> success()
                    false -> fail()
                }
            }
    }

    fun checkEmail(email:String, success: () -> Unit, fail: () -> Unit){
        firebaseService.checkEmail(email)
            .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (it.result?.signInMethods?.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD) == true) {
                            success()
                        } else {
                            fail()
                        }
                    } else {
                        fail()
                    }
            }
    }

    fun setAutoSignIn(context: Context){
        SaveSharedPreference.setAutoSignIn(context)
    }

    fun isAutoSignIn(context: Context): Boolean{
        return SaveSharedPreference.isAutoSignIn(context)
    }

    fun clearAutoSignIn(context: Context){
        SaveSharedPreference.clearAutoSignIn(context)
    }

    fun autoSignIn(context: Context, doSignIn:() -> Unit){
        if (isAutoSignIn(context)){
            doSignIn()
        }
    }

    fun signOut(context: Context){
        clearAutoSignIn(context)
        firebaseService.signOut()
    }

    fun autoSignOut(context: Context) {
        if (!isAutoSignIn(context)) {
            signOut(context)
        }
    }
}