package com.wantobeme.lira.views.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

fun <A : Activity> Context.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}