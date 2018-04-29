package com.aniapps.utils.RestClient

import com.aniapps.utils.Models.Stories

interface ApiResults {
    fun onSuccess(res: Stories?)
    fun onFailure(res: String)
}