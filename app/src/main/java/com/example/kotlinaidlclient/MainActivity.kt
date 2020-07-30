package com.example.kotlinaidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import com.example.kotlinaidlserver.aidl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "abc"
        val SERVER_URI = "com.example.kotlinaidlserver"
        val SERVER_ACTION = "aidl.service"
    }


    var iRemoteService: aidl? = null

    val mConnection = object : ServiceConnection {

        // Called when the connection with the service is established
        override fun onServiceConnected(className: ComponentName, service: IBinder) {

            Log.e(TAG, "Service has unexpectedly disconnected")
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            iRemoteService = aidl.Stub.asInterface(service)
        }

        // Called when the connection with the service disconnects unexpectedly
        override fun onServiceDisconnected(className: ComponentName) {
            Log.e(TAG, "Service has unexpectedly disconnected")
            iRemoteService = null
        }
    }

    fun initConection() {

            val intent = Intent(aidl::class.java.name)
            intent.action = SERVER_ACTION
            intent.setPackage(SERVER_URI)
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

    }

    override fun onStart() {
        super.onStart()
        initConection()
    }

    override fun onStop() {
        super.onStop()
        unbindService(mConnection)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        btn_sum.setOnClickListener(View.OnClickListener {
//            iRemoteService?.let {
//                Log.d(TAG, "sssssssss")
//                if (edt_number_one.length() > 0 && edt_number_tow.length() > 0) {
//                    Log.d(TAG, "sssssssss")
//                    tv_result.setText(
//                        iRemoteService!!.sum(
//                            edt_number_one.text.toString().toInt(),
//                            edt_number_tow.text.toString().toInt()
//                        ).toString()
//                    )
//                }
//            }
//        })

    }

    fun sum(view: View) {
        iRemoteService?.let {
            Log.d(TAG, "sssssssss")
            if (edt_number_one.length() > 0 && edt_number_tow.length() > 0) {
                Log.d(TAG, "sssssssss")
                tv_result.setText(
                    iRemoteService!!.sum(
                        edt_number_one.text.toString().toInt(),
                        edt_number_tow.text.toString().toInt()
                    ).toString()
                )
            }
        }
    }
}