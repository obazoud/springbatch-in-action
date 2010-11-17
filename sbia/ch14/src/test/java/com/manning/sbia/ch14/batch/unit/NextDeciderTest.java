/**
 *
 */
package com.manning.sbia.ch14.batch.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.test.MetaDataInstanceFactory;

import com.manning.sbia.ch14.batch.NextDecider;

/**
 * Unit with mock Spring Batch.
 *
 * @author bazoud
 *
 */
public class NextDeciderTest {
  @Test
  public void decideNext() {
    StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();
    JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution();
    stepExecution.setWriteCount(5);

    NextDecider decider = new NextDecider();
    FlowExecutionStatus status = decider.decide(jobExecution, stepExecution);

    assertEquals(status.getName(), "NEXT");
  }

  @Test
  public void decideCompleted() {
    StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();
    JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution();
    stepExecution.setWriteCount(0);

    NextDecider decider = new NextDecider();
    FlowExecutionStatus status = decider.decide(jobExecution, stepExecution);

    assertEquals(status, FlowExecutionStatus.COMPLETED);
  }
}
