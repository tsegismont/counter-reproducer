import org.infinispan.counter.api.SyncStrongCounter;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class CounterTest {

  private ExecutorService executorService = Executors.newFixedThreadPool(2);
  private Node node1;
  private Node node2;

  @Before
  public void setUp() throws Exception {
    node1 = new Node().init();
    node2 = new Node().init();
  }

  @Test
  public void testAddAndGet() {
    String name = "foo";

    SyncStrongCounter counter1 = node1.getCounter(name);
    assertEquals(1, counter1.addAndGet(1));
    assertEquals(1, counter1.getValue());

    SyncStrongCounter counter2 = node2.getCounter(name);
    assertEquals(2, counter2.addAndGet(1));
    assertEquals(2, counter2.getValue());
  }
}
