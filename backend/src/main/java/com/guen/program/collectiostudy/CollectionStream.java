package com.guen.program.collectiostudy;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/***************************************************************************************************
    team 별
    member의 teamName,teamId , memberNm , age , age의 평균을 구하고
    age로 내림차순 정렬해라
****************************************************************************************************/
public class CollectionStream {

    @Getter
    public static class team{
        private int teamId;
        private String teamName;

        public team(int teamId, String teamName) {
            this.teamId = teamId;
            this.teamName = teamName;
        }
    }
    @Getter
    public static class member{
        private int memberId;
        private int teamId;
        private String memberNm;

        private int age;

        public member(int memberId, int teamId, String memberNm, int age) {
            this.memberId = memberId;
            this.teamId = teamId;
            this.memberNm = memberNm;
            this.age = age;
        }
    }

    @Getter
    public static class dto{
        private int teamId;
        private String teamName;

        private String memberNm;

        private int age;

        private double avgAge;

        public dto(int teamId, String teamName, String memberNm, int age, double avgAge) {
            this.teamId = teamId;
            this.teamName = teamName;
            this.memberNm = memberNm;
            this.age = age;
            this.avgAge = avgAge;
        }

        @Override
        public String toString() {
            return "dto{" +
                    "teamId=" + teamId +
                    ", teamName='" + teamName + '\'' +
                    ", memberNm='" + memberNm + '\'' +
                    ", age=" + age +
                    ", avgAge=" + avgAge +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<CollectionStream.team> teams = Arrays.asList(
                new CollectionStream.team(1, "grab"),
                new CollectionStream.team(2, "baidu"),
                new CollectionStream.team(3, "nvdia")
        );

        List<CollectionStream.member> members = Arrays.asList(
                new CollectionStream.member(1, 1, "m1", 46),
                new CollectionStream.member(2, 1, "'m2", 47),
                new CollectionStream.member(3, 1, "'m3", 48),
                new CollectionStream.member(4, 1, "'m4", 49),
                new CollectionStream.member(5, 1, "'m5", 50),
                new CollectionStream.member(6, 1, "'m6", 51),
                new CollectionStream.member(7, 1, "'m7", 52),
                new CollectionStream.member(8, 1, "'m8", 53),
                new CollectionStream.member(9, 1, "'m9", 54),
                new CollectionStream.member(10, 2, "'m10", 55),
                new CollectionStream.member(11, 2, "'m11", 56),
                new CollectionStream.member(12, 2, "'m12", 57)
        );



        // teamId를 기준으로 member를 그룹화
        Map<Integer, List<member>> groupedMembers = members.stream()
                .collect(Collectors.groupingBy(member::getTeamId));

        // 각 그룹의 teamName, teamId, memberNm, age 및 age의 평균을 계산
        List<CollectionStream.dto> result = new ArrayList<>();
        for (team team : teams) {
            List<member> teamMembers = groupedMembers.get(team.getTeamId());
            if (teamMembers != null) {
                double avgAge = teamMembers.stream()
                        .mapToInt(member::getAge)
                        .average()
                        .orElse(0.0);

                for (member member : teamMembers) {
                    CollectionStream.dto dto = new CollectionStream.dto(
                            team.getTeamId(),
                            team.getTeamName(),
                            member.getMemberNm(),
                            member.getAge(),
                            avgAge
                    );
                    result.add(dto);
                }
            }
        }

        // age를 기준으로 결과를 내림차순으로 정렬
        result.sort((dto1, dto2) -> Integer.compare((Integer)dto2.getAge() , (Integer) dto1.getAge()));

        // 결과 출력
        for (CollectionStream.dto dto : result) {
            System.out.println(dto.toString());
        }


    }

}
