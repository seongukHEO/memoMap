package kr.co.lion.android01.mapmemoproject

class Data {
}


enum class FragmentName(var str:String){
    MAIN_FRAGMENT("MainFragment"),
    JOIN_FRAGMENT("JoinFragment"),
    SEARCH_ID_FRAGMENT("SearchIDFragment"),
    SEARCH_PW_FRAGMENT("SearchPWFragment")
}

enum class FragmentName2(var str:String){
    INPUT_FRAGMENT("InputFragment"),
    MODIFY_FRAGMENT("ModifyFragment"),
    BOTTOM_FRAGMENT("BottomFragment")
}

data class UserInfo(
    var name:String,
    var number:Int,
    var nickName:String,
    var id:String,
    var pw:String
)















