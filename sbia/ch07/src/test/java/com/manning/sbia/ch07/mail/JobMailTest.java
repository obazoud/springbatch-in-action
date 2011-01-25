package com.manning.sbia.ch07.mail;

import static org.junit.Assert.assertEquals;

import javax.mail.internet.MimeMessage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

/**
 * @author bazoud
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JobMailTest {
  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;
  private GreenMail greenMail;

  @Test
  public void testXml() throws Exception {
    try {
      greenMail = new GreenMail(ServerSetupTest.ALL);
      greenMail.start();
      JobExecution exec = jobLauncherTestUtils.launchJob();
      Assert.assertEquals(BatchStatus.COMPLETED, exec.getStatus());

      greenMail.waitForIncomingEmail(5);
      MimeMessage[] received = greenMail.getReceivedMessages();
      assertEquals(5, received.length);
      assertEquals("Hello Mr. White\r\n", received[0].getContent());

    } finally {
      greenMail.stop();
    }
  }

  static class ServerSetupTest {
    public static int portOffset = 3000;

    public static final ServerSetup SMTP = new ServerSetup(25 + portOffset, null, ServerSetup.PROTOCOL_SMTP);
    public static final ServerSetup SMTPS = new ServerSetup(465 + portOffset, null, ServerSetup.PROTOCOL_SMTPS);
    public static final ServerSetup POP3 = new ServerSetup(110 + portOffset, null, ServerSetup.PROTOCOL_POP3);
    public static final ServerSetup POP3S = new ServerSetup(995 + portOffset, null, ServerSetup.PROTOCOL_POP3S);
    public static final ServerSetup IMAP = new ServerSetup(143 + portOffset, null, ServerSetup.PROTOCOL_IMAP);
    public static final ServerSetup IMAPS = new ServerSetup(993 + portOffset, null, ServerSetup.PROTOCOL_IMAPS);

    public static final ServerSetup[] SMTP_POP3 = new ServerSetup[] { SMTP, POP3 };
    public static final ServerSetup[] SMTP_IMAP = new ServerSetup[] { SMTP, IMAP };
    public static final ServerSetup[] SMTP_POP3_IMAP = new ServerSetup[] { SMTP, POP3, IMAP };

    public static final ServerSetup[] SMTPS_POP3S = new ServerSetup[] { SMTPS, POP3S };
    public static final ServerSetup[] SMTPS_POP3S_IMAPS = new ServerSetup[] { SMTPS, POP3S, IMAPS };
    public static final ServerSetup[] SMTPS_IMAPS = new ServerSetup[] { SMTPS, IMAPS };

    public static final ServerSetup[] ALL = new ServerSetup[] { SMTP, SMTPS, POP3, POP3S, IMAP, IMAPS };

  }
}
