package com.jd.dogapp.models

import android.app.Activity
import android.content.Context

//import android.os.Parcelable
//import kotlinx.parcelize.Parcelize

//@Parcelize
class User(
    val id: Long,
    val email: String,
    val authenticationToken: String,
)//: Parcelable
{
    companion object
    {
        fun setLoggedinUser(activity: Activity, user: User)
        {
            activity.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE).also {
                it.edit().putLong("id", user.id).putString("email", user.email)
                    .putString("auth_token",user.authenticationToken).apply()
            }
        }

        fun getLoggedinUser(activity: Activity): User?
        {
            val prefs = activity.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            ?: return null

            val userId = prefs.getLong("id", 0)
            if(userId == 0L)
            {
                return null
            }

            val user = User(userId, prefs.getString("email", "") ?: ""
                , prefs.getString("auth_token", "")?: "")

            return user
        }

        fun logout(activity: Activity)
        {
            activity.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE).also {
                it.edit().clear().apply()
            }
        }
    }
}