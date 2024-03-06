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
            |nickName text not null,
            |id text primary key,
            |pw text not null)
        """.trimMargin()
        db?.execSQL(sql)

        //테이블 생성
        val sql2 = """create table MemoTable
            |(idx integer primary key autoincrement,
            |nickName text not null,
            |date text not null,
            |title not null,
            |contents not null,
            |latitude REAL,
            |longitude REAL)
        """.trimMargin()
        db?.execSQL(sql2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}