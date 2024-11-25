package com.example.oxentesushi.data.imgUpload

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url

interface IUploadImg {
    @PUT
    suspend fun uploadImage(
        @Url url: String,
        @Body image: RequestBody
    ): Response<Void>
}