package com.robbin.java.jdbc.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * 数据库并发问题测试
 * 
 * @author robbin.zhang
 * @date 2018/01/17 21:20
 * 
 */
public class DBConcurrentTest {
	
	/**
	 * 下面，我们把并发中的共享资源从类变量转移到数据库中。 
	 * 先来看看使用框架的情况，以JPA为例（充血模型）
	 */
	@Test
	public void test_01(){
		
		 Executor executor = Executors.newFixedThreadPool(10);
	        for(int i=0;i<1000;i++){
	            executor.execute(new Runnable() {
	                @Override
	                public void run() {
//	                    demo2.test();
	                }
	            });
	        }
	}
	
	/* public void test(){
	        TestNum testNum = testNumDao.findOne("1");
	        testNum.setCount(testNum.getCount()+1);
	        testNumDao.save(testNum);
	 }*/
	
	/**
	 * 改成直接用sql如何呢（贫血模型）
	 * 
	 * 数据库结果： count ： 1000 达到了预期效果 这个例子我顺便记录了耗时,控制台打印:共花费：113 ms
	 * 简单对比一下二，三两个例子，都是想对数据库的count进行+1操作，
	 * 唯一的区别就是，后者的+1计算发生在数据库，而前者的计算依赖于事先查出来的值，并且计算发生在程序的内存中。
	 * 而现在大部分的ORM框架的兴起，导致了写第二种代码的程序员变多，不注意并发的话，就会出现问题。
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String testSql() throws InterruptedException {
		
        final CountDownLatch countDownLatch = new CountDownLatch(1000);
        long start = System.currentTimeMillis();
        Executor executor = Executors.newFixedThreadPool(10);
        for(int i=0;i<1000;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
//                    jdbcTemplate.execute("update test_num set count = count + 1 where id = '1'");
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        long costTime =System.currentTimeMillis() - start;
        System.out.println("共花费："+costTime+" s");
        return "testSql";
    }
	
	/**
	 * 业务场景
	 * 
	 *	修改个人信息
	 *	修改商品信息
	 *	扣除账户余额，扣减库存
	 *
	 * 业务场景分析
	 * =======================================第一个场景==========================================================================
	 * 
	 * 
	 *	第一个场景，互联网如此众多的用户修改个人信息，这算不算并发？答案是：算也不算。 
	 *	算，从程序员角度来看，每一个用户请求进来，都是调用的同一个修改入口，具体一点，就是映射到controller层的同一个requestMapping，所以一定是并发的。 
	 *	不算，虽然程序是并发的，但是从用户角度来分析，每个人只可以修改自己的信息，所以，不同用户的操作其实是隔离的，所以不算“并发”。
	 *  这也是为什么很多开发者，在日常开发中一直不注意并发控制，却也没有发生太大问题的原因，大多数初级程序员开发的还都是CRM，OA，CMS系统。
	 *		
	 *	回到我们的并发，第一种业务场景，是可以使用如上模式的，对于一条用户数据的修改，我们允许程序员读取数据到内存中，内存计算修改（耗时操作），提交更改，提交事务。
	 *
	 *  Transaction start
		User user = userDao.findById("1");
		user.setName("newName");
		user.setAge(user.getAge()+1);
		...//其他耗时操作
		userDao.save(user);
		//Transaction commit
		
		这个场景变现为：几乎不存在并发，不需要控制，场景乐观。
		
		为了严谨，也可以选择控制并发，但我觉得这需要交给写这段代码的同事，让他自由发挥。
		
		=======================================第二个场景==========================================================================
		
		第二个场景已经有所不同了，同样是修改一个记录，但是系统中可能有多个操作员来维护，此时，商品数据表现为一个共享数据，所以存在【微弱的并发】，【通常表现为数据的脏读】，
		例如操作员A，B同时对一个商品信息维护，我们希望只能有一个操作员修改成功，另外一个操作员得到错误提示（该商品信息已经发生变化），否则，两个人都以为自己修改成功了，但是其实只有一个人完成了操作，另一个人的操作被覆盖了。
		
		这个场景表现为：存在并发，需要控制，允许失败，场景乐观。
		
		通常我建议这种场景使用乐观锁，即在商品属性添加一个version字段标记修改的版本，这样两个操作员拿到同一个版本号，第一个操作员修改成功后版本号变化，另一个操作员的修改就会失败了。
		                 
		
		=======================================第三个场景==========================================================================
		
		这个场景表现为：存在频繁的并发，需要控制，不允许失败，场景悲观。

		强调一下，本例不应该使用在项目中，只是为了举例而设置的一个场景，因为这种贫血模型无法满足复杂的业务场景，而且依靠单机事务来保证一致性，并发性能和可扩展性能不好。
		
		一个秒杀场景，大量请求在短时间涌入，是不可能像第二种场景一样，100个并发请求，一个成功，其他99个全部异常的。
		
		设计方案应该达到的效果是：有足够库存时，允许并发，库存到0时，之后的请求全部失败；有足够金额时，允许并发，金额不够支付时立刻告知余额不足。
		
		可以利用数据库的行级锁， 
		update set balance = balance - money where userId = ? and balance >= money; 
		update stock = stock - number where goodsId = ? and stock >= number ; 然后在后台 查看返回值是否影响行数为1，判断请求是否成功，利用数据库保证并发。
		
		需要补充一点，我这里所讲的秒杀，并不是指双11那种级别的秒杀，那需要多层架构去控制并发，前端拦截，负载均衡….不能仅仅依赖于数据库的，会导致严重的性能问题。为了留一下一个直观的感受，这里对比一下oracle，mysql的两个主流存储引擎：innodb，myisam的性能问题。
		
		oracle:
		10000个线程共计1000000次并发请求：共花费：101017 ms =>101s
		innodb:
		10000个线程共计1000000次并发请求：共花费：550330 ms =>550s
		myisam:
		10000个线程共计1000000次并发请求：共花费：75802 ms =>75s
		
		可见，如果真正有大量请求到达数据库，光是依靠数据库解决并发是不现实的，所以仅仅只用数据库来做保障而不是完全依赖。需要根据业务场景选择合适的控制并发手段。
		
	 *
	 */
	public void test_03(){
		
		
		
	}
	
	
}
