package com.lieslee.kotlin_example

import android.os.Bundle
import android.os.Environment
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.socks.library.KLog
import java.io.File
import android.os.Environment.getExternalStorageDirectory



class MainActivity : AppCompatActivity() {

    var x:(()->Unit)? = null

    private var mTextMessage: TextView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage!!.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mTextMessage!!.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mTextMessage!!.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextMessage = findViewById(R.id.message) as TextView
        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mTextMessage?.apply { setOnClickListener { v: View -> main() } }
        findViewById(R.id.btn1)?.apply {setOnClickListener { this@MainActivity.x?.invoke() }  }
        findViewById(R.id.null_)?.apply { setOnClickListener { this@MainActivity.x = null } }
        findViewById(R.id.new_)?.apply { setOnClickListener { this@MainActivity.x = add() } }
        main()
    }

    fun main() {
        //map()
        //flatMap()
        //reduce() //fold类似reduce,就是第一个原素是外部传入的，不是从列表里面取的
        //filter()
//        this@MainActivity.x = add()
//        this@MainActivity.x?.invoke()
        //readTxt()
        readTxtSimple()
    }

}

fun readTxt(){
    var m = HashMap<Char,Int>()
    File("${getSDPath()}/记事.txt").readText().toCharArray().filterNot(Char::isWhitespace).forEach {
        var count = m[it]
        if(count == null) m[it] = 1 else m[it] = count+1
    }
    
    m.forEach(::printLog)
}
fun readTxtSimple(){
    File("${getSDPath()}/记事.txt").readText().toCharArray().filterNot(Char::isWhitespace)
            .groupBy { it }
            .map { it.key to it.value.size }.
            forEach (::printLog)
}

fun getSDPath(): String {
    var sdDir: File? = null
    val sdCardExist = Environment.getExternalStorageState()
            .equals(android.os.Environment.MEDIA_MOUNTED)//判断sd卡是否存在
    if (sdCardExist) {
        sdDir = Environment.getExternalStorageDirectory()//获取跟目录
    }
    return sdDir!!.toString()
}

fun add() : () -> Unit{
    var count = 1
    return fun(){
        printLog(++count)
    }
}
//fun add(x:Int = 0) = fun(y:Int) = x=y
fun add(x:Int = 0):(Int)-> Int{
    return fun(y:Int):Int{
        return x+y
    }
}



fun filter(){
    var list = (1..10).toList()
    //过滤，逐个传入Lambda表达式根据表达式的返回值true就添加到新的集合里面false就忽略
    var newList = list.filter { it % 2 == 0 }
    printLog(newList)
    //takeWhile根据表达式返回ture添加到新列表里面返回，返回false就braak打断后面的不执行
    var tList = list.takeWhile { it != 5 }

    printLog(tList)

}



fun reduce(){
    val list = listOf(1..5, 10..15, 20..25)
    var flatList = list.flatMap { it }
    printLog(flatList)
    //reduce就是取第一个原素，和下一个原素，传入Lambda表达式里面运算，返回值作为第一个原素继续循环操作直到列表结束
    var result = flatList.reduce { acc, int ->
        acc + int //返回值与下一个原素相加，就是叠加
    }

    printLog(result)
    var ranList = list.map {
        it.toList()
    }
    var rangeSumList = ranList.reduce { acc, list ->
        var accList: ArrayList<Int> = arrayListOf()
        for((index, value) in acc.withIndex()){
            accList.add(list.get(index) + value)
        }
        accList
    }
    printLog("""
        二维数组数与数之间相加
        """)
    printLog(ranList)
    printLog(rangeSumList)
}

fun flatMap() {
    val list = listOf(1..5, 10..15, 20..25)
    //flatmap如果什么都做直接返回it的话，就是一个抹平的动作，把大集合里面的子集抹平到一个新的List里面
    var flatList = list.flatMap { it }
    //因为flatMap方法需要表达式返回的是一个Iterable<R>，可迭代的任意类型，可以把里面的原素转换后抹平到新的list里面
    var stringFlatList = list.flatMap { it.map { "NO:$it" } }
    //上面的两个it代替的是不一样的原素，下发可以明朗一些：
    /*var stringFlatList = list.flatMap { intRange ->
        intRange.map {
            intElement -> "NO:$intElement"
        }
    }*/
    printLog(stringFlatList)
}


fun map() {
    var list = listOf(1, 2, 3, 4, 5, 6)
    //map方法 其实就是创建了一个新的List按Lambda表达是的返回值添加到新的list里面
    var newList = list.map { it + it }
    newList.forEach { printLog(it) }
}


fun printLog(any: Any?) {
    KLog.a(any)
}
