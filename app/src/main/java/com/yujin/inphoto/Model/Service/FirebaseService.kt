package com.yujin.inphoto.Model.Service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class FirebaseService {
    private var auth:FirebaseAuth = FirebaseAuth.getInstance()

    // 회원가입
    fun createUser(email: String, pw: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, pw)
    }

    // 로그인
    fun signIn(email:String, pw:String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, pw)
    }
}