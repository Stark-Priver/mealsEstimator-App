
package com.joelkanyi.auth.presentation.landing

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.joelkanyi.auth.presentation.AuthNavigator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.json.JSONObject


@Destination(start = true )
@Composable
fun LandingPageScreen(
    navigator: AuthNavigator,
    viewModel: LandingPageViewModel = hiltViewModel(),
) {
    val auth = Firebase.auth
    val context = LocalContext.current

    // Your Google OAuth client ID
    val googleClientId = "953600714967-l75d5lq2bilkk8hsebbqc6trb8qjg5aq.apps.googleusercontent.com"

    val oneTapSignInState = rememberOneTapSignInState()
    OneTapSignInWithGoogle(
        state = oneTapSignInState,
        clientId = googleClientId,
        onTokenIdReceived = { tokenId ->
            val firebaseCredential = GoogleAuthProvider.getCredential(tokenId, null)
            CoroutineScope(Dispatchers.Main).launch {
                val result = auth.signInWithCredential(firebaseCredential).await()
                if (result.user != null) {
                    viewModel.signInWithGoogle(tokenId)
                    viewModel.setUserProfile(
                        userId = result.user?.uid ?: "",
                        userProperties = JSONObject()
                            .put("name", "${result?.user?.displayName}")
                            .put("email", "${result?.user?.email}")

                    )
                    navigator.switchNavGraphRoot()
                }
            }
        },
        onDialogDismissed = { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = com.joelkanyi.common.R.drawable.banner),
            contentDescription = "Landing Page Food Image",
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(
                    Alignment.BottomCenter
                )
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    viewModel.trackUserEvent("Google Sign In Clicked")
                    oneTapSignInState.open()
                },
                enabled = !oneTapSignInState.opened,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    disabledContainerColor = Color.LightGray
                )
            ) {
                Image(
                    modifier = Modifier.padding(6.dp),
                    painter = painterResource(id = com.joelkanyi.common.R.drawable.ic_google),
                    contentDescription = "Google Icon"
                )

                Text(
                    text = "Sign In with Google",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    viewModel.trackUserEvent("Email Sign In Clicked")
                    navigator.openSignUp()
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(
                    modifier = Modifier.padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Sign Up with Email Icon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                    Text(
                        text = "Sign Up with Email",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 14.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    viewModel.trackUserEvent("Already have an account? Sign In Clicked")
                    navigator.openSignIn()
                }
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            append("Already have an account? ")
                        }

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            append("Sign In")
                        }
                    }
                )
            }
        }
    }
}