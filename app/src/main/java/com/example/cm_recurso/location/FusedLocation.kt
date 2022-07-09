package com.example.cm_recurso.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.*

@SuppressLint("MissingPermission")
class FusedLocation private constructor(context: Context) : LocationCallback() {

}