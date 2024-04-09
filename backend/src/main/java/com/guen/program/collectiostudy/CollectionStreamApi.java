package com.guen.program.collectiostudy;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionStreamApi {
    @Getter
    public static class Customer{
        private int cId;
        private String name;
        private String genderCd;
        private String gender;

        public Customer(int cId, String name, String genderCd, String gender) {
            this.cId = cId;
            this.name = name;
            this.genderCd = genderCd;
            this.gender = gender;
        }
    }

    @Getter
    public static class OtherCustomer{
        private int cId;
        private String name;
        private String genderCd;
        private String gender;

        public OtherCustomer(int cId, String name, String genderCd, String gender) {
            this.cId = cId;
            this.name = name;
            this.genderCd = genderCd;
            this.gender = gender;
        }
    }

    @Getter
    public static class dto{
        private int cId;
        private String name;
        private String genderCd;
        private String gender;

        public dto(int cId, String name, String genderCd, String gender) {
            this.cId = cId;
            this.name = name;
            this.genderCd = genderCd;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "dto{" +
                    "cId=" + cId +
                    ", name='" + name + '\'' +
                    ", genderCd='" + genderCd + '\'' +
                    ", gender='" + gender + '\'' +
                    '}';
        }
    }
    public static void main(String[] args) {
        List<Customer> customers = Arrays.asList(
                new Customer(1, "John", "M", "남성"),
                new Customer(2, "Jane", "F", "여성")
        );

        List<OtherCustomer> otherCustomers = Arrays.asList(
                new OtherCustomer(3, "Tom", "M", "남성"),
                new OtherCustomer(4, "Emily", "F", "여성")
        );

        // 여러 컬렉션 합치고 dto 객체에 매핑
        List<dto> dtos = Stream.concat(customers.stream(), otherCustomers.stream())
                .map(c -> {
                            if (c instanceof Customer) {
                                return new dto(((Customer) c).getCId(), ((Customer) c).getName(), ((Customer) c).getGenderCd(), ((Customer) c).getGender());
                            } else {
                                return new dto(((OtherCustomer) c).getCId(), ((OtherCustomer) c).getName(), ((OtherCustomer) c).getGenderCd(), ((OtherCustomer) c).getGender());
                            }
                        }
                ).collect(Collectors.toList());


        // 정렬 (id로 정렬 내림차순 , 성별로 올림차순 정렬)
        List<dto> sorted = dtos.stream()
                .sorted(Comparator.comparing(dto::getCId).reversed().thenComparing(dto::getGenderCd))
                .collect(Collectors.toList());


        // 그룹핑 (성별로 그룹핑)
        Map<String, List<dto>> grouped = sorted.stream()
                .collect(Collectors.groupingBy(dto -> dto.getGenderCd()));



        // 필터링 (남성만 필터링)
        List<dto> filtered = sorted.stream()
                .filter(dto -> dto.getGenderCd().equals("M"))
                .collect(Collectors.toList());


        // 통계 (필터링된 객체의 수)
        long count = filtered.stream().count();

        System.out.println("컬렉션 합치기 : " + dtos);
        System.out.println("id로 정렬 내림차순 , 성별로 올림차순 정렬 : " + sorted);
        System.out.println(" 그룹핑 (성별로 그룹핑) " + grouped);
        System.out.println(" 필터링 (남성만 필터링) " + filtered);
        System.out.println("통계 (필터링된 객체의 수) " + count);

    }

}
