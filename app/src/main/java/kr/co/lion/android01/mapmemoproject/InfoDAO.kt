package kr.co.lion.android01.mapmemoproject

import android.content.Context

class InfoDAO {

    companion object{

        //selectOne
        fun selectOneInfo(context: Context, nickName:String) : UserInfo? {
            //쿼리 생성
            var sql = """select *
                |from InfoTable
                |where nickName = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(nickName)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            var cursor = dbHelper.writableDatabase.rawQuery(sql, args)

            if (cursor.moveToNext()){
                //순서값을 가져온다
                var a1 = cursor.getColumnIndex("name")
                var a2 = cursor.getColumnIndex("number")
                var a3 = cursor.getColumnIndex("nickName")
                var a4 = cursor.getColumnIndex("id")
                var a5 = cursor.getColumnIndex("pw")

                //값을 가져온다
                var name = cursor.getString(a1)
                var number = cursor.getInt(a2)
                var nickName = cursor.getString(a3)
                var id = cursor.getString(a4)
                var pw = cursor.getString(a5)

                var infoModel = UserInfo(name, number, nickName, id, pw)
                dbHelper.close()
                cursor.close()
                return infoModel
            }
            dbHelper.close()
            cursor.close()
            return null
        }

        fun selectOneInfo1(context: Context, number:Int) : UserInfo? {
            //쿼리 생성
            var sql = """select *
                |from InfoTable
                |where number = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(number.toString())

            //쿼리 실행
            var dbHelper = DBHelper(context)
            var cursor = dbHelper.writableDatabase.rawQuery(sql, args)

            if (cursor.moveToNext()){
                //순서값을 가져온다
                var a1 = cursor.getColumnIndex("name")
                var a2 = cursor.getColumnIndex("number")
                var a3 = cursor.getColumnIndex("nickName")
                var a4 = cursor.getColumnIndex("id")
                var a5 = cursor.getColumnIndex("pw")

                //값을 가져온다
                var name = cursor.getString(a1)
                var number = cursor.getInt(a2)
                var nickName = cursor.getString(a3)
                var id = cursor.getString(a4)
                var pw = cursor.getString(a5)

                var infoModel = UserInfo(name, number, nickName, id, pw)
                dbHelper.close()
                cursor.close()
                return infoModel
            }
            dbHelper.close()
            cursor.close()
            return null
        }

        fun selectOneInfo2(context: Context, id:String) : UserInfo? {
            //쿼리 생성
            var sql = """select *
                |from InfoTable
                |where id = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(id)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            var cursor = dbHelper.writableDatabase.rawQuery(sql, args)

            if (cursor.moveToNext()){
                //순서값을 가져온다
                var a1 = cursor.getColumnIndex("name")
                var a2 = cursor.getColumnIndex("number")
                var a3 = cursor.getColumnIndex("nickName")
                var a4 = cursor.getColumnIndex("id")
                var a5 = cursor.getColumnIndex("pw")

                //값을 가져온다
                var name = cursor.getString(a1)
                var number = cursor.getInt(a2)
                var nickName = cursor.getString(a3)
                var id = cursor.getString(a4)
                var pw = cursor.getString(a5)

                var infoModel = UserInfo(name, number, nickName, id, pw)
                dbHelper.close()
                cursor.close()
                return infoModel
            }
            dbHelper.close()
            cursor.close()
            return null
        }


        //selectAll
        fun selectAllInfo(context: Context):MutableList<UserInfo>{
            //쿼리 생성
            var sql = """select *
                |from InfoTable
                |order by nickName desc
            """.trimMargin()

            //쿼리 실행
            var dbHelper = DBHelper(context)
            var cursor = dbHelper.writableDatabase.rawQuery(sql, null)

            //정보를 담을리스트
            var infoList = mutableListOf<UserInfo>()

            while (cursor.moveToNext()){
                //순서값을 가져온다
                var a1 = cursor.getColumnIndex("name")
                var a2 = cursor.getColumnIndex("number")
                var a3 = cursor.getColumnIndex("nickName")
                var a4 = cursor.getColumnIndex("id")
                var a5 = cursor.getColumnIndex("pw")

                //값을 가져온다
                var name = cursor.getString(a1)
                var number = cursor.getInt(a2)
                var nickName = cursor.getString(a3)
                var id = cursor.getString(a4)
                var pw = cursor.getString(a5)

                var infoModel = UserInfo(name, number, nickName, id, pw)
                infoList.add(infoModel)
            }
            dbHelper.close()
            cursor.close()
            return infoList
        }


        //insert
        fun insertInfo(context: Context, userInfo: UserInfo){
            //쿼리 생성
            var sql = """insert into InfoTable
                |(name, number, nickName, id, pw)
                |values(?, ?, ?, ?, ?)
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(userInfo.name, userInfo.number, userInfo.nickName, userInfo.id, userInfo.pw)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql , args)
            dbHelper.close()
        }


        //update
        fun updateInfo(context: Context, userInfo: UserInfo){
            //쿼리 생성
            var sql = """update InfoTable
                |set name = ?, number = ?, id = ?, pw = ?
                |where nickName = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(
                userInfo.name, userInfo.number, userInfo.id, userInfo.pw, userInfo.nickName
            )

            //쿼리 실행
            var dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        //delete
        fun deleteInfo(context: Context, nickName: String){
            //쿼리 생성
            var sql = """delete from InfoTable
                |where nickName = ?
            """.trimMargin()

            //?에 들어갈 값
            var args = arrayOf(nickName)

            //쿼리 실행
            var dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }
    }
}