package com.info.mybook.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.info.mybook.MainActivity
import com.info.mybook.R
import com.info.mybook.databinding.ActivityLoginBinding
import com.info.mybook.models.LoginResponse
import com.info.mybook.utils.AppUtils
import com.info.mybook.utils.NetworkUtils
import com.info.mybook.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginviewModel : LoginViewModel by viewModels()

    private lateinit var auth: FirebaseAuth
    private lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        configureGoogleSignIn()

        //Resources.getSystem().getString(R.string.requsername)
        binding.btnSignIn.setOnClickListener{
            if(NetworkUtils.isNetworkAvailable(this@Login)){
                if(binding.etSinInEmail.text.toString().isEmpty()) {
                    AppUtils.errorShowSnackbar(this@Login, findViewById(android.R.id.content),
                        getString(R.string.requsername)
                    )
                }
                else if(binding.etSinInPassword.text.toString().isEmpty()){
                    AppUtils.errorShowSnackbar(this@Login, findViewById(android.R.id.content),
                        getString(R.string.reqpwd)
                    )
                }
                else{
                    hideKeyBoard()
                    //AppUtils.showProgressBar(this@Login)
                    loginviewModel.loginUsers(binding.etSinInEmail.text.toString(), binding.etSinInPassword.text.toString())
                }
                // Observe the loginViewState LiveData
                loginviewModel.loginViewState.observe(this@Login, Observer { state ->
                    when (state) {
                        is LoginViewModel.LoginViewState.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is LoginViewModel.LoginViewState.Success -> showSuccess(state.loginResponse)
                        is LoginViewModel.LoginViewState.Error -> showError(state.errorMessage)
                    }
                })
            }
            else{
                AppUtils.errorShowSnackbar(this@Login, findViewById(android.R.id.content),"Please check your internet connection!!")
            }
        }

        binding.btnSignInWithGoogle.setOnClickListener{
           signInWithGoogle()
        }
    }

    private fun configureGoogleSignIn() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this) {
                Toast.makeText(this, "Google Play Services error", Toast.LENGTH_SHORT).show()
            }
            .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
            .build()
    }

    fun signInWithGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            if (result != null) {
                if (result.isSuccess) {
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account)
                } else {
                    Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(this, "Sign-in successful: ${user?.displayName} ${user?.photoUrl}", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun showSuccess(loginResponse: LoginResponse) {
        AppUtils.successShowSnackbar(this@Login, findViewById(android.R.id.content),"Successfully login!!")
        binding.progressBar.visibility = View.GONE
        //AppUtils.hideProgressBar()
        val intent = Intent(this@Login, MainActivity::class.java)
        //intent.putExtra("key", "value")
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    private fun showError(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
        AppUtils.errorShowSnackbar(this@Login, findViewById(android.R.id.content), errorMessage)
    }

    private fun hideKeyBoard(){
        val view: View? = this@Login.currentFocus
        if (view != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}