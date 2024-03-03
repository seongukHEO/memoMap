package kr.co.lion.android01.mapmemoproject

import android.os.Bundle
import androidx.fragment.app.Fragment
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
        return fragmentBottomBinding.root
    }
}

















































