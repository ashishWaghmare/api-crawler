package in.waghmare;

import io.parallec.core.ParallecResponseHandler;
import io.parallec.core.ParallelClient;
import io.parallec.core.ResponseOnSingleTask;

import java.util.Map;

/**
 * Created by ashishw on 29/4/16.
 */
public class HTTPClient {
    public static void main(String[] args) {
        ParallelClient pc = new ParallelClient();
        pc.prepareHttpGet("").setConcurrency(1000).setTargetHostsFromString("www.ebay.com").execute(
                new ParallecResponseHandler() {
                    @Override
                    public void onCompleted(ResponseOnSingleTask res, Map<String, Object> responseContext) {
                        System.out.println(res.getResponseContent());
                    }
                }
        );
    }
}
