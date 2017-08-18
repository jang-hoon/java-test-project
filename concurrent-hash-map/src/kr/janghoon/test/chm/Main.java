package kr.janghoon.test.chm;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	public static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//	public static HashMap<String, String> map = new HashMap<>();
	
	public static AtomicInteger count = new AtomicInteger(0);
	public static Thread mainThread;
	
	public static void main(String[] args) throws InterruptedException {
		mainThread = Thread.currentThread();
		
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<1000; i++){
					map.put("" + i, "" + i);
					System.out.println("add " + i);
				}
				
				if(count.decrementAndGet() == 0){
					synchronized (mainThread){
						mainThread.notify();	
					}
				}
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<1000; i++){
					int count = 0;
					ArrayList<String> removedList = new ArrayList<>();
					for(String key : map.keySet()){
						String value = map.get(key);
						if(key.equals(value)){
							removedList.add(map.remove(key));
							count++;
						}
					}
					
					StringBuilder builder = new StringBuilder();
					for(String value : removedList){
						builder.append(value + ", ");
					}
					System.out.println("remove " + count + " : " + builder.toString());
				}
				
				if(count.decrementAndGet() == 0){
					synchronized (mainThread){
						mainThread.notify();	
					}
				}
			}
		});
		
		count.incrementAndGet();
		count.incrementAndGet();

		thread1.start();
		thread2.start();
		
		synchronized (mainThread){
			mainThread.wait();
		}
		
		System.out.println(map.size());
	}
}
