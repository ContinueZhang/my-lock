package com.example.mylock;

import com.example.mylock.entity.ICBCEntity;
import com.example.mylock.service.LockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyLockApplicationTests {

    @Autowired
    private LockService lockService;

    interface IMoneyFormat {
        String format(int i);
    }

    static class MyMoney {
        private final int money;

        public MyMoney(int money) {
            this.money = money;
        }

        public void printMoney(Function<Integer, String> iMoneyFormat) {
            System.out.println("我的存款：" + iMoneyFormat.apply(this.money));
        }
    }

    public static void main(String[] args) {
        MyMoney myMoney = new MyMoney(999999);

        Supplier ss = () -> "你好";
        Function<Integer, String> function = i -> new DecimalFormat("#,###").format(i);
        myMoney.printMoney(function.andThen(s -> "人民币" + s));

    }

    @Test
    public void selectTAll() {
        List<ICBCEntity> icbcList = lockService.selectTAll();
        icbcList.forEach(t -> System.out.println(t.toString()));
    }

    @Test
    public void contextLoads() {
        int[] nums = {33, 22, 55, -100, 2147483647, -1000};
        int min = Integer.MAX_VALUE;

        System.out.println(min);

        for (int num : nums) {
            if (num < min) {
                min = num;
            }
        }

        System.out.println(min);

        //jdk8
        System.out.println(IntStream.of(nums).min().getAsInt());
    }

}
