package kr.co.lion.android01.mapmemoproject

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.android01.mapmemoproject.databinding.FragmentBottomBinding

class BottomFragment : BottomSheetDialogFragment() {

    lateinit var fragmentBottomBinding: FragmentBottomBinding
    lateinit var thirdActivity: ThirdActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentBottomBinding = FragmentBottomBinding.inflate(layoutInflater)
        thirdActivity = activity as ThirdActivity
        showResult()
        setEvent()
        return fragmentBottomBinding.root
    }

    //내용을 보여준다
    fun showResult(){
        fragmentBottomBinding.apply {
            textViewTitle.text = "안녕"
            textViewDate.text
            textViewContents.text = "안요요용옹"
        }
    }

    //이벤트 설정
    fun setEvent(){
        fragmentBottomBinding.apply {
            modifyButton.setOnClickListener {
                thirdActivity.replaceFragment(FragmentName2.MODIFY_FRAGMENT, true, true, null)

            }

            deleteButton.setOnClickListener {
                enum.showDiaLog(thirdActivity, "메모 삭제", "메모를 삭제하시겠습니까?"){ dialogInterface: DialogInterface, i: Int ->
                    dismiss()
                }

            }

            cancelButton.setOnClickListener {
                dismiss()
            }
        }
    }
}

















































