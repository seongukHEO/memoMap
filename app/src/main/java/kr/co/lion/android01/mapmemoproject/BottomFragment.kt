package kr.co.lion.android01.mapmemoproject

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.android01.mapmemoproject.databinding.FragmentBottomBinding

class BottomFragment : BottomSheetDialogFragment() {

    lateinit var fragmentBottomBinding: FragmentBottomBinding
    lateinit var secondActivity: SecondActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentBottomBinding = FragmentBottomBinding.inflate(layoutInflater)
        secondActivity = activity as SecondActivity
        showResult()
        setEvent()
        return fragmentBottomBinding.root
    }

    //내용을 보여준다
    fun showResult(){
        fragmentBottomBinding.apply {
            var nickname = arguments?.getString("nickname")

            //Log.d("sho1234", "${nickname}")
            if (nickname != null){
                var str = MemoDAO.selectOneMemo(secondActivity, nickname)
                Log.d("seong12", "${str?.title}")

                textViewTitle.text = "${str?.title}"
                textViewDate.text = "${str?.date}"
                textViewContents.text = "${str?.contents}"
            }

        }
    }

    //이벤트 설정
    fun setEvent(){
        fragmentBottomBinding.apply {
            modifyButton.setOnClickListener {
                dismiss()

            }

            deleteButton.setOnClickListener {
                enum.showDiaLog(secondActivity, "메모 삭제", "메모를 삭제하시겠습니까?"){ dialogInterface: DialogInterface, i: Int ->
                    dismiss()
                }

            }

            cancelButton.setOnClickListener {
                dismiss()
            }
        }
    }
}

















































