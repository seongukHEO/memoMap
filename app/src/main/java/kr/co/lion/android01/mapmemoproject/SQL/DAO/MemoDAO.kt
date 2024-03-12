package kr.co.lion.android01.mapmemoproject.SQL.DAO

import android.content.Context
import kr.co.lion.android01.mapmemoproject.DataClassAll.MemoInfo
import kr.co.lion.android01.mapmemoproject.DataClassAll.UserInfoAll
import kr.co.lion.android01.mapmemoproject.SQL.DBHelper

class MemoDAO {

    companion object{

        //selectOne
        fun selectOneMemo(context: Context, idx:Int) : MemoInfo? {
            //쿼리 생성
            var sql = """select *
                |from MemoTable
                |where idx = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(idx.toString())

            //쿼리 실행
            var dbHelper = DBHelper(context)
            var cursor = dbHelper.writableDatabase.rawQuery(sql , args)

            if (cursor.moveToNext()){
                //순서값을 가져온다
                var idx1 = cursor.getColumnIndex("idx")
                var a1 = cursor.getColumnIndex("nickName")
                var a2 = cursor.getColumnIndex("date")
                var a3 = cursor.getColumnIndex("title")
                var a4 = cursor.getColumnIndex("contents")
                var a5 = cursor.getColumnIndex("latitude")
                var a6 = cursor.getColumnIndex("longitude")

                //값을 가져온다
                var idx = cursor.getInt(idx1)
                var nickName = cursor.getString(a1)
                var date = cursor.getString(a2)
                var title = cursor.getString(a3)
                var contents = cursor.getString(a4)
                var latitude = cursor.getDouble(a5)
                var longitude = cursor.getDouble(a6)

                var memoModel = MemoInfo(idx, nickName, date, title, contents, latitude, longitude)
                dbHelper.close()
                cursor.close()
                return  memoModel

            }else{
                dbHelper.close()
                cursor.close()
                return null
            }

        }


        //selectAll
        fun selectAllMemo(context: Context) : MutableList<MemoInfo>{
            //쿼리 생성
            var sql = """select *
                |from MemoTable
                |order by idx desc
            """.trimMargin()

            //쿼리 실행
            var dbHelper = DBHelper(context)
            var cursor = dbHelper.writableDatabase.rawQuery(sql, null)

            //리스트 생성
            var memoList = mutableListOf<MemoInfo>()

            while (cursor.moveToNext()){
                //순서값을 가져온다
                var a0 = cursor.getColumnIndex("idx")
                var a1 = cursor.getColumnIndex("nickName")
                var a2 = cursor.getColumnIndex("date")
                var a3 = cursor.getColumnIndex("title")
                var a4 = cursor.getColumnIndex("contents")
                var a5 = cursor.getColumnIndex("latitude")
                var a6 = cursor.getColumnIndex("longitude")

                //값을 가져온다
                var idx = cursor.getInt(a0)
                var nickName = cursor.getString(a1)
                var date = cursor.getString(a2)
                var title = cursor.getString(a3)
                var contents = cursor.getString(a4)
                var latitude = cursor.getDouble(a5)
                var longitude = cursor.getDouble(a6)

                var memoModel = MemoInfo(idx, nickName, date, title, contents, latitude, longitude)
                memoList.add(memoModel)
            }
            dbHelper.close()
            cursor.close()
            return memoList
        }



        //insert
        fun insertMemo1(context: Context, memoInfo: MemoInfo){
            //쿼리 생성
            var sql = """insert into MemoTable
                |(nickName, date, title, contents, latitude, longitude)
                |values(?, ?, ?, ?, ?, ?)
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(memoInfo.nickName, memoInfo.date, memoInfo.title, memoInfo.contents, memoInfo.latitude, memoInfo.longitude)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            //Log.d("show3434","${result}" )
            dbHelper.close()
        }


        //update
        fun updateMemo(context: Context, memoInfo: MemoInfo){
            //쿼리 생성
            var sql = """update MemoTable
                |set nickName = ?, date = ?, title = ?, contents = ?, latitude = ?, longitude = ?
                |where idx = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(memoInfo.nickName, memoInfo.date, memoInfo.title, memoInfo.contents, memoInfo.latitude, memoInfo.longitude, memoInfo.idx)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }




        //delete
        fun deleteMemo(context: Context, idx: Int){
            //쿼리 생성
            var sql = """delete from MemoTable
                |where idx = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(idx)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql,args)
            dbHelper.close()

        }
        fun deleteAllMemos(context: Context) {
            // 쿼리 생성
            val sql = "DELETE FROM MemoTable"

            // DBHelper 인스턴스 생성
            val dbHelper = DBHelper(context)

            // 쿼리 실행
            val db = dbHelper.writableDatabase
            db.execSQL(sql)

            // DBHelper 닫기
            dbHelper.close()
        }



        fun getUserAllInfo(context: Context, nickName: String) : UserInfoAll? {
            var sql = """select InfoTable.nickName, InfoTable.id, MemoTable.nickName, MemoTable.date, MemoTable.title, MemoTable.contents, 
                MemoTable.latitude, MemoTable.longitude
                From InfoTable
                Left Join MemoTable ON InfoTable.nickName = MemoTable.nickName
                where InfoTable.nickName = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(nickName)

            var dbHelper = DBHelper(context)
            var cursor = dbHelper.writableDatabase.rawQuery(sql , args)

            var userInfoAll: UserInfoAll? = null

            if (cursor.moveToNext()){
                var a1 = cursor.getColumnIndex("nickName")
                var a2 = cursor.getColumnIndex("id")
                var a3 = cursor.getColumnIndex("date")
                var a4 = cursor.getColumnIndex("title")
                var a5 = cursor.getColumnIndex("contents")
                var a6 = cursor.getColumnIndex("latitude")
                var a7 = cursor.getColumnIndex("longitude")

                var nickName = cursor.getString(a1)
                var id = cursor.getString(a2)
                var date = cursor.getString(a3)
                var title = cursor.getString(a4)
                var contents = cursor.getString(a5)
                var latitude = cursor.getDouble(a6)
                var longitude = cursor.getDouble(a7)

                userInfoAll = UserInfoAll(nickName, id, date, title, contents, latitude, longitude)
            }
            cursor.close()
            dbHelper.close()
            return userInfoAll
        }




    }
}