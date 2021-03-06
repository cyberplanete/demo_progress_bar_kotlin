package net.cyberplanete.backgroundexecution

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import eu.tutorials.backgroundexecution.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    //Create a dialog variable
    var customProgressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExecute:Button = findViewById(R.id.btn_execute)
        btnExecute.setOnClickListener {
            showProgressDialog()
            //Which scope: It will run on the lifecycle scope
            lifecycleScope.launch {
                execute("Task executed successfully.")
            }
        }

    }

// Use suspend to make it works as a coroutine function
    private suspend fun execute(result:String){
        //TODO(You can code here what you wants to execute in background execution without freezing the UI
        // Suspend fonction.. It suspend until it return the result ---
        withContext(Dispatchers.IO) {
            // This is just a for loop which is executed for 1000000 times.
            for (i in 1..1000000) {
                Log.e("delay : ", "" + i)
            }
            // show a toast when thread Dispatchers.IO for loop is finished
            runOnUiThread {
                cancelProgressDialog()
                //I must specify on which thread (ex: runOnUiThread) this toast must be runned otherwise i will get an error as it run in the background withContext(Dispatchers.IO) thread
                Toast.makeText(
                    this@MainActivity, result,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Method is used to show the Custom Progress Dialog.
     */
    private fun showProgressDialog() {
        customProgressDialog = Dialog(this@MainActivity)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it on screen.
        customProgressDialog?.show()
    }


    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }



}