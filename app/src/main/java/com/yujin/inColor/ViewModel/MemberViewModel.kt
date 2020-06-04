package com.yujin.inColor.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.firestore.QuerySnapshot
import com.yujin.inColor.Base.BaseViewModel
import com.yujin.inColor.Model.FirebaseService
import com.yujin.inColor.Model.VO.DiaryVO
import com.yujin.inColor.Model.VO.UserVO
import com.yujin.inColor.Model.SaveSharedPreference
import com.yujin.inColor.util.DLog
import java.util.*


class MemberViewModel : BaseViewModel(){
    private val firebaseService = FirebaseService()

    companion object {
        const val success = 1
        const val fail = 0
        const val default = -1
    }

    private val _userVO = MutableLiveData<UserVO>()
    val userVO: LiveData<UserVO>
        get() = _userVO

    private val _diaryDocuments = MutableLiveData<QuerySnapshot>()
    val diaryDocuments: LiveData<QuerySnapshot>
        get() = _diaryDocuments

    private val _signUpResult = MutableLiveData<Int>()
    val signUpResult: LiveData<Int>
        get() = _signUpResult

    private val _sendEmailResult = MutableLiveData<Int>()
    val sendEmailResult: LiveData<Int>
        get() = _sendEmailResult

    private val _checkEmailResult = MutableLiveData<Int>()
    val checkEmailResult: LiveData<Int>
        get() = _checkEmailResult

    fun resetResult(){
        _signUpResult.value = default
        _sendEmailResult.value = default
        _checkEmailResult.value = default
    }

    fun signUp(name:String, email:String, pw:String){
        firebaseService.createUser(name, email, pw)
            .addOnCompleteListener {
                _signUpResult.value =
                    when (it.isSuccessful) {
                        true -> {
                            firebaseService.setName(name)
                            success
                        }
                        false -> fail
                    }
            }
    }

    fun singIn(email:String, pw:String){
        firebaseService.signIn(email, pw)
            .addOnSuccessListener {
                val currentUser = firebaseService.getCurrentUser()!!
                val curId = currentUser.uid
                val curName = currentUser.displayName ?: ""
                val curEmail = currentUser.email ?: ""
                _userVO.value = UserVO(curId, curName, curEmail)
            }
    }

    fun sendPasswordResetEmail(email:String){
        firebaseService.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                _sendEmailResult.value = when(it.isSuccessful) {
                    true -> success
                    false -> fail
                }
            }
    }

    fun checkEmail(email:String){
        firebaseService.checkEmail(email)
            .addOnCompleteListener {
                _checkEmailResult.value =
                    if (it.isSuccessful) {
                        if (it.result?.signInMethods?.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD) == true) {
                            success
                        } else {
                            fail
                        }
                    } else {
                        fail
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

    fun addDiary(diaryVO: DiaryVO) {
        firebaseService.addDiary(diaryVO)
    }

    fun getMonthDiary(year:Int, month:Int){
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1, 0, 0, 0)
        val startDate = calendar.time
        calendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0, 0)
        val finishDate = calendar.time

        firebaseService.getMonthDiary(startDate, finishDate)
            ?.addOnSuccessListener { result ->
                _diaryDocuments.value = result
            }
    }
}