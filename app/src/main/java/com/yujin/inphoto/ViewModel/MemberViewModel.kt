package com.yujin.inphoto.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yujin.inphoto.Base.BaseViewModel
import com.yujin.inphoto.Model.Service.FirebaseService
import com.yujin.inphoto.Model.VO.UserVO
import com.yujin.inphoto.Util.SaveSharedPreference


class MemberViewModel : BaseViewModel(){
    private val firebaseService = FirebaseService()
    private val _userVO = MutableLiveData<UserVO?>()
    val userVO: LiveData<UserVO?>
        get() = _userVO

    fun signUp(name:String, email:String, pw:String, success:()->Unit, fail:()->Unit, finally:()->Unit){
        firebaseService.createUser(name, email, pw, success, fail, finally)
    }

    fun singIn(email:String, pw:String, success:()->Unit, fail:()->Unit, finally:()->Unit){
        firebaseService.signIn(email, pw, {
            val currentUser = firebaseService.getCurrentUser()!!
            val curId = currentUser.uid
            val curName = currentUser.displayName ?: ""
            val curEmail = currentUser.email ?: ""
            _userVO.value = UserVO(curId, curName, curEmail)

            success()
        }, fail, finally)
    }

    fun sendPasswordResetEmail(email:String, success:()->Unit, fail:()->Unit, finally:()->Unit){
        firebaseService.sendPasswordResetEmail(email, success, fail, finally)
    }

    fun checkEmail(email:String, notExisting:()->Unit, alreadyExisting:()->Unit, fail:()->Unit, finally:()->Unit){
        firebaseService.checkEmail(email, notExisting, alreadyExisting, fail, finally)
    }

    fun setAutoSignIn(context: Context){
        SaveSharedPreference.setAutoSignIn(context)
    }

    fun isAutoSignIn(context: Context): Boolean{
        return SaveSharedPreference.isAutoSignIn(context)
    }

    fun autoSignIn(context: Context, doSignIn:() -> Unit){
        if (isAutoSignIn(context)){
            doSignIn()
        }
    }

}