## String
### 为什么String是不可变类？

```
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final char value[];
    //...
```
在String类中，使用一个char类型的数组存放String所代表的字面值。由于value数组被**final**修饰，所以value数组的**引用**不会改变；由于value数组被**private**修饰，而且String类没有暴露能够修改value数组**元素**的方法，所以value数组的**元素**不会被修改。  
综上所述，String中保存字面意思的字段及其内容无法在外界被修改，所以它是不可变类。

### 为什么两个变量被同一个字符串常量赋值时用==比较会相等？

```
String a = "lyz";
String b = "lyz";
System.out.println(a == b); //true
```

在JVM中，为了提高性能和减少内存的开销，高频使用的字符串将会被存入**字符串常量池**。**++每当我们创建字符串常量时，JVM会首先检查字符串常量池，如果该字符串已经存在常量池中，那么就直接返回常量池中的实例引用。如果字符串不存在常量池中，就会实例化该字符串并且将其放到常量池中。++**  

### 那如果通过new String("lyz")赋值给一个变量，==比较时又不相等了？

```
String c = new String("lyz");
String d = new String(a);
System.out.println(a == c); //false
System.out.println(a == d); //false
```
String(String)源码：
```
public String(String original) {
        this.value = original.value;
        this.hash = original.hash;
    }
```
在String源码中，调用new String("lyz")创建的字符串对象只不过是将"lyz"的字面意思和哈希值传递出去，而并没有将"lyz"在**字符串常量池**中的**引用**传递出去。这种方式创建的String对象得到了一个新的**引用**，所以在进行==比较操作时出现false的结果也不意外了。

### 通过String类的常用API创建的String对象，是不是通过==比较时也会不相等？

```
String e = a.concat("hi");
String f = a.concat("hi");
System.out.println(e == f); //false
```
concat源码：
```
public String concat(String str) {
        int otherLen = str.length();
        if (otherLen == 0) {
            return this;
        }
        int len = value.length;
        char buf[] = Arrays.copyOf(value, len + otherLen);
        str.getChars(buf, len);
        return new String(buf, true);
    }
```

举concat的例子，可以清楚地看到返回的时候写着**new**的字样，说明concat返回的时候是返回了新的对象。  

其他API也是一样返回**新**的对象。

### String与String，String与其他类型的变量使用的“+”操作生成新的String，底层是怎么回事呢？

在String源码中可以看到这样一段注释：
```
* The Java language provides special support for the string
 * concatenation operator (&nbsp;+&nbsp;), and for conversion of
 * other objects to strings. String concatenation is implemented
 * through the {@code StringBuilder}(or {@code StringBuffer})
 * class and its {@code append} method.
 * String conversions are implemented through the method
 * {@code toString}, defined by {@code Object} and
 * inherited by all classes in Java. For additional information on
 * string concatenation and conversion, see Gosling, Joy, and Steele,
 * <i>The Java Language Specification</i>.
```
意思大体是：+号连接的时候借助了StringBuilder或者StringBuffer和它们的append方法。而且受到连接的对象会依赖于Object.toString()方法。  
但是这里只是注释，两个“SB”底层的实现在这里是没有说的，于是我做了个测试：
```
String s0="abcedf";
String s1="abc"+"edf";
String s2="abc"+"edf";
System.out.println(s1==s2); //true
System.out.println(s1==s0); //true
String s3="abc";
String s4="edf";
String s5=s3+s4;    
System.out.println(s0==s5); //false
String s6="abc"+s4;
System.out.println(s0==s6); //false
```
s1 == s2,s1 == s0的结果说明，当两个字符串常量拼接的时候，生成的字符串引用与代表原字符串常量的字符串引用相等。  
s5 == s0,s6 == s0的结果说明当两个字符串**对象**或一边是字符串变量，一边是字符串常量相拼接的时候，生成的字符串引用与原字符串不相等。  
使用javap反编译该测试.class文件，查看两个结果的所代表的code不同之处。  

s1 == s2,s1 == s0:
```
0: ldc           #2                  // String abcedf
2: astore_1
3: ldc           #2                  // String abcedf
5: astore_2
6: ldc           #2                  // String abcedf
```
s5 == s0,s6 == s0:
```
41: ldc           #5                  // String abc
43: astore        4
45: ldc           #6                  // String edf
47: astore        5
49: new           #7                  // class java/lang/StringBuilder
52: dup
53: invokespecial #8                  // Method java/lang/StringBuilder."<init>":()V
56: aload         4
58: invokevirtual #9                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
61: aload         5
63: invokevirtual #9                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
66: invokevirtual #10                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
//...
85: invokevirtual #4                  // Method java/io/PrintStream.println:(Z)V
88: new           #7                  // class java/lang/StringBuilder
91: dup
92: invokespecial #8                  // Method java/lang/StringBuilder."<init>":()V
95: ldc           #5                  // String abc
97: invokevirtual #9                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
100: aload         5
102: invokevirtual #9                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
105: invokevirtual #10                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
108: astore        7
110: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
```
可以看到，当字符串**对象**与字符串常量相拼接的时候，**会用到StringBuilder和它的append方法进行拼接，最后面返回拼接结果的时候使用到了StringBuilder.toString方法**。
```
@Override
public String toString() {
    // Create a copy, don't share the array
    return new String(value, 0, count);
}
```
由于toString方法会**new**一个新的字符串对象，那么当==号比较落在新对象的时候自然会返回false。  
**除了字符串对象会这么做，其他基本类型和对象表现都是使用StringBuilder进行“+”操作符的操作。**  
而两个字符串常量相拼接的时候，jvm的做法是用**字符串常量池**的那一套做法。

参考资料：String源码和StringBuilder源码。