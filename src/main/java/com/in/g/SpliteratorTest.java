package com.in.g;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.*;

/**
 * 测试自定义的Spliterator，并实现并行流时能正常拆分
 *
 */
public class SpliteratorTest {
    //用函数式实现
    public static void main(String[] args) {
        String s = " Nel   mezzo del cammin  di nostra  vita " + "mi  ritrovai in una  selva oscura" + " ché la  dritta via era   smarrita ";
//        System.out.println(s.split(" "));
//        String[] p1 = s.split(" ");
//        List<String> list = Arrays.stream(s.split(" ")).filter(p -> !" ".equals(p)&&!"".equals(p)).collect(Collectors.toList());
        int count = Arrays.stream(s.split(" ")).filter(p -> !" ".equals(p) && !"".equals(p))
                .map(p -> 1).reduce(Integer::sum).orElse(0);
//        System.out.println(count);
//        Stream<Character> stream = IntStream.range(0, s.length()).mapToObj(s::charAt);
//        System.out.println(new SpliteratorTest().strCount(stream));
        //此时，并行流得到的最终结果不对，因为并行流时随机拆分，1个词会拆为2个词
//        System.out.println(new SpliteratorTest().strCount(stream.parallel()));
        Spliterator<Character> spliterator = new WordCountSpliterator(s);
        //利用StreamSupport工厂方法创建并行流
        Stream<Character> stream = StreamSupport.stream(spliterator, true);
        System.out.println(new SpliteratorTest().strCount(stream));
    }

    /**
     * 函数风格重写单词计数器
     *
     * @return
     */
    public int strCount(Stream<Character> stream) {
        WordCount wordCount = stream.reduce(new WordCount(0, true), WordCount::accumulate, WordCount::combine);
        return wordCount.getWordCount();
    }

}
