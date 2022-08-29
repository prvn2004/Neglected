package com.project.neglected.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.project.neglected.DataFiles.UserDetailsDataFile
import com.project.neglected.HomeActivity
import com.project.neglected.R
import com.project.neglected.databinding.FragmentLoginPageBinding

class LoginPage : Fragment() {

    lateinit var binding: FragmentLoginPageBinding
    private lateinit var auth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var database: DatabaseReference
    val Req_Code: Int = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginPageBinding.inflate(inflater, container, false)
        val view = binding.root

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        database = FirebaseDatabase.getInstance().reference


        mGoogleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }!!
        binding.loginButton.setOnClickListener {
            signInGoogle()
        }
        val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (acct != null) {
            val personName = acct.displayName
            Toast.makeText(activity, "signed-in as $personName", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val account = activity?.let { GoogleSignIn.getLastSignedInAccount(it) }
        account?.let { UpdateUI(it) }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
                val editor = preferences.edit()
                editor.putString("login", "Login")
                val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
                if (acct != null) {
                    val personName = acct.displayName.toString()
                    val personEmail = acct.email.toString()
                    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
                    WriteNewUser(personName, personEmail, uid)
                }
                editor.apply()
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun WriteNewUser(personName: String, personEmail: String, uid: String) {
        val User1 = UserDetailsDataFile(personName, personEmail)
        database.child("Users").child("UserNames").child("$uid").setValue(User1)
    }
}