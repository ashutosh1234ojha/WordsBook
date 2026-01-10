package com.mywordsbook.login

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.identity.Identity
import com.mywordsbook.core.network.GoogleAuthUiClient
import com.mywordsbook.core.ui.Header

@Composable()
fun LoginScreen(state: SignInState, onSignInClick: () -> Unit) {
//    val isSwitchOn by commonViewModel.isSwitchOn.collectAsState()
//    val isDarkTheme by commonViewModel.isDarkTheme.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError) {

        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Header(
            headerText = "Login Screen"
        )
        Spacer(modifier = Modifier.height(20.dp))


        TextButton(modifier = Modifier
            .background(Color.LightGray),
            onClick = {
                onSignInClick()
                //    navController.navigate("HomeScreen")


//                googleSignIn(signInLauncher)

            }
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Black
                ), text = "Google Login"
            )
        }
    }
}

@Composable
fun googleSignIn(signInLauncher: ManagedActivityResultLauncher<Intent, FirebaseAuthUIAuthenticationResult>) {

    val googleClient =
        GoogleAuthUiClient(LocalContext.current, Identity.getSignInClient(LocalContext.current))
//    googleClient.signIn()
//    life

    val context = LocalContext.current

}
