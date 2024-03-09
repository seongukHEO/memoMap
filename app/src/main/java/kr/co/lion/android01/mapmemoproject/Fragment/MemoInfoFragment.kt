package kr.co.lion.android01.mapmemoproject.Fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.naver.maps.geometry.LatLng
import kr.co.lion.android01.mapmemoproject.Activity.MemoActivity
import kr.co.lion.android01.mapmemoproject.Activity.NaverMapActivity
import kr.co.lion.android01.mapmemoproject.DataClassAll.MemoInfo
import kr.co.lion.android01.mapmemoproject.SQL.DAO.InfoDAO
import kr.co.lion.android01.mapmemoproject.SQL.DAO.MemoDAO
import kr.co.lion.android01.mapmemoproject.Util
import kr.co.lion.android01.mapmemoproject.databinding.FragmentMemoInfoBinding

class MemoInfoFragment : BottomSheetDialogFragment() {

    lateinit var fragmentMemoInfoBinding: FragmentMemoInfoBinding
    lateinit var naverMapActivity: NaverMapActivity




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMemoInfoBinding = FragmentMemoInfoBinding.inflate(layoutInflater)
        naverMapActivity = activity as NaverMapActivity
        showResult()
        setEvent()

        return fragmentMemoInfoBinding.root
    }



    //내용을 보여준다
    private fun showResult() {
        fragmentMemoInfoBinding.apply {
            val getIdx = arguments?.getInt("idx")
            if (getIdx != null){
                val idx = MemoDAO.selectOneMemo(naverMapActivity, getIdx)

                textViewTitle.text = "${idx?.title}"
                textViewDate.text = "${idx?.date}"
                textViewContents.text = "${idx?.contents}"
            }


        }
    }

    //이벤트 설정
    private fun setEvent() {
        fragmentMemoInfoBinding.apply {
            modifyButton.setOnClickListener {
                var newIntent = Intent(naverMapActivity, MemoActivity::class.java)
                startActivity(newIntent)

            }

            deleteButton.setOnClickListener {
                Util.showDiaLog(
                    naverMapActivity,
                    "메모 삭제",
                    "메모를 삭제하시겠습니까?"
                ) { dialogInterface: DialogInterface, i: Int ->
                    val getIdx = arguments?.getInt("idx")!!
                    val info = MemoDAO.selectOneMemo(naverMapActivity, getIdx)
                    if (info != null){
                        naverMapActivity.removeMarker(info.latitude, info.longitude)
                    }

                    if (getIdx != null){
                        MemoDAO.deleteMemo(naverMapActivity, getIdx)
                    }
                    dismiss()
                }

            }
            cancelButton.setOnClickListener {
                dismiss()
            }
        }
    }
}

















































