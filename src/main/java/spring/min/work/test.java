package spring.min.work;

import spring.min.work.domain.Estimate;

import java.util.*;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
        Estimate estimate4 = new Estimate();
        estimate4.setRoom("room");
        estimate4.setCategory("cat");
        estimate4.setSum("10");
        Estimate estimate1 = new Estimate();
        estimate1.setRoom("room1");
        estimate1.setCategory("cat1");
        estimate1.setSum("10");
        Estimate estimate2 = new Estimate();
        estimate2.setRoom("room");
        estimate2.setCategory("cat");
        estimate2.setSum("10");
        Estimate estimate3 = new Estimate();
        estimate3.setRoom("room1");
        estimate3.setCategory("cat1");
        estimate3.setSum("10");

        List<Estimate> list = new ArrayList<>();

        list.add(estimate4);
        list.add(estimate1);
        list.add(estimate2);
        list.add(estimate3);

        list.stream().forEach(System.out::println);
        System.out.println();
        Map<String, Estimate> result = list.stream().collect(Collectors
                .toMap(estimate -> estimate.getRoom() + " " +estimate.getCategory(),
                        estimate -> estimate, (existing, toAdd) ->
                                new Estimate(existing.getRoom(), existing.getCategory(),
                                        String.valueOf(Double.valueOf(existing.getSum()) + Double.valueOf(toAdd.getSum()))) ));
        List<Estimate> resList = result.values().stream().collect(Collectors.toList());
        resList.stream().forEach(System.out::println);
    }
}
