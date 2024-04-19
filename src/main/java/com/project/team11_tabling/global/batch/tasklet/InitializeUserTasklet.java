package com.project.team11_tabling.global.batch.tasklet;

import com.project.team11_tabling.domain.user.entity.User;
import com.project.team11_tabling.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitializeUserTasklet implements Tasklet {

  private final UserRepository userRepository;
  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    List<User> userList = userRepository.findAll();

    for (User user : userList) {
      user.updateGrade("Bronze");
    }

    return RepeatStatus.FINISHED;
  }
}
