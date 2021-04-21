import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GuavaTest {
    public static void main(String[] args) throws InterruptedException, IOException {

        //集合的创建与交、并、差
        HashMap<Object, Object> mapA = Maps.newHashMap();
        HashMap<Object, Object> mapB = Maps.newHashMap();
        MapDifference<Object, Object> mapDifference = Maps.difference(mapA, mapB);
        mapDifference.areEqual();
        mapDifference.entriesDiffering();
        mapDifference.entriesInCommon();
        mapDifference.entriesOnlyOnLeft();
        mapDifference.entriesOnlyOnRight();

        HashSet<Object> set1 = Sets.newHashSet();
        HashSet<Object> set2 = Sets.newHashSet();
        Sets.SetView<Object> union = Sets.union(set1, set2);
        Sets.SetView<Object> difference = Sets.difference(set1, set2);
        Sets.SetView<Object> intersection = Sets.intersection(set1, set2);

        // 创建不可变对象
        ImmutableMap<String, String> map = ImmutableMap.of("k1", "v1", "k2", "v2");
        ImmutableSet<String> set = ImmutableSet.of("e1", "e2", "e3");
        ImmutableList<String> list1 = ImmutableList.of("e1", "e2", "e3");

        //集合的过滤，转化为 iterable
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Iterable<Integer> filter = Iterables.filter(list, Predicates.or(Predicates.equalTo(1), Predicates.equalTo(2)));
        Maps.filterEntries(map, Predicates.or(Predicates.isNull()));
        Maps.filterKeys(map, Predicates.equalTo(0));
        Maps.filterValues(map, Predicates.equalTo(0));

        // 自定义过滤器，以 map 为例，Function<F, T> 中 F 表示 apply 方法的 input 类型，T 表示返回类型，可使用 lambda 表达式
        //Maps.transformEntries();
        Maps.transformValues(map, input -> input + 1);
        List<Integer> transform = Lists.transform(list, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                if (input > 3) {
                    return input + 1;
                } else {
                    return input - 1;
                }
            }
        });
        System.out.println(transform);


        //将集合转化为特定字符串
        Joiner.on('-').join(list1);
        Joiner.on(',').withKeyValueSeparator("=").join(map);

        //String 转化为特定的集合，指定 Seperator 并提供去空串和空字符的方法
        String strA = "1-2-3-4-5-6";
        String strB = "1-2- 3 -4--5- 6  ";
        String strC = "a=1,b=2";
        List<String> list2 = Splitter.on('-').splitToList(strA);
        List<String> list3 = Splitter.on('-').omitEmptyStrings().trimResults().splitToList(strB);
        Map<String, String> map1 = Splitter.on(',').withKeyValueSeparator('=').split(strC);

        //guava 同时支持多字符切割字符串
        String strD = "aa,bb;dd..cc";
        List<String> list4 = Splitter.onPattern("[,|;|.]").omitEmptyStrings().splitToList(strD);

        //判断匹配结果
        String strE = "abc123abc345";
        CharMatcher.digit().removeFrom(strE);
        CharMatcher.digit().replaceFrom(strE, '*');
        CharMatcher.digit().retainFrom(strE);
        CharMatcher.digit().matches('1');

        // Strings 检查参数
        String str = "123";
        boolean nullOrEmpty = Strings.isNullOrEmpty(str);

        int count = 1;
        Preconditions.checkArgument(count > 0, "%s must be positive", count);
        Preconditions.checkNotNull(str);
        Preconditions.checkState(count > 0, "must be positive");
        Preconditions.checkElementIndex(1, 2);
        Preconditions.checkPositionIndexes(0, 10, 12);

        // 代替大量重写 toString()
        Apple apple = new Apple(12,12);
        System.out.println(MoreObjects.toStringHelper(apple).add("price", apple.getPrice()).toString());

        Apple appleA = new Apple(0, 0);
        Apple appleB = new Apple(12, 12);
        Ordering<Apple> ordering = Ordering.natural().nullsLast().onResultOf(new Function<Apple, String>() {
            @Override
            public String apply(Apple apple) {
                return apple.like + "";
            }
        });
        System.out.println(ordering.compare(appleA, appleB));

        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i=0; i<1000; i++){
            Thread.sleep(1);
        }
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(nanos);

        //文件操作
        List<String> fileLines = Files.readLines(new File("a.txt"), Charsets.UTF_8);
        Files.copy(new File("a.txt"), new File("b.txt"));
        Files.move(new File("a.txt"), new File("b.txt"));

    }
}
