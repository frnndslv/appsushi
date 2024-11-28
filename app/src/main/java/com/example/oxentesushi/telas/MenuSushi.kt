package com.example.oxentesushi.telas

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.oxentesushi.data.cardapio.Cardapio
import com.example.oxentesushi.data.imgUpload.IUploadImg
import com.example.oxentesushi.ui.theme.ImageFromUrl
import com.example.oxentesushi.ui.viewmodel.CardapioViewModel
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.URLEncoder
import java.util.UUID

@Composable
fun MenuSushi(navController: NavHostController,  cardapioViewModel: CardapioViewModel) {
    val coroutineScope = rememberCoroutineScope()

    val sushiLiveData by cardapioViewModel.cardapios.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
    ) {
        Text("Adicione um prato ao menu do restaurante", style = MaterialTheme.typography.bodyLarge)
        Text("Caso deseje editar por aqui, clique sobre o prato desejado", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(26.dp))


        SushiInputField (cardapioViewModel) { sushiName ->
            coroutineScope.launch {
                //db.sushiDao().insertSushi(Sushi(name = sushiName))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(text = "--Cardapio--", fontWeight = FontWeight.Bold)
        }

        LazyColumn {
            items(sushiLiveData) { sushi ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Box(
                        modifier = Modifier
                                    .fillMaxWidth(),
                    ) {
                        Column {
                            Text(text = "Nome do prato:", fontWeight = FontWeight.Bold)
                            Text(text = sushi.nome)
                            Text(text = "Imagem do prato:", fontWeight = FontWeight.Bold)
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                ImageFromUrl(imageUrl = sushi.img)
                            }
                            Text(text = "Valor do prato:", fontWeight = FontWeight.Bold)
                            Text(text = sushi.valor)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row {
                        Button(
                            onClick = { navController.navigate("listaSushi/${sushi.id}") },
                            modifier = Modifier
                                .fillMaxWidth(0.33f)
                        ) {
                            Text(text = "Editar")
                        }

                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    cardapioViewModel.excluir(Cardapio(sushi.id,sushi.nome, sushi.img, sushi.valor))
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.50f)
                                .background(Color.Red),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "Deletar")
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun SushiInputField(cardapioViewModel: CardapioViewModel, onAddSushi: (String) -> Unit) {
    var newSushi by remember { mutableStateOf("") }
    var valorDoPrato by remember { mutableStateOf("") }
    var imgUploaded by remember { mutableStateOf<StorageReference?>(null) }

    //
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            imageUri = uri
            imgUploaded = uploadImage(uri)
        }
    }

    //Permissões
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permissão concedida!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permissão negada!", Toast.LENGTH_SHORT).show()
        }
    }
    // Solicitar permissão baseada na versão do Android
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
    //Fim Permissões

    Column {
        TextField(
            value = newSushi,
            onValueChange = { newSushi = it },
            placeholder = { Text("Digite o nome do prato") },
            modifier = Modifier.fillMaxWidth()
        )

        //Tratamento de imagem
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para selecionar imagem
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Selecionar Imagem")
        }


        TextField(
            value = valorDoPrato,
            onValueChange = { valorDoPrato = it },
            placeholder = { Text("Digite o valor do prato") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.NumberPassword,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (newSushi.isNotBlank()) {
                    val cardapio = Cardapio()
                    imgUploaded?.downloadUrl?.addOnSuccessListener { uri ->
                        cardapio.nome = newSushi
                        cardapio.img = uri.toString()
                        cardapio.valor = valorDoPrato
                        cardapioViewModel.gravar(cardapio)
                        newSushi = ""
                        valorDoPrato = ""
                        imgUploaded = null
                        imageUri = null
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar")
        }
    }
}

fun uploadImage(uploadUrl: Uri): StorageReference {
    val storage = Firebase.storage
    val storageRef = storage.reference
    val fileRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

    Log.d("Firebase", "Caminho do Firebase: ${fileRef.path}")

    val uploadTask = fileRef.putFile(uploadUrl)

    uploadTask
        .addOnSuccessListener {
            Log.d("Firebase", "Upload bem-sucedido!")
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                Log.d("Firebase", "URL da imagem: $uri")
            }
        }
        .addOnFailureListener { exception ->
            Log.e("Firebase", "Erro ao fazer upload: ${exception.localizedMessage}")
            exception.printStackTrace()
        }

    return fileRef;
}



