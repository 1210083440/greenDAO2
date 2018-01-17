package net.bwie.greendao2.aplication;

import android.app.Application;

import net.bwie.greendao2.bean.DaoMaster;
import net.bwie.greendao2.bean.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * ORM：Object relative mapping对象关系映射：表 -> 数据模型类 ，列名 -> 类中的属性
 * greenDAO的使用
 * 1、导包：3行导包代码
 * 操作数据库得先有表，所以先有类
 * 2、创建类并指定相关属性，添加注解即可实现映射关系
 * 3、make project自动生成相关代码：
 * DaoMaster：框架主干类
 * DaoSession：每一次操作数据库就是一次和数据库的交互行为，称作会话，是一个概念，真正操作数据库的动作在会话中完成
 * xxxDao对象：专门用于操作具体某张表（类）的对象，他能够执行具体的增删改查操作
 *
 * 在全局环境application中初始化greenDAO
 * 创建开发者使用的数据库助手，DevOpenHelper，能够帮我们创建数据库db文件，并指定文件名，默认版本号为1
 * 用数据库助手帮我们获取get到数据库对象db
 * 告诉框架主干我要操作哪个数据库，由主干对象产生会话，是全局变量，方便在各个Activity/fragment使用
 * 会话中包含了具体的增删改查操作，dao对象
 *
 * */
public class MyApplication extends Application {

    private static DaoSession sDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        initGreenDAO();
    }

    private void initGreenDAO() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "food.db");
        Database db = helper.getWritableDb();
        sDaoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return sDaoSession;
    }
}
