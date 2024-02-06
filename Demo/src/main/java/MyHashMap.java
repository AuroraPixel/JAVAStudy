import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class MyHashMap<K, V> extends AbstractMap<K, V>
        implements Map<K, V>, Cloneable, Serializable {

    private static final long serialVersionUID = 362498820763181265L;

    /**
     * 初始容量，必须是2的幂次方 16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /**
     * 最大容量，必须是2的幂次方 2^30
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 默认负载因子 0.75
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 当桶(bucket)上的结点数大于这个值时会转成红黑树
     * 这个值必须为大于2的数
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * 当桶(bucket)上的结点数小于这个值时树转链表
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * 桶中结构转化为红黑树对应的table的最小大小
     * 当哈希表中的容量大于这个值时才能进行树形化
     * 否则桶中结构转化为红黑树时会优先扩容，而不是树形化
     * 这个值至少是4 * TREEIFY_THRESHOLD，否则桶中的结构永远不会转化为树
     */
    static final int MIN_TREEIFY_CAPACITY = 64;


    /**
     * 基本的哈希bin节点，用于大多数条目
     */
    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        MyHashMap.Node<K, V> next;

        Node(int hash, K key, V value, MyHashMap.Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }

    /**
     * 计算key的hash值
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 返回大于等于cap的最小的2的幂次方
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1; // 0000 0000 0000 0000 0000 0000 0000 1111
        n |= n >>> 2; // 0000 0000 0000 0000 0000 0000 0011 1111
        n |= n >>> 4; // 0000 0000 0000 0000 0000 0000 1111 1111
        n |= n >>> 8; // 0000 0000 0000 0000 0000 1111 1111 1111
        n |= n >>> 16;// 0000 0000 0000 0000 1111 1111 1111 1111
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    
    /**
     * 用于存储数据的数组
     */
    transient Node<K, V>[] table;

    /**
     * 元素个数
     */
    transient int size;
    
    /**
     * 扩容的临界值
     * 当size超过这个值时就需要扩容了
     * threshold = 容量 * 负载因子
     */
    int threshold;
    
    /**
     * 负载因子
     */
    final float loadFactor;

    /**
     * 构造一个具有指定初始容量和负载因子的空HashMap
     * @param initialCapacity 初始容量
     * @param loadFactor 负载因子
     */ 
    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        // 最大容量
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        // 负载因子
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        
        // 设置负载因子
        this.loadFactor = loadFactor;
        // 设置扩容的临界值
        this.threshold = tableSizeFor(initialCapacity);
    }

    /**
     * 构造一个具有指定初始容量和默认负载因子（0.75）的空HashMap
     * @param initialCapacity 初始容量
     */
    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造一个具有默认初始容量（16）和默认负载因子（0.75）的空HashMap
     */
    public MyHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

    /**
     * 根据key获取对应的value
     * @param key 键
     * @return value
     */
    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    /**
     * 添加元素
     * @param hash key的hash值
     * @param key 键
     * @param value 值
     * @param onlyIfAbsent 如果为true，则不改变现有值
     * @param evict 如果为false，则表处于创建模式
     * @return value
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;

        // 判断table是否为空，以及key的hash值对应的桶是否为空
        if ((tab = table) == null || (n = tab.length) == 0)
            // 扩容
            n = (tab = resize()).length;
        // 计算出key对应的桶的位置
        if ((p = tab[i = (n - 1) & hash]) == null)
            // 如果桶为空，直接创建一个新的Node放到桶里面
            tab[i] = newNode(hash, key, value, null);
        else {
            // 桶不为空，判断第一个元素的key是否和传入的key相等
            Node<K, V> e;
            K k;
            // 判断key的hash值是否相等，判断key是否相等
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                // 相等，直接将第一个元素赋值给e
                e = p;
            // 判断第一个元素是否为红黑树
            else if (p instanceof TreeNode)
                // 如果是红黑树，调用红黑树的putTreeVal方法
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            else {
                // 遍历链表
                for (int binCount = 0; ; ++binCount) {
                    // 判断是否到了链表的最后一个元素
                    if ((e = p.next) == null) {
                        // 到了链表的最后一个元素，直接创建一个新的Node放到链表的最后
                        p.next = newNode(hash, key, value, null);
                        // 判断链表的长度是否大于等于8
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            // 如果大于等于8，调用treeifyBin方法将链表转换成红黑树
                            treeifyBin(tab, hash);
                        // 跳出循环
                        break;
                    }
                    // 判断key的hash值是否相等，判断key是否相等
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        // 相等，跳出循环
                        break;
                    // 将e赋值给p
                    p = e;
                }
            }
            // 判断e是否为空
            if (e != null) { // existing mapping for key
                // 获取e的value
                V oldValue = e.value;
                // 判断onlyIfAbsent是否为false，或者oldValue是否为null
                if (!onlyIfAbsent || oldValue == null)
                    // 将value赋值给e
                    e.value = value;
                // 访问后回调
                //afterNodeAccess(e);
                // 返回oldValue
                return oldValue;
            }
        }
        if (++size > threshold)
            resize();
        //afterNodeInsertion(evict);
        return null;
    }

    /**
     * 根据key获取对应的Node
     * @param hash key的hash值
     * @param key 键
     * @return Node
     */
    final Node<K,V> getNode(int hash,Object key){
        Node<K,V>[] tab;
        Node<K,V> first,e;
        int n;
        K k;
        // 判断table是否为空，以及key的hash值对应的桶是否为空
        if((tab = table)!=null && (n = tab.length)>0 && (first = tab[(n-1)&hash])!=null){
            // 判断第一个元素的key是否和传入的key相等
            if(first.hash == hash && ((k = first.key) == key || (key!=null && key.equals(k)))){
                return first;
            }
            // 判断第一个元素是否有下一个元素
            if((e = first.next)!=null){
                // 如果是红黑树
                if(first instanceof TreeNode){
                    return ((TreeNode<K,V>)first).getTreeNode(hash,key);
                }
                // 遍历链表
                do{
                    // 判断key是否相等
                    if(e.hash == hash && ((k = e.key) == key || (key!=null && key.equals(k)))){
                        return e;
                    }
                }while((e = e.next)!=null);
            }
        }
        return null;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    /**
     * 构造一个新的HashMap与指定的相同映射
     * @return hashMap的元素个数
     */
    public int size() {
        return size;
    }

    /**
     * 判断hashMap是否为空
     * @return true为空，false不为空
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    static class Entry<K,V> extends MyHashMap.Node<K,V> {
        Entry<K,V> before, after;
        Entry(int hash, K key, V value, MyHashMap.Node<K,V> next) {
            super(hash, key, value, next);
        }
    }

    /**
     * 扩容
     * @return 返回扩容后的table
     */
    final Node<K, V>[] resize() {
        // 获取原来的table
        Node<K, V>[] oldTab = table;
        // 获取原来的容量
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        // 获取原来的扩容临界值
        int oldThr = threshold;
        // 新的容量和扩容临界值
        int newCap, newThr = 0;
        // 判断原来的容量是否大于0
        if (oldCap > 0) {
            // 判断原来的容量是否大于等于最大容量
            if (oldCap >= MAXIMUM_CAPACITY) {
                // 将扩容临界值设置为int的最大值
                threshold = Integer.MAX_VALUE;
                // 返回原来的table
                return oldTab;
            }
            // 判断原来的容量是否小于最大容量，并且原来的容量是否大于等于默认容量
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
                // 将扩容临界值设置为原来的两倍
                newThr = oldThr << 1; // double threshold
        }
        // 判断原来的扩容临界值是否大于0
        else if (oldThr > 0) // initial capacity was placed in threshold
            // 将容量设置为扩容临界值
            newCap = oldThr;
        // 原来的容量和扩容临界值都为0
        else {               // zero initial threshold signifies using defaults
            // 将容量设置为默认容量
            newCap = DEFAULT_INITIAL_CAPACITY;
            // 将扩容临界值设置为默认容量*默认负载因子
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        // 判断新的扩容临界值是否为0
        if (newThr == 0) {
            // 计算新的扩容临界值
            float ft = (float) newCap * loadFactor;
            // 判断新的容量是否小于最大容量，并且新的扩容临界值是否小于最大容量
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }
        // 将新的扩容临界值赋值给扩容临界值
        threshold = newThr;
        // 创建一个新的table
        @SuppressWarnings({"rawtypes", "unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        // 将新的table赋值给table
        table = newTab;
        // 判断原来的table是否为空
        if (oldTab != null) {
            // 遍历原来的table
            for (int j = 0; j < oldCap; ++j) {
                // 获取原来table的每个桶的第一个元素
                Node<K, V> e;
                if ((e = oldTab[j]) != null) {
                    // 将原来的table的每个桶的第一个元素置为null
                    oldTab[j] = null;
                    // 判断原来的桶是否只有一个元素
                    if (e.next == null)
                        // 如果只有一个元素，将原来的桶放到新的table中
                        newTab[e.hash & (newCap - 1)] = e;
                    // 判断原来的桶是否为红黑树
                    else if (e instanceof TreeNode)
                        // 如果是红黑树，调用红黑树的split方法
                        ((TreeNode<K, V>) e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        // 链表
                        // 将原来的桶分成两个桶，一个是原来的桶，一个是原来的桶+原来的容量
                        // loHead和loTail是原来的桶的头和尾
                        // hiHead和hiTail是原来的桶+原来的容量的头和尾
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        // 遍历原来的桶
                        do {
                            // 获取原来桶的下一个元素
                            next = e.next;
                            // 判断原来的桶的hash值与原来的容量的与的结果是否为0
                            if ((e.hash & oldCap) == 0) {
                                // 如果为0，将原来的桶放到loTail后面
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                // 将loTail指向e
                                loTail = e;
                            } else {
                                // 如果不为0，将原来的桶放到hiTail后面
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                // 将hiTail指向e
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        // 判断loTail是否为空
                        if (loTail != null) {
                            // 将loTail的下一个元素置为null
                            loTail.next = null;
                            // 将原来的桶放到新的table中
                            newTab[j] = loHead;
                        }
                        // 判断hiTail是否为空
                        if (hiTail != null) {
                            // 将hiTail的下一个元素置为null
                            hiTail.next = null;
                            // 将原来的桶+原来的容量放到新的table中
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        // 返回新的table
        return newTab;
    }

    /**
     * 红黑树节点
     * @param <K> key
     * @param <V> value
     */
    static final class TreeNode<K,V> extends MyHashMap.Entry<K,V> {
        TreeNode<K,V> parent;  // red-black tree links
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        boolean red;
        TreeNode(int hash, K key, V val, MyHashMap.Node<K,V> next) {
            super(hash, key, val, next);
        }

        /**
         * Returns root of tree containing this node.
         */
        final TreeNode<K,V> root() {
            for (TreeNode<K,V> r = this, p;;) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }

        /**
         * Ensures that the given root is the first node of its bin.
         */
        static <K,V> void moveRootToFront(MyHashMap.Node<K,V>[] tab, TreeNode<K,V> root) {
            int n;
            if (root != null && tab != null && (n = tab.length) > 0) {
                int index = (n - 1) & root.hash;
                TreeNode<K,V> first = (TreeNode<K,V>)tab[index];
                if (root != first) {
                    MyHashMap.Node<K,V> rn;
                    tab[index] = root;
                    TreeNode<K,V> rp = root.prev;
                    if ((rn = root.next) != null)
                        ((TreeNode<K,V>)rn).prev = rp;
                    if (rp != null)
                        rp.next = rn;
                    if (first != null)
                        first.prev = root;
                    root.next = first;
                    root.prev = null;
                }
                assert checkInvariants(root);
            }
        }

        /**
         * Finds the node starting at root p with the given hash and key.
         * The kc argument caches comparableClassFor(key) upon first use
         * comparing keys.
         */
        final TreeNode<K,V> find(int h, Object k, Class<?> kc) {
            TreeNode<K,V> p = this;
            do {
                int ph, dir; K pk;
                TreeNode<K,V> pl = p.left, pr = p.right, q;
                if ((ph = p.hash) > h)
                    p = pl;
                else if (ph < h)
                    p = pr;
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if (pl == null)
                    p = pr;
                else if (pr == null)
                    p = pl;
                else if ((kc != null ||
                        (kc = comparableClassFor(k)) != null) &&
                        (dir = compareComparables(kc, k, pk)) != 0)
                    p = (dir < 0) ? pl : pr;
                else if ((q = pr.find(h, k, kc)) != null)
                    return q;
                else
                    p = pl;
            } while (p != null);
            return null;
        }

        /**
         * Calls find for root node.
         */
        final TreeNode<K,V> getTreeNode(int h, Object k) {
            return ((parent != null) ? root() : this).find(h, k, null);
        }

        /**
         * Tie-breaking utility for ordering insertions when equal
         * hashCodes and non-comparable. We don't require a total
         * order, just a consistent insertion rule to maintain
         * equivalence across rebalancings. Tie-breaking further than
         * necessary simplifies testing a bit.
         */
        static int tieBreakOrder(Object a, Object b) {
            int d;
            if (a == null || b == null ||
                    (d = a.getClass().getName().
                            compareTo(b.getClass().getName())) == 0)
                d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
                        -1 : 1);
            return d;
        }

        /**
         * Forms tree of the nodes linked from this node.
         */
        final void treeify(MyHashMap.Node<K,V>[] tab) {
            TreeNode<K,V> root = null;
            for (TreeNode<K,V> x = this, next; x != null; x = next) {
                next = (TreeNode<K,V>)x.next;
                x.left = x.right = null;
                if (root == null) {
                    x.parent = null;
                    x.red = false;
                    root = x;
                }
                else {
                    K k = x.key;
                    int h = x.hash;
                    Class<?> kc = null;
                    for (TreeNode<K,V> p = root;;) {
                        int dir, ph;
                        K pk = p.key;
                        if ((ph = p.hash) > h)
                            dir = -1;
                        else if (ph < h)
                            dir = 1;
                        else if ((kc == null &&
                                (kc = comparableClassFor(k)) == null) ||
                                (dir = compareComparables(kc, k, pk)) == 0)
                            dir = tieBreakOrder(k, pk);

                        TreeNode<K,V> xp = p;
                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
                            x.parent = xp;
                            if (dir <= 0)
                                xp.left = x;
                            else
                                xp.right = x;
                            root = balanceInsertion(root, x);
                            break;
                        }
                    }
                }
            }
            moveRootToFront(tab, root);
        }

        /**
         * Returns a list of non-TreeNodes replacing those linked from
         * this node.
         */
        final MyHashMap.Node<K,V> untreeify(MyHashMap<K,V> map) {
            MyHashMap.Node<K,V> hd = null, tl = null;
            for (MyHashMap.Node<K,V> q = this; q != null; q = q.next) {
                MyHashMap.Node<K,V> p = map.replacementNode(q, null);
                if (tl == null)
                    hd = p;
                else
                    tl.next = p;
                tl = p;
            }
            return hd;
        }

        /**
         * Tree version of putVal.
         */
        final TreeNode<K,V> putTreeVal(MyHashMap<K,V> map, MyHashMap.Node<K,V>[] tab,
                                               int h, K k, V v) {
            Class<?> kc = null;
            boolean searched = false;
            TreeNode<K,V> root = (parent != null) ? root() : this;
            for (TreeNode<K,V> p = root;;) {
                int dir, ph; K pk;
                if ((ph = p.hash) > h)
                    dir = -1;
                else if (ph < h)
                    dir = 1;
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if ((kc == null &&
                        (kc = comparableClassFor(k)) == null) ||
                        (dir = compareComparables(kc, k, pk)) == 0) {
                    if (!searched) {
                        TreeNode<K,V> q, ch;
                        searched = true;
                        if (((ch = p.left) != null &&
                                (q = ch.find(h, k, kc)) != null) ||
                                ((ch = p.right) != null &&
                                        (q = ch.find(h, k, kc)) != null))
                            return q;
                    }
                    dir = tieBreakOrder(k, pk);
                }

                TreeNode<K,V> xp = p;
                if ((p = (dir <= 0) ? p.left : p.right) == null) {
                    MyHashMap.Node<K,V> xpn = xp.next;
                    TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn);
                    if (dir <= 0)
                        xp.left = x;
                    else
                        xp.right = x;
                    xp.next = x;
                    x.parent = x.prev = xp;
                    if (xpn != null)
                        ((TreeNode<K,V>)xpn).prev = x;
                    moveRootToFront(tab, balanceInsertion(root, x));
                    return null;
                }
            }
        }

        /**
         * Removes the given node, that must be present before this call.
         * This is messier than typical red-black deletion code because we
         * cannot swap the contents of an interior node with a leaf
         * successor that is pinned by "next" pointers that are accessible
         * independently during traversal. So instead we swap the tree
         * linkages. If the current tree appears to have too few nodes,
         * the bin is converted back to a plain bin. (The test triggers
         * somewhere between 2 and 6 nodes, depending on tree structure).
         */
        final void removeTreeNode(MyHashMap<K,V> map, MyHashMap.Node<K,V>[] tab,
                                  boolean movable) {
            int n;
            if (tab == null || (n = tab.length) == 0)
                return;
            int index = (n - 1) & hash;
            TreeNode<K,V> first = (TreeNode<K,V>)tab[index], root = first, rl;
            TreeNode<K,V> succ = (TreeNode<K,V>)next, pred = prev;
            if (pred == null)
                tab[index] = first = succ;
            else
                pred.next = succ;
            if (succ != null)
                succ.prev = pred;
            if (first == null)
                return;
            if (root.parent != null)
                root = root.root();
            if (root == null
                    || (movable
                    && (root.right == null
                    || (rl = root.left) == null
                    || rl.left == null))) {
                tab[index] = first.untreeify(map);  // too small
                return;
            }
            TreeNode<K,V> p = this, pl = left, pr = right, replacement;
            if (pl != null && pr != null) {
                TreeNode<K,V> s = pr, sl;
                while ((sl = s.left) != null) // find successor
                    s = sl;
                boolean c = s.red; s.red = p.red; p.red = c; // swap colors
                TreeNode<K,V> sr = s.right;
                TreeNode<K,V> pp = p.parent;
                if (s == pr) { // p was s's direct parent
                    p.parent = s;
                    s.right = p;
                }
                else {
                    TreeNode<K,V> sp = s.parent;
                    if ((p.parent = sp) != null) {
                        if (s == sp.left)
                            sp.left = p;
                        else
                            sp.right = p;
                    }
                    if ((s.right = pr) != null)
                        pr.parent = s;
                }
                p.left = null;
                if ((p.right = sr) != null)
                    sr.parent = p;
                if ((s.left = pl) != null)
                    pl.parent = s;
                if ((s.parent = pp) == null)
                    root = s;
                else if (p == pp.left)
                    pp.left = s;
                else
                    pp.right = s;
                if (sr != null)
                    replacement = sr;
                else
                    replacement = p;
            }
            else if (pl != null)
                replacement = pl;
            else if (pr != null)
                replacement = pr;
            else
                replacement = p;
            if (replacement != p) {
                TreeNode<K,V> pp = replacement.parent = p.parent;
                if (pp == null)
                    root = replacement;
                else if (p == pp.left)
                    pp.left = replacement;
                else
                    pp.right = replacement;
                p.left = p.right = p.parent = null;
            }

            TreeNode<K,V> r = p.red ? root : balanceDeletion(root, replacement);

            if (replacement == p) {  // detach
                TreeNode<K,V> pp = p.parent;
                p.parent = null;
                if (pp != null) {
                    if (p == pp.left)
                        pp.left = null;
                    else if (p == pp.right)
                        pp.right = null;
                }
            }
            if (movable)
                moveRootToFront(tab, r);
        }

        /**
         * Splits nodes in a tree bin into lower and upper tree bins,
         * or untreeifies if now too small. Called only from resize;
         * see above discussion about split bits and indices.
         *
         * @param map the map
         * @param tab the table for recording bin heads
         * @param index the index of the table being split
         * @param bit the bit of hash to split on
         */
        final void split(MyHashMap<K,V> map, MyHashMap.Node<K,V>[] tab, int index, int bit) {
            TreeNode<K,V> b = this;
            // Relink into lo and hi lists, preserving order
            TreeNode<K,V> loHead = null, loTail = null;
            TreeNode<K,V> hiHead = null, hiTail = null;
            int lc = 0, hc = 0;
            for (TreeNode<K,V> e = b, next; e != null; e = next) {
                next = (TreeNode<K,V>)e.next;
                e.next = null;
                if ((e.hash & bit) == 0) {
                    if ((e.prev = loTail) == null)
                        loHead = e;
                    else
                        loTail.next = e;
                    loTail = e;
                    ++lc;
                }
                else {
                    if ((e.prev = hiTail) == null)
                        hiHead = e;
                    else
                        hiTail.next = e;
                    hiTail = e;
                    ++hc;
                }
            }

            if (loHead != null) {
                if (lc <= UNTREEIFY_THRESHOLD)
                    tab[index] = loHead.untreeify(map);
                else {
                    tab[index] = loHead;
                    if (hiHead != null) // (else is already treeified)
                        loHead.treeify(tab);
                }
            }
            if (hiHead != null) {
                if (hc <= UNTREEIFY_THRESHOLD)
                    tab[index + bit] = hiHead.untreeify(map);
                else {
                    tab[index + bit] = hiHead;
                    if (loHead != null)
                        hiHead.treeify(tab);
                }
            }
        }

        /* ------------------------------------------------------------ */
        // Red-black tree methods, all adapted from CLR

        static <K,V> TreeNode<K,V> rotateLeft(TreeNode<K,V> root,
                                                      TreeNode<K,V> p) {
            TreeNode<K,V> r, pp, rl;
            if (p != null && (r = p.right) != null) {
                if ((rl = p.right = r.left) != null)
                    rl.parent = p;
                if ((pp = r.parent = p.parent) == null)
                    (root = r).red = false;
                else if (pp.left == p)
                    pp.left = r;
                else
                    pp.right = r;
                r.left = p;
                p.parent = r;
            }
            return root;
        }

        static <K,V> TreeNode<K,V> rotateRight(TreeNode<K,V> root,
                                                       TreeNode<K,V> p) {
            TreeNode<K,V> l, pp, lr;
            if (p != null && (l = p.left) != null) {
                if ((lr = p.left = l.right) != null)
                    lr.parent = p;
                if ((pp = l.parent = p.parent) == null)
                    (root = l).red = false;
                else if (pp.right == p)
                    pp.right = l;
                else
                    pp.left = l;
                l.right = p;
                p.parent = l;
            }
            return root;
        }

        static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,
                                                            TreeNode<K,V> x) {
            x.red = true;
            for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                }
                else if (!xp.red || (xpp = xp.parent) == null)
                    return root;
                if (xp == (xppl = xpp.left)) {
                    if ((xppr = xpp.right) != null && xppr.red) {
                        xppr.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.right) {
                            root = rotateLeft(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateRight(root, xpp);
                            }
                        }
                    }
                }
                else {
                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.left) {
                            root = rotateRight(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }

        static <K,V> TreeNode<K,V> balanceDeletion(TreeNode<K,V> root,
                                                           TreeNode<K,V> x) {
            for (TreeNode<K,V> xp, xpl, xpr;;) {
                if (x == null || x == root)
                    return root;
                else if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                }
                else if (x.red) {
                    x.red = false;
                    return root;
                }
                else if ((xpl = xp.left) == x) {
                    if ((xpr = xp.right) != null && xpr.red) {
                        xpr.red = false;
                        xp.red = true;
                        root = rotateLeft(root, xp);
                        xpr = (xp = x.parent) == null ? null : xp.right;
                    }
                    if (xpr == null)
                        x = xp;
                    else {
                        TreeNode<K,V> sl = xpr.left, sr = xpr.right;
                        if ((sr == null || !sr.red) &&
                                (sl == null || !sl.red)) {
                            xpr.red = true;
                            x = xp;
                        }
                        else {
                            if (sr == null || !sr.red) {
                                if (sl != null)
                                    sl.red = false;
                                xpr.red = true;
                                root = rotateRight(root, xpr);
                                xpr = (xp = x.parent) == null ?
                                        null : xp.right;
                            }
                            if (xpr != null) {
                                xpr.red = (xp == null) ? false : xp.red;
                                if ((sr = xpr.right) != null)
                                    sr.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateLeft(root, xp);
                            }
                            x = root;
                        }
                    }
                }
                else { // symmetric
                    if (xpl != null && xpl.red) {
                        xpl.red = false;
                        xp.red = true;
                        root = rotateRight(root, xp);
                        xpl = (xp = x.parent) == null ? null : xp.left;
                    }
                    if (xpl == null)
                        x = xp;
                    else {
                        TreeNode<K,V> sl = xpl.left, sr = xpl.right;
                        if ((sl == null || !sl.red) &&
                                (sr == null || !sr.red)) {
                            xpl.red = true;
                            x = xp;
                        }
                        else {
                            if (sl == null || !sl.red) {
                                if (sr != null)
                                    sr.red = false;
                                xpl.red = true;
                                root = rotateLeft(root, xpl);
                                xpl = (xp = x.parent) == null ?
                                        null : xp.left;
                            }
                            if (xpl != null) {
                                xpl.red = (xp == null) ? false : xp.red;
                                if ((sl = xpl.left) != null)
                                    sl.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateRight(root, xp);
                            }
                            x = root;
                        }
                    }
                }
            }
        }

        /**
         * Recursive invariant check
         */
        static <K,V> boolean checkInvariants(TreeNode<K,V> t) {
            TreeNode<K,V> tp = t.parent, tl = t.left, tr = t.right,
                    tb = t.prev, tn = (TreeNode<K,V>)t.next;
            if (tb != null && tb.next != t)
                return false;
            if (tn != null && tn.prev != t)
                return false;
            if (tp != null && t != tp.left && t != tp.right)
                return false;
            if (tl != null && (tl.parent != t || tl.hash > t.hash))
                return false;
            if (tr != null && (tr.parent != t || tr.hash < t.hash))
                return false;
            if (t.red && tl != null && tl.red && tr != null && tr.red)
                return false;
            if (tl != null && !checkInvariants(tl))
                return false;
            if (tr != null && !checkInvariants(tr))
                return false;
            return true;
        }
    }

    /**
     * 将链表转换成红黑树
     * @param tab table
     * @param hash key的hash值
     */
    final void treeifyBin(Node<K, V>[] tab, int hash) {
        // 获取table的长度
        int n, index;
        Node<K, V> e;
        // 判断table是否为空，以及table的长度是否小于64
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            // 将链表转换成红黑树
            resize();
            // 判断key的hash值对应的桶是否为空
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            // 创建一个红黑树的头节点
            TreeNode<K, V> hd = null, tl = null;
            do {
                // 创建一个红黑树的节点
                TreeNode<K, V> p = replacementTreeNode(e, null);
                // 判断tl是否为空
                if (tl == null)
                    // 如果tl为空，将p赋值给hd
                    hd = p;
                else {
                    // 如果tl不为空，将p赋值给tl的下一个节点
                    p.prev = tl;
                    tl.next = p;
                }
                // 将tl指向p
                tl = p;
            } while ((e = e.next) != null);
            // 将hd放到table的对应的桶中
            if ((tab[index] = hd) != null)
                // 将红黑树转换成红黑树
                hd.treeify(tab);
        }
    }

    /**
     * 判断是class否为comparable
     * @param x class
     * @return class
     */
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            ((p = (ParameterizedType)t).getRawType() ==
                                    Comparable.class) &&
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }


    /**
     * 比较x和y的大小
     * @param kc
     * @param k
     * @param x
     * @return
     */
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 :
                ((Comparable)k).compareTo(x));
    }

    Node<K,V> replacementNode(Node<K,V> p, Node<K,V> next) {
        return new Node<>(p.hash, p.key, p.value, next);
    }

    TreeNode<K,V> newTreeNode(int hash, K key, V value, Node<K,V> next) {
        return new TreeNode<>(hash, key, value, next);
    }

    Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
        return new Node<>(hash, key, value, next);
    }

    TreeNode<K,V> replacementTreeNode(Node<K,V> p, Node<K,V> next) {
        return new TreeNode<>(p.hash, p.key, p.value, next);
    }
}