package com.yujin.inphoto.Model.Service

import com.google.firebase.auth.*

class FirebaseService {
    private var auth:FirebaseAuth = FirebaseAuth.getInstance()

    // 회원가입
    fun createUser(name:String, email: String, pw: String, success:()->Unit, fail:() -> Unit, finally:() -> Unit) {
        auth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    setName(name)
                    success()
                } else {
                    fail()
                }
                finally()
            }
    }

    // 로그인
    fun signIn(email:String, pw:String, success:()->Unit, fail:() -> Unit, finally:() -> Unit) {
        auth.signInWithEmailAndPassword(email, pw)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    success()
                } else {
                    fail()
                }
                finally()
            }
    }

    // 현재 로그인된 사용자
    fun getCurrentUser(): FirebaseUser?{
        return auth.currentUser
    }

    // 이메일 중복 체크
    fun checkEmail(email: String, notExisting:()->Unit, alreadyExisting:()->Unit, fail:()->Unit, finally:()->Unit){
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if(it.result?.signInMethods?.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD) == true){
                        alreadyExisting()
                    } else {
                        notExisting()
                    }
                } else {
                    fail()
                }
                finally()
            }
    }

    // 이메일 비밀번호 리셋
    fun sendPasswordResetEmail(email: String, success:()->Unit, fail:()->Unit, finally:()->Unit){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    success()
                } else {
                    fail()
                }
                finally()
            }
    }

    fun signOut(){
        auth.signOut()
    }

    // 이름설정
    private fun setName(name:String){
        val user = auth.currentUser
        val profileUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        user?.updateProfile(profileUpdate)
    }
}