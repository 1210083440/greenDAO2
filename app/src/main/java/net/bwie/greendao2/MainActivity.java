package net.bwie.greendao2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.bwie.greendao2.adapter.FoodAdapter;
import net.bwie.greendao2.aplication.MyApplication;
import net.bwie.greendao2.bean.DataBean;
import net.bwie.greendao2.bean.DataBeanDao;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * ORM：Object relative mapping对象关系映射：表 -> 数据模型类 ，列名 -> 类中的属性
 * greenDAO的使用
 * 1、导包：3行导包代码
 * 操作数据库得现有表，所以先有类
 * 2、创建类并指定相关属性，添加注解即可实现映射关系
 * 3、make project自动生成相关代码：
 * DaoMaster：框架主干类
 * DaoSession：每一次操作数据库就是一次和数据库的交互行为，称作会话，是一个概念，真正操作数据库的动作在会话中完成
 * xxxDao对象：专门用于操作具体某张表（类）的对象，他能够执行具体的增删改查操作
 * <p>
 * 在全局环境application中初始化greenDAO
 * 创建开发者使用的数据库助手，DevOpenHelper，能够帮我们创建数据库db文件，并指定文件名，默认版本号为1
 * 用数据库助手帮我们获取get到数据库对象db
 * 告诉框架主干我要操作哪个数据库，由主干对象产生会话
 * 会话中包含了具体的增删改查操作，dao对象
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button mInsertBtn;
    protected Button mDeleteBtn;
    protected Button mUpdateBtn;
    protected Button mQueryBtn;
    protected RecyclerView mRecyclerView;
    protected EditText mArticleIdEt;
    protected EditText mArticleTitleEt;
    private DataBeanDao mDao;
    private FoodAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();

        // 获取专门用于操作DataBean的dao对象
        mDao = MyApplication.getDaoSession()
                .getDataBeanDao();
    }

    @Override
    public void onClick(View view) {
        String articleId = mArticleIdEt.getText().toString();
        String articleTitle = mArticleTitleEt.getText().toString();
        if (view.getId() == R.id.insert_btn) {
            insert(articleId, articleTitle);
        } else if (view.getId() == R.id.delete_btn) {
            delete(articleId);
        } else if (view.getId() == R.id.update_btn) {
            update(articleId, articleTitle);
        } else if (view.getId() == R.id.query_btn) {
            query();
        }
    }

    private void insert(String articleId, String articleTitle) {
        // 插入之前先判断：如果不存在id相同的文章，能插入
        QueryBuilder<DataBean> builder = mDao.queryBuilder();
        Query<DataBean> query = builder.where(DataBeanDao.Properties.Id.eq(articleId))
                .build();
        DataBean queryData = query.unique();
        if (queryData == null) {
            DataBean data = new DataBean();
            data.set_id(System.currentTimeMillis());
            data.setId(articleId);
            data.setTitle(articleTitle);
            data.setPic("https://www.baidu.com/img/bd_logo1.png");

            mDao.insert(data);
            Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(String articleId) {
        // 先查询符合条件的数据，如果能查到则删除，否则没有意义
        // 以文章id作为查询条件
        QueryBuilder<DataBean> builder = mDao.queryBuilder();
        Query<DataBean> query = builder.where(DataBeanDao.Properties.Id.eq(articleId))// 【databean表】的【列名】，列名叫【id】，值是多少
                .build();
        // 查询满足条件的唯一一个数据
        DataBean data = query.unique();// 唯一
        // 如果能查到该数据，则删除
        if (data != null) {
            mDao.delete(data);
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }

    // 以文章id为条件，修改标题
    private void update(String articleId, String articleTitle) {
        QueryBuilder<DataBean> builder = mDao.queryBuilder();
        Query<DataBean> query = builder.where(DataBeanDao.Properties.Id.eq(articleId))
                .build();
        DataBean data = query.unique();
        if (data != null) {
            data.setTitle(articleTitle);
            mDao.update(data);
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        }
    }

    // 查询全部数据
    private void query() {
        // 构建一个Query类执行查询操作
        QueryBuilder<DataBean> builder = mDao.queryBuilder();
        // 如没有查询条件，直接构建一个查询对象
        Query<DataBean> query = builder.build();
        // 查询全部数据
        List<DataBean> list = query.list();
        // 判断是否能够查询到数据
        if (list != null && !list.isEmpty()) {
            // 展示数据
            mAdapter.setDatas(list);
        }
    }

    private void initView() {
        mInsertBtn = (Button) findViewById(R.id.insert_btn);
        mInsertBtn.setOnClickListener(MainActivity.this);
        mDeleteBtn = (Button) findViewById(R.id.delete_btn);
        mDeleteBtn.setOnClickListener(MainActivity.this);
        mUpdateBtn = (Button) findViewById(R.id.update_btn);
        mUpdateBtn.setOnClickListener(MainActivity.this);
        mQueryBtn = (Button) findViewById(R.id.query_btn);
        mQueryBtn.setOnClickListener(MainActivity.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mArticleIdEt = (EditText) findViewById(R.id.article_id_et);
        mArticleTitleEt = (EditText) findViewById(R.id.article_title_et);

        mAdapter = new FoodAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
