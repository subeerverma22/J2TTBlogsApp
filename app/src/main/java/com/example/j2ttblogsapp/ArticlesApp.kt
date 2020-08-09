package com.example.j2ttblogsapp

import android.app.Application
import com.example.j2ttblogsapp.utils.NetworkUtil

class ArticlesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkUtil.init(this)
    }

}