package kr.co.lion.android01.mapmemoproject

import android.content.Context
import android.content.DialogInterface
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.concurrent.thread

class enum {

    companion object{
        //DiaLog 생성
        fun showDiaLog(context: Context, title:String, message:String, listener:(DialogInterface, Int) -> Unit){
            var diaLogBuilder = MaterialAlertDialogBuilder(context)
            diaLogBuilder.setTitle(title)
            diaLogBuilder.setMessage(message)
            diaLogBuilder.setPositiveButton("확인", listener)
            diaLogBuilder.setNegativeButton("취소", null)
            diaLogBuilder.show()

        }
        //키보드를 올려준다
        fun showSoftInput(view: View, context: Context){
            // 포커스를 준다.
            view.requestFocus()

            thread {
                SystemClock.sleep(1000)
                val inputMethodManager = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(view, 0)
            }
        }
        //키보드를 내려준다
        fun hideSoftInput(activity: AppCompatActivity){
            //현재 포커스를 가지고있는 뷰가 있다면 키보드를 내린다
            if(activity.window.currentFocus != null){
                val inputMethodManager = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(activity.window.currentFocus?.windowToken, 0);
            }
        }


    }
}