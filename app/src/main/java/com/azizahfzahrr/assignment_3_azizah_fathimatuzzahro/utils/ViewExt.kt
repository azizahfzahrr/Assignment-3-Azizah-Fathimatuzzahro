package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.utils

import android.view.View
import androidx.core.view.isVisible

fun String ?.orEmpty() = this ?: ""

fun View.gone(){
    isVisible = false
}