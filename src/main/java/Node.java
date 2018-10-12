import org.infinispan.counter.EmbeddedCounterManagerFactory;
import org.infinispan.counter.api.CounterConfiguration;
import org.infinispan.counter.api.CounterManager;
import org.infinispan.counter.api.CounterType;
import org.infinispan.counter.api.SyncStrongCounter;
import org.infinispan.manager.DefaultCacheManager;

import java.io.IOException;

public class Node {


  private DefaultCacheManager cacheManager;
  private CounterManager counterManager;

  public Node init() throws IOException {
    cacheManager = new DefaultCacheManager("infinispan.xml");
    counterManager = EmbeddedCounterManagerFactory.asCounterManager(cacheManager);
    return this;
  }

  public SyncStrongCounter getCounter(String name) {
    if (!counterManager.isDefined(name)) {
      counterManager.defineCounter(name, CounterConfiguration.builder(CounterType.UNBOUNDED_STRONG).build());
    }
    return counterManager.getStrongCounter(name).sync();
  }
}
