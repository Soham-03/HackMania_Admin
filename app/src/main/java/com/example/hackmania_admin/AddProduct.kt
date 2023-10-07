package com.example.hackmania_admin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint.Align
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hackmania_admin.ui.theme.HackMania_AdminTheme
import com.example.hackmania_admin.ui.theme.offWhite
import com.example.hackmania_admin.ui.theme.peach
import com.github.drjacky.imagepicker.ImagePicker
import com.google.firebase.firestore.FirebaseFirestore

class AddProduct : ComponentActivity() {
    private val uriImageState = mutableStateOf<Uri?>(null)
    private val uriImageState2 = mutableStateOf<Uri?>(null)
    val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                uriImageState.value = uri
//                uri.let { galleryUri->
//                    contentResolver.takePersistableUriPermission(
//                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    )
//                }
//                    Toast.makeText(this@AddProduct, uriImageState.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    val launcher2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                uriImageState2.value = uri
//                uri.let { galleryUri->
//                    contentResolver.takePersistableUriPermission(
//                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    )
//                }
//                    Toast.makeText(this@AddProduct, uriImageState.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(offWhite)
                            .verticalScroll(rememberScrollState())
                    ){
                        Text(
                            text = "List Product",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(12.dp)
                        )
                        //
                        var productName by remember {
                            mutableStateOf(TextFieldValue(""))
                        }
                        var productPrice by remember {
                            mutableStateOf(TextFieldValue(""))
                        }
                        var productDescp by remember {
                            mutableStateOf(TextFieldValue(""))
                        }
                        var carbonEmission by remember {
                            mutableStateOf(TextFieldValue(""))
                        }
                        AsyncImage(
                            model = uriImageState.value,
                            contentDescription = "",
                            error = painterResource(id = R.drawable.ic_launcher_background),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(12.dp)
                                .width(400.dp)
                                .height(200.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .align(Alignment.CenterHorizontally)
                        )

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = peach
                            ),
                            onClick = {
                                    ImagePicker.with(this@AddProduct)
                                        .bothCameraGallery()
                                        .createIntentFromDialog { launcher.launch(it) }
                            },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {

                            Text(text = "Choose Product Image")
                        }

                        var category by remember {
                            mutableStateOf(TextFieldValue(""))
                        }

                        OutlinedTextField(
                            value = category,
                            onValueChange = {
                                category = it
                            },
                            label = {
                                Text(text = "Enter Category")
                            },
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = productName,
                            onValueChange = {
                                productName = it
                            },
                            label = {
                                Text(text = "Enter Product Name")
                            },
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = productDescp,
                            onValueChange = {
                                productDescp = it
                            },
                            label = {
                                Text(text = "Enter Product Description")
                            },
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        OutlinedTextField(
                            value = productPrice,
                            onValueChange = {
                                productPrice = it
                            },
                            label = {
                                Text(text = "Enter Product Price")
                            },
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = carbonEmission,
                            onValueChange = {
                                carbonEmission = it
                            },
                            label = {
                                Text(text = "Approx Carbon Emission")
                            },
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                        )

                        var sustainLvl by remember {
                            mutableIntStateOf(0)
                        }
                        var envImpact by remember {
                            mutableIntStateOf(0)
                        }
                        var packAndWasteRed by remember {
                            mutableIntStateOf(0)
                        }
                        val sustainabilityQuestions = arrayListOf(
                            "Is the product made from renewable or recycled materials?",
                            "Does the product have a long lifespan or durability, reducing the need for frequent replacement?",
                            "Is the product energy-efficient in its operation or use?",
                            "Does the product have certifications or labels indicating environmentally friendly attributes (e.g., Energy Star, Fair Trade, Organic)?",
                            "Is the packaging of the product made from recyclable or biodegradable materials?",
                            "Does the manufacturing process of the product prioritize sustainability, such as reducing waste or emissions?",
                            "Is the product designed to be easily disassembled and recycled at the end of its life cycle?",
                            "Is the product's transportation or distribution designed to minimize carbon emissions (e.g., local sourcing, efficient logistics)?",
                            "Does the product come with clear instructions or information on how to use it in an environmentally responsible manner?",
                            "Is the company behind the product actively involved in sustainability initiatives, such as reducing its overall environmental footprint or supporting social causes?"
                        )
                        QuestionnaireScreen(questions = sustainabilityQuestions, "Sustainability")

                        val packagingQuestions = arrayListOf(
                            "Is the packaging of the product designed to minimize excess materials and reduce waste?",
                            "Does the packaging of the product use recycled or recyclable materials?",
                            "Is the packaging of the product designed to be easily opened and resealed for multiple uses?",
                            "Does the packaging of the product clearly indicate recycling instructions and symbols?",
                            "Is the packaging of the product free from unnecessary plastic or non-biodegradable materials?",
                            "Does the product's packaging prioritize compactness or efficient use of space to reduce shipping and storage impacts?",
                            "Is the company committed to reducing packaging waste and actively working towards sustainable packaging solutions?",
                            "Does the product offer a take-back or recycling program for its packaging or encourage customers to recycle it?",
                            "Does the company implement measures to minimize packaging waste throughout its supply chain, from manufacturing to distribution?",
                            "Does the company actively support or participate in initiatives to reduce plastic pollution or promote responsible packaging practices?"
                        )
                        QuestionnaireScreen(questions = packagingQuestions, "Packaging And Waste Reduction")

                        val environmentalImpactQuestions = arrayListOf(
                            "Does the product use natural resources in its production, and are efforts made to minimize excessive resource consumption?",
                            "Is the product's production process energy-efficient, and does it minimize greenhouse gas emissions?",
                            "Is the product's disposal or end-of-life management designed to minimize environmental harm (e.g., recycling, proper disposal)?",
                            "Does the product use hazardous materials or chemicals, and are there efforts to minimize or eliminate their use?",
                            "Is the product sourced or manufactured in a way that minimizes harm to ecosystems and wildlife?",
                            "Does the product contribute to water pollution during its production or use, and are measures taken to reduce such pollution?",
                            "Is the product designed to minimize excess packaging and waste generation throughout its lifecycle?",
                            "Does the product have a low carbon footprint compared to similar products in the market?",
                            "Are efforts made to reduce environmental impacts throughout the product's supply chain, from sourcing raw materials to distribution?",
                            "Does the company behind the product have a clear environmental sustainability policy or goals in place?"
                        )
                        QuestionnaireScreen(questions = environmentalImpactQuestions, "Impact on Environment")
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        val context = LocalContext.current
                        Button(onClick = {
                            ImagePicker.with(this@AddProduct)
                                .bothCameraGallery()
                                .createIntentFromDialog { launcher2.launch(it) }
                        }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text(text = "Add Image of LCA Certificate")
                        }
                        AnimatedVisibility(visible = uriImageState2.value != null) {
                            AsyncImage(
                                model = uriImageState2.value,
                                contentDescription = "",
                                error = painterResource(id = R.drawable.ic_launcher_background),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .width(400.dp)
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                        Button(onClick = {
                            val sharedPreferences: SharedPreferences =
                                context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            val companyName = sharedPreferences.getString("company", "null").toString()
                            val db = FirebaseFirestore.getInstance()
                            val hashMap = HashMap<String, Any>()
                            hashMap["productName"] = productName.text
                            hashMap["carbonEmission"] = carbonEmission.text
                            hashMap["companyName"] = companyName
                            hashMap["envImpact"] = Global.evnImpact!!.toInt()
                            hashMap["packagingAndWasteRed"] = Global.packgAndWasteRed!!.toInt()
                            hashMap["price"] = productPrice.text
                            hashMap["productDescp"] = productDescp.text
                            hashMap["productImage"] = "https://img.freepik.com/premium-photo/eco-natural-bamboo-toothbrushes-glass-rustic-background-with-greenery-sustainable-lifestyle-concept-zero-waste-home-bathroom-essentials-plastic-free-items_250813-17385.jpg"
                            hashMap["rating"] = 10
                            hashMap["rewards"] = "80"
                            hashMap["sustainLvl"] = Global.sustainLvl!!.toInt()
                            db.collection("categories")
                                .document("j3Ud6GuThUAnJ94F2xeM")
                                .collection("products")
                                .document()
                                .set(hashMap)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Product Listed!!", Toast.LENGTH_SHORT).show()
                                }
                        }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text(text = "List Product")
                        }
                        
                    }

                }
        }
    }
}
