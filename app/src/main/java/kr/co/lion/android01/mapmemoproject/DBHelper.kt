package kr.co.lion.android01.mapmemoproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Info.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //테이블 생성
        var sql = """create table InfoTable
            |(name text not null,
            |number integer not null,
            |nickName text primary key,
            |id text not null,
            |pw text not null)
        """.trimMargin()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}