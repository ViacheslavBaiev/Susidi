package com.example.susidi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var etPhoneNumber: EditText
    private lateinit var btnSignIn: MaterialButton
    private var phoneNumber = ""
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etPhoneNumber = findViewById(R.id.etPhone)
        btnSignIn = findViewById(R.id.btnSignIn)

        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                val text = s.toString()
                if(text.length == 13) {
                    Log.d(TAG, "Phone number is ready: $text")
                    phoneNumber = text;
                }

                if (text.length >= 14) {
                    val newText = text.substring(0, text.length - 1)
                    s?.replace(0, s.length, newText)
                    isFormatting = false

                    return
                }

                if (text.isEmpty()) {
                    s?.replace(0, 0, "+380")
                } else if (text.startsWith("0")) {
                    s?.replace(0, 1, "+380")
                } else if (
                    text.startsWith("+380+")
                    || text.startsWith("+380-")
                    || text.startsWith("+3800")
                ) {
                    s?.replace(0, 5, "+380")
                } else if (text.startsWith("+380")) {
                    s?.replace(0, s.length, text)
                } else if (text.startsWith("+38") || text.startsWith("+80") || text.startsWith("380")) {
                    s?.replace(0, 3, "+380")
                } else if (text.startsWith("+3")) {
                    s?.replace(0, 2, "+38")
                } else if (text.startsWith("+") || text.startsWith("-")) {
                    s?.replace(0, 1, "+380")
                }

                if(s != null) {
                    phoneNumber = s.toString();
                }
                isFormatting = false
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })

        etPhoneNumber.setOnFocusChangeListener{v, hasFocus ->

            if (!hasFocus) {
                if(phoneNumber.length < 13) {
                    Toast.makeText(this, "phoneNumber is short", Toast.LENGTH_LONG).show()
                    v.setBackgroundResource(R.drawable.custom_input_error)
                } else if(phoneNumber.length > 13) {
                    Toast.makeText(this, "phoneNumber is long", Toast.LENGTH_LONG).show()
                } else {
                    v.setBackgroundResource(R.drawable.custom_input_good)

                    Log.d(TAG, phoneNumber)
                }
            }
        }

        btnSignIn.setOnClickListener { view ->


        }

    }


}

