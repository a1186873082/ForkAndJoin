package com.in.g;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 实现自动机制进行拆分流(工作窃取技术)
 */
public class WordCountSpliterator implements Spliterator<Character> {

    private String str;
    private int currentChar = 0;

    public WordCountSpliterator(String str) {
        this.str = str;
    }

    /**
     * 把String中当前位置的Character传给了Consumer，并让位置+1
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        //处理当前字符
        action.accept(str.charAt(currentChar++));
        //如果还有字符要处理，返回true
        return currentChar < str.length();
    }

    /**
     * @return
     */
    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = str.length() - currentChar;
        //返回null表示解析的string足够小，无需再次分解
        if (currentSize < 10) {
            return null;
        }
        //将试探拆分位置设定为要解析的string的中间
        for (int splitPos = currentSize / 2 + currentChar; splitPos < str.length(); splitPos++) {
            //让拆分位置前进知道下一个空格
            if (Character.isWhitespace(str.charAt(splitPos))) {
                //创建一个新WordCounterSpliterator来解析String从开始到拆分位置的部分
                Spliterator<Character> spliterator =
                        new WordCountSpliterator(str.substring(currentChar, splitPos));
                //将WordCountSpliterator起始位置设置为拆分位置
                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return str.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
