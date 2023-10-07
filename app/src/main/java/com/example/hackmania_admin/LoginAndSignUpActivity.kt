package com.example.hackmania_admin

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.hackmania_admin.ui.theme.HackMania_AdminTheme
import com.example.hackmania_admin.ui.theme.offWhite
import com.example.hackmania_admin.ui.theme.peach
import com.example.hackmania_admin.ui.theme.pinkMera
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginAndSignUpActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HackMania_AdminTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(offWhite),
                ) {
                    var company by remember {
                        mutableStateOf(TextFieldValue(""))
                    }
                    val startForResult =
                        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                            if (result.resultCode == RESULT_OK) {
                                val intent = result.data
                                if (result.data != null) {
                                    lifecycleScope.launch {
                                        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
                                        handleSignInResult(task, company.text)
                                    }
                                }
                            }
                        }
                    if(FirebaseAuth.getInstance().currentUser==null){
                        Text(
                            text = "Seller LogIn",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(start = 12.dp, top = 2.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.coverart),
                            contentDescription = ""
                        )
                        OutlinedTextField(
                            value = company,
                            onValueChange = {
                                company = it
                            },
                            label = {
                                    Text(text = "Enter Company Name")
                            },
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                        )
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = peach
                            ),
                            onClick = {
                            if(company.text.isNotEmpty()){
                                startForResult.launch(googleSignInClient.signInIntent)
                            }
                            else{
                                Toast.makeText(this@LoginAndSignUpActivity, "Please Enter your Company Name", Toast.LENGTH_SHORT).show()
                            }

                        }) {
                            Text(text = "Sign Up with Google")
                        }
                    }
                    else{
                        val intent = Intent(this@LoginAndSignUpActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        getGoogleLoginAuth()
    }
    private fun getGoogleLoginAuth(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }

    private suspend fun handleSignInResult(completedTask: Task<GoogleSignInAccount>, companyName: String) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Now, you can authenticate with Firebase using the GoogleSignInAccount's ID token.
            if (account != null) {
                val idToken = account.idToken
                val credential = GoogleAuthProvider.getCredential(idToken, null)

                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign-in successful. You can access the Firebase user here.
                            val user = FirebaseAuth.getInstance().currentUser
                            if (user != null) {
//                                val doc = FirebaseFirestore.getInstance().collection("users").document(user.uid).get()
//                                val exists = doc.result.exists()
//                                if(!exists){
                                val hashMap = HashMap<String, Any>()
                                hashMap["userName"] = user.displayName.toString()
                                hashMap["userImage"] = user.photoUrl.toString()
                                hashMap["wallet"] = 0
                                hashMap["carbonFoot"] = "0.0"
                                hashMap["companyName"] = companyName
                                FirebaseFirestore.getInstance().collection("users").document(user.uid)
                                    .set(hashMap).addOnSuccessListener {
                                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                                    }
//                                }
                                val sharedPreferences: SharedPreferences =
                                    this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                                sharedPreferences.edit().putString("company",companyName).apply()
                                println("Current User: ${user.uid}")
                                val intent = Intent(this@LoginAndSignUpActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            // Sign-in failed.
                            Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                        }
                    }
                    .await()
            } else {
                // Handle the case where the GoogleSignInAccount is null.
            }
        } catch (e: ApiException) {
            // Handle ApiException here.
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

}
