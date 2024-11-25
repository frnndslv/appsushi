package com.example.oxentesushi.telas
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.oxentesushi.R
import com.google.firebase.storage.FirebaseStorage


class Imagem {
    @Composable
    fun DisplayImage(url: String) {
        AsyncImage(
            model = url,
            contentDescription = "Minha Imagem",
            placeholder = painterResource(R.drawable.ic_laucher_background), // Opcional: imagem temporária
            error = painterResource(R.drawable.error_image)        // Opcional: imagem de erro
        )
    }

    @Composable
    fun getImageUrl(imagePath: String, onUrlReady: (String) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().reference.child(imagePath)
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            onUrlReady(uri.toString())
        }.addOnFailureListener {
            // Lidar com erro
        }


    }




    /* fun getImageUrl(imagePath: String, onUrlReady: (String) -> Unit) {
         val storageRef = FirebaseStorage.getInstance().reference.child(imagePath)
         storageRef.downloadUrl.addOnSuccessListener { uri ->
             onUrlReady(uri.toString())
         }.addOnFailureListener {

         }
     }*/

}