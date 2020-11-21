# ArrayMultithreading
A simple Way to use Multithreading with Arrays or similar Objects

Usage (see HelloWorld.java)

```java
String[] strings = new String[42];

new AM(strings.length) {
  @Override
  public void runForEach(int i) {
    strings[i] = "Hello World";
    strings[i] = strings[i] + " \n";
    strings[i] = strings[i] + "from Number: " + i; 
    strings[i] = strings[i] + " \n";
  }
};

for (int i = 0; i < strings.length; i++) {
  System.out.println(strings[i]);
}
```
