package kr.co.lion.android01.mapmemoproject

import android.content.Context

class MemoDAO {

    companion object{

        //selectOne
        fun selectOneMemo(context: Context, nickname:String) : MemoInfo? {
            //쿼리 생성
            var sql = """select *
                |from MemoTable
                |where nickname = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(nickname)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            var cursor = dbHelper.writableDatabase.rawQuery(sql , args)

            if (cursor.moveToNext()){
                //순서값을 가져온다
                var a1 = cursor.getColumnIndex("nickname")
                var a2 = cursor.getColumnIndex("date")
                var a3 = cursor.getColumnIndex("title")
                var a4 = cursor.getColumnIndex("contents")
                var a5 = cursor.getColumnIndex("latitude")
                var a6 = cursor.getColumnIndex("longitude")

                //값을 가져온다
                var nickname = cursor.getString(a1)
                var date = cursor.getString(a2)
                var title = cursor.getString(a3)
                var contents = cursor.getString(a4)
                var latitude = cursor.getFloat(a5)
                var longitude = cursor.getFloat(a6)

                var memoModel = MemoInfo(nickname, date, title, contents, latitude, longitude)
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
                |order by nickname desc
            """.trimMargin()

            //쿼리 실행
            var dbHelper = DBHelper(context)
            var cursor = dbHelper.writableDatabase.rawQuery(sql, null)

            //리스트 생성
            var memoList = mutableListOf<MemoInfo>()

            while (cursor.moveToNext()){
                //순서값을 가져온다
                var a1 = cursor.getColumnIndex("nickname")
                var a2 = cursor.getColumnIndex("date")
                var a3 = cursor.getColumnIndex("title")
                var a4 = cursor.getColumnIndex("contents")
                var a5 = cursor.getColumnIndex("latitude")
                var a6 = cursor.getColumnIndex("longitude")

                //값을 가져온다
                var nickname = cursor.getString(a1)
                var date = cursor.getString(a2)
                var title = cursor.getString(a3)
                var contents = cursor.getString(a4)
                var latitude = cursor.getFloat(a5)
                var longitude = cursor.getFloat(a6)

                var memoModel = MemoInfo(nickname, date, title, contents, latitude, longitude)
                memoList.add(memoModel)
            }
            dbHelper.close()
            cursor.close()
            return memoList
        }



        //insert
        fun insertMemo(context: Context, memoInfo: MemoInfo){
            //쿼리 생성
            var sql = """insert into MemoTable
                |(nickname, date, title, contents, latitude, longitude)
                |values(?, ?, ?, ?, ?, ?)
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(memoInfo.nickName, memoInfo.date, memoInfo.title, memoInfo.contents, memoInfo.latitude, memoInfo.longitude)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }




        //update
        fun updateMemo(context: Context, memoInfo: MemoInfo){
            //쿼리 생성
            var sql = """update MemoTable
                |set date = ?, title = ?, contents = ?, latitude = ?, longitude = ?
                |where nickname = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(memoInfo.date, memoInfo.title, memoInfo.contents, memoInfo.latitude, memoInfo.longitude)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }




        //delete
        fun deleteMemo(context: Context, nickName:String){
            //쿼리 생성
            var sql = """delete from MemoTable
                |where nickName = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(nickName)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql,args)

        }

        fun getUserAllInfo(context: Context, nickName: String) : UserInfoAll? {
            var sql = """select InfoTable.nickname, InfoTable.id, MemoTable.nickname, MemoTable.date, MemoTable.title, MemoTable.contents, 
                MemoTable.latitude, MemoTable.longitude
                From InfoTable
                Left Join MemeTable ON InfoTable.nickname = MemoTable.nickname
                where InfoTable.nickname = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(nickName)

            var dbHelper = DBHelper(context)
            var cursor = dbHelper.writableDatabase.rawQuery(sql , args)

            var userInfoAll:UserInfoAll? = null

            if (cursor.moveToNext()){
                var a1 = cursor.getColumnIndex("nickname")
                var a2 = cursor.getColumnIndex("id")
                var a3 = cursor.getColumnIndex("date")
                var a4 = cursor.getColumnIndex("title")
                var a5 = cursor.getColumnIndex("contents")
                var a6 = cursor.getColumnIndex("latitude")
                var a7 = cursor.getColumnIndex("longitude")

                var nickname = cursor.getString(a1)
                var id = cursor.getString(a2)
                var date = cursor.getString(a3)
                var title = cursor.getString(a4)
                var contents = cursor.getString(a5)
                var latitude = cursor.getFloat(a6)
                var longitude = cursor.getFloat(a7)

                userInfoAll = UserInfoAll(nickname, id, date, title, contents, latitude, longitude)
            }
            cursor.close()
            dbHelper.close()
            return userInfoAll
        }




    }
}