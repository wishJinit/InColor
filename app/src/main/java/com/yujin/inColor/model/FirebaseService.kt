package com.yujin.inColor.model

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.yujin.inColor.model.vo.DiaryVO
import com.yujin.inColor.util.DLog
import java.util.*

class FirebaseService {
    private val auth:FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // 회원가입
    fun createUser(name:String, email: String, pw: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, pw)
    }

    // 로그인
    fun signIn(email:String, pw:String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, pw)
    }

    // 현재 로그인된 사용자
    fun getCurrentUser(): FirebaseUser?{
        return auth.currentUser
    }

    // 이메일 중복 체크
    fun checkEmail(email: String): Task<SignInMethodQueryResult> {
        return auth.fetchSignInMethodsForEmail(email)
    }

    // 이메일 비밀번호 리셋
    fun sendPasswordResetEmail(email: String): Task<Void> {
        return auth.sendPasswordResetEmail(email)
    }

    fun signOut(){
        auth.signOut()
    }

    fun addDiary(diaryVO: DiaryVO){
        val diary = hashMapOf(
            "date" to diaryVO.date,
            "weather" to diaryVO.weather,
            "mood_color" to diaryVO.moodColor,
            "content" to diaryVO.content
        )

        getCurrentUser()?.let {
            db.collection(it.uid)
                .add(diary)
                .addOnSuccessListener {
                    DLog.d("success")
                }
        }
    }

    fun getMonthDiary(startDate:Date, lastDate:Date): Task<QuerySnapshot>? {
        getCurrentUser()?.let {
            return db.collection(it.uid)
                .whereGreaterThan("date", startDate)
                .whereLessThan("date", lastDate)
                .orderBy("date")
                .get()
        }
        return null
    }

    fun setName(name:String){
        val user = auth.currentUser
        val profileUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        user?.updateProfile(profileUpdate)
    }
}