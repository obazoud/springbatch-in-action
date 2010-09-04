/**
 *
 */
package com.manning.sbia.ch14.batch;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import static com.manning.sbia.ch14.batch.ImportValidator.PARAM_INPUT_RESOURCE;
import static com.manning.sbia.ch14.batch.ImportValidator.PARAM_REPORT_RESOURCE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit with mock.
 *
 * @author bazoud
 *
 */
public class ImportValidatorTest {
  String PRODUCTS_PATH = "classpath:com/manning/sbia/ch14/input/products.txt";
  String STATISTIC_PATH = "file:./target/statistic.txt";
  private ResourceLoader resourceLoader;
  private Resource resource;
  private ImportValidator validator;

  @Before
  public void setUp() {
    resourceLoader = mock(ResourceLoader.class);
    resource = mock(Resource.class);
    when(resourceLoader.getResource(PRODUCTS_PATH)).thenReturn(resource);
    when(resource.exists()).thenReturn(true);
    validator = new ImportValidator();
  }

  @Test
  public void validate() throws JobParametersInvalidException {
    JobParameters jobParameters = new JobParametersBuilder() //
        .addString(PARAM_INPUT_RESOURCE, PRODUCTS_PATH) //
        .addString(PARAM_REPORT_RESOURCE, STATISTIC_PATH) //
        .toJobParameters();
    JobParameters spy = spy(jobParameters);
    validator.setResourceLoader(resourceLoader);
    validator.validate(spy);
    verify(spy, times(2)).getParameters();
    verify(spy, times(1)).getString(PARAM_INPUT_RESOURCE);
    verifyNoMoreInteractions(spy);
  }

  @Test(expected = JobParametersInvalidException.class)
  public void validateEmpty() throws JobParametersInvalidException {
    JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
    validator.setResourceLoader(resourceLoader);
    validator.validate(jobParameters);
  }

  @Test(expected = JobParametersInvalidException.class)
  public void validateMissingMaxPrice() throws JobParametersInvalidException {
    JobParameters jobParameters = new JobParametersBuilder() //
        .addString(PARAM_INPUT_RESOURCE, PRODUCTS_PATH) //
        .toJobParameters();
    validator.setResourceLoader(resourceLoader);
    validator.validate(jobParameters);
  }
}
