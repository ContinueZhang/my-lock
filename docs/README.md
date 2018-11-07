# 锁
# 快速开始

> An awesome project.

## 什么是锁
    锁是计算机协调多个进程或线程并发访问一个资源的机制

## 表级锁
           读锁（共享锁）：针对一张表，多个session可以同时去读操作并互相不会影响
           写锁（排它锁）：针对一张表，多个session在一个session没有完成前，它会阻塞其它写锁和读锁

----------
### 读锁演示
           上锁语句：lock tables [tablename] read;
           解锁语句：unlock tables;
           create table t_inquiry_lock(id int,name varchar(200))engine='myisam';
           insert into t_inquiry_lock values(1,'小红');
           insert into t_inquiry_lock values(2,'小蓝');
           insert into t_inquiry_lock values(3,'小绿');
           
           SESSION1:
           lock tables t_inquiry_lock read;
           select id,name from t_inquiry_lock; v
           update t_inquiry_lock set name='笑笑' where id=2; x
           insert into t_inquiry_lock values(4,'小白'); x
           
           select id,name from t_user; x
           update t_user set name='笑笑' where id=2; x
           insert into t_user values(4,'小白'); x
           
           SESSION2:
           select id,name from t_inquiry_lock; v
           update t_inquiry_lock set name='笑笑' where id=2; ...
           insert into t_inquiry_lock values(4,'小白'); ...
           
           select id,name from t_user; v
           update t_user set name='笑笑' where id=2; v
           insert into t_user values(4,'小白'); v
            
            小结：
                操作粒度为表级锁时的读锁，读共享，在当前上锁session中只能对当前表进行读操作，不可对当前表和其他任何表
                进行写操作，如果进行写操作会抛错，必须释放锁才可以进行所有表的其他操作；
                非当前session不可对上锁的表进行写的操作，如果进行写操作会阻塞等待，但可以进行其它不上锁的表进行写操作。
### 写锁演示
           上锁语句：lock tables [tablename] write;
           解锁语句：unlock tables;
           SESSION1:
           select id,name from t_inquiry_lock; v
           insert into t_inquiry_lock values(4,'小紫');
           update t_inquiry_lock set name='大紫' where id=4; v
           insert into t_user values(1,'嗯'); x
           update t_user set name='cili' where id=1; x
           select id,name from t_user; x
           
           SESSION2:
           select id,name from t_inquiry_lock; ...
           insert into t_inquiry_lock values(4,'小紫'); ...
           update t_inquiry_lock set name='大紫' where id=4; ...
           insert into t_user values(1,'嗯'); v
           update t_user set name='cili' where id=1; v
           select id,name from t_user; v
           
           小结：
                对比读锁共同点是当前执行上锁的session都无法对其他表进行任何操作，非当前session都可以对非上锁的表进行
                操作，不同之处写锁对于上锁的session允许查看修改和新增，对于非上锁的session不允许查看、修改、新增。
           
           

## 行级锁
           
### -共享锁
           create table t_innodb_lock(id int,name varchar(200))engine='innodb';
           insert into t_innodb_lock values(1,'红红');
           insert into t_innodb_lock values(2,'蓝蓝');
           insert into t_innodb_lock values(3,'绿绿');
           create index test_innodb_a_ind on t_innodb_lock(id);
           create index test_innodb_a_ind on t_innodb_lock(name);
           
           SESSION1:
           set autocommit=0;
           update t_innodb_lock set name='1224' where id=1;           
           
           SESSION2:
           set autocommit=0;
           update t_innodb_lock set name='1995' where id=1; -- 同行进行阻塞
           update t_innodb_lock set name='1995' where id=2; -- 不同行成功
           
           异常可怕操作：
                更新时如果where条件没有索引，需要全盘检索进行锁表；
                更新时如果有索引但是索引是字符串没有加引号也会引起锁表；
           
           
### -排它锁


