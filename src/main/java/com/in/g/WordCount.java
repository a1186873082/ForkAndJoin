package com.in.g;

/**
 * 单词计数
 */
public class WordCount {
    private int counter;
    private boolean lastSpace;

    public WordCount(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    public WordCount accumulate(Character c) {
        //和迭代算法一样，accumulate方法一个个遍历Character
        if (Character.isWhitespace(c)) {
            return lastSpace ? this : new WordCount(counter, true);
        } else {
            //上一个字符时空格，而当前遍历的字符不是空格时，将单词计数器加一
            return lastSpace ? new WordCount(counter + 1, false) : this;
        }
    }

    /**
     * 合并2个WordCount，将其计数器加起来
     * @param wordCount
     * @return
     */
    public WordCount combine(WordCount wordCount) {
        return new WordCount(counter + wordCount.counter, wordCount.lastSpace);
    }

    public int getWordCount(){
        return counter;
    }

}
