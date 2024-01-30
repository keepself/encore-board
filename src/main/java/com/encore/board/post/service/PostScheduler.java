package com.encore.board.post.service;

import com.encore.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostScheduler {
    private final PostRepository postRepository;
    @Autowired
    public PostScheduler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
// 초 분 시간 일 월 요일 형태로 스케쥴링 설정
//    * : 매초(분/시 등)을 의미
//    특정숫자 : 특정숫자의 초(분/시 등)을 의미
//    0/특정숫자 : 특정숫자마다
//    ex)0 0 * * * * : 매일 0시 0분 0초에 스케쥴링 시작
//    ex)0 0/1 * * * * : 매일 1분마다 0초에 스케쥴링 시작
//    ex)매일1분마다 0초에 시작한다 -> 0 0/1 * * * * *  매초마다 0/1 * * * * *
//    ex) 0 0 11 * * * * : 매일 11시에 스케쥴링
//    @Scheduled(cron = "0 0/1 * * * * ")
//    public void postSchedule(){
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println("===스케쥴러 시작===");
//        Page<Post> posts = postRepository.findByAppointment("Y", Pageable.unpaged());
//        for(Post p : posts.getContent()){
//            if(now.isBefore(p.getAppointmentTime())) {
//            p.updateAppointment(null);
//            postRepository.save(p);
//            }
//
//        }
//
//    }
}
