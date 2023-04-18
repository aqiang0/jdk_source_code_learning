# ArrayList

### ArrayList简介
ArrayList是一个动态数组，是通过数组来实现的，相比Array，ArrayList使用更灵活一点，可以在使用过程中动态的增长容量。
ArrayList可以在创建它时不指定大小，当我们动态添加和删除项目时，数组的大小会自动增加。当数组容量已经满了，再添加新的元素时，大概就是以下的步骤：
- 在堆内存上创建更大的内存（例如1.5倍大小的内存）。
- 将当前内存元素复制到新内存。
- 添加新元素，因为现在有更大的可用内存。
- 删除旧内存。

ArrayList实现了Serializable, Cloneable, Iterable<E>, Collection<E>, List<E>, RandomAccess接口，接下来通过增删改查几个方面了解ArrayList。

### 1.构造函数
了解构造函数之前，先来看看ArrayList的成员属性
```java
    // 默认容量，给空参构造的容量
    private static final int DEFAULT_CAPACITY = 10;
    // 空数组EE，当通过构造函数 ArrayList(int initialCapacity)构造列表时，elemantData 指向该空数组
    private static final Object[] EMPTY_ELEMENTDATA = {};
    // 空数组DEE 当通过无参构造函数 ArrayList()构造列表时，elemantData 指向该空数组
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    // 真正列表的引用
    transient Object[] elementData; // non-private to simplify nested class access
    // 列表元素的数量
    private int size;
```
>属性中有默认容量，两个空数组，这里简记为DEE和EE。最重要的是elementData和size，elementData可理解为数组的引用，指向储存数组元素的
> 地址，size是列表中元素的个数，这个要区别容量。

ArrayList有三种构造函数
```java
//构造初始容量为 10 的空列表。
ArrayList()
//集合中的元素按照迭代顺序放到列表中
ArrayList(Collection<? extends E> c)
//构造具有指定初始容量的空列表
ArrayList(int initialCapacity)
```
先来看看空参构造
```java
public ArrayList() {
        // 无参构造，直接给到DEE空数组
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
```
>把elementData指向DEE默认空数组，当首次添加元素时，会判断elementData是否为DEE，如果是会把容量直接给到默认容量10。

构造参数为集合
```java
public ArrayList(Collection<? extends E> c) {
        // 转换成数组
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            // 类型为ArrayList
            if (c.getClass() == ArrayList.class) {
                elementData = a;
            } else {
                // 复制数组
                elementData = Arrays.copyOf(a, size, Object[].class);
            }
        } else {
            // 元素大小为0，给到空数组EE
            elementData = EMPTY_ELEMENTDATA;
        }
    }
```
>先把集合转成数组，再把数组元素复制到列表中。
>
构造具有指定初始容量的空列表
```java
public ArrayList(int initialCapacity) {
        // 有参构造，容量大于0时，构造对应容量大小的数组
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            // 容量=0时，给到EE空数组
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }
```
>给定构造容量=0时，是把elementData执行空数组EE。

**空数组EE和DEE两者都是用来减少空数组的创建，这两个空数组都是static修饰，属于静态属性，是可以共享到所有实例的，所以所有空ArrayList都共享空数组，
减少空数组创建。EE和DEE两者的区别主要是用来起区分用，针对有参无参的构造在扩容时做区分走不通的扩容逻辑，优化性能。**
### 2.增
```java
public boolean add(E e) {
        // 先判断够不够容量，不够扩容
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        // 追加元素
        elementData[size++] = e;
        return true;
    }
private void ensureCapacityInternal(int minCapacity) {
        // 先计算实际需要容量，再扩容
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
        }
private static int calculateCapacity(Object[] elementData, int minCapacity) {
        // 无参构造给默认容量10，如果是首次添加，minCapacity肯定等于1
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
        }
private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        if (minCapacity - elementData.length > 0)
        // 扩容
        grow(minCapacity);
        }
```
>添加元素时，首先判断容量是否足够，实际当前容量+1即为实际需要的容量。这里calculateCapacity()方法中，如果是elementData是空数组DEE，则
> 直接扩容到默认容量10。
> 
**再看看具体扩容逻辑grow()**
```java
private void grow(int minCapacity) {
        // 现有元素数量
        int oldCapacity = elementData.length;
        // >> 1 相当于除于2  所以 newCapacity = 1.5 * oldCapacity
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        // 参数容量即minCapacity是实际需要扩充的容量，newCapacity是理论需要扩充的容量
        // 实际需要的比理论的大，取实际的大小
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        // 这里数组最大大小MAX_ARRAY_SIZE比整数最大大小Integer.MAX_VALUE小8
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            // MAX_ARRAY_SIZE < newCapacity < Integer.MAX_VALUE  取 Integer.MAX_VALUE
            // newCapacity > Integer.MAX_VALUE 取 Integer.MAX_VALUE
            newCapacity = hugeCapacity(minCapacity);,
        // 开辟新数组并且把旧数据复制过去
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```
>扩容的大小为当前容量的1.5倍，这里有一个最大数组大小MAX_ARRAY_SIZE，它比最大整数int小于8，如果计算扩容容量大于最大数组大小时，
> 扩容容量=最大整数大小Integer.MAX_VALUE。扩容时是开辟了一个新的数组，把旧的元素复制过去。

### 3.删


### 4.改


### 5.查


