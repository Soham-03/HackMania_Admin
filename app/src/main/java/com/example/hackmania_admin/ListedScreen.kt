package com.example.hackmania_admin

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hackmania_admin.ui.theme.offWhite
import com.example.hackmania_admin.ui.theme.peach
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

@Composable
fun ListedScreen(){
    val db = FirebaseFirestore.getInstance()
    var listOfProduct by remember {
        mutableStateOf(ArrayList<Product>())
    }
    val context = LocalContext.current
    val listOfProducts = ArrayList<Product>()
    LaunchedEffect(key1 = Unit){
        db.collection("categories")
            .get()
            .addOnSuccessListener {
                val cats = it
                for(cat in cats){
                    db.collection("categories")
                        .document(cat.id)
                        .collection("products")
                        .orderBy("sustainLvl", Query.Direction.DESCENDING)
                        .get()
                        .addOnSuccessListener {g->
                            val prods = g.documents
                            for(prod in prods){
                                val sharedPreferences: SharedPreferences =
                                    context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                                val companyName = sharedPreferences.getString("company", "null")
                                if(companyName == prod["companyName"].toString()){
                                    listOfProducts.add(
                                        Product(
                                            productName = prod["productName"].toString(),
                                            productImage = prod["productImage"].toString(),
                                            price = prod["price"].toString()
                                        )
                                    )
                                }
                            }
                            listOfProduct = listOfProducts
                        }
                }
            }
            .await()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(offWhite)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Listed Products",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            val user = FirebaseAuth.getInstance().currentUser
            AsyncImage(
                model = user!!.photoUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(12.dp),
        ){
            for(prod in listOfProduct) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFCCCCCC)),
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .clickable {
//                            Global.product = product
//                            val intent = Intent(context, ProductDetails::class.java)
//                            context.startActivity(intent)
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            AsyncImage(
                                model = prod.productImage,
                                contentDescription = "",
                                error = painterResource(id = R.drawable.ic_launcher_background),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Text(
                                text = prod.productName,
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                            Text(
                                text = prod.price+" /-",
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}