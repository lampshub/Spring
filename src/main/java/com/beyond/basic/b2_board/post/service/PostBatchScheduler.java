//package com.beyond.basic.b2_board.post.service;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PostBatchScheduler {
//    private final JobLauncher jobLauncher;
//    private final Job job;
//
//    public PostBatchScheduler(JobLauncher jobLauncher, Job job) {
//        this.jobLauncher = jobLauncher;
//        this.job = job;
//    }
//
//    @Scheduled(cron = "0 0/1 * * * *")    //1분에 1번씩 실행
//    public void batchScheduler() {
//        try {
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())    //currentTimeMillis : job을 구분지을수있는 key값(기준점)처럼 사용되나, 큰 의미는 없음
//                    .toJobParameters();
//            jobLauncher.run(job, jobParameters);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
