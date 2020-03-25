package com.demo.test;

/**
 * @author Joe
 * @version 1.0
 * @date 2019/11/25 17:15
 */

import com.demo.bean.Account;
import com.demo.configration.AppConfig;
import com.demo.service.AccountService;
import com.demo.service.impl.AccountServiceImpl;
import com.demo.service.impl.service;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 所有juint进行单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
//
@ContextConfiguration({"classpath:applicationContext.xml"})
public class test {
    @Autowired
    private AccountService ac;
//    ApplicationContext context = null;
//    @Before
//    public void before(){
//    //  context = new ClassPathXmlApplicationContext("applicationContext.xml");context
//        context = new AnnotationConfigApplicationContext(AppConfig.class);
//    }
    @Test
    public void test1(){


    //    Account a=ac.findAccountById(1);


        List<Account> a = ac.findAccountAll();
        for(Account account:a){
            System.out.println(account);
        }
        System.out.println("好的");
      //  System.out.println(a);
//        Account a = new Account();
//        a.setAccount(22);
//        a.setName("你好啊");
//        ac.addAccount(a);
    }
    @Autowired
    com.demo.service.impl.service service;
    @Test
    public void test2() {

    ac.transFrom("a","b",100);
       // System.out.println(ac.getClass().getName());

    }
    public static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    @Test
    public void test3(){

        threadLocal.set("mainVal");
                //新创建一个子线程
        Thread thread = new Thread(new Runnable() {

             public void run() {
                               System.out.println("子线程中的本地变量值:"+threadLocal.get());
                           }
        });
                thread.start();
               //输出main线程中的本地变量值
                System.out.println("mainx线程中的本地变量值:"+threadLocal.get());
    }
}
