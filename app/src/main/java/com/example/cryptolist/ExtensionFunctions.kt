package com.example.cryptolist

import android.content.Context

fun Context.resIdByName(resIdName: String?, resType: String): Int {
    resIdName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    return R.mipmap.logo_mark
}