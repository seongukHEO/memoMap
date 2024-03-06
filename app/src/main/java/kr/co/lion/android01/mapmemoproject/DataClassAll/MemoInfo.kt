package kr.co.lion.android01.mapmemoproject.DataClassAll

data class MemoInfo(
    var idx:Int,
    var nickName: String,
    var date:String,
    var title:String,
    var contents:String,
    var latitude:Double,
    var longitude:Double
)