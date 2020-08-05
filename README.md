### Concurrent Systems:  
**Processes and Threads:**  
Process is an instance of a program or an application. Process contains the image of the application code, memory and some other resources. An OS can run multiple process at a same time. This is called concurrency at the process level. Each process have their own memory space. We can also have concurrency within the process by using Threads. 
Thread is sequence of instructions. Basically our codes are executed by Threads. Each process will have atleast one thread. In Java this one thread is called as main thread. We can create additional threads to run many Tasks concurrently. Applications that are using multiple threads then its called as MultiThreading. A thread cannot exist without process. All threads under a single process can share memory space between them. Threads can communicate with other threads of the same process.  

**Runnable Interface: (FunctionalInterface)**  
It represents a task to be run under a thread. It has one method run(), this method has no return type as well as no parameter.

We are creating a task called DownloadFilesTask which implements Runnable Interface.
```
public class DownloadFilesTask implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.activeCount());
        System.out.println("Downloading files:"+Thread.currentThread().getName());//Thread-0
        try {
            Thread.sleep(5000);//Thread stops for a 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Download completed:"+Thread.currentThread().getName());
    }
}
```

**Thread Start:**    
Thread class have start() method. Thread class will accept the implementation of Runnable interface as a constructor parameter(i.e DownloadFilesTask).

```
public static void main(String[] args) {
	System.out.println(Thread.currentThread().getName());//main
	Thread thread = new Thread(new DownloadFilesTask());
	thread.start();
	try {
		thread.join();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	System.out.println("Files are ready to be scanned.");
}
```

_thread.start()_ will start the run method of DownloadFilesTask in new Thread. If we call this Thread.start in loop for 10 times, it will create 10 separate threads and execute DownloadFilesTask.  
_Thread.sleep(5000)_ will cause the thread to sleep for specific time in milliseconds.   
_thread.join()_ will make the current main thread to wait for completion or interruption of referenced threads. In this case the main thread cannot do another things.  

**Thread Interruption:**  
Adding _thread.interrupt()_ in the main thread will send the signal to other threads for interruption and we need to check if the thread is interrupted to break the operation.

```
public static void main(String[] args) {
	Thread thread = new Thread(new DownloadFilesTask());
	thread.start();
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	thread.interrupt();//sends the interrupt request to the first thread started from this main thread
}
```

```
@Override
public void run() {
	System.out.println("Downloading files:"+Thread.currentThread().getName());
	for(int i = 0; i < Integer.MAX_VALUE; i++) {
		if(Thread.currentThread().isInterrupted()) return;
		System.out.println("Downloading bytes:"+i);
	}
	System.out.println("Download completed:"+Thread.currentThread().getName());
}
```

**Concurrency Issues:**  
If multiple threads access the same object and while changing that common object state we will run into below problems.  
**1) Race Condition:**  
Multiple threads try to modify the same data at same time.This will cause data inconsistent.  
**2) Visibility Problem:**  
If multiple threads access the same data and atleast one of them tries to change the data and this changes not visible to other threads.
Each CPU will have its own small sized Cache to store the values temporarily after fetching from the memory. JVM uses this CPU cache to avoid constant fetch to the memory (RAM). When
two different CPU threads are requesting a value from memory, each thread will store the current value to the respective CPU cache. Once the value calculation finished, the thread will update back to the memory. If the CPU1 thread updates the current value and CPU2 thread will still refer the old value from the cache. So CPU1 thread changed the value in memory but its not visible to the CPU2 thread. This called as visibility problem.

```
private static void raceConditionSimulation() {
	DownloadStatus status = new DownloadStatus();
	List<Thread> threads = new ArrayList<>();
	for (int i = 0; i < 10; i++) {
		Thread thread = new Thread(new DownloadFilesTask(status));
		thread.start();
		threads.add(thread);
	}
	for(Thread thread: threads) {
		try {
			thread.join(); //Join is used to denote, wait for all the 10 treads to complete their execution.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	System.out.println("Total Bytes Downloaded:"+status.getTotalBytes());
}
```


**Atomic Operations:**  (compare-and-swap)
Atomic operations are take place in one step. Like read and write operation of variable. Atomic operations cannot be interrupted and they are thread safe. An operation acting on shared memory is atomic if it completes in a single step relative to other threads.

**For example:**  
```
// It is atomic operation, It is take place in one step.  
int totalBytes = 0; 
 
//It is not an atomic operation. It is take please in three steps. 
totalBytes++;
/*
   1. Read value of totalBytes from main memory and store in CPU.
   2. CPU will Increment totalBytes variable by 1.
   3. After increment CPU will update this new value into the memory.
*/
```
**Strategies for Thread Safety:**  
1) Confinement or Restriction => Instead of sharing common object across threads, we can create object for each thread and we can combine the results once all the threads are completed.  
2) Immutability => If the state/data of the object is not changed once its created. Sharing immutable objects between threads are fine. Because these objects are read by the threads.  
3) Synchronization => Prevent the threads from accessing the same objects concurrently. We need to coordinate the access to an object across different threads. This can be achieved via Locks. This can cause deadlock where two threads waiting each other to complete.  
4) Atomic Objects => We can use Atomic Classes in Java, which is Thread safe. If we increment an Atomic integer, JVM will execute the increment in one single operation.
5) Partitioning => Multiple threads can access collection objects but only one thread can access the segment of Collection.

**1) Confinement:**  
Creating individual Task object for every thread and combining them once all the threads are finished. Refer ThreadDemoConfinement class.

**2) Synchronization:**  
**a) Using Lock Interface:**  
We can use any Lock implementation in the DownloadStatus class and add lock before the increment totalBytes and unlock after increment.

```
private Lock lock = new ReentrantLock();

public void incrementTotalBytes() {
	lock.lock();
	try {
		totalBytes++;
	} finally { //best way to release a lock incase of any exception also the lock will be released
		lock.unlock();
	}
}
```
	
**b) Using Synchronized Keyword in Method level:**  
We can specify synchronized keyword in the incrementTotalBytes method so that only one thread at a time can update the value.

```
public synchronized void incrementTotalBytes() {
	totalBytes++;
}

public synchronized void incrementFiles() {
	totalFiles++;
}

```

**c) Using Synchronized Block inside Method:**  

```
public void incrementTotalBytes() {
	synchronized (this) {
		totalBytes++;
	}
}

public void incrementTotalBytes() {
	synchronized (this) {
		totalFiles++;
	}
}
```

In the above solutions b and c we have two synchronized methods / synchronized blocks with monitoring value as this keyword, in the DownloadStatus class during the update of method incrementTotalBytes, the update for incrementFiles method has to happen but instead the incrementFiles will enter into lock mode. We need to lock a method incrementTotalBytes during the update of this method, at the same time we shouldn't lock incrementFiles method. 

Instead of using this keyword in the synchronized block we can define our own monitoring objects to each methods for avoiding the lock of other methods.

```
private Object totalBytesLock = new Object();
private Object totalFilesLock = new Object();

public void incrementTotalBytes() {
	synchronized (totalBytesLock) {
		totalBytes++;
	}
}

public synchronized void incrementTotalFiles() {
	synchronized (totalFilesLock) {
		totalFiles++;
	}
}

```

This will solve the lock happening between the two methods incrementTotalBytes & incrementFiles during the increment.

**d) Using volatile keyword:**  

