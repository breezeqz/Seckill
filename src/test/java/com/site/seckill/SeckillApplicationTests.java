package com.site.seckill;

import com.site.seckill.redis.UserKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {

	@Test
	public void contextLoads() {
		final List<List<Integer>> lists = combinationSum3(2, 18);
		for (List<Integer> list : lists) {
			for (Integer integer : list) {
				System.out.println(integer);
			}
		}
	}
	public List<List<Integer>> combinationSum3(int k, int n) {
		List<Integer> ls = new ArrayList<>();
		List<List<Integer>> lss = new ArrayList<>();
		backtrack(k,n,1,ls,lss);
		return lss;
	}
	public void backtrack(int k,int n,int begin,List<Integer> ls,List<List<Integer>> lss){
		if(n<0){
			return;
		}
		if(ls.size()==k&&n==0){
			lss.add(new ArrayList<>(ls));
		}
		for(int i = begin;i<=n;i++){
			ls.add(i);
			backtrack(k,n-i,i+1,ls,lss);
			ls.remove(ls.size()-1);

		}
	}
}
